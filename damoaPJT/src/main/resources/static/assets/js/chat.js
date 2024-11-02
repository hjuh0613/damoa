window.onload = function () {

    // 페이지 처음 열었을때 활성화 해야할 채팅방
    let selectRoomNo = $("#selectRoomNo").val();
    // 색상 변환
    $("#roomListDiv").find("#"+selectRoomNo).addClass("bg-primary");
    $("#roomListDiv").find("#"+selectRoomNo).addClass("bg-opacity-10");

    let chatName = $("#roomListDiv").find("#"+selectRoomNo).parent().find('.chatRoom').text().trim();
    if(chatName == '전체 채팅방'){
        $("#isPurchaseBtnTag").html('');
    }else{
        let tmpHtml = '<button id="isPurchase" class="btn btn-outline-secondary">확정</button>';
        $("#isPurchaseBtnTag").html('');
        $("#isPurchaseBtnTag").append(tmpHtml);
    }


    let socket = io('http://localhost:3000');

    // 서버로 자신의 정보 전송
    socket.emit("login", {
        userNo: $("#userNo").val(),             // 로그인한 사용자 no
        userNickname: $("#userNick").html(),    // 로그인한 사용자의 닉네임
        roomNo: $("#selectRoomNo").val()        // 활성화된 채팅방 번호
    });

    // 서버에서 과거 메시지를 받아 메시지 목록을 화면에 추가
    socket.on('previousMessages', function(messages){

        // 기존 내역 제거
        $("#chatContentArea").html('');

        messages.forEach(msg => {

            let html = '';

            // 내가 쓴 메시지
            if($("#userNo").val() == msg.userNo){
                html = '<div class="chat-me d-flex justify-content-end me-3">'
                + '<div class=" my-2">'
                + '<div class="d-flex justify-content-end">'
                + '<div class="border border-2 rounded-3 p-2 d-inline-block">'
                + '<div class="chatContent text-break">';
                if(msg.isFile){
                    html += '<img src="' + msg.msg + '" style="max-width: 500px;">';
                }else {
                    html += msg.msg;
                }
                html += '</div>'
                + '<div class="chatDate d-flex flex-row-reverse">'
                + '<div class="text-secondary">' + formatDate(msg.regDate) + '</div>'
                + '<div style="width: 30px; "></div>'
                + '</div>'
                + '</div>'
                + '</div>'
                + '</div>'
                + '</div>';
            }else{
                html = '<div class="chat-user my-2">'
                + '<div class="chatUser mb-1">'
                + '<strong>' + msg.userNickname + '</strong>'
                + '</div>'
                + '<div class="d-flex ">'
                + '<div style="width: 25px; ">' + '</div>'
                + '<div class="border border-2 rounded-3 p-2 d-inline-block">'
                + '<div class="chatContent text-break">';
                if(msg.isFile){
                    html += '<img src="' + msg.msg + '" style="max-width: 500px;">';
                }
                else {
                    html += msg.msg;
                }
                html += '</div>'
                + '<div class="chatDate d-flex flex-row-reverse">'
                + '<div class="text-secondary">' + formatDate(msg.regDate) + '</div>'
                + '<div style="width: 30px; "></div>'
                + '</div>'
                + '</div>'
                + '</div>'
                + '</div>';
            }

            $("#chatContentArea").append(html);

            // scroll 맨 아래로
            $("#chatContentArea").scrollTop($("#chatContentArea")[0].scrollHeight);
        });
    });

    // 서버로부터의 메시지가 수신되면
    socket.on("login", function(data){
        let html = '<div class="my-2">'
        + '<div class="d-flex justify-content-center">'
        + '<strong>' + data + '님이 접속하였습니다</strong>'
        + '</div>'
        + '</div>';

        $("#chatContentArea").append(html);

        // scroll 맨 아래로
        $("#chatContentArea").scrollTop($("#chatContentArea")[0].scrollHeight);
    });

    // 서버로부터 메시지가 수신되면
    socket.on("logout", function(data){
        let html = '<div class="my-2">'
        + '<div class="d-flex justify-content-center">'
        + '<strong>' + data + '님이 퇴장하였습니다</strong>'
        + '</div>'
        + '</div>';

        $("#chatContentArea").append(html);

        // scroll 맨 아래로
        $("#chatContentArea").scrollTop($("#chatContentArea")[0].scrollHeight);
    })

    // 채팅입력에서 엔터키 누를 경우 이벤트
    $("#chatSendContent").on('keyup', (key)=>{
        if(key.keyCode == 13){
            sendChat();
        }
    });

    // 채팅 전송 버튼 클릭
    $("#chatSendBtn").on('click', ()=>{
        sendChat();
    });

    // 채팅 전송 socket요청
    function sendChat(){
        let content = $("#chatSendContent").val();

        if(content){
            socket.emit('chat', {msg: content});

            $("#chatSendContent").val('');

            let html = '<div class="chat-me d-flex justify-content-end me-3">'
            + '<div class=" my-2">'
            + '<div class="d-flex justify-content-end">'
            + '<div class="border border-2 rounded-3 p-2 d-inline-block">'
            + '<div class="chatContent text-break">' + content + '</div>'
            + '<div class="chatDate d-flex flex-row-reverse">'
            + '<div class="text-secondary">' + formatDate(new Date()) + '</div>'
            + '<div style="width: 30px; "></div>'
            + '</div>'
            + '</div>'
            + '</div>'
            + '</div>'
            + '</div>';

            $("#chatContentArea").append(html);

            // scroll 맨 아래로
            $("#chatContentArea").scrollTop($("#chatContentArea")[0].scrollHeight);
        }else{
            alert('채팅창을 입력해주세요');
        }

        $("#chatSendContent").focus();
    }

    // 채팅 전송 socket 수신
    socket.on('chat', function(data){
        console.log(data);

        let html = '<div class="chat-user my-2">'
        + '<div class="chatUser mb-1">'
        + '<strong>' + data.from.userNickname + '</strong>'
        + '</div>'
        + '<div class="d-flex ">'
        + '<div style="width: 25px; ">' + '</div>'
        + '<div class="border border-2 rounded-3 p-2 d-inline-block">'
        + '<div class="chatContent text-break">' + data.msg + '</div>'
        + '<div class="chatDate d-flex flex-row-reverse">'
        + '<div class="text-secondary">' + formatDate(data.from.regDate) + '</div>'
        + '<div style="width: 30px; "></div>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '</div>';

        $("#chatContentArea").append(html);

        // scroll 맨 아래로
        $("#chatContentArea").scrollTop($("#chatContentArea")[0].scrollHeight);

    });

    // 채팅방 클릭했을 경우
    $(".room").on("click", function(){

        // 기존 방
        let old = $("#roomListDiv").find(".bg-primary").find(".chatRoomNo").val();

        // 색상 변환
        $("#roomListDiv").find(".bg-primary").removeClass("bg-primary");
        $("#roomListDiv").find(".bg-opacity-10").removeClass("bg-opacity-10");
        $(this).addClass("bg-primary");
        $(this).addClass("bg-opacity-10");

        // 현재 클릭된 `.room` 내부에 있는 `.chatRoomNo` input 태그의 값을 가져옴
        let chatRoomNo = $(this).find(".chatRoomNo").val();
        $("#selectRoomNo").val(chatRoomNo);
        console.log("Chat Room No: ", chatRoomNo);

        let chatName = $(this).find('.chatRoom').text().trim();
        if(chatName == '전체 채팅방'){
            $("#isPurchaseBtnTag").html('');
        }else{
            let tmpHtml = '<button id="isPurchase" class="btn btn-outline-secondary">확정</button>';
            $("#isPurchaseBtnTag").html('');
            $("#isPurchaseBtnTag").append(tmpHtml);
        }

        // client가 기존 방 접속을 끊고 새로운 방에 접속할 경우
        socket.emit("joinRoom", {
            userNo: $("#userNo").val(),             // 로그인한 사용자 no
            userNickname: $("#userNick").html(),    // 로그인한 사용자의 닉네임
            newRoom: chatRoomNo,                    // 새로 접속할 채팅방 번호
            oldRoom: old                            // 기존에 접속해 있던 채팅방 번호
        });

        // chatContentArea 내용 지우기
        $("#chatContentArea").html('');
    });

    function formatDate(dateString) {
        const date = new Date(dateString);

        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더함
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }

    // 파일 버튼 클릭
    $("#fileSendBtn").on("click", () =>{
        $("#fileInput").click();
    });

    // fileInput 값 변경 시 이벤트
    $("#fileInput").on("change", (event)=>{
        let selectedFile = event.target.files[0];
        if(selectedFile){
            const reader = new FileReader();

            reader.onload = function(event){
                let fileData = {
                    fileName: selectedFile.name,
                    file: event.target.result   // 파일 데이터를 base64로 읽음
                };

                console.log(fileData);

                socket.emit('file-upload', fileData);
            };

            reader.readAsArrayBuffer(selectedFile); // 파일을 이진 데이터로 읽음
        }
    });

    // 서버에서 파일 전송에 대한 결과 수신
    socket.on('file-upload-status', (data)=>{
        if(data.success){
            //alert('File upload successfully : ' + data.location);    // 업로드된 파일 URL 표시

            let html = '<div class="chat-me d-flex justify-content-end me-3">'
            + '<div class=" my-2">'
            + '<div class="d-flex justify-content-end">'
            + '<div class="border border-2 rounded-3 p-2 d-inline-block">'
            + '<div class="chatContent text-break">'
            +   '<img src="' + data.location + '" style="max-width: 500px;">'
            + '</div>'
            + '<div class="chatDate d-flex flex-row-reverse">'
            + '<div class="text-secondary">' + formatDate(new Date()) + '</div>'
            + '<div style="width: 30px; "></div>'
            + '</div>'
            + '</div>'
            + '</div>'
            + '</div>'
            + '</div>';

            $("#chatContentArea").append(html);

            // scroll 맨 아래로
            $("#chatContentArea").scrollTop($("#chatContentArea")[0].scrollHeight);

        } else {
            alert('File upload failed');
        }
    });

    // 서버에서 파일 전송에 대한 결과 수신
    socket.on('fileSend', (data)=>{
        let html = '<div class="chat-user my-2">'
        + '<div class="chatUser mb-1">'
        + '<strong>' + data.from.userNickname + '</strong>'
        + '</div>'
        + '<div class="d-flex ">'
        + '<div style="width: 25px; ">' + '</div>'
        + '<div class="border border-2 rounded-3 p-2 d-inline-block">'
        + '<div class="chatContent text-break">'
        + '<img src="' + data.msg + '" style="max-width: 500px;">'
        + '</div>'
        + '<div class="chatDate d-flex flex-row-reverse">'
        + '<div class="text-secondary">' + formatDate(data.from.regDate) + '</div>'
        + '<div style="width: 30px; "></div>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '</div>';

        $("#chatContentArea").append(html);

        // scroll 맨 아래로
        $("#chatContentArea").scrollTop($("#chatContentArea")[0].scrollHeight);
    });

    $(document).on("click","#isPurchase",function(){
        let sendData = {
            "chatRoomNo": $("#selectRoomNo").val()
        }

        // 첫번째 - selectRoomNo를 가지고 서버로 가서 서비스단에서 chatRoom 엔티티를 얻어서 boardNo와 categoryType을 얻는다
        // 두번째 - board에서 boardNo와 categoryNo를 가지고 board 테이블을 불러와서 isPurchase를 1로 바꿔준다
        $.ajax({
            url: "/isPurchase",                   // controller에 요청할 api 요청
            type: "post",                       // api 요청 타입
            contentType: "application/json",
            data: JSON.stringify(sendData),     // api 요청 시 전달되는 값
            dataType: "text",

            //정상 처리될 경우
            success: function(data) {
                console.log("요청 성공");
                console.log(data);

                document.getElementById("boardIsPurchase").value='';

                getBoardIsPurchase();

            },
            //에러일 경우 xmlHttp 객체를 받음
            error: function(err) {
                Swal.fire({
                    title: "요청 실패",
                    text: "조금 뒤 다시 시도해주세요",
                    icon: "error"
                });
            }
        });
    });

};