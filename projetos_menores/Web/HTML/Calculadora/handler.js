var textArea;
var button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
var buttonSom, buttonSub, buttonMul, buttonDiv, buttonEquals, buttonClear;

onload = () => {
    textArea = document.querySelector('#visor p');
    button0 = document.getElementById("0btn").onclick = () => { addToOperation(0); }
    button1 = document.getElementById("1btn").onclick = () => { addToOperation(1); }
    button2 = document.getElementById("2btn").onclick = () => { addToOperation(2); }
    button3 = document.getElementById("3btn").onclick = () => { addToOperation(3); }
    button4 = document.getElementById("4btn").onclick = () => { addToOperation(4); }
    button5 = document.getElementById("5btn").onclick = () => { addToOperation(5); }
    button6 = document.getElementById("6btn").onclick = () => { addToOperation(6); }
    button7 = document.getElementById("7btn").onclick = () => { addToOperation(7); }
    button8 = document.getElementById("8btn").onclick = () => { addToOperation(8); }
    button9 = document.getElementById("9btn").onclick = () => { addToOperation(9); }
    buttonSom = document.getElementById("som-btn").onclick = () => { addToOperation('+'); }
    buttonSub = document.getElementById("sub-btn").onclick = () => { addToOperation('-'); }
    buttonMul = document.getElementById("mul-btn").onclick = () => { addToOperation('x'); }
    buttonDiv = document.getElementById("div-btn").onclick = () => { addToOperation('/'); }
    buttonEquals = document.getElementById("equals-btn").onclick = () => { updateVisor(doOperation()) }
    buttonClear = document.getElementById("clear-btn").onclick = clearVisor;
}

function updateVisor(value){ textArea.innerHTML = value; }
function clearVisor() { textArea.innerHTML = ''; }

//adiciona os símbolos no visor
function addToOperation(value){
    let text = textArea.innerHTML;
    text = text == '' ? ('' + value) : (text += value);
    textArea.innerHTML = text;
}

//retorna um objeto com os dois valores e a operação
function parseOperation(){
    let text = textArea.innerHTML;
    if(text == '') return 'not defined operation';

    let value1 = '', value2 = '', type = '';
    let checker = 0;

    for(let i = 0; i < text.length; i++){
        //controla a leitura das entradas, caso for um símbolo, faz a leitura e já pula o contador duas vezes
        //primeiro valor (1-n), símbolo (1-1), segundo valor (1-n)
        if(text.charAt(i) < '0' || text.charAt(i) > '9') checker++;

        //checagem de limite de operações
        if(checker > 2){
            return 'out of bounds';
        }

        if(checker==0){
            value1 += text.charAt(i);
        }else if(checker==1){
            type += text.charAt(i);
        }else{
            value2 += text.charAt(i);
        }

        if(text.charAt(i) < '0' || text.charAt(i) > '9') checker++;
    }

    return {
        v1: value1,
        v2: value2,
        operation: type
    };
}

function doOperation(){
    let base = parseOperation();
    if(typeof(base) == 'string'){
        console.log(base);
        return base;
    }

    let result = 0;

    if(base.operation == '+'){
        result = parseInt(base.v1) + parseInt(base.v2);
    }else if(base.operation == '-'){
        result = parseFloat(base.v1) - parseFloat(base.v2);
    }else if(base.operation == 'x'){
        result = parseInt(base.v1) * parseInt(base.v2);
    }else{
        result = parseFloat(base.v1) / parseFloat(base.v2);
    }

    return result;
}
