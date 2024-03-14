// 현재 진행 상황 : 같은 태그로 계속 바뀌는 값이 들어가서 cardNum과 userCord가 바뀜. 따라서 bookCode가 들어오면 두 정보가 0으로 뜸.(userCode를 고정 할 방법이 필요.)


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
    const borrowHeaderTd = document.querySelector('#borrow-header').children[0];
    const borrowTr = document.querySelector('#borrow-tr');
    const returnHeaderTd = document.querySelector('#return-header').children[0];
    const returnTr = document.querySelector('#return-tr');
    const reserveHeaderTd = document.querySelector('#reserve-header').children[0];
    const reserveTr = document.querySelector('#reserve-tr');
    const history = document.querySelector('#history');

    const inputValue = inputData;
    const selectedCardNum = document.querySelector('input[name="selectedCardNum"]').value


    fetch('/bookAdmin/selectBorrowInfo', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: JSON.stringify({
            'inputValue' : inputValue,
            'selectedCardNum' : selectedCardNum
        })
    })
    .then((response) => {
        return response.json(); //나머지 경우에 사용
    })
    //fetch 통신 후 실행 영역
    .then((data) => {//data -> controller에서 리턴되는 데이터!

        console.log(data);

        if(data.userCode == 0){
            alert('회원번호 혹은 책번호가 정확하지 않습니다.\n다시 입력하세요.');
            return ;
        }

        //selectedCardNum 값을 이전에 입력한 값으로 저장
        //조회가 됐으면 조회된 데이터, 아니면 0이 들어감
        document.querySelector('input[name="selectedCardNum"]').value = data.cardNum;

        userInfo.innerHTML = '';
        
        let str1 = '';

        //조회한 데이터가 있을 때만 다시 그림
        if(data.cardNum != 0){

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

        borrowHeaderTd.innerHTML = '';
        borrowTr.innerHTML = '';
        returnHeaderTd.innerHTML = '';
        returnTr.innerHTML = '';
        reserveHeaderTd.innerHTML = '';
        reserveTr.innerHTML = '';
        history.innerHTML = '';


        let str2 = '';
        let str3 = '';

        let str4 = '';
        let str5 = '';

        let str6 = '';
        let str7 = '';

        let str8 = '';


            ////////////////////////////////////////////////////

            const data_cnt = data.bookBorrowList.length;

            //대출 및 반납, 예약 내역이 없을 경우
            if(data_cnt == 1 && data.bookBorrowList[0].bookCode == null){
                str2 += `
                        <b>자관대출 (0/5)</b>`;

                str3 += `
                    <td colspan="7">
                        대출 내역이 없습니다.
                    </td>`;

                str4 += `
                    <td colspan="7">
                        <b>반납 (0)</b>
                    </td>`;

                str5 += `
                    <td colspan="7">
                        반납 내역이 없습니다.
                    </td>`;

                str6 += `
                    <td colspan="7">
                        <b>예약 (예약 가능 건수 : 5)</b>
                    </td>`;

                str7 += `
                    <td colspan="7">
                        예약 내역이 없습니다.
                    </td>`;

            borrowHeaderTd.insertAdjacentHTML("afterbegin", str2);
            borrowTr.insertAdjacentHTML("afterbegin", str3);
            returnHeaderTd.insertAdjacentHTML("afterbegin", str4);
            returnTr.insertAdjacentHTML("afterbegin", str5);
            reserveHeaderTd.insertAdjacentHTML("afterbegin", str6);
            reserveTr.insertAdjacentHTML("afterbegin", str7);

            }
            else{
                data.bookBorrowList.forEach((bookBorrowInfo, idx) => {
                    str2 += `
                        <b>자관대출 (`
                        if(data.bookBorrowList.returnYN){
                            `${data.bookBorrowList.length}
                            `
                        }
                        `/5)</b>`;

                    str3 += `
                    <td>
                        표지
                    </td>
                    <td>
                        ${bookBorrowInfo.bookCode}
                    </td>
                    <td>
                        ${bookBorrowInfo.bookCode}
                    </td>

                    `;


                    borrowHeaderTd.insertAdjacentHTML("afterbegin", str2);
                    borrowTr.insertAdjacentHTML("afterbegin", str3);
                });
            }


            //////////////////////////////////////////
        //     data.bookBorrowList.forEach((bookBorrowInfo) => {
                
        //         if(bookBorrowInfo.bookCode == null){
        //             str2 +=   `
        //                 <div class="col text-center">
        //                     대출 내역이 없습니다.
        //                 </div>
        //             </div>`; 
        //         } 
        //         else {
                    


        //             str2+=`
        //             <div class="col">
        //                 표지
        //             </div>
        //             <div class="col">
        //                 ${bookBorrowInfo.bookCode}
        //             </div>
        //             <div class="col">
        //                 ${bookBorrowInfo.libraryBookVO.bookTitle}
        //             </div>
        //             <div class="col">
        //                 청구기호
        //             </div>
        //             <div class="col">
        //                 ${bookBorrowInfo.borrowDate}
        //             </div>
        //             <div class="col">
        //                 ${bookBorrowInfo.exReturnDate}
        //             </div>
        //             <div class="col">
        //                 대출처리자
        //             </div>`;
        //         }
        //     });               

        //     str2 += `</div>`;                       

        // str2 += `
        // <div class="row">
        //     <div class="col" id="return">
        //         반납 (0)        
        //     </div>
        // </div>
        // `;
        
        // str2 += `<div class="row tbody returnInfo">`;
        //     data.bookBorrowList.forEach((bookReturnInfo) => {

        //         if(bookReturnInfo.returnDate == null){
        //             str2 +=   `
        //                 <div class="col text-center">
        //                     반납 내역이 없습니다.
        //                 </div>
        //             </div>`; 
        //         } else {

        //         str2 += `<div class="col">
        //                     표지
        //                 </div>
        //                 <div class="col">
        //                     ${bookReturnInfo.bookCode}
        //                 </div>
        //                 <div class="col">
        //                     ${bookReturnInfo.libraryBookVO.bookTitle}
        //                 </div>
        //                 <div class="col">
        //                     청구기호
        //                 </div>
        //                 <div class="col">
        //                     ${bookReturnInfo.borrowDate}
        //                 </div>
        //                 <div class="col">
        //                     ${bookReturnInfo.returnDate}
        //                 </div>
        //                 <div class="col">
        //                     반납처리자
        //                 </div>`;
        //     }});
            
        //     `</div>`;   

        // str2 += `
        // <div class="row">
        //     <div class="col" id="reserveInfo">
        //         예약 (예약가능 건수 : 0)  예약 내역은 db쪽으로 더 생각해 봐야 할 것 같아서 다음에 다시 올 거예요.      
        //     </div>
        // </div>
        // `;
        // str2 +=   `<div class="row tbody">
        //         <div class="col text-center">
                    
        //         </div>
        //     </div>`; 
        // str2 += `<div class="row tbody">
        //     <div class="col">
        //         표지
        //     </div>
        //     <div class="col">
        //         등록번호
        //     </div>
        //     <div class="col">
        //         서명
        //     </div>
        //     <div class="col">
        //         청구기호
        //     </div>
        //     <div class="col">
        //         대출일
        //     </div>
        //     <div class="col">
        //         반납일(연체일)
        //     </div>
        //     <div class="col">
        //         반납처리자
        //     </div>
        // </div>`;   
        // str2 += `
        // <div class="row">
        //     <div class="col" id="allInfo">
        //         당일처리내역 여기도 조회, 대출, 반납 모두 떠야해서 이용자 먼저 하고 올게요.      
        //     </div>
        // </div>
        // `;
        
        // brInfo.insertAdjacentHTML("afterbegin", str2);

        }})

        /////////////////////////////////////////////////////////////
        
        // if(inputData.includes("GR")){



        //     console.log(data);

        //     let str3 = '';

        //     data.bookBorrowList.forEach((bookBorrowInfo) => {
        //         str3 +=`
        //         <div class="col">
        //             표지
        //         </div>
        //         <div class="col">
        //             ${bookBorrowInfo.bookCode}
        //         </div>
        //         <div class="col">
        //             ${bookBorrowInfo.libraryBookVO.bookTitle}
        //         </div>
        //         <div class="col">
        //             청구기호
        //         </div>
        //         <div class="col">
        //             ${bookBorrowInfo.borrowDate}
        //         </div>
        //         <div class="col">
        //             ${bookBorrowInfo.exReturnDate}
        //         </div>
        //         <div class="col">
        //             대출처리자
        //         </div>`;

        //         borrowInfo.insertAdjacentHTML("afterbegin", str3);
        //     })
        // };
        
    //fetch 통신 실패 시 실행 영역
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
}