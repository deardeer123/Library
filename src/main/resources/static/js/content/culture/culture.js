



function deleteBoard(){
    if(confirm('게시물을 삭제하시겠습니까')){
        location.href='/cuLDeleteBoard';
    }
}

function culDeleteBoard(){
    const chks = document.querySelectorAll('.chk:checked')

    if(chks.length == 0){
        alert('삭제 할 게시물을 선택하세요')
        return ;
    }

    const boardNum = []
    for(const chk of chks){
        boardNum.push(chk.value);
    }

    location=`/selectDeletes?boardNums=${boardNum}`;

}

function allchk(){
    const allChk = document.querySelector('#allChk');
    const chks =  document.querySelectorAll('.chk');

    if(allChk.checked){
        for(const chk of chks){
            chk.checked = true;
        }
    }
    else{
        for(const chk of chks){
            chk.checked = false;
        }
    }
}



//행사 게시글

function deleteEventBoard(boardNum){
    
    if(confirm('게시물을 삭제하시겠습니까')){
        location.href=`/goEventDelete?boardNum=${boardNum}`;
    }
    console(boardNum)
}

function goEventDeletes(){
    const chks = document.querySelectorAll('.chk:checked')

    if(chks.length == 0){
        alert('삭제 할 게시물을 선택하세요')
        return ;
    }

    const boardNum = []
    for(const chk of chks){
        boardNum.push(chk.value);
    }

    location=`/goEventDeletes?boardNums=${boardNum}`;
}

function goAppDeletes(){
    const chks = document.querySelectorAll('.chk:checked')

    if(chks.length == 0){
        alert('삭제 할 게시물을 선택하세요')
        return ;
    }

    const boardNum = []
    for(const chk of chks){
        boardNum.push(chk.value);
    }

    location=`/goAppDelete?boardNums=${boardNum}`;
}






function dateView(boardNum){
    const modal = new bootstrap.Modal('#myModal')
    const boardNumTag = document.querySelector('#boardNumTag');
    boardNumTag.value = boardNum
    modal.show();
    
}

function oninputPhone(target){
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/(^02.{0}|^01.{1}|[0-9]{3,4})([0-9]{3,4})([0-9]{4})/g, "$1-$2-$3");
};

function approve(boardNum){

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
            boardNum : boardNum
        })
    })
    .then((response) => {
        if(!response.ok){
            alert('fetch error!\n컨트롤러로 통신중에 오류가 발생했습니다.');
            return ;
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
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
}

function deleteApply(boardNum){
    
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
            boardNum : boardNum
        })
    })
    .then((response) => {
        if(!response.ok){
            alert('fetch error!\n컨트롤러로 통신중에 오류가 발생했습니다.');
            return ;
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
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
}

function CF(boardNum){
    
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
            boardNum : boardNum
        })
    })
    .then((response) => {
        if(!response.ok){
            alert('fetch error!\n컨트롤러로 통신중에 오류가 발생했습니다.');
            return ;
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
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
}





