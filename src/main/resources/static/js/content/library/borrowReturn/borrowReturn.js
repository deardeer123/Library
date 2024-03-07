function selectMemberInfo(){

    // 인풋 데이터 선택하기
    let inputData = document.querySelector('#inputData').value;
    
    // 그림 그릴 이용자 정보 태그 선택하기(userInfo)
    const userInfo = document.querySelector('#userInfo');
    const cardNum = document.querySelector('#card-num');
    const userName = document.querySelector('#user-name');
    const tel = document.querySelector('#tel');

    // 그림 그릴 대출반납 정보 태그 선택하기(Info)
    const brInfo = document.querySelector('#brInfo');

    // 컨트롤러로 보낼 데이터 객체로 만들기
    let requestDataVO = {
        cardNum : !isNaN(parseInt(inputData, 10)) ? parseInt(inputData, 10) : null
        , bookCode : inputData
    }

    console.log(requestDataVO)
    
    fetch('/bookAdmin/selectBorrowInfo', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: JSON.stringify(requestDataVO)
    })
    .then((response) => {
        return response.json(); //나머지 경우에 사용
    })
    //fetch 통신 후 실행 영역
    .then((data) => {//data -> controller에서 리턴되는 데이터!

        console.log(data);

        userInfo.innerHTML = '';
        
        let str1 = '';

        str1= `
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

        userInfo.insertAdjacentHTML('afterbegin', str1);

        brInfo.innerHTML = '';

        let str2 = '';

        if(data.bookCode == null){

            str2 +=`
        <div class="row">
            <div class="col" id="borrowInfo">
                자관대출 (0/5)        
            </div>
        </div>`;

        if(data.bookBorrowList.length == 1 && data.bookBorrowList[0].bookCode == null){
            str2 +=   `<div class="row tbody">
                <div class="col text-center">
                    대출 내역이 없습니다.
                </div>
            </div>`; 
        } else {
            str2 += `<div class="row tbody">`;
            data.bookBorrowList.forEach((bookBorrowInfo) => {
                str2+=`
                <div class="col">
                    표지
                </div>
                <div class="col">
                    ${bookBorrowInfo.bookCode}
                </div>
                <div class="col">
                    ${bookBorrowInfo.libraryBookVO.bookTitle}
                </div>
                <div class="col">
                    청구기호
                </div>
                <div class="col">
                    ${bookBorrowInfo.borrowDate}
                </div>
                <div class="col">
                    ${bookBorrowInfo.exReturnDate}
                </div>
                <div class="col">
                    대출처리자
                </div>`;
            });               
            str2 += `</div>`;   
        }                     

        str2 += `
        <div class="row">
            <div class="col" id="returnInfo">
                반납 (0)        
            </div>
        </div>
        `;
        if(data.bookReturnList.length == 1 && data.bookReturnList[0].bookCode == null){
            str2 +=   `<div class="row tbody">
                <div class="col text-center">
                    반납 내역이 없습니다.
                </div>
            </div>`; 
        } else {
        str2 += `<div class="row tbody">`;
            data.bookReturnList.forEach((bookReturnInfo) => {
                str2 += `<div class="col">
                            표지
                        </div>
                        <div class="col">
                            등록번호
                        </div>
                        <div class="col">
                            서명
                        </div>
                        <div class="col">
                            청구기호
                        </div>
                        <div class="col">
                            대출일
                        </div>
                        <div class="col">
                            반납일(연체일)
                        </div>
                        <div class="col">
                            반납처리자
                        </div>`;
            });
            
            `</div>`;   
        }              
        str2 += `
        <div class="row">
            <div class="col" id="bookCodeInfo">
                예약 (예약가능 건수 : 0)  예약 내역은 db쪽으로 더 생각해 봐야 할 것 같아서 다음에 다시 올 거예요.      
            </div>
        </div>
        `;
        str2 +=   `<div class="row tbody">
                <div class="col text-center">
                    
                </div>
            </div>`; 
        str2 += `<div class="row tbody">
            <div class="col">
                표지
            </div>
            <div class="col">
                등록번호
            </div>
            <div class="col">
                서명
            </div>
            <div class="col">
                청구기호
            </div>
            <div class="col">
                대출일
            </div>
            <div class="col">
                반납일(연체일)
            </div>
            <div class="col">
                반납처리자
            </div>
        </div>`;   
        str2 += `
        <div class="row">
            <div class="col" id="returnInfo">
                당일처리내역 여기도 조회, 대출, 반납 모두 떠야해서 이용자 먼저 하고 올게요.      
            </div>
        </div>
        `;

        }

        if(data.bookCode != null){
            str2 +=`
        <div class="row">
            <div class="col" id="borrowInfo">
                자관대출 (0/5)        
            </div>
        </div>`;

        if(data.bookBorrowList.length == 1 && data.bookBorrowList[0].bookCode == null){
            str2 +=   `<div class="row tbody">
                <div class="col text-center">
                    대출 내역이 없습니다.
                </div>
            </div>`; 
        } else {
            str2 += `<div class="row tbody">`;
            data.bookBorrowList.forEach((bookBorrowInfo) => {
                str2+=`
                <div class="col">
                    표지
                </div>
                <div class="col">
                    ${bookBorrowInfo.bookCode}
                </div>
                <div class="col">
                    ${bookBorrowInfo.libraryBookVO.bookTitle}
                </div>
                <div class="col">
                    청구기호
                </div>
                <div class="col">
                    ${bookBorrowInfo.borrowDate}
                </div>
                <div class="col">
                    ${bookBorrowInfo.exReturnDate}
                </div>
                <div class="col">
                    대출처리자
                </div>`;
            });               
            str2 += `</div>`;   
        }                     

        str2 += `
        <div class="row">
            <div class="col" id="returnInfo">
                반납 (0)        
            </div>
        </div>
        `;
        if(data.bookReturnList.length == 1 && data.bookReturnList[0].bookCode == null){
            str2 +=   `<div class="row tbody">
                <div class="col text-center">
                    반납 내역이 없습니다.
                </div>
            </div>`; 
        } else {
        str2 += `<div class="row tbody">`;
            data.bookReturnList.forEach((bookReturnInfo) => {
                str2 += `<div class="col">
                            표지
                        </div>
                        <div class="col">
                            등록번호
                        </div>
                        <div class="col">
                            서명
                        </div>
                        <div class="col">
                            청구기호
                        </div>
                        <div class="col">
                            대출일
                        </div>
                        <div class="col">
                            반납일(연체일)
                        </div>
                        <div class="col">
                            반납처리자
                        </div>`;
            });
            
            `</div>`;   
        }              
        str2 += `
        <div class="row">
            <div class="col" id="bookCodeInfo">
                예약 (예약가능 건수 : 0)  예약 내역은 db쪽으로 더 생각해 봐야 할 것 같아서 다음에 다시 올 거예요.      
            </div>
        </div>
        `;
        str2 +=   `<div class="row tbody">
                <div class="col text-center">
                    
                </div>
            </div>`; 
        str2 += `<div class="row tbody">
            <div class="col">
                표지
            </div>
            <div class="col">
                등록번호
            </div>
            <div class="col">
                서명
            </div>
            <div class="col">
                청구기호
            </div>
            <div class="col">
                대출일
            </div>
            <div class="col">
                반납일(연체일)
            </div>
            <div class="col">
                반납처리자
            </div>
        </div>`;   
        str2 += `
        <div class="row">
            <div class="col" id="returnInfo">
                당일처리내역 여기도 조회, 대출, 반납 모두 떠야해서 이용자 먼저 하고 올게요.      
            </div>
        </div>
        `;
        }

        brInfo.insertAdjacentHTML("afterbegin", str2);
    })
    //fetch 통신 실패 시 실행 영역
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
}