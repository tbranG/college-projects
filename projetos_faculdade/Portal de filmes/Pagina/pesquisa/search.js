
addEventListener('load', () => {
    document.querySelector('.search-bar button').onclick = setupSearch;
});

function setupSearch(){
    let searchText = $('.search-bar input').val();

    if(searchText == ''){
        alert("Digite o nome de um filme");
        return;
    }
    window.location.href = `../pesquisa/pesquisa.html?name=${searchText}`;
}