// 개인정보 변경 
const changeMy = () => {
  const userTel = document.querySelector('#userTel').value
  const postCode = document.querySelector('#postCode').value
  const userAddr = document.querySelector('#userAddr').value
  const addrDetail = document.querySelector('#addrDetail').value
  const email = document.querySelector('#email').value
  const form = document.querySelector('#updateForm')


  if (userTel == '' || userTel == 'null') {
    alert('변경하실 정보를 입력해주세요')
    document.querySelector('#userTel').focus();
    return;
  }
  else if (postCode == '' || postCode == 'null') {
    alert('변경하실 정보를 입력해주세요')
    document.querySelector('#postCode').focus();
    return;
  }
  else if (userAddr == '' || userAddr == 'null') {
    alert('변경하실 정보를 입력해주세요')
    document.querySelector('#userAddr').focus();
    return;
  }
  else if (addrDetail == '' || addrDetail == 'null') {
    alert('변경하실 정보를 입력해주세요')
    document.querySelector('#addrDetail').focus();
    return;
  }
  else if (email == '' || email == 'null') {
    alert('변경하실 정보를 입력해주세요')
    document.querySelector('#email').focus();
    return;
  }
  else {
    form.submit();
  }

}





// 비밀번호 변경  
const pwCF2 = (userPw, userCode) => {
  let newPw1 = document.querySelector('#newPw1').value;
  let newPw2 = document.querySelector('#newPw2').value;
  let pw = document.querySelector('#pw').value;

  // console.log(newPw1)
  // console.log(newPw2)

  if ((newPw1 != newPw2) || (pw != userPw)) {
    alert('비밀번호를 확인해주세요');
    return;
  }

  fetch('/changePwFetch', { //요청경로
    method: 'POST',
    cache: 'no-cache',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    //컨트롤러로 전달할 데이터
    body: new URLSearchParams({
      // 데이터명 : 데이터값

      userPw: pw,
      newPw: newPw2,
      userCode: userCode
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
      alert('비밀번호 변경이 완료되었습니다.')
      location.href = '/logout'
    })
    //fetch 통신 실패 시 실행 영역
    .catch(err => {
      alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
      console.log(err);
    });




  // if(newPw1 == newPw2 && pw == userPw){
  //   alert('비밀번호 변경이 완료되었습니다.')
  // }
  // else{
  //   alert('비밀번호를 다시 확인해주시길 바랍니다')
  //   document.querySelector('#newPw1').focus();
  //   return;
  // }





}


