var id;

onload = () => {
    
    let query = new URLSearchParams(window.location.search);
    id = parseInt(query.get('id'));

    initOverview();
}



function initOverview(){
    fetch(`https://api.themoviedb.org/3/movie/${id}?api_key=6eb1ea09669445a6419e12259aec26f3&language=pt-BR`)
        .then(resp => resp.json())
        .then(function(data){
            console.log(data);
            //recuperando todos os recursos
            let title = data.title;
            let original_title = data.original_title;
            let poster = data.poster_path
            let banner = data.backdrop_path;
            let overview = data.overview;
            let genresCount = Object.keys(data.genres);
            let companyCount = Object.keys(data.production_companies);

            //adicionando os recursos nas devidas seções
            //seção principal
            $('.card').append($(`<img src="https://image.tmdb.org/t/p/original/${poster}">`));
            $('.overview h2').text(title);       
            $('.movie-details').css('background-image',`url(https://image.tmdb.org/t/p/original/${banner})`);
            $('.movie-details').css('background-size', 'cover');

            $('.info').append($(`<div><p>${data.overview}</p></div>`));
            $('.info').append($(`<div><p>Lançamento: ${dateFix(data.release_date)}</p></div>`));
            $('.info').append($(`<div><p>Nota: ${data.vote_average}</p></div>`));
            
            let genresDiv = $('<div class="genres"></div>');
            for(let i = 0; i < genresCount.length; i++){
                genresDiv.append($(`<h4>${data.genres[i].name}</h4>`));
            }

            $('.info').append($('<h3>Categorias</h3>'));
            $('.info').append(genresDiv);

            let companiesDiv = $('<div class="companies"></div>');
            for(let i = 0; i < companyCount.length; i++){
                if(data.production_companies[i].logo_path == null) continue;
                companiesDiv.append($(`<img src="https://image.tmdb.org/t/p/original/${data.production_companies[i].logo_path}">`));
            }

            $('.info').append($('<h3>Estúdios</h3>'));
            $('.info').append(companiesDiv);

            //informações extras
            $('.movie-extra-info').append($(`<p>Título original: ${original_title}</p>`));
            $('.movie-extra-info').append(`<p>Idioma original: ${data.original_language}</p>`);
            $('.movie-extra-info').append($(`<p>Status: ${data.status == 'Released' ? 'Disponível' : 'Em breve'}</p>`));
            $('.movie-extra-info').append($(`<p>Orçamento: ${data.budget == 0 ? 'Não informado': numToCurrency(data.budget)}</p>`));
        })
        .catch(err => console.log("URL inválida"));
}

function numToCurrency(valor){
    let casas = 0;
    let digitos = [];
    let numString = '';

    while(valor >= 1){
        
        digitos.push(Math.floor(valor % 10));
        valor /= 10;
        casas++;
    }

    let iterac = 1;
    for(let i = 0; i < casas;){
        if(iterac % 4 == 0){
            numString = '.' + numString;
            iterac++;
        }
        else{
            numString = digitos[i] + numString;
            iterac++;
            i++;
        }
    }
    numString = numString + ',00';
    numString = '$' + numString;

    console.log(digitos);

    return numString;
}

function dateFix(data){
    let newList = data.split('-');
    let newDate = newList[2] + '/' + newList[1] + '/' + newList[0];
    return newDate;
}