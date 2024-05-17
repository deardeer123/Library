function searchAddress(){
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
            document.querySelector('#postCode').value = data.zonecode;
            
            
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            document.querySelector('#address').value = roadAddr;

        }
    }).open();
}

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
            window.location.href = "/bookAdmin/userApproval";
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

            window.location.href = "/bookAdmin/userApproval";

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
                <td class="table-light">이름</td>
                <td><input type="text" name="userName" class="form-control" id="detailUserName" value="${data.userName}"></td>
            </tr>
            <tr>
                <td class="table-light">아이디</td>
                <td><input type="text" name="userId" value="${data.userId}" id="detailUserId" class="form-control"></td>
                <td class="table-light">성별</td>
                <td>
                    <select name="detailGender" class="form-select">`
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
                <td class="table-light">전화번호</td>
                <td><input type="text" value="${data.userTel}" name="detailUserTel" class="form-control"></td>
                <td class="table-light">이메일</td>
                <td><input type="text" value="${data.email}" name="email" class="form-control"></td>
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
            str += `</table>`

            modalBody.insertAdjacentHTML('afterbegin', str);

            user_detail_modal.show();
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
    const userId = document.querySelector('#detailUserId').value;
    const userName = document.querySelector('#detailUserName').value;
    const userCode = document.querySelector('#getUserCode').value;
    const isAdmin = document.querySelector('select[name="isAdmin"]').value;
    const gender = document.querySelector('select[name="detailGender"]').value;
    const email = document.querySelector('input[name="email"]').value;
    const userTel = document.querySelector('input[name="detailUserTel"]').value;
    const postCode = document.querySelector('input[name="postCode"]').value;
    const userAddr = document.querySelector('input[name="userAddr"]').value;
    const addrDetail = document.querySelector('input[name="addrDetail"]').value;
    const userIntro = document.querySelector('#detailUserIntro').value;

    const test_data = {
        // 데이터명 : 데이터값
        userCode : userCode
        , isAdmin : isAdmin
        , gender : gender
        , email : email
        , userTel : userTel
        , postCode : postCode
        , userAddr : userAddr
        , addrDetail : addrDetail
        , userIntro : userIntro
        , userId : userId
        , userName : userName
    };
    
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

        window.location.href = "/bookAdmin/userApproval"; 

    })
    //fetch 통신 실패 시 실행 영역
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
    
}