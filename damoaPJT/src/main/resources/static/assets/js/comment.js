window.onload = function (){

    // reivew.html 페이지가 로드됨과 동시에
    // 댓글 전제 조회 비동기 요청 실행

    // 현재 reivew_no
    let review_no = $("#review_no").val();

    console.log(review_no);

    $.ajax({
        type : 'get',           // 타입 (get, post, put 등등)
        url : '/commentList',           // 요청할 서버url
        data: { "review_no": review_no },
        success: function(data, status, xhr) {
            console.log(data);
        },
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

};