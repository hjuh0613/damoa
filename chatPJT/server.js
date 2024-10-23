//npm i express
//npm i http
//npm i socket.io
//npm i path
//npm i mongodb
//npm i mongoose
//npm i mongoose-sequence
//npm i aws-sdk
//npm install @aws-sdk/client-s3

// 서버실행 ; node server.js

const express = require('express');
const cors = require('cors');
const path = require('path');

require('dotenv').config();
const mongoose = require('mongoose');
const Message = require('./models/Message');  // 메시지 모델 가져오기

const { S3Client, PutObjectCommand } = require('@aws-sdk/client-s3');

const app = express();

const { PORT, MONGO_URI, AWS_SECRET_KEY, AWS_ACCESS, BUCKET_NAME, REGION } = process.env;

app.use(cors());
// 정적 파일 제공 (chat.html 파일을 / 경로로 열어줌)
app.use(express.static(path.join(__dirname, 'assets')));
app.use(express.static(path.join(__dirname, 'forms')));
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

// 몽고 db 연결
mongoose
    .connect(MONGO_URI, { useNewUrlParser: true, useUnifiedTopology: true })
    .then(() => console.log('Successfully connected to mongodb'))
    .catch(e => console.error(e));

// http 서버 생성
const server = require('http').createServer(app);
// http 서버를 socket.io 서버로 전환
const io = require('socket.io')(server, {
    cors: {
        origin: "http://localhost:8080"
    }
});

// '/' 경로로 요청이 들어오면 chat.html 파일을 응답
/*
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'chat.html'));
});
*/

// AWS S3 설정
const s3 = new S3Client({
    region: REGION,
    credentials: {
        accessKeyId: AWS_ACCESS,
        secretAccessKey: AWS_SECRET_KEY
    }
});

// S3에 파일 업로드 함수 
const uploadFileToS3 = async (fileName, fileData) => {

    let params = {
      Bucket: BUCKET_NAME,                      // 버킷 이름
      Key: `uploads/${fileName}`,               // 파일 경로 및 이름
      Body: fileData                            // 파일 데이터
    };
  
    try{

        let command = new PutObjectCommand(params);
        let data = await s3.send(command);
        return `https://${BUCKET_NAME}.s3.${REGION}.amazonaws.com/uploads/${fileName}`;

    } catch(err) {
        console.log("s3에 파일을 업로드하는 도중 에러 발생");
        throw error;
    }
};

// client가 socket.io 서버에 접속할 경우 connection 이벤트 발생
io.on('connection', (socket)=>{
    console.log("someone connected")
    
    // 접속한 클라이언트의 정보가 수신되면
    socket.on('login', async function(data){

        console.log('사용자 로그인 name: ' + data.userNo + " userNickname: " + data.userNickname + " roomNo: " + data.roomNo);

        // socket에 클라이언트 정보 저장
        socket.userNo = data.userNo;
        socket.userNickname = data.userNickname;
        socket.roomNo = data.roomNo;

        // 특정 room에 join
        socket.join(socket.roomNo);

        try {
            // MongoDB에서 roomNo가 data.roomNo와 일치하는 메시지를 조회
            let messageList = await Message.find({roomNo: data.roomNo}).sort({ regDate: 1 });

            // 조회된 메시지를 로그인한 유저에게만 전송
            socket.emit('previousMessages', messageList);
        } catch (error) {
            console.error('Error during room search or message retrieval:', error);
        }

        // 특정 room에 접속된 모든 클라이언트에게 메시지 전송
        socket.to(socket.roomNo).emit('login', data.userNickname);
    });

    // client가 기존 방 접속을 끊고 새로운 방에 접속할 경우
    socket.on('joinRoom', async (data)=>{

        // 현재 유저가 속한 방 목록
        const roomList = Array.from(socket.rooms);

        roomList.forEach((room) => {
            // 기본적으로 유저는 자신의 소켓 ID 방에 속하므로 이는 제외
            if(room !== socket.id){
                
                // 특정 room에 접속된 모든 client에게 메시지 전달
                socket.to(data.oldRoom).emit('logout', data.userNickname);

                socket.leave(room);
                console.log(data.userNickname, '이 ' + room + " 을 떠납니다.");
            }
        });

        // socket에 client 정보 저장
        socket.roomNo = data.newRoom;

        socket.join(socket.roomNo);
        console.log('사용자 로그인 name: ' + socket.userNo + " userNickname: " + socket.userNickname + " roomNo: " + socket.roomNo);

        try {
            // MongoDB에서 roomNo가 data.roomNo와 일치하는 메시지를 조회
            let messageList = await Message.find({roomNo: socket.roomNo}).sort({ regDate: 1 });

            // 조회된 메시지를 로그인한 유저에게만 전송
            socket.emit('previousMessages', messageList);
        } catch (error) {
            console.error('Error during room search or message retrieval:', error);
        }

        // 특정 room에 접속된 모든 client에게 메시지 전달
        socket.to(socket.roomNo).emit('login', socket.userNickname);

    });

    // client가 전송한 'chat' 수신
    socket.on('chat', async function(data){
        console.log('message from ' + socket.userNo + " " + socket.userNickname);

        let msg = {
            from: {
                userNo: socket.userNo,
                userNickname: socket.userNickname,
                roomNo: socket.roomNo,
                regDate: new Date()
            },
            msg: data.msg
        };

        // 메시지를 전송한 클라이언트 제외 모든 클라이언트에게 메시지 전송
        socket.to(socket.roomNo).emit('chat', msg);

        // 메시지 DB 저장
        try{
            let message = new Message({
                userNo: socket.userNo,
                userNickname: socket.userNickname,
                roomNo: socket.roomNo,
                isFile: false,
                msg: data.msg
            });

            await message.save();
            console.log('Message saved to DB');
        } catch (error) {
            console.error('Error saving message: ', error);
        }
    });

    // client 보낸 'file-upload' 이벤트 수신
    socket.on('file-upload', async (fileData) => {
        
        try{
            let buffer = Buffer.from(fileData.file); // 파일 데이터 변환
            let fileName = Math.floor(10000 + Math.random() * 90000) + "_" + fileData.fileName;

            // S3에 파일 업로드
            let fileUrl = await uploadFileToS3(fileName, buffer);
            console.log('File uploaded successfully to S3:', fileUrl);

            // 메시지 DB 저장
            try{
                let message = new Message({
                    userNo: socket.userNo,
                    userNickname: socket.userNickname,
                    roomNo: socket.roomNo,
                    isFile: true,
                    msg: fileUrl
                });

                await message.save();
                console.log('Message saved to DB');
            } catch (error) {
                console.error('Error saving message: ', error);
            }

            // 업로드 성공 메시지 전송
            socket.emit('file-upload-status', { success: true, location: fileUrl });

            // 메시지를 전송한 클라이언트 제외 모든 클라이언트에게 메시지 전송
            let msg = {
                from: {
                    userNo: socket.userNo,
                    userNickname: socket.userNickname,
                    roomNo: socket.roomNo,
                    regDate: new Date()
                },
                msg: fileUrl
            };
            socket.to(socket.roomNo).emit('fileSend', msg);
        } catch (err) {
            console.error('Error uploading file to S3:', err);
            socket.emit('file-upload-status', { success: false });
        }
        
    });

    socket.on('forceDisconnect', () => {
        socket.disconnect();
    })

    socket.on('disconnect', () => {
        console.log('user disconnected : ' + socket.id);

        // 특정 room에 접속된 모든 client에게 메시지 전달
        socket.to(socket.roomNo).emit('logout', socket.userNickname);
    })
});

server.listen(PORT, () => {
    console.log('서버 실행중 ...');
});