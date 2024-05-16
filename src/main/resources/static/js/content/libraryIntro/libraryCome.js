console.log("실행은 됩니까?")
// 지도 위도 구하는법
//구글쓰셈


let mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(35.5421094, 129.3382413), // 지도의 중심좌표
        level: 1 // 지도의 확대 레벨
    };

let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 마커가 표시될 위치입니다 
let markerPosition  = new kakao.maps.LatLng(35.5421094, 129.3382413); 

// 마커를 생성합니다
let marker = new kakao.maps.Marker({
    position: markerPosition
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);
