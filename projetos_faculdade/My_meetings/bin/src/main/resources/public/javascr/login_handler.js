onload = () => {
	document.getElementById("login-anchor").onclick = callLogin;
}

function callLogin(){
	let log = document.getElementById("login-nome").value;
	let pass = document.getElementById("login-senha").value;
	
	document.location.href = `http://localhost:4567/autenticar?login=${log}&password=${pass}`;
}