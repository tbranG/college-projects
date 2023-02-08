onload = () => {
	document.getElementById("submit-button").onclick = sendInfo;
}

function sendInfo(){
	let cpf = document.getElementById("cpf-usuario").value;
	let novaSenha = document.getElementById("nova-senha").value;
	
	location.href = `/recuperar/send?cpf=${cpf}&novaSenha=${novaSenha}`;
}