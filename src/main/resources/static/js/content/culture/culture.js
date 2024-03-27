



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

function oninputPhone(target){
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/(^02.{0}|^01.{1}|[0-9]{3,4})([0-9]{3,4})([0-9]{4})/g, "$1-$2-$3");
};
