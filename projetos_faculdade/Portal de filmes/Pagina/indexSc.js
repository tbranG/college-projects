onload = () => {
    initSectionNew();
    initSectionSpotlight();
}

function initSectionNew(){

    fetch('https://api.themoviedb.org/3/trending/movie/week?api_key=6eb1ea09669445a6419e12259aec26f3&language=pt-BR')
        .then(resp => resp.json())
        .then(function(data){
            let qtd = 3;

            let cards = document.querySelectorAll('.movie-card');
            let titles = document.querySelectorAll('.movie-title');
            let content = document.querySelectorAll('.extra-info');

            for(let i = 0; i < qtd; i++){
               
                // setup dos cards
                let anchor = document.createElement('a');
                anchor.setAttribute('href', `../detalhes/detalhes.html?id=${data.results[i].id}`);
                let img = document.createElement('img');
                img.setAttribute('src', `https://image.tmdb.org/t/p/original/${data.results[i].poster_path}`);
                anchor.prepend(img);
                cards[i].prepend(anchor); 

                //setup conteúdo,3
                titles[i].innerHTML = `${data.results[i].title}`;
                let parag = document.createElement('p');
                parag.innerHTML = `${data.results[i].overview}`;
                content[i].prepend(parag);

                let stats = document.createElement('p');
                stats.innerHTML = `Lançamento: ${dateFix(data.results[i].release_date)}<br>Nota: ${data.results[i].vote_average}`;
                content[i].append(stats);
            }
        })
        .catch(err => console.log("Não encontrado"));

}

function initSectionSpotlight(){
    fetch('https://api.themoviedb.org/3/trending/movie/day?api_key=6eb1ea09669445a6419e12259aec26f3&language=pt-BR')
        .then(resp => resp.json())
        .then(function(data){
            let qtd = 5;
            for(let i = 0; i < qtd; i++){
                let group = $('<div></div>');
                
                group.append($(`<a href="../detalhes/detalhes.html?id=${data.results[i].id}"><img src="https://image.tmdb.org/t/p/original/${data.results[i].poster_path}"></a>`));
                group.append($(`<h3>${data.results[i].title}</h3>`));

                $('.spotlight-cards').append(group);
            }
        })
        .catch(err => console.log('Não encontrado'));
}

function dateFix(data){
    let newList = data.split('-');
    let newDate = newList[2] + '/' + newList[1] + '/' + newList[0];
    return newDate;
}