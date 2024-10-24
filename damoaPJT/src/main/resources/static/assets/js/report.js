window.onload = function () {

    // 상세보기 버튼 클릭 이벤트
    $(".reportDetailBtn").on("click", function() {

        let reportDetail = {
            "reportNo" : $(this).parent().parent().find(".reportNo").html(),
            "reportContent" : $(this).parent().parent().find(".reportContent").html()
        };

        console.log(reportDetail);

        // 파일 정보 확인
        $.ajax({
            url: '/findFile?reportNo=' + reportDetail.reportNo,
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
        })

        // 파일 클릭시 다운로드 되도록
        $(document).on('click', '.filetag', (event)=>{
            let tag = event.target;
            let fileNo = $(tag).find('.fileNo').val();

            // 서버에서 파일 다운로드 처리 후 파일 URL을 응답으로 제공하지 않고,
            // 다운로드 요청을 유도하는 것이므로 파일 다운로드를 직접 트리거.
            window.location.href = '/board/downloadBoardFile.do?fileNo=' + fileNo;
        });

        $("#reportModalContent").html(reportDetail.reportContent);

    });

    $("#removeUserBtn").on("click", function () {
        let removeUser = {
            "toUserNickname" : $(this).parent().parent().find(".toUserNickname").html()
        };

        $.ajax({
            url: "/deleteUser",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(removeUser),
            dataType: "text",

            success: function(data) {
                console.log("요청 성공");
                console.log(data)
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
};