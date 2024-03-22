



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

function dateView(){
    const modal = new bootstrap.Modal('#myModal')
    modal.show();
    
}
function apply(){
    const applyDate = document.querySelector('#applyDate').value;
    fetch('/apply', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: JSON.stringify({
           // 데이터명 : 데이터값

        })
    })
    .then((response) => {
        return response.json(); //나머지 경우에 사용
    })
    //fetch 통신 후 실행 영역
    .then((data) => {//data -> controller에서 리턴되는 데이터!
        
    })
    //fetch 통신 실패 시 실행 영역
    .catch(err=>{
        alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
        console.log(err);
    });
}