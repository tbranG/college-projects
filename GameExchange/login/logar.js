
onload = () => {
    document.querySelector('.submit-button').onclick = checarInfo;
}

function checarInfo(){
    let users = JSON.parse(localStorage.getItem('users'));

    let uname = $('#username-field').val();
    let pass = $('#password-field').val();

    let userSize = Object.keys(users.usuarios);

    for(let i = 0; i < userSize.length; i++){
        if(uname === users.usuarios[i].login){
            if(pass === users.usuarios[i].senha){
                let newStatus = {
                    id : users.usuarios[i].id,
                    login : 1
                }
                localStorage.setItem('status', JSON.stringify(newStatus));
                window.location.href = "../index.html";
                return;
            }
        }
    }

    alert('não encontrado');
}