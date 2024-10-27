window.onload = function (){

    // 파일 중 1건 이라도 클릭 할 경우 파일 다운로드
    $(".fileSelectDiv").on('click', function(){
        // 클릭한 .fileSelectDiv 요소 내의 input[type="hidden"]을 찾습니다.
        let fileNo = $(this).find('input[type="hidden"]').val();

        console.log("fileNo : " + fileNo);

        $.ajax({
            type : 'get',           // 타입 (get, post, put 등등)
            url : '/board/downloadBoardFile.do',           // 요청할 서버url
            data: { fileNo: fileNo },
            xhrFields: {
                responseType: 'blob' // 파일을 Blob 형태로 받기
            },
            success: function(data, status, xhr) {
                var filename = ""; // 파일 이름 초기화
                var disposition = xhr.getResponseHeader('Content-Disposition');

                if (disposition && disposition.indexOf('attachment') !== -1) {
                    var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                    var matches = filenameRegex.exec(disposition);
                    if (matches != null && matches[1]) {
                        filename = matches[1].replace(/['"]/g, '');
                        filename = decodeURIComponent(filename); // 파일명을 디코딩하여 한글 처리
                    }
                }

                var blob = new Blob([data], { type: xhr.getResponseHeader('Content-Type') });
                var link = document.createElement('a');
                var url = window.URL.createObjectURL(blob);

                link.href = url;
                link.download = filename || 'downloadedFile'; // 파일 이름 설정 (없을 경우 'downloadedFile'로 설정)
                document.body.appendChild(link);
                link.click();

                // 다운로드 후 URL 및 링크 해제
                window.URL.revokeObjectURL(url);
                document.body.removeChild(link);
            },
            //에러일 경우 xmlHttp 객체를 받음
            error: function(err) {
                Swal.fire({
                  title: "요청 실패",
                  text: "조금 뒤 다시 시도해주세요",
                  icon: "error"
                });
            }
        })
    });

    // 모달 창을 닫았을 때 모달 내부의 값 초기화
    $('.modal').on('hidden.bs.modal', function (e) {
        $(this).find('input[type="text"], input[type="file"], textarea').val('');

        console.log('modal close');
    });

    // 신고 모달 창의 save 버튼 클릭 시 이벤트
    $("#addReportBtn").on("click", function(){
        let sendData = {
            "boardNo" : $("#boardNo").val(),
            "boardTypeNo" : $("#categoryNo").val(),
            "reportToUserNo" : $("#productUserNo").val(),
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

    // DB에 저장된 주소 불러오기
    let address = $("#address").val();

    console.log("address" + address);

    var mapContainer = document.getElementById('map'); // 지도를 표시할 div

    mapOption = {
        center: new kakao.maps.LatLng(36.9495, 127.9082), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

    // 지도 생성
    var map = new kakao.maps.Map(mapContainer, mapOption);

    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(address, function(result, status) {

        // 정상적으로 검색이 완료됐을때
         if (status === kakao.maps.services.Status.OK) {

            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 결과값으로 받은 위치를 마커로 표시
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });

            // 인포윈도우로 장소에 대한 설명을 표시
            var infowindow = new kakao.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:6px 0;">' + address + '</div>'
            });
            infowindow.open(map, marker);

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);
        }
    });

    // 찜하기 버튼 클릭 이벤트
    $(".heartBtn").on("click", function(){

        // '찜하기'를 위해 필요한 데이터
        // 사용자가 선택한 글의 번호
        // 사용자가 선택한 글의 게시판 유형 정보
        let sendData = {
            "no": $("#boardNo").val(),
            "type": $("#categoryNo").val()
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