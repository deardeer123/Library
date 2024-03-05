function selectMemberInfo(){

    // 인풋 데이터 선택하기
    const inputData = document.querySelector('#inputData').value;
    
    // 그림 그릴 이용자 정보 태그 선택하기
    const userInfo = document.querySelector('#userInfo');
    const cardNum = document.querySelector('#card-num');
    const userName = document.querySelector('#user-name');
    const tel = document.querySelector('#tel');

    guessType();
    
    fetch('/bookAdmin/selectBorrowInfo', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: JSON.stringify({
           // 데이터명 : 데이터값
           inputData : inputData
        })
    })
    .then((response) => {
        return response.json(); //나머지 경우에 사용
    })
    //fetch 통신 후 실행 영역
    .then((data) => {//data -> controller에서 리턴되는 데이터!

        console.log(data);

        userInfo.innerHTML = '';
        
        let str = '';

        str= `
        <div class="row">
            <div class="col" id="cardNum">
                ${data.cardNum}
            </div>
        </div>
        <div class="row">
            <div class="col" id="userName">
                ${data.userName}
            </div>
        </div>
        <div class="row">
            <div class="col" id="tel">
                ${data.userTel}
            </div>
        </div>
        `;

        userInfo.insertAdjacentHTML('afterbegin', str);
    })
    //fetch 통신 실패 시 실행 영역
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
}