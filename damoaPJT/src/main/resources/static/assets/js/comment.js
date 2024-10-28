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
                            + '</div>';
                            + '</div>';
                    }else {
                        html += '<div class="commentContent col-9">' + obj.commentContent + '</div>'
                            + '<div class="col-2">' + obj.commentDate + '<br>' + obj.userNickname + '</div>'
                            + '<div class="col-1">'
                            + '<button class="m-1 btn btn-outline-danger deleteCommentBtn">삭제</button>'
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

    // 신고 시작
    // 모달 창을 닫았을 때 모달 내부의 값 초기화
    $('.modal').on('hidden.bs.modal', function (e) {
        $(this).find('input[type="text"], input[type="file"], textarea').val('');

        console.log('modal close');
    });

    // 신고 모달 창의 save 버튼 클릭 시 이벤트
    $("#addReportBtn").on("click", function(){
        let sendData = {
            "boardNo" : $("#review_no").val(),
            "boardTypeNo" : 7,
            "reportToUserNo" : $("#user_no").val(),
            "reportContent" : $("#modalContent").val()
        };

        console.log(sendData);

        const formData = new FormData();
        formData.append(
            "sendData", new Blob([JSON.stringify(sendData)], { type: "application/json" })
        );
        formData.append("file", $('#modalFile')[0].files[0]);


        $.ajax({
            url: "/addReport",
            type: "post",
            contentType: "multipart/form-data",
            data: formData,
            contentType: false,
            processData: false,
            dataType: "text",

            success: function(data) {
                console.log("요청 성공");
                console.log(data)

                Swal.fire({
                    title: "신고 완료",
                    text: "신고가 완료되었습니다.",
                    icon: "success"
                });
            },

            error: function(err) {
                Swal.fire({
                    title: "요청 실패",
                    text: "조금 뒤 다시 시도해주세요",
                    icon: "error"
                });
            }
        });
    });
    // 신고 종료

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

    // 찜하기 버튼 클릭 이벤트
    $(".heartBtn").on("click", function(){

        // '찜하기'를 위해 필요한 데이터
        // 사용자가 선택한 글의 번호
        // 사용자가 선택한 글의 게시판 유형 정보
        let sendData = {
            "no": $("#review_no").val(),
            "type": '7'
        };

        console.log(sendData);

        // 비동기 작업 시작~
        $.ajax({
            url: "/addHeart",                   // controller에 요청할 api 요청
            type: "post",                       // api 요청 타입
            contentType: "application/json",
            data: JSON.stringify(sendData),     // api 요청 시 전달되는 값
            dataType: "text",                   // 요청 후 서버가 화면(html)에게 주는 데이터의 형식(text(json,html,txt,script), xml)

            //정상 처리될 경우
            success: function(data) {
                console.log("요청 성공");
                console.log(data);

                if(data == "add"){
                    $(".heartBtn").removeClass("btn-outline-primary");
                    $(".heartBtn").addClass("btn-outline-danger");
                }else{
                    $(".heartBtn").addClass("btn-outline-primary");
                    $(".heartBtn").removeClass("btn-outline-danger");
                }
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
