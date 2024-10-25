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

                    html += '<input class="commentNo" type="hidden" value="' + obj.commentNo + '" />'

                    if(userNo != obj.userNo){
                        html += '<div class="commentContent col-9">' + obj.commentContent + '</div>'
                            + '<div class="col-2">' + obj.commentDate + '<br>' + obj.userNickname + '</div>'
                            + '<div class="col-1">'
                            + '<button class="m-1 btn btn-outline-danger reportCommentBtn">신고</button>'
                            + '<button class="m-1 btn btn-outline-danger addMoreCommentBtn">답글</button>'
                            + '</div>';
                            + '</div>';
                    }else {
                        html += '<div class="commentContent col-9">' + obj.commentContent + '</div>'
                            + '<div class="col-2">' + obj.commentDate + '<br>' + obj.userNickname + '</div>'
                            + '<div class="col-1">'
                            + '<button class="m-1 btn btn-outline-danger deleteCommentBtn">삭제</button>'
                            + '<button class="m-1 btn btn-outline-danger addMoreCommentBtn">답글</button>'
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

    // 대댓글 작성 시작



    // 대댓글 작성 종료

    // 댓글 삭제 시작

    $(document).on("click",".deleteCommentBtn",function(){

        let sendData = {
            "commentNo": $(this).parent().parent().find('.commentNo').val(),
            "commentContent": '삭제된 댓글입니다.'
        };

        console.log(sendData);

        $.ajax({
            url: "/deleteComment",
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

    // 댓글 삭제 종료

    // 리뷰 파일 정보 획득
    // 파일 정보 확인

    $.ajax({
        url: '/findReviewFile?reviewNo=' + $("#review_no").val(),
        type: "get",
        success: function(data){
            console.log(data);

            $("#fileList").html('');

            for(let i=0; i<data.length; i++){
                let html = '<div class="filetag m-3 p-2 border rounded">';
                html += data[i].originalName;
                html += '<input class="fileNo" type="hidden" value="' + data[i].fileNo + '" />';
                html += '</div>';

                $("#fileList").append(html);
            }
        },
        error: function(err) {
            Swal.fire({
                title: "요청 실패",
                text: "조금 뒤 다시 시도해주세요",
                icon: "error"
            });
        }
    });

    // 파일 클릭시 다운로드 되도록
    $(document).on('click', '.filetag', (event)=>{
        let tag = event.target;
        let fileNo = $(tag).find('.fileNo').val();

        // 서버에서 파일 다운로드 처리 후 파일 URL을 응답으로 제공하지 않고,
        // 다운로드 요청을 유도하는 것이므로 파일 다운로드를 직접 트리거.
        window.location.href = '/board/downloadBoardFile.do?fileNo=' + fileNo;
    });
};
