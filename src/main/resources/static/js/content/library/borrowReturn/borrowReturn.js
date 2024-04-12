// 대출 일자와 반납 일자 html에 default 값 오늘로
// document.querySelector('#borrow-date').value = new Date().toISOString().substring(0, 10);
// document.querySelector('#return-date').value = new Date().toISOString().substring(0, 10);


function selectMemberInfo() {

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
    const inputValue = document.querySelector('#inputData').value;
    const selectedCardNum = document.querySelector('input[name="selectedCardNum"]').value
    const borrowDate = document.querySelector('#borrow-date').value;
    const returnDate = document.querySelector('#return-date').value;

    fetch('/bookAdmin/selectBorrowInfoFetch', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: JSON.stringify({
            'inputValue': inputValue,
            'selectedCardNum': selectedCardNum,
            'borrowDate': borrowDate,
            'returnDate': returnDate,
            'userDetail': String(false)
        })
    })
        .then((response) => {
            return response.json(); //나머지 경우에 사용
        })
        //fetch 통신 후 실행 영역
        .then((data) => {//data -> controller에서 리턴되는 데이터!

            console.log(data);

            if (data.userCode == 0) {
                alert('회원번호 혹은 책번호가 정확하지 않습니다.\n다시 입력하세요.');
                return;
            }

            //selectedCardNum 값을 이전에 입력한 값으로 저장
            //조회가 됐으면 조회된 데이터, 아니면 0이 들어감

            document.querySelector('input[name="selectedCardNum"]').value = data.cardNum;

            userInfo.innerHTML = '';

            let str1 = '';

            //조회한 데이터가 있을 때만 다시 그림
            if (data.cardNum != 0) {

                str1 = `
                <div class="row">
                    <div class="col">
                        카드번호 : ${data.cardNum}
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        이름 : ${data.userName}
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        전화번호 : ${data.userTel}
                    </div>
                </div>
                <div class="row mt-1">
                    <div class="col-8">
                        <textarea id="userIntro" name="userIntro" class="form-control" rows="1" style="resize:none;">`
                    
                
                        if (data.userIntro == null) {
                    str1 += ``
                } else {
                    str1 += `${data.userIntro}`
                }
                str1 += `</textarea>
                    </div>
                    <div class="col-4 d-grid">
                        <input type="button" class="btn btn-secondary" value="메모수정" onclick="updateUserIntro(${data.cardNum})">
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-6 d-grid">
                        <input type="button" class="btn btn-secondary" value="SMS 전송">
                    </div>
                    <div class="col-6 d-grid">
                        <input type="button" class="btn btn-secondary" value="상세정보" onclick="showModal(${data.cardNum})">
                    </div>
                </div>`;

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
                if (data_cnt == 1 && data.bookBorrowList[0].bookCode == null) {
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
                else {

                    data.bookBorrowList.forEach((bookBorrowInfo, idx) => {
                        if (bookBorrowInfo.returnYN == 'N') {
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


                        if (bookBorrowInfo.returnYN == 'Y') {
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

            }
        })

        //fetch 통신 실패 시 실행 영역
        .catch(err => {
            alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
            console.log(err);
        });
}


// 유저 인트로 업데이트
function updateUserIntro(cardNum) {
    // 유저 인트로 선택
    let userIntro = document.querySelector('#userIntro').value;
    
    fetch('/bookAdmin/updateUserIntroFetch', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: JSON.stringify({
            // 데이터명 : 데이터값
            'cardNum': cardNum,
            'userIntro': userIntro
        })
    })
        .then((response) => {
            // return response.json(); //나머지 경우에 사용
        })
        //fetch 통신 후 실행 영역
        .then((data) => {//data -> controller에서 리턴되는 데이터!

        })
        //fetch 통신 실패 시 실행 영역
        .catch(err => {
            alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
            console.log(err);
        });

}




// 모달 태그 선택
const user_detail_modal = new bootstrap.Modal('#user-detail-modal');

function showModal(userCode) {

    // 그림 그릴 모달 태그 선택
    const modalBody = document.querySelector('.modal-body');

    fetch('/bookAdmin/showUserDetail', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: JSON.stringify({
            // 데이터명 : 데이터값
            userCode: String(userCode),
            userDetail: String(true)
        })
    })
        .then((response) => {
            return response.json(); //나머지 경우에 사용
        })
        //fetch 통신 후 실행 영역
        .then((data) => {//data -> controller에서 리턴되는 데이터!

            console.log(data);

            modalBody.innerHTML = '';

            let str = '';

            str += `
        <table class="table table-bordered">
            <colgroup>
                <col width="">
                <col width="">
                <col width="">
                <col width="">
            </colgroup>
            <tr>
                <td class="table-light">번호</td>
                <td>${data.cardNum} 
                    <button class="btn btn-primary" onclick="">카드번호 재부여</button>
                </td>
                <td class="table-light">직급</td>
                <td>
                    <select name="isAdmin" class="form-select">
                        <option value="USER">이용자</option>
                        <option value="ADMIN">관리자</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="table-light">이름</td>
                <td>${data.userName}</td>
                <td class="table-light">카드상태</td>
                <td>
                    <select name="cardStatus" class="form-select">
                        <option value="사용중">사용중</option>
                        <option value="분실">분실</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="table-light">아이디</td>
                <td>${data.userId}</td>
                <td class="table-light">성별</td>
                <td>
                    <select name="gender" class="form-select">
                        <option value="남자">남자</option>
                        <option value="여자">여자</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="table-light">이메일</td>
                <td>${data.email}</td>
                <td class="table-light">이메일 수신 여부</td>
                <td>
                    <select class="form-select">
                        <option>수신함</option>
                        <option>수신안함</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="table-light">전화번호</td>
                <td>${data.userTel}</td>
                <td class="table-light">SMS 수신여부</td>
                <td>
                    <select class="form-select">
                        <option>수신함</option>
                        <option>수신안함</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td rowspan="2" class="table-light">주소</td>
                <td colspan="3">
                    <input type="text" name="postCode" class="form-control" placeholder="${data.postCode}" id="postCode" readonly>
                    <input type="button" onclick="searchAddress()" value="주소 찾기" style="width: 90px;"class="btn btn-secondary">
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <input type="text" placeholder="${data.userAddr}" name="userAddr" class="form-control" id="address" readonly>
                    <input type="text" placeholder="${data.addrDetail}" name="addrDetail" class="form-control">
                </td>
            </tr>
            <tr>
                <td class="table-light">비고</td>`
            if (data.userIntro == null) {
                str += `<td colspan="3"></td>`
            } else {
                str += `<td colspan="3">${data.userIntro}</td>`
            }

            str += `</tr>
            <tr>
                <td class="table-active" colspan="4" text-align>이용조회</td>
            </tr>
            <tr>
                <td class="table-light">대출</td>
                <td colspan="3">${data.borrowCnt}건</td>
            </tr>
            <tr>
                <td class="table-light">예약</td>
                <td colspan="3"></td>
            </tr>
            <tr>
                <td class="table-light">연체</td>
                <td colspan="3">${data.overdueCnt}건</td>
            </tr>
            <tr>
                <td class="table-light">최근 대출일</td>
                <td colspan="3">${data.recentDate}</td>
            </tr>
        </table>
        `
            modalBody.insertAdjacentHTML('afterbegin', str);

            user_detail_modal.show();
        })
        //fetch 통신 실패 시 실행 영역
        .catch(err => {
            alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
            console.log(err);
        });
}

function searchAddress() {
    new daum.Postcode({
        oncomplete: function (data) {

            document.querySelector('#postCode').value = data.zonecode;
            document.querySelector('#address').value = data.roadAddress;

        }
    }).open();
}

exReturnDate();
function exReturnDate(){
    // 태그 선택
    const accordion_body = document.querySelector('.accordion-body')

    let newDate = new Date()
    //console.log(`${newDate.getMonth()+1}월 ${newDate.getDate()}일`)
    exReturnDate = newDate.setDate(newDate.getDate() + 14);

    
    let date = `${newDate.getFullYear()}-${newDate.getMonth()+1}-${newDate.getDate()}`

    
    
    //console.log(date)

    let newDate1 = new Date(`${date}`)
    //console.log(newDate1.toLocaleDateString())
    newDate1.setDate(newDate.getDate()+14)
    //console.log(newDate1.toDateString());
    newDate1.toLocaleDateString();
    //console.log(newDate1.toDateString());
    //console.log(newDate1.toDateString());
    //console.log(newDate1.toDateString());
    console.log(newDate1.toDateString().substring(0,4));

    const day = '';

    if(newDate1.toDateString().substring(0,4) === 'Mon'){
        day = '(월)'
    }
    
    accordion_body.innerHTML = newDate1.toLocaleDateString();
}

