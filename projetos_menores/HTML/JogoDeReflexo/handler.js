var timer = 3;
var animFinished = false;
var canShoot = false;
var winner = '';

var counter;

onload = () => {
    
    counter = document.querySelector('.countdown');
    
    document.querySelector('.player1-win').setAttribute('style', 'visibility: hidden;');
    document.querySelector('.player2-win').setAttribute('style', 'visibility: hidden;');

    setTimeout(() => {
        
        console.log("Animação terminou");
        animFinished = true;
        counter.setAttribute('style', 'visibility: visible;');   

        setTimeout(() => {

            canShoot = true;
            console.log("Pode atirar");
            counter.setAttribute('style', 'visibility: hidden;');  
        }, 3000);
    }, 2500);
    
    addEventListener('keydown', fire);
    addEventListener('keydown', (key) => {
        if(key.keyCode == 82) window.location.reload();
    });
}

function fire(key){
    if(!canShoot) return;
    if(winner != ''){
        console.log("Já temos um vencedor");
        return;
    }

    if(key.keyCode == 32){
    
        document.querySelector('.shoot img').setAttribute('class', 'call-shot');

        console.log("Player 1 venceu");
        winner = 'player1';
        document.querySelector('.player1-win').setAttribute('style', 'visibility: visible;');
        const p2 = document.querySelector('.player2');
        p2.setAttribute('style', 'display: none;');
    }
    else if(key.keyCode == 13){

        document.querySelector('.shoot img').setAttribute('class', 'call-shot');

        console.log("Player 2 venceu");
        winner = 'player2';
        document.querySelector('.player2-win').setAttribute('style', 'visibility: visible;');
        const p1 = document.querySelector('.player1');
        p1.setAttribute('style', 'display: none;');
    }
}