var state = false;

addEventListener("load", () => {
	document.getElementById("user-view").onclick = switchView;
});

function switchView(){
	let menu = document.querySelector(".user-menu");
	state = !state;
	
	if(state){
		menu.setAttribute("style", "visibility: visible");
	}else{
		menu.setAttribute("style", "visibility: hidden");
	}
}