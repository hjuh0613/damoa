window.onload = function () {

    $("#reportDetailBtn").on("click", function() {

        let reportDetail = {
            "reportNo" : $(this).parent().parent().find(".reportNo").html(),
            "reportContent" : $(this).parent().parent().find(".reportContent").html()
        };

        $.ajax({
            type: 'get',
            url: '/report',
            data: { "reportNo": report_no },
            success: function(data) {

                // 신고내용을 표시할 요소를 선택합니다.
                let reportDetail = $("#commentList");
            }
        })


    });

};