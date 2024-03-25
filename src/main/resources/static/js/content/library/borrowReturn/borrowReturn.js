// 대출 일자와 반납 일자 html에 default 값 오늘로
// document.querySelector('#borrow-date').value = new Date().toISOString().substring(0, 10);
// document.querySelector('#return-date').value = new Date().toISOString().substring(0, 10);


function selectMemberInfo(){

    // 인풋 데이터 선택하기
    let inputData = document.querySelector('#inputData').value;
    
    // 그림 그릴 이용자 정보 태그 선택하기(userInfo)
    const userInfo = document.querySelector('#userInfo');

    // 모든 테이블의 tbody 태그 선택
    // 1. 대출 내역 테이블 tbody
    const borrow_list_tbody = document.querySelector('.borrow-list-table tbody');
    // 2. 반납 내역 테이블 tbody
    const return_list_tbody = document.querySelector('.return-list-table tbody');
    // 3. 예약 내역 테이블 tbody
    const reserve_list_tbody = document.querySelector('.reserve-list-table tbody');

    // 들어오는 데이터 관리
    const inputValue = inputData;
    const selectedCardNum = document.querySelector('input[name="selectedCardNum"]').value
    const borrowDate = document.querySelector('#borrow-date').value;
    const returnDate = document.querySelector('#return-date').value;

    fetch('/bookAdmin/selectBorrowInfo', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: JSON.stringify({
            'inputValue' : inputValue,
            'selectedCardNum' : selectedCardNum,
            'borrowDate' : borrowDate,
            'returnDate' : returnDate
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
            <div class="col">
                ${data.cardNum}
            </div>
        </div>
        <div class="row">
            <div class="col">
                ${data.userName}
            </div>
        </div>
        <div class="row">
            <div class="col">
                ${data.userTel}
            </div>
        </div>
        `;

        userInfo.insertAdjacentHTML('afterbegin', str1);

        // ---------------  각 테이블을 조회한 데이터를 바탕으로 다시 그림 ------------------------//

        borrow_list_tbody.innerHTML = '';
        return_list_tbody.innerHTML = '';
        reserve_list_tbody.innerHTML = '';

        let str2 = '';
        let str3 = '';
        let str4 = '';

            ////////////////////////////////////////////////////

            const data_cnt = data.bookBorrowList.length;

            //대출 및 반납, 예약 내역이 없을 경우
            if(data_cnt == 1 && data.bookBorrowList[0].bookCode == null){
                str2 += `
                    <td colspan="7">
                        대출 내역이 없습니다.
                    </td>`;

                str3 += `
                    <td colspan="7">
                        반납 내역이 없습니다.
                    </td>`;

                str4 += `
                    <td colspan="7">
                        예약 내역이 없습니다.
                    </td>`;

        borrow_list_tbody.insertAdjacentHTML("afterbegin", str2);
        return_list_tbody.insertAdjacentHTML("afterbegin", str3);
        reserve_list_tbody.insertAdjacentHTML("afterbegin", str4);

            }
            else{

                data.bookBorrowList.forEach((bookBorrowInfo, idx) => {
                    if(bookBorrowInfo.returnYN == 'N'){
                        str2 += `
                        <tr>
                            <td>
                                <img width="70px" src="/upload/${bookBorrowInfo.libraryBookVO.libraryBookInfoVO.bookInfoAttachedFileName}">
                            </td>
                            <td>
                                ${bookBorrowInfo.bookCode}
                            </td>
                            <td>
                                ${bookBorrowInfo.libraryBookVO.bookTitle}
                            </td>
                            <td>
                                ${bookBorrowInfo.libraryBookVO.libraryBookInfoVO.bookCateCode}${bookBorrowInfo.libraryBookVO.libraryBookInfoVO.bookMidCateCode}
                            </td>
                            <td>
                                ${bookBorrowInfo.borrowDate.substring(0, 10)}
                            </td>
                            <td>
                                ${bookBorrowInfo.exReturnDate.substring(0, 10)}
                            </td>
                            <td>
                                
                            </td>
                        </tr>`;

                        
                    }
                    

                    if(bookBorrowInfo.returnYN == 'Y'){
                        str3 += `
                        <tr>
                            <td>
                                <img width="70px" src="/upload/${bookBorrowInfo.libraryBookVO.libraryBookInfoVO.bookInfoAttachedFileName}">
                            </td>
                            <td>
                                ${bookBorrowInfo.bookCode}
                            </td>
                            <td>
                                ${bookBorrowInfo.libraryBookVO.bookTitle}
                            </td>
                            <td>
                                ${bookBorrowInfo.libraryBookVO.libraryBookInfoVO.bookCateCode}${bookBorrowInfo.libraryBookVO.libraryBookInfoVO.bookMidCateCode}
                            </td>
                            <td>
                                ${bookBorrowInfo.borrowDate.substring(0, 10)}
                            </td>
                            <td>
                                ${bookBorrowInfo.returnDate.substring(0, 10)}
                            </td>
                            <td>
                                
                            </td>
                        </tr>`;
                    }
                    
                });

                borrow_list_tbody.insertAdjacentHTML("afterbegin", str2);
                return_list_tbody.insertAdjacentHTML("afterbegin", str3);
            }

        }})
        
    //fetch 통신 실패 시 실행 영역
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
}