window.onload = function (){

    // 신고 모달 창의 save 버튼 클릭 시 이벤트
    $("#addReportBtn").on("click", function(){
        let sendData = {
                           "reportType" : $("#categoryNo").html(),
                           "reportToUserNo" : $("#userNo").val(),
                           "reportContent" : $("#modalContent").html()
                        };

        console.log(sendData);

        const formData = new FormData();
        formData.append(
            "sendData", new Blob([JSON.stringify(sendData)], { type: "application/json" })
        );
        formData.append("file", $('#modalFile')[0].files[0]);

        /*
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

                // 모달창 닫기 기능 구현

            },

            error: function(err) {
                Swal.fire({
                    title: "요청 실패",
                    text: "조금 뒤 다시 시도해주세요",
                    icon: "error"
                });
            }
        });
        */
    });
};