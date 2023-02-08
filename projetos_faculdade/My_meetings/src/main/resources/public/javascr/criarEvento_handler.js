onload = () => {
	document.getElementById("submit-button").onclick = sendInfo;
}

function sendInfo(){
	let nome = document.getElementById("name-field").value;
	let data = document.getElementById("date-field").value;
	let horario = document.getElementById("time-field").value;
	let endereco = document.getElementById("address-field").value;
	let maxP = document.getElementById("maxP-field").value;
	let descricao = document.getElementById("info-field").value;
	let publico = document.getElementById("privacy-field").checked;
	
	location.href = `/criarEvento/send?nome=${nome}&data=${data}&horario=${horario}&endereco=${endereco}&maxP=${maxP}&descricao=${descricao}&publico=${publico}`;
}