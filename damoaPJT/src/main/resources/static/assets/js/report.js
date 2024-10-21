window.onload = function () {

    $(".reportDetailBtn").on("click", function() {

        let reportDetail = {
            "reportNo" : $(this).parent().parent().find(".reportNo").html(),
            "reportContent" : $(this).parent().parent().find(".reportContent").html()
        };

        console.log(reportDetail);

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