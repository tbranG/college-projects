
onload = () => {
    
    let query = new URLSearchParams(window.location.search);
    let gameIndex = parseInt(query.get('index'));

    $.getJSON('../banco_jogos.json', function(json){
        let imageHolder = $('.image-holder');
        let descHolder = $('.desc-holder');

        imageHolder.append($(`<img src=${json.disponiveis[gameIndex].capa}>`));
        descHolder.append($(`<h3>${json.disponiveis[gameIndex].nome}</h3>`));
        descHolder.append($(`<p>${json.disponiveis[gameIndex].info}</p>`));

        let qtdPlataformas = Object.keys(json.disponiveis[gameIndex].plataformas).length;
        
        let box = $('<div><h4>Plataformas</h4></div>');
        let ul = $('<ul></ul>');

        for(let i = 0; i < qtdPlataformas; i++){
            ul.append(`<li>${json.disponiveis[gameIndex].plataformas[i]}</li>`);
        }
        box.append(ul);
        descHolder.append(box);
    });
    
}