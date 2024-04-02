// 모달 태그 선택
const user_detail_modal = new bootstrap.Modal('#user-detail-modal');

function showModal(userCode){

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
           userCode : String(userCode),
           userDetail : String(true)
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
                    <button class="btn btn-primary">카드번호 재부여</button>
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
                if(data.userIntro == null){
                    str += `<td colspan="3"></td>`
                } else{
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
    .catch(err=>{
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