const check = () => {
  const check_td = document.querySelector('.check-td')
  let pw1 = document.querySelector('#pw1').value;
  let pw2 = document.querySelector('#pw2').value;
  console.log(pw1)
  console.log(pw2)

  check_td.innerHTML = '';
  let str = '';
  
    if(pw2.length == pw1.length){
      if(pw2 == pw1){
        str += '비밀번호가 일치합니다';
        check_td.insertAdjacentHTML("afterbegin",str);
        check_td.style="color: forestgreen;"
      }
      else{
        str += '비밀번호를 다시 확인해주시길 바랍니다'; 
        check_td.insertAdjacentHTML("afterbegin",str)
        check_td.style = '';
      }
    }
  

}