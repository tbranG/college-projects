
var gameOpt;    //objeto que contêm as opções disponíveis de jogos
var gameBib;    //objeto com a biblioteca de jogos
var bibId;      //ID da minha biblioteca

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

    // mensagem de bem-vindo
    const logado = objDados.usuarios.find(usuario => usuario.id == loginStatus.id);
    const bemVindo = document.querySelector("#areaBemVindo");
    bemVindo.innerHTML = `<p>Bem vindo ${logado.login}! <button id="sair">Sair</button></p>`;
    document.querySelector("#sair").addEventListener('click', () => {
        let _obj = { login : 0, id : 0};
        localStorage.setItem('status', JSON.stringify(_obj));
        window.location.href = '../index.html';
    });

    // requisitando a biblioteca global
    gameBib = JSON.parse(localStorage.getItem('bd'));
    
    // definindo o usuario
    let status = JSON.parse(localStorage.getItem('status'));
    const listaDeUsuarios = JSON.parse(localStorage.getItem('users'));
    let thisUsuario = listaDeUsuarios.usuarios.find(usuario => usuario.id == status.id);

    // encontrando a biblioteca do usuario
    let qtdBibliotecas = Object.keys(gameBib.bibliotecas).length;
    for(let i = 0; i < qtdBibliotecas; i++)
        if(gameBib.bibliotecas[i].userId == status.id) { bibId = i; }
    
    // requisitando a lista de jogos
    $.getJSON('../banco_jogos.json', function(json){
        
        gameOpt = json;
        
        // autocomplete para novos jogos
        const dataNewGames = document.querySelector('#new-game-list');
        dataNewGames.innerHTML = '';
        let numJogos = Object.keys(gameOpt.disponiveis).length;

        for(let i = 0; i < numJogos; i++){
            let newOption = document.createElement('option');
            newOption.setAttribute('value', gameOpt.disponiveis[i].nome);
            dataNewGames.append(newOption);
        }
    });
    
    // autocomplete jogos da biblioteca
    const dataLibGames = document.querySelector('#lib-game-list');
    dataLibGames.innerHTML = '';
    let numJogos = Object.keys(gameBib.bibliotecas[bibId].games).length;
    for(let i = 0; i < numJogos; i++){
        let newOption = document.createElement('option');
        newOption.setAttribute('value', gameBib.bibliotecas[bibId].games[i].nome);
        dataLibGames.append(newOption);
    }

    // eventos de controle
    document.querySelector('.lib-game-input').onchange = getPlataformaBiblioteca;
    document.querySelector('.new-game-input').onchange = updatePlataformaNew;
    document.querySelector('.submit-button').onclick = trocarJogos;
}

// autocomplete para plataforma dos jogos novos
// lista de jogos novos é fixa
function updatePlataformaNew(){
    
    let gameName = $('.new-game-input').val();
    let thisGame = gameOpt.disponiveis.find(game => game.nome === gameName);

    if(thisGame == null){
        const dataNewPlatform = document.querySelector('#new-platform-list');
        dataNewPlatform.innerHTML = '';

        let capa = document.querySelector('.jogo-troca img');
        capa.setAttribute('src', '../imgs/Wireframe.png');

        return;
    }

    // defininfo a capa do jogo
    let capa = document.querySelector('.jogo-troca img');
    capa.setAttribute('src', thisGame.capa);


    const dataNewPlatform = document.querySelector('#new-platform-list');
    dataNewPlatform.innerHTML = '';
    let qtdPlataformas = Object.keys(thisGame.plataformas).length;
    for(let i = 0; i < qtdPlataformas; i++){
        let newOption = document.createElement('option');
        newOption.setAttribute('value', thisGame.plataformas[i]);
        dataNewPlatform.append(newOption); 
    }
}

// pega a plataforma do jogo escolhido
function getPlataformaBiblioteca(){

    let gameName = $('.lib-game-input').val();
    let thisGame = gameBib.bibliotecas[bibId].games.find(game => game.nome === gameName);

    if(thisGame == null){
        const dataLibPlatform = document.querySelector('#lib-platform-list');
        dataLibPlatform.innerHTML = '';

        let capa = document.querySelector('.meu-jogo img');
        capa.setAttribute('src', '../imgs/Wireframe.png');

        return;
    }

    // definido a capa do jogo
    let capa = document.querySelector('.meu-jogo img');
    capa.setAttribute('src', thisGame.capa);

    const dataLibPlatform = document.querySelector('#lib-platform-list');
    dataLibPlatform.innerHTML = `<option value="${thisGame.plataforma}">`;

}

function trocarJogos(){

    // recuperando informações na biblioteca
    let gameName = $('.lib-game-input').val();
    let gamePlatform = $('.lib-platform-input').val();
    let qtdJogos = Object.keys(gameBib.bibliotecas[bibId].games).length;

    // checagem de erro 1: Campos vazios
    if(gameName == '' || gamePlatform == ''){
        alert('Campos vazios. Escolha os jogos e tente novamente');
        return;
    }

    // indice do jogo na biblioteca
    let gameIndex;

    for(let i = 0; i < qtdJogos; i++){
        if(gameBib.bibliotecas[bibId].games[i].nome == gameName){
            if(gameBib.bibliotecas[bibId].games[i].plataforma == gamePlatform){
                gameIndex = i;
            }
        }
    }

    // removendo o jogo da biblioteca
    gameBib.bibliotecas[bibId].games.splice(gameIndex, 1);

    // adicionando jogo selecionado na biblioteca
    let newGameName = $('.new-game-input').val();
    let newGamePlatform = $('.new-platform-input').val();

    // checagem de erro 2: Campos vazios
    if(newGameName == '' || newGamePlatform == ''){
        alert('Campos vazios. Escolha os jogos e tente novamente');
        return;
    }

    // criando objeto do novo jogo
    let novo_jogo = {
        nome : newGameName,
        plataforma : newGamePlatform,
        id : 'bd' + gameBib.bibliotecas[bibId].idSufix++,
        capa : gameOpt.disponiveis.find(game => game.nome == newGameName).capa
    }

    // enviando novo banco de dados
    gameBib.bibliotecas[bibId].games.push(novo_jogo);
    localStorage.setItem('bd', JSON.stringify(gameBib));

    alert('Troca realizada com sucesso');

    window.location.reload();
}
