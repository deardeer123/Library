// const check = () => {
//   const cf1 = document.querySelector('#pw-cf1')
//   const cf2 = document.querySelector('#pw-cf2')








//   let pw1 = document.querySelector('#pw1').value;
//   let pw2 = document.querySelector('#pw2').value;
//   console.log(pw1)
//   console.log(pw2)

//   check_td.innerHTML = '';
//   let str = '';
  
//     if(pw2.length == pw1.length){
//       if(pw2 == pw1){
//         str += '비밀번호가 일치합니다';
//         check_td.insertAdjacentHTML("afterbegin",str);
//         check_td.style="color: forestgreen;"
//       }
//       else{
//         str += '다시 확인해주시길 바랍니다'; 
//         check_td.insertAdjacentHTML("afterbegin",str)
//         check_td.style = "color: red;";
//       }
//     }
  
// }


// const cf = () => {
//   let pw1 = document.querySelector('#pw1').value;
//   let pw2 = document.querySelector('#pw2').value;

//   if(pw1 == '' && pw2 == ''){
//     confirm('')
//   }
// }


const pwCF = (userPw) => {
  const cf1 = document.querySelector('#pw-cf1')
  let pw = document.querySelector('#pw').value;

  cf1.innerHTML = '';
  let str = '';

  if(pw == userPw){
    str += '비밀번호가 일치합니다'
    cf1.insertAdjacentHTML('afterbegin',str)
    cf1.style="color: forestgreen;"
  }
  else{
    str += '비밀번호를 확인해주시길 바랍니다'
    cf1.insertAdjacentHTML('afterbegin',str)
    cf1.style = "color: red;";
  }

}

const pwCF2 = (userPw,userCode) => {
  let newPw1 = document.querySelector('#newPw1').value;
  let newPw2 = document.querySelector('#newPw2').value;
  let pw = document.querySelector('#pw').value;

  // console.log(newPw1)
  // console.log(newPw2)

  if(newPw1 == newPw2 && pw == userPw){
    alert('비밀번호 변경이 완료되었습니다.')
  }
  else{
    alert('비밀번호를 다시 확인해주시길 바랍니다')
    document.querySelector('#newPw1').focus();
    return;
  }
  fetch('/changePw', { //요청경로
    method: 'POST',
    cache: 'no-cache',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    //컨트롤러로 전달할 데이터
    body: new URLSearchParams({
       // 데이터명 : 데이터값
        userPw : newPw2,
        userCode : userCode
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
    location.href='/logout'
  })
  //fetch 통신 실패 시 실행 영역
  .catch(err=>{
    alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
    console.log(err);
  });



  
}


