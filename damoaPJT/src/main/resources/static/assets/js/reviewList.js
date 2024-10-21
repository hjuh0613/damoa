window.onload = function (){

   $(".heartBtn").on("click", function(){

        // '찜하기'를 위해 필요한 데이터
        // 사용자가 선택한 글의 번호
        // 사용자가 선택한 글의 게시판 유형 정보
        let sendDate = {
            "no": $(this).parent().parent().find(".reviewNo").html(),
            "type": '후기게시판'
        };

        console.log(sendDate);

        // 비동기 작업 시작~
        $.ajax({
            url: "/addHeart",                   // controller에 요청할 api 요청
            type: "post",                       // api 요청 타입
            contentType: "application/json",
            data: JSON.stringify(sendDate),     // api 요청 시 전달되는 값
            dataType: "text",                   // 요청 후 서버가 화면(html)에게 주는 데이터의 형식(text(json,html,txt,script), xml)

            //정상 처리될 경우
            success: function(data) {
                console.log("요청 성공");
                console.log(data)
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