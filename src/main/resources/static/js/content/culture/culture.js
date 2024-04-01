



function deleteBoard() {
    if (confirm('게시물을 삭제하시겠습니까')) {
        location.href = '/cuLDeleteBoard';
    }
}

function culDeleteBoard() {
    const chks = document.querySelectorAll('.chk:checked')

    if (chks.length == 0) {
        alert('삭제 할 게시물을 선택하세요')
        return;
    }

    const boardNum = []
    for (const chk of chks) {
        boardNum.push(chk.value);
    }

    location = `/selectDeletes?boardNums=${boardNum}`;

}

function allchk() {
    const allChk = document.querySelector('#allChk');
    const chks = document.querySelectorAll('.chk');

    if (allChk.checked) {
        for (const chk of chks) {
            chk.checked = true;
        }
    }
    else {
        for (const chk of chks) {
            chk.checked = false;
        }
    }
}



//행사 게시글

function deleteEventBoard(boardNum) {

    if (confirm('게시물을 삭제하시겠습니까')) {
        location.href = `/goEventDelete?boardNum=${boardNum}`;
    }
    console(boardNum)
}

function goEventDeletes() {
    const chks = document.querySelectorAll('.chk:checked')

    if (chks.length == 0) {
        alert('삭제 할 게시물을 선택하세요')
        return;
    }

    const boardNum = []
    for (const chk of chks) {
        boardNum.push(chk.value);
    }

    location = `/goEventDeletes?boardNums=${boardNum}`;
}

function goAppDeletes() {
    const chks = document.querySelectorAll('.chk:checked')

    if (chks.length == 0) {
        alert('삭제 할 게시물을 선택하세요')
        return;
    }

    const boardNum = []
    for (const chk of chks) {
        boardNum.push(chk.value);
    }

    location = `/goAppDelete?boardNums=${boardNum}`;
}






function dateView(boardNum) {
    const modal = new bootstrap.Modal('#myModal')
    const boardNumTag = document.querySelector('#boardNumTag');
    boardNumTag.value = boardNum
    modal.show();

}

function oninputPhone(target) {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/(^02.{0}|^01.{1}|[0-9]{3,4})([0-9]{3,4})([0-9]{4})/g, "$1-$2-$3");
};

function approve(boardNum) {

    console.log(boardNum)
    fetch('/upPersonnel', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: new URLSearchParams({
            // 데이터명 : 데이터값
            boardNum: boardNum
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
            const myTr = document.querySelector('.tr-list')
            myTr.remove()
        })
        //fetch 통신 실패 시 실행 영역
        .catch(err => {
            alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
            console.log(err);
        });
}

function deleteApply(boardNum) {
    location.href=`/deleteApply?boardNum=${boardNum}`
    
}

function CF(boardNum) {

    console.log(boardNum)
    fetch('/confirm', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: new URLSearchParams({
            // 데이터명 : 데이터값
            boardNum: boardNum
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
            const btn = document.querySelector('.btn')
            btn.remove();
        })
        //fetch 통신 실패 시 실행 영역
        .catch(err => {
            alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
            console.log(err);
        });
}




function updateDetail() {
    confirm('신청을 취소하시겠습니까 ?')
    console.log(boardNum)
    fetch('/deleteApply', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: new URLSearchParams({
            // 데이터명 : 데이터값
            boardNum: boardNum
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
            const myTr = document.querySelector('.tr-list')
            myTr.remove()
        })
        //fetch 통신 실패 시 실행 영역
        .catch(err => {
            alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
            console.log(err);
        });
}

function dateCode() {
    const openDateValue = document.querySelector("#openDate").value;
    const closeDateValue = document.querySelector("#closeDate").value;
    // const toDateValue = document.querySelector("#toDate").value;
    // const FromDateValue = document.querySelector("#FromDate").value;

    // 날짜 문자열을 Date 객체로 변환
    const openDate = new Date(openDateValue);
    const closeDate = new Date(closeDateValue);

    // 연도, 월, 일 추출
    const openYear = openDate.getFullYear();
    const openMonth = openDate.getMonth() + 1; // getMonth()는 0부터 시작하므로 1을 더함
    const openDay = openDate.getDate();

    const closeYear = closeDate.getFullYear();
    const closeMonth = closeDate.getMonth() + 1; // getMonth()는 0부터 시작하므로 1을 더함
    const closeDay = closeDate.getDate();

    // const toYear = toDate.getFullYear();
    // const toMonth = toDate.getMonth() + 1; // getMonth()는 0부터 시작하므로 1을 더함
    // const toDay = toDate.getDate();

    // const fromYear = fromDate.getFullYear();
    // const fromMonth = fromDate.getMonth() + 1; // getMonth()는 0부터 시작하므로 1을 더함
    // const fromDay = fromDate.getDate();

    //현재 날짜 구하기
    let today = new Date();   
    


    console.log("Open Date:", openYear, openMonth, openDay);
    console.log("Close Date:", closeYear, closeMonth, closeDay);

    // (^\d{4}) - 숫자 4개 : 연도
    //  (0[1-9]|1[0-2]) - '0 다음 1부터 9 사이 값' 또는 '1 다음 0부터 2 사이 값 : 01~12월
    //  (0[1-9]|[12][0-9]|3[01])$ - '0 다음 1부터 9 사이' 또는 '1 또는 2 다음 0부터 9 사이' 또는 '3 다음 0 또는 1' : 01~31일
    const pattern = /(^\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/

    console.log(pattern.test(openDate))

    if (openDate > closeDate) {
        let tomorrow = new Date(today);
        tomorrow.setDate(today.getDate() + 1);

         // openDateInput의 값을 변경하여 현재 날짜 + 1일로 설정
        const tomorrowString = tomorrow.toISOString().split('T')[0]; // ISO 형식의 문자열로 변환
        openDateValue.value = tomorrowString;
        console.log(openDateValue)

        alert('데이터를 다시 입력하시길 바랍니다');
        return ;
    }
    else if(today> openDate){
        alert('행사 시작 날짜를 다시 입력하시길 바랍니다.')
        return ;
    }
    
    

}








