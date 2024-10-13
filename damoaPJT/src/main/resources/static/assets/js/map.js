window.onload = function () {
    var mapContainer = document.getElementById('map'); // 지도를 표시할 div

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {

            var lat = position.coords.latitude; // 위도
            var lon = position.coords.longitude; // 경도

            // 사용자의 현재 위치를 지도의 중심으로 설정
            var locPosition = new kakao.maps.LatLng(lat, lon);

            mapOption = {
                center: locPosition, // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };

            // 지도 생성
            var map = new kakao.maps.Map(mapContainer, mapOption);

            // 지도를 클릭한 위치에 표출할 마커입니다
            var marker = new kakao.maps.Marker({
                // 지도 중심좌표에 마커를 생성합니다
                position: map.getCenter()
            });

            // 지도에 마커를 표시합니다
            marker.setMap(map);

            // 지도에 클릭 이벤트를 등록합니다
            // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
            kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

                // 클릭한 위도, 경도 정보를 가져옵니다
                var latlng = mouseEvent.latLng;

                // 마커 위치를 클릭한 위치로 옮깁니다
                marker.setPosition(latlng);

            });
        });
    } else {
        // 브라우저가 geolocation을 지원하지 않을 때 기본 위치 설정

        var defaultPosition = new kakao.maps.LatLng(33.450701, 126.570667); // 기본 좌표

        var mapOption = {
            center: defaultPosition,
            level: 3
        };

        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        var marker = new kakao.maps.Marker({
            position: defaultPosition
        });

        marker.setMap(map);

        kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
            var latlng = mouseEvent.latLng;
            marker.setPosition(latlng);
        });
    }
}