window.onload = function () {

    // 페이지 처음 열었을때 활성화 해야할 채팅방
    let selectRoomNo = $("#selectRoomNo").val();
    // 색상 변환
    $("#roomListDiv").find("#"+selectRoomNo).addClass("bg-primary");
    $("#roomListDiv").find("#"+selectRoomNo).addClass("bg-opacity-10");

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
                + '<div class="chatContent text-break">' + msg.msg + '</div>'
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
                + '<div class="chatContent text-break">' + msg.msg + '</div>'
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
        console.log("Chat Room No: ", chatRoomNo);

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

};