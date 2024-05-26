
// controle de status de login
let loginStatus = JSON.parse(localStorage.getItem('status'));
const objDados = JSON.parse(localStorage.getItem('users'));
const thisUsuario = objDados.usuarios.find(usuario => usuario.id == loginStatus.id);

let statusCode = loginStatus.login;
if(statusCode == 0){
    alert("O usuário não está logado");
    window.location.href = "../login/login.html";
} 

// checando o status da assinatura
if(thisUsuario.assinante == 0){
    alert("O usuário não possuí uma assinatura");
    window.location.href = "../index.html";
}

onload = () => {
    
    // mensagem de bem-vindo!
    const logado = objDados.usuarios.find(usuario => usuario.id == loginStatus.id);
    const bemVindo = document.querySelector("#areaBemVindo");
    bemVindo.innerHTML = `<p>Bem vindo ${logado.login}! <button id="sair">Sair</button></p>`;
    document.querySelector("#sair").addEventListener('click', () => {
        let _obj = { login : 0, id : 0};
        localStorage.setItem('status', JSON.stringify(_obj));
        window.location.href = '../index.html';
    });

    $.getJSON("../banco_jogos.json", function(json){

        let gameQtd = Object.keys(json.disponiveis);

        for(let i = 0; i < gameQtd.length; i++){
            $('.game-thumbs').append($(`<a href="../info/info.html?index=${i}"><img src="${json.disponiveis[i].capa}"></a>`));
            $('.game-list ul').append($(`<li>${json.disponiveis[i].nome}</li>`));
        }
    });
}