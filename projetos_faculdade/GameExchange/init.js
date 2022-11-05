onload = () => {
    let loginStatus = JSON.parse(localStorage.getItem('status'));
    const objDados = JSON.parse(localStorage.getItem('users'));

    if(loginStatus != null){
        if(loginStatus.login != 0){
            const logado = objDados.usuarios.find(usuario => usuario.id == loginStatus.id);
            const bemVindo = document.querySelector("#areaBemVindo");
            bemVindo.innerHTML = `<p>Bem vindo ${logado.login}! <button id="sair">Sair</button></p>`;
            document.querySelector("#sair").addEventListener('click', () => {
                let _obj = { login : 0, id : 0};
                localStorage.setItem('status', JSON.stringify(_obj));
                window.location.reload();
            });
        }
    }else{
        // cria o stats de login
        // 0 - não está logado
        let _obj = { login : 0, id : 0};
        localStorage.setItem('status', JSON.stringify(_obj));
    }

    if(objDados == null){
        let userKey = {
            usuarios : [
                {
                    id : 1,
                    login : "admin",
                    senha : "123",
                    email : "admin@email.com",
                    nome : "Administrador",
                    sobrenome : "",
                    nascimento : "",
                    assinante : 1
                }
            ]
        }
        localStorage.setItem('users', JSON.stringify(userKey));
    }
}