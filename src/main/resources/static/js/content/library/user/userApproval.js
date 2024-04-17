document.querySelector('#all').addEventListener('change', checkAll);

function checkAll() {
    const ones = document.querySelectorAll('.one');
    const all = document.querySelector('#all');

    const isChecked = all.checked;

    ones.forEach(chk => {
        chk.checked = isChecked;
    });
}

const updateCardNum = (userCode) => {

    let count = 0;

    const chkTags = [];
    let cheks = []
    //console.log(userCode);
    if (userCode === 0) {

        cheks = document.querySelectorAll('.one');
        cheks.forEach((e, idx) => {
            if (e.checked) {
                chkTags.push(e.value)
            } else {
                count = count + 1;
                //console.log(count);
                //console.log(idx);
            }

        })
        console.log(count)
        console.log(cheks.length)
        if (count === cheks.length) {
            console.log("asdsadsdasdasda")
            alert("체크박스를 준비해주세요");
            location.href = "/bookAdmin/userApproval";
            return
        }

    } else {
        chkTags.push(userCode)
    }


    //console.log(chkTags);

    fetch('/bookAdmin/updateCardNumFetch', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: new URLSearchParams({
            // 데이터명 : 데이터값
            "userCodeList": chkTags
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
            console.log(data);
            location.href = "/bookAdmin/userApproval";

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
                <input type="hidden" name="cardNum" value="${data.cardNum}">
                <td class="table-light">번호</td>
                <td><span id="detailCardNum">${data.cardNum}</span>
                    <button class="btn btn-primary" onclick="reGrant(${data.userCode})">카드번호 재부여</button>
                </td>
                <td class="table-light">직급</td>
                <td>
                    <select name="isAdmin" class="form-select">`
                    if(data.isAdmin == 'USER'){
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
                    if(data.gender == '사용중'){
                        str += `<option value="남자" selected>남자</option>
                            <option value="여자">여자</option>`
                        }else{
                        str += `<option value="남자">남자</option>
                            <option value="여자" selected>여자</option>`
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
                str += `<td colspan="3" name="userIntro"></td>`
            } else {
                str += `<td colspan="3"><textarea id="detailUserIntro" name="userIntro" class="form-control">${data.userIntro}</textarea></td>`;
            }

            str += `</table>`;
            
            modalBody.insertAdjacentHTML('afterbegin', str);

            user_detail_modal.show();
        })
        //fetch 통신 실패 시 실행 영역
        .catch(err => {
            alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
            console.log(err);
        });
}