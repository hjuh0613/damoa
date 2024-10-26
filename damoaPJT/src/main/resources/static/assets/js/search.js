document.addEventListener("DOMContentLoaded", () => {



    // 비동기 작업 시작~
    $.ajax({
        url: "/chartMake?search=" + $("#search").val(),                   // controller에 요청할 api 요청
        type: "get",                       // api 요청 타입
        contentType: "application/json",
        dataType: "json",                   // 요청 후 서버가 화면(html)에게 주는 데이터의 형식(text(json,html,txt,script), xml)
        //정상 처리될 경우
        success: function(result) {
            console.log("요청 성공");

            const maxMonth = Object.values(result.maxMonth);
            const avgMonth = Object.values(result.avgMonth);
            const minMonth = Object.values(result.minMonth);
            const x = Object.values(result.xMonth);

            console.log(maxMonth);
            console.log(avgMonth);
            console.log(minMonth);
            console.log(x);

            console.log(Array.isArray(maxMonth));

            new ApexCharts(document.querySelector("#lineChart"), {
                series: [{
                    name: "최고가",
                    data: maxMonth
                }, {
                    name: '평균가 ',
                    data: avgMonth
                }, {
                    name: '평균가 ',
                    data: minMonth
                }],
                chart: {
                    height: 200,
                    type: 'line',
                    zoom: {
                        enabled: false
                    }
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    curve: 'straight'
                },
                grid: {
                    row: {
                        colors: ['#f3f3f3', 'transparent'],
                        opacity: 0.5
                    },
                },
                xaxis: {
                    labels: {
                        format: 'YY-MM',
                    },
                    categories: x,
                }
            }).render();
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