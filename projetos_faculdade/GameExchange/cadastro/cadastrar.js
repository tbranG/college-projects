var userObj;

onload = () => {
    document.querySelector('.submit-button').onclick = checarInfo;
}

function checarInfo(){
    let fname = document.querySelector('#fname');
    let lname = document.querySelector('#lname');
    let usuario = document.querySelector('#uname');
    let senha = document.querySelector('#password');
    let cSenha = document.querySelector('#confirm');
    let email = document.querySelector('#email');
    let data = document.querySelector('#date');
    let termos = document.querySelector("#terms");
    // checagens de campo
    let verifica = 0;
    if(!certificaLogin(usuario)){
        verifica++;
    }
    if(!cetificaSenha(senha, cSenha)){
        verifica++;
    }
    if(!certificaEmail(email)){
        verifica++;
    }
    if(!certificaNascimento(data)){
        verifica++;
    }
    if(!certificaNome(fname)){
        verifica++;
    }
    if(!certificaSobrenome(lname)){
        verifica++;
    }
    if(!certificaTermos(termos)){
        verifica++;
    }
    if(verifica == 0){
        const novoUsuario = {
            login: usuario.value,
            senha: senha.value,
            email: email.value,
            nascimento: data.value,
            nome: fname.value,
            sobrenome: lname.value,
            assinante: 0
        };
        efetivarCadastro(novoUsuario);
    }
}

const certificaNascimento = nascimento => {
    const string = String(nascimento.value).replace('/','');
    if(string.length == 8){
        nascimento.style.border = "";
        return true;
    }else {
        nascimento.style.border = "solid #E8833A";
        alert('Voce deve digitar sua data de nascimento com apenas os 8 numeros (DD/MM/AAAA) para cadastrar!');
        return false
    }
}

const certificaEmail = email => {
    if(email.value.trim()){
        email.style.border = "";
        return true;
    }else {
        email.style.border = "solid #E8833A";
        return false
    }
}

const certificaSobrenome = sobrenome => {
    if(sobrenome.value.trim()){
        sobrenome.style.border = "";
        return true;
    }else {
        sobrenome.style.border = "solid #E8833A";
        return false
    }
}

const certificaNome = nome => {
    if(nome.value.trim()){
        nome.style.border = "";
        return true;
    }else {
        nome.style.border = "solid #E8833A";
        return false
    }
}

const certificaLogin = userName => {
    if(userName.value.trim()){
        userName.style.border = "";
        return true;
    }else {
        userName.style.border = "solid #E8833A";
        return false
    }
}

const certificaTermos = termos => {
    if(termos.checked) {
        return true;
    }else {
        alert('voce deve aceitar os termos para se cadastrar');
        return false;
    }
}

const cetificaSenha = (senha, cSenha) => {
    if(senha.value.trim() == cSenha.value.trim() && senha.value.trim()!=''){
        senha.style.border = "";
        cSenha.style.border = "";
        return true;
    }else {
        senha.style.border = "solid #E8833A";
        cSenha.style.border = "solid #E8833A";
        return false;
    }
}

function efetivarCadastro(usuario){

    let key = JSON.parse(localStorage.getItem('users'));
    let nid = (Object.keys(key.usuarios).length) + 1;

    let newUser = {
        id : nid,
        login : usuario.login,
        senha : usuario.senha,
        email : usuario.email,
        nome : usuario.nome,
        sobrenome : usuario.sobrenome,
        nascimento : usuario.nascimento,
        assinante : 0
    }


    key.usuarios.push(newUser);
    localStorage.setItem('users', JSON.stringify(key));

    alert("Cadastro realizado com sucesso");
    window.location.href = "../login/login.html";
}
