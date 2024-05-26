
// recuperando o status de login
const loginStatus = JSON.parse(localStorage.getItem('status'));
const objDados = JSON.parse(localStorage.getItem('users'));
const thisUsuario = objDados.usuarios.find(usuario => usuario.id == loginStatus.id);

if(loginStatus.login == 0){
    alert('O usuário não está logado');
    window.location.href = '../login/login.html';
}

onload = () => {
    document.querySelector('#monthly').addEventListener('click',() => { switchServiceType('monthly'); });
    document.querySelector('#yearly').addEventListener('click', () => { switchServiceType('yearly'); });

    document.querySelector('.submit-button').onclick = checarInfo;
}

function switchServiceType(type){
    let month_checkbox = document.querySelector('#monthly');
    let year_checkbox = document.querySelector('#yearly');

    if(type == 'monthly'){
        month_checkbox.checked = true;
        year_checkbox.checked = false;
    }else if(type == 'yearly'){
        month_checkbox.checked = false;
        year_checkbox.checked = true;
    }else{
        console.log("Tipo inválido");
    }
}

function checarInfo(){
    //recuperando os campos
    let campoCartao = document.querySelector('#card-info');
    let campoData = document.querySelector('#date-info');
    let campoCodigo = document.querySelector('#code-info');
    let campoNome = document.querySelector('#owner-info');

    let month_checkbox = document.querySelector('#monthly');
    let year_checkbox = document.querySelector('#yearly');

    //checagem da seleção da assinatura
    if(month_checkbox.checked == false && year_checkbox.checked == false){
        alert('Escolha um plano de assinatura');
        return;
    }
    
    //checagem dos campos vazios
    if(campoCartao.value == '' || campoData.value == '' || campoCodigo.value == '' || campoNome.value == ''){
        alert('Preencha todas as informações');
        return;
    }

    //definindo os valores
    let numCartao = String(campoCartao.value).replaceAll(" ","");
    let numData = String(campoData.value).replace("/","");
    let numCodigo = campoCodigo.value;

    //realizando as checagens
    if(!checarCartao(numCartao)){
        alert('Cartão inválido');
        return;
    }
    if(!checarData(numData)){
        alert('Data inválida');
        return;
    }
    if(!checarCodigo(numCodigo)){
        alert('Código inválido');
        return;
    }

    alert("Assinatura realizada com sucesso!");
    ativarAssinatura();
}

function checarCartao(cartao){
    let str = String(cartao);
    let valor = parseInt(str);

    if(valor == NaN) return false;
    if(str.length != 16) return false;

    return true;
}

function checarData(data){
    let str = String(data);
    let valor = parseInt(str);

    if(valor == NaN) return false;
    if(str.length != 4) return false;

    return true;
}

function checarCodigo(codigo){
    let str = String(codigo);
    let valor = parseInt(str);

    if(valor == NaN) return false;
    if(str.length != 3) return false;

    return true;
}

function ativarAssinatura(){
    
    // criando o novo banco de dados
    let size = Object.keys(objDados.usuarios).length;
    for(let i = 0; i < size; i++){
        if(objDados.usuarios[i].id == thisUsuario.id){
            objDados.usuarios[i].assinante = 1;
            break;
        }
    }

    localStorage.setItem('users', JSON.stringify(objDados));
    window.location.href = "../index.html";
}