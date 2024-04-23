// 모달 태그 선택
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
                <input type="hidden" name="userCode" value="${data.userCode}" id="getUserCode">
                <td class="table-light">번호</td>
                <td><span id="detailCardNum">${data.cardNum}</span>
                    <button class="btn btn-primary" onclick="reGrant(${data.userCode})">카드번호 재부여</button>
                </td>
                <td class="table-light">직급</td>
                <td>
                    <select name="isAdmin" id="detailIsAdmin" class="form-select">`
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
                    <select id="detailGender" class="form-select">`
                    if(data.gender == '남성'){
                        str += `<option value="남성" selected>남성</option>
                            <option value="여성">여성</option>`
                        }else if(data.gender == '여성'){
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
                <td><input type="text" value="${data.userTel}" id="detailUserTel" class="form-control"></td>
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
                <td colspan="3"></td>
            </tr>
            <tr>
                <td class="table-light">연체</td>
                <td colspan="3" id="overdueCnt">${data.overdueCnt}</td>
            </tr>
            <tr>
                <td class="table-light">최근 대출일</td>
                <td colspan="3" id="recentDate">${data.recentDate}</td>
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

document.querySelector('#all').addEventListener('change', checkAll);

function checkAll(){
    const ones = document.querySelectorAll('.one');
    const all = document.querySelector('#all');

    const isChecked = all.checked;

    ones.forEach(chk => {
        chk.checked = isChecked;
    });
}

function searchAddress(){
    new daum.Postcode({
        oncomplete: function(data) {
            
            document.querySelector('#postCode').value = data.zonecode;
            document.querySelector('#address').value = data.roadAddress;

        }
    }).open();
}

// 카드번호 재부여

const reGrant = () => {

    // 그림 그릴 모달 태그 선택
    const modalBody = document.querySelector('.modal-body');

    const userCode = document.querySelector('#getUserCode').value;
    const cardNum = document.querySelector('#detailCardNum').innerHTML;
    const userId = document.querySelector('#detailUserId').value;
    const userName = document.querySelector('#detailUserName').value;
    const isAdmin = document.querySelector('#detailIsAdmin').value;
    const cardStatus = document.querySelector('select[name="cardStatus"]').value;
    const gender = document.querySelector('#detailGender').value;
    const email = document.querySelector('input[name="email"]').value;
    const userTel = document.querySelector('#detailUserTel').value;
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
                    <select name="isAdmin" id="detailIsAdmin" class="form-select">`
                    if(isAdmin == 'N'){
                    str += `<option value="N" selected>이용자</option>
                        <option value="Y">관리자</option>`
                    }else{
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
                    <select id="detailGender" name="gender" class="form-select">`
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
                <td><input type="text" value="${userTel}" id="detailUserTel" class="form-control"></td>
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

function updateUserDetail (){
    
    // 업데이트 할 모달 정보 선택
    const cardNum = document.querySelector('#detailCardNum').innerHTML;
    const userId = document.querySelector('#detailUserId').value;
    const userName = document.querySelector('#detailUserName').value;
    const userCode = document.querySelector('#getUserCode').value;
    const isAdmin = document.querySelector('#detailIsAdmin').value;
    const cardStatus = document.querySelector('select[name="cardStatus"]').value;
    const gender = document.querySelector('#detailGender').value;
    const email = document.querySelector('input[name="email"]').value;
    const userTel = document.querySelector('#detailUserTel').value;
    const postCode = document.querySelector('input[name="postCode"]').value;
    const userAddr = document.querySelector('input[name="userAddr"]').value;
    const addrDetail = document.querySelector('input[name="addrDetail"]').value;
    const userIntro = document.querySelector('#detailUserIntro').value;
    const borrowCnt = document.querySelector('#borrowCnt').innerHTML;
    const overdueCnt = document.querySelector('#overdueCnt').innerHTML;

    const test_data = {
        // 데이터명 : 데이터값
        userCode : userCode
        , isAdmin : isAdmin
        , cardStatus : cardStatus
        , gender : gender
        , email : email
        , userTel : userTel
        , postCode : postCode
        , userAddr : userAddr
        , addrDetail : addrDetail
        , userIntro : userIntro
        , cardNum : cardNum
        , userId : userId
        , userName : userName
        , borrowCnt : borrowCnt
        , overdueCnt : overdueCnt
    };

    console.log(test_data);

    fetch('/bookAdmin/updateUserDetailFetch', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: JSON.stringify(test_data)
    })
    .then((response) => {
        return response.json(); //나머지 경우에 사용
    })
    //fetch 통신 후 실행 영역
    .then((data) => {//data -> controller에서 리턴되는 데이터!
        alert('수정 되었습니다.');

        window.location.href = "/bookAdmin/user"; 

    })

    //fetch 통신 실패 시 실행 영역
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
    
}