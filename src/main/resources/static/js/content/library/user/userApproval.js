document.querySelector('#all').addEventListener('change', checkAll);

function checkAll(){
    const ones = document.querySelectorAll('.one');
    const all = document.querySelector('#all');

    const isChecked = all.checked;

    ones.forEach(chk => {
        chk.checked = isChecked;
    });
}