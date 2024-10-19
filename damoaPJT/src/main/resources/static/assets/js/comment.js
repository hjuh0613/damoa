window.onload = function () {

    // 사용자 정보
    let userNo = $("#userNo").val();

    // 댓글 전체 조회 시작
    getCommentList();

    function getCommentList(){
        // 현재 review_no
        let review_no = $("#review_no").val(); // review_no를 가져옵니다.

        $.ajax({
            type: 'get',
            url: '/commentList',
            data: { "review_no": review_no },
            success: function(data) {

                console.log(data);

                // 댓글을 표시할 요소를 선택합니다.
                let commentContainer = $("#commentList");

                // 기존 댓글 삭제 (선택 사항)
                commentContainer.empty();

                // 댓글을 DOM에 추가합니다.
                $.each(data, function(index, obj) {

                    let html = '';

                    if(obj.parentCommentNo == '' || obj.parentCommentNo == null){
                        html = '<div class="alert alert-secondary row">'
                    } else {
                        html = '<div class="alert alert-secondary row ms-5">'
                    }

                    if(userNo != obj.userNo){
                        html += '<div class="col-10">' + obj.commentContent + '</div>'
                            + '<div class="col-2">' + obj.commentDate + '<br>' + obj.userNickname + '</div>'
                            + '</div>';
                    }else {
                        html += '<div class="col-9">' + obj.commentContent + '</div>'
                            + '<div class="col-2">' + obj.commentDate + '<br>' + obj.userNickname + '</div>'
                            + '<div class="col-1">'
                            + '<button class="m-1 btn btn-outline-primary updateCommentBtn">수정</button>'
                            + '<button class="m-1 btn btn-outline-primary deleteCommentBtn">삭제</button>'
                            + '</div>';
                            + '</div>';
                    }



                    commentContainer.append(html);
                });
            },
            error: function(err) {
                console.error("댓글 조회 실패:", err);
                Swal.fire({
                    title: "요청 실패",
                    text: "조금 뒤 다시 시도해주세요",
                    icon: "error"
                });
            }
        });
    }

    // 댓글 전체 조회 종료

    // 댓글 작성 시작
    $("#AddCommentBtn").on("click", function() {
        console.log('123123');

        let sendData = {
            "reviewNo": $("#review_no").val(),
            "commentContent": $("#commentContent").val()
        };

        console.log(sendData);

        $.ajax({
            url: "/addComment",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(sendData),     // api 요청 시 전달되는 값
            dataType: "text",
            //정상 처리될 경우
            success: function(data) {
                console.log("요청 성공");
                console.log(data);

                document.getElementById("commentContent").value='';

                getCommentList();

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

    // 댓글 작성 종료
};
