window.onload = function () {

    let newPw = document.getElementById('newPw');
    let renewPw = document.getElementById('renewPw');

    renewPw.addEventListener("change", function(e) {
        if(newPw.value == '' || newPw.value == null || renewPw.value == '' || renewPw.value == null) {
            // 지나감...
        } else {
            if(newPw.value == renewPw.value) {
                document.getElementById('pwSuccess').classList.remove('d-none');
                document.getElementById('pwFail').classList.add('d-none');
             } else {
                document.getElementById('pwSuccess').classList.add('d-none');
                document.getElementById('pwFail').classList.remove('d-none');
                document.getElementById('newPw').value='';
                document.getElementById('renewPw').value='';
            }
        }
    });

    newPw.addEventListener("change", function(e){
        document.getElementById('renewPw').value='';
        document.getElementById('pwSuccess').classList.add('d-none');
        document.getElementById('pwFail').classList.add('d-none');
    });

};