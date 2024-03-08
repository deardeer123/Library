function culDeleteBoard(boardNo){
    const chks = document.querySelectorAll('.chk')

    if(chks.length == 0){
        alert('게시물을 선택하세요')
        return ;
    }

    const boardNo = []
    for(const chk of chks){
        boardNo.push(chk.value);
    }

    location=`/cuLDeleteBoard?boardNo=${boardNo}`;

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