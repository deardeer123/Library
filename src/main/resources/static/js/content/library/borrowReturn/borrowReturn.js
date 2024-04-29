// 대출 일자와 반납 일자 html에 default 값 오늘로
document.querySelector('#borrow-date').value = new Date().toISOString().substring(0, 10);
document.querySelector('#return-date').value = new Date().toISOString().substring(0, 10);

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
    const renderCnt = document.querySelector('#render-cnt');
    const returnCnt = document.querySelector('#return-cnt');
    const reserveCnt = document.querySelector('#reserve-cnt');
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

            if (data.userInfo.userCode == 0) {
                alert('회원번호 혹은 책번호가 정확하지 않습니다.\n다시 입력하세요.');
                return;
            }

            //selectedCardNum 값을 이전에 입력한 값으로 저장
            //조회가 됐으면 조회된 데이터, 아니면 0이 들어감

            document.querySelector('input[name="selectedCardNum"]').value = data.userInfo.cardNum;

            userInfo.innerHTML = '';

            let str1 = '';

            //조회한 데이터가 있을 때만 다시 그림
            if (data.userInfo.cardNum != 0) {

                str1 = `
                <div class="row">
                    <div class="col">
                        카드번호 : ${data.userInfo.cardNum}
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        이름 : ${data.userInfo.userName}
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        전화번호 : ${data.userInfo.userTel}
                    </div>
                </div>
                <div class="row mt-1">
                    <div class="col-8">
                        <textarea id="userIntro" name="userIntro" class="form-control" rows="1" style="resize:none;">`;
                        if (data.userInfo.userIntro == null) {
                            str1 += ``;
                        } else {
                            str1 += `${data.userInfo.userIntro}`;
                        }
                str1 += `</textarea>
                    </div>
                    <div class="col-4 d-grid">
                        <input type="button" class="btn btn-secondary" value="메모수정" onclick="updateUserIntro(${data.userInfo.userCode})">
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-6 d-grid">
                        <input type="button" class="btn btn-secondary" value="SMS 전송">
                    </div>
                    <div class="col-6 d-grid">
                        <input type="button" class="btn btn-secondary" value="상세정보" onclick="showModal(${data.userInfo.userCode})">
                    </div>
                </div>`;

                userInfo.insertAdjacentHTML('afterbegin', str1);

                // ---------------  각 테이블을 조회한 데이터를 바탕으로 다시 그림 ------------------------//

                borrow_list_tbody.innerHTML = '';
                return_list_tbody.innerHTML = '';
                reserve_list_tbody.innerHTML = '';
                
                renderCnt.innerHTML = '';
                returnCnt.innerHTML = '';
                reserveCnt.innerHTML = '';

                let str2 = '';
                let str3 = '';
                let str4 = '';
                let cnt1 = '';
                let cnt2 = '';
                let cnt3 = '';

                ////////////////////////////////////////////////////

                const data_cnt = data.userInfo.bookBorrowList.length;

                //대출 및 반납, 예약 내역이 없을 경우
                if (data_cnt == 1 && data.userInfo.bookBorrowList[0].bookCode == null) {

                    cnt1 += `<b>대출(0/5)</b>`;

                    cnt2 += `<b>반납(0)</b>`;

                    cnt3 += `<b>예약 (예약 가능 건수 : 5)</b>`;

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

                    renderCnt.insertAdjacentHTML("afterbegin", cnt1);
                    returnCnt.insertAdjacentHTML("afterbegin", cnt2);
                    reserveCnt.insertAdjacentHTML("afterbegin", cnt3);
                    borrow_list_tbody.insertAdjacentHTML("afterbegin", str2);
                    return_list_tbody.insertAdjacentHTML("afterbegin", str3);
                    reserve_list_tbody.insertAdjacentHTML("afterbegin", str4);

                }
                else {

                    let renCnt = 0;
                    let retCnt = 0;

                    data.userInfo.bookBorrowList.forEach((bookBorrowInfo, idx) => {
                        if (bookBorrowInfo.returnYN == 'N') {
                            renCnt = renCnt + 1;

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

                        // else{
                        //     if(renCnt == 6){
                        //         alert('최대 대출 권수를 초과 하였습니다.');
                        //      구현 하고싶은데 좀 있다가... 
                        //     }
                        // }


                        if (bookBorrowInfo.returnYN == 'Y') {
                            retCnt = retCnt + 1;

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

                    if(data.isReserved){
                        alert('해당 도서는 예약 내역이 있습니다.\n카운터에 보관해주세요.');
                    }
                    
                    cnt1 += `<b>대출(${renCnt}/5)</b>`;
                    cnt2 += `<b>반납(${retCnt})</b>`;
                    

                    renderCnt.insertAdjacentHTML("afterbegin", cnt1);
                    returnCnt.insertAdjacentHTML("afterbegin", cnt2);
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
function updateUserIntro(userCode) {
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
            'userCode': userCode,
            'userIntro': userIntro
        })
    })
        .then((response) => {
            // return response.json(); //나머지 경우에 사용
        })
        //fetch 통신 후 실행 영역
        .then((data) => {//data -> controller에서 리턴되는 데이터!
            alert('수정 되었습니다.');
        })
        //fetch 통신 실패 시 실행 영역
        .catch(err => {
            alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
            console.log(err);
        });

}

// 부트스트랩 모달 선택
const user_detail_modal = new bootstrap.Modal('#user-detail-modal');

function showModal(userCode) {

    // 그림 그릴 모달 태그 선택
    const modalBody = document.querySelector('.modal-body');

    fetch('/bookAdmin/showUserDetailFetch', { //요청경로
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

            // console.log(data);

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
                <input type="hidden" name="userCode" value="${data.userCode}" id="getUserCode">
                <td class="table-light">번호</td>
                <td><span id="detailCardNum">${data.cardNum}</span>
                    <button class="btn btn-primary" onclick="reGrant(${data.userCode})">카드번호 재부여</button>
                </td>
                <td class="table-light">직급</td>
                <td>
                    <select name="isAdmin" class="form-select">`
                    if(data.isAdmin == '이용자'){
                    str += `<option value="N" selected>이용자</option>
                        <option value="Y">관리자</option>`
                    }else if(data.isAdmin == '관리자'){
                    str += `<option value="N">이용자</option>
                        <option value="Y" selected>관리자</option>`
                    }
                    str += `</select>
                </td>
            </tr>
            <tr>
                <td class="table-light">이름</td>
                <td><input type="text" name="userName" class="form-control" id="detailUserName" value="${data.userName}"></td>
                <td class="table-light">카드상태</td>
                <td>
                    <select name="cardStatus" class="form-select">`
                    if(data.cardStatus == '사용중'){
                    str += `<option value="사용중" selected>사용중</option>
                        <option value="분실">분실</option>`
                    }else{
                    str += `<option value="사용중">사용중</option>
                        <option value="분실" selected>분실</option>`
                    }
                    str += `</select>
                </td>
            </tr>
            <tr>
                <td class="table-light">아이디</td>
                <td><input type="text" name="userId" value="${data.userId}" id="detailUserId" class="form-control"></td>
                <td class="table-light">성별</td>
                <td>
                    <select name="gender" class="form-select">`
                    if(data.gender == '남성'){
                        str += `<option value="남성" selected>남성</option>
                            <option value="여성">여성</option>`
                        }else{
                        str += `<option value="남성">남성</option>
                            <option value="여성" selected>여성</option>`
                        }
                        str += `</select>
                </td>
            </tr>
            <tr>
                <td class="table-light">이메일</td>
                <td><input type="text" value="${data.email}" name="email" class="form-control"></td>
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
                <td><input type="text" value="${data.userTel}" name="userTel" class="form-control"></td>
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
                    <input type="text" name="postCode" class="form-control" value="${data.postCode}" id="postCode" style="width: 200px;" readonly>
                    <input type="button" onclick="searchAddress()" value="주소 찾기" style="width: 90px;"class="btn btn-secondary">
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <input type="text" value="${data.userAddr}" name="userAddr" class="form-control" id="address" readonly>
                    <input type="text" value="${data.addrDetail}" name="addrDetail" class="form-control">
                </td>
            </tr>
            <tr>
                <td class="table-light">비고</td>`
            if (data.userIntro == null) {
                str += `<td colspan="3" name="userIntro"><textarea id="detailUserIntro" name="userIntro" class="form-control"></textarea></td>`
            } else {
                str += `<td colspan="3"><textarea id="detailUserIntro" name="userIntro" class="form-control">${data.userIntro}</textarea></td>`
            }

            str += `</tr>
            <tr>
                <td class="table-active" colspan="4" text-align>이용조회</td>
            </tr>
            <tr>
                <td class="table-light">대출</td>
                <td colspan="3" id="borrowCnt">${data.borrowCnt}</td>
            </tr>
            <tr>
                <td class="table-light">예약</td>
                <td colspan="3" ></td>
            </tr>
            <tr>
                <td class="table-light">연체</td>
                <td colspan="3" id="overdueCnt">${data.overdueCnt}</td>
            </tr>
            <tr>
                <td class="table-light">최근 대출일</td>`
            if(data.recentDate == null){
            str += `<td colspan="3" id="recentDate"></td>`
            } else{
            str += `<td colspan="3" id="recentDate">${data.recentDate}</td>`
            }
            str += `</tr>
        </table>`
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

// 대출반납 페이지 반납 예정일
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
    console.log(newDate1.toLocaleDateString())
    //console.log(newDate1.setDate(newDate.getDate()+14))
    //console.log(newDate1.toDateString());
    newDate1.toLocaleDateString();
    //console.log(newDate1.toDateString());
    //console.log(newDate1.toDateString());
    //console.log(newDate1.toDateString());
    console.log(newDate1.toDateString().substring(0,4));

    const days = ['(월)', '(화)', '(수)', '(목)', '(금)', '(토)', '(일)'];
    const nowDay = '';

    // newDate1.toDateString().substring(0,4).forEach((e, i) => {
    //     if(e === 'Mon'){
    //         nowDay === days[0];
    //     }
    // });
    
    accordion_body.innerHTML = newDate1.toLocaleDateString() + nowDay;
}

// 카드번호 재부여

const reGrant = () => {

    // 그림 그릴 모달 태그 선택
    const modalBody = document.querySelector('.modal-body');

    const userCode = document.querySelector('#getUserCode').value;
    const cardNum = document.querySelector('#detailCardNum').innerHTML;
    const userId = document.querySelector('#detailUserId').value;
    const userName = document.querySelector('#detailUserName').value;
    const isAdmin = document.querySelector('select[name="isAdmin"]').value;
    const cardStatus = document.querySelector('select[name="cardStatus"]').value;
    const gender = document.querySelector('select[name="gender"]').value;
    const email = document.querySelector('input[name="email"]').value;
    const userTel = document.querySelector('input[name="userTel"]').value;
    const postCode = document.querySelector('input[name="postCode"]').value;
    const userAddr = document.querySelector('input[name="userAddr"]').value;
    const addrDetail = document.querySelector('input[name="addrDetail"]').value;
    const userIntro = document.querySelector('#detailUserIntro').value;
    const borrowCnt = document.querySelector('#borrowCnt').innerHTML;
    const overdueCnt = document.querySelector('#overdueCnt').innerHTML;
    const recentDate = document.querySelector('#recentDate').innerHTML;

    fetch('/bookAdmin/updateCardNumFetch', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: new URLSearchParams({
            // 데이터명 : 데이터값
            "cardNum" : cardNum
        })
    })
        .then((response) => {
            if (!response.ok) {
                alert('fetch error!\n컨트롤러로 통신중에 오류가 발생했습니다.');
                return;
            }

            return response.text(); //컨트롤러에서 return하는 데이터가 없거나 int, String 일 때 사용
            //return response.json(); //나머지 경우에 사용
        })
        //fetch 통신 후 실행 영역
        .then((data) => {//data -> controller에서 리턴되는 데이터!
            alert('변경 되었습니다.');


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
                <input type="hidden" name="userCode" value="${userCode}" id="getUserCode">
                <td class="table-light">번호</td>
                <td><span id="detailCardNum">${data}</span>
                    <button class="btn btn-primary" onclick="reGrant(${userCode})">카드번호 재부여</button>
                </td>
                <td class="table-light">직급</td>
                <td>
                    <select name="isAdmin" class="form-select">`
                    if(isAdmin == 'N'){
                    str += `<option value="N" selected>이용자</option>
                        <option value="Y">관리자</option>`
                    }else {
                    str += `<option value="N">이용자</option>
                        <option value="Y" selected>관리자</option>`
                    }
                    str += `</select>
                </td>
            </tr>
            <tr>
                <td class="table-light">이름</td>
                <td><input type="text" name="userName" class="form-control" id="detailUserName" value="${userName}"></td>
                <td class="table-light">카드상태</td>
                <td>
                    <select name="cardStatus" class="form-select">`
                    if(cardStatus == '사용중'){
                    str += `<option value="사용중" selected>사용중</option>
                        <option value="분실">분실</option>`
                    }else{
                    str += `<option value="사용중">사용중</option>
                        <option value="분실" selected>분실</option>`
                    }
                    str += `</select>
                </td>
            </tr>
            <tr>
                <td class="table-light">아이디</td>
                <td><input type="text" name="userId" value="${userId}" id="detailUserId" class="form-control"></td>
                <td class="table-light">성별</td>
                <td>
                    <select name="gender" class="form-select">`
                    if(gender == '남성'){
                        str += `<option value="남성" selected>남성</option>
                            <option value="여성">여성</option>`
                        }else{
                        str += `<option value="남성">남성</option>
                            <option value="여성" selected>여성</option>`
                        }
                        str += `</select>
                </td>
            </tr>
            <tr>
                <td class="table-light">이메일</td>
                <td><input type="text" value="${email}" name="email" class="form-control"></td>
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
                <td><input type="text" value="${userTel}" name="userTel" class="form-control"></td>
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
                    <input type="text" name="postCode" class="form-control" value="${postCode}" id="postCode" style="width: 200px;" readonly>
                    <input type="button" onclick="searchAddress()" value="주소 찾기" style="width: 90px;"class="btn btn-secondary">
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <input type="text" value="${userAddr}" name="userAddr" class="form-control" id="address" readonly>
                    <input type="text" value="${addrDetail}" name="addrDetail" class="form-control">
                </td>
            </tr>
            <tr>
                <td class="table-light">비고</td>`
            if (userIntro == null) {
                str += `<td colspan="3" name="userIntro"><textarea id="detailUserIntro" name="userIntro" class="form-control"></textarea></td>`
            } else {
                str += `<td colspan="3"><textarea id="detailUserIntro" name="userIntro" class="form-control">${userIntro}</textarea></td>`
            }

            str += `</tr>
            <tr>
                <td class="table-active" colspan="4" text-align>이용조회</td>
            </tr>
            <tr>
                <td class="table-light">대출</td>
                <td colspan="3" id="borrowCnt">${borrowCnt}</td>
            </tr>
            <tr>
                <td class="table-light">예약</td>
                <td colspan="3" ></td>
            </tr>
            <tr>
                <td class="table-light">연체</td>
                <td colspan="3" id="overdueCnt">${overdueCnt}</td>
            </tr>
            <tr>
                <td class="table-light">최근 대출일</td>`
            if(recentDate == null){
            str += `<td colspan="3" id="recentDate"></td>`
            } else{
            str += `<td colspan="3" id="recentDate">${recentDate}</td>`
            }
            str += `</tr>
        </table>`
            modalBody.insertAdjacentHTML('afterbegin', str);

        })
        //fetch 통신 실패 시 실행 영역
        .catch(err => {
            alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
            console.log(err);
        });
}

// 이용자 정보 업데이트

const updateUserDetail = () => {

    // 업데이트 할 모달 정보 선택
    const cardNum = document.querySelector('#detailCardNum').innerHTML;
    const userId = document.querySelector('#detailUserId').value;
    const userName = document.querySelector('#detailUserName').value;
    const userCode = document.querySelector('input[type="hidden"]').value;
    const isAdmin = document.querySelector('select[name="isAdmin"]').value;
    const cardStatus = document.querySelector('select[name="cardStatus"]').value;
    const gender = document.querySelector('select[name="gender"]').value;
    const email = document.querySelector('input[name="email"]').value;
    const userTel = document.querySelector('input[name="userTel"]').value;
    const postCode = document.querySelector('input[name="postCode"]').value;
    const userAddr = document.querySelector('input[name="userAddr"]').value;
    const addrDetail = document.querySelector('input[name="addrDetail"]').value;
    const userIntro = document.querySelector('#detailUserIntro').value;

    // 대출반납 페이지에 바뀐 거 다시 그려주기(카드번호, 이름, 전화번호, 비고란)
    const userInfo = document.querySelector('#userInfo');
    
    fetch('/bookAdmin/updateUserDetailFetch', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: JSON.stringify({
           // 데이터명 : 데이터값
            "userCode" : userCode
            , "isAdmin" : isAdmin
            , "cardStatus" : cardStatus
            , "gender" : gender
            , "email" : email
            , "userTel" : userTel
            , "postCode" : postCode
            , "userAddr" : userAddr
            , "addrDetail" : addrDetail
            , "userIntro" : userIntro
            , "cardNum" : cardNum
            , "userId" : userId
            , "userName" : userName
        })
    })
    .then((response) => {
        return response.json(); //나머지 경우에 사용
    })
    //fetch 통신 후 실행 영역
    .then((data) => {//data -> controller에서 리턴되는 데이터!
        alert('수정 되었습니다.');
        
        userInfo.innerHTML = '';
        str = '';

        str += `
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
                    str += ``
                } else {
                    str += `${data.userIntro}`
                }
        str += `</textarea>
            </div>
            <div class="col-4 d-grid">
                <input type="button" class="btn btn-secondary" value="메모수정" onclick="updateUserIntro(${data.userCode})">
            </div>
        </div>
        <div class="row mt-3">
            <div class="col-6 d-grid">
                <input type="button" class="btn btn-secondary" value="SMS 전송">
            </div>
            <div class="col-6 d-grid">
                <input type="button" class="btn btn-secondary" value="상세정보" onclick="showModal(${data.userCode})">
            </div>
        </div>`;

        userInfo.insertAdjacentHTML("afterbegin", str);
    })
    //fetch 통신 실패 시 실행 영역
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
    
}