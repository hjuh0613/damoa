window.onload = function () {

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

    // 결과값으로 받은 위치를 마커로 표시
    var marker = new kakao.maps.Marker({
        map: map,
        position: ''
    });

    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(address, function(result, status) {

        // 정상적으로 검색이 완료됐을때
         if (status === kakao.maps.services.Status.OK) {

            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 결과값으로 받은 위치를 마커로 표시
            marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);
        }
    });

    // 지도에 클릭 이벤트를 등록합니다
    // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

        // 클릭한 위도, 경도 정보를 가져옵니다
        var latlng = mouseEvent.latLng;

        // 마커 위치를 클릭한 위치로 옮깁니다
        marker.setPosition(latlng);

        // 주소 변환 함수
        updateAddress(latlng);

    });

    // 주소 변환 함수
    function updateAddress(latlng) {
        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();

        // 좌표로 법정동 상세 주소 정보를 요청합니다
        geocoder.coord2Address(latlng.getLng(), latlng.getLat(),  function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                console.log("주소 정보 ==============");
                console.log(result);

                var address = result[0].address.address_name;

                $("#address").val(address);
            } else {
                console.error('주소 변환 실패:', status);
            }
        });
    }


}