onload = displayResults;

function displayResults(){

    let query = new URLSearchParams(window.location.search);
    let movieName = query.get('name');

    fetch(`https://api.themoviedb.org/3/search/movie?api_key=6eb1ea09669445a6419e12259aec26f3&language=pt-BR&query=${movieName}`)
        .then(resp => resp.json())
        .then(function(data){
            let arr = data.results;
            let size = Object.keys(arr);
            console.log(size.length);

            document.querySelector('.results-value').innerHTML = size.length;
            for(let i = 0; i < 20; i++){
                let box = $('.results-content');
                let newContent = $(`
                    <div style="display: block;">
                        <a href="../detalhes/detalhes.html?id=${data.results[i].id}">
                            <img src="https://image.tmdb.org/t/p/original/${data.results[i].poster_path}" style="float: left;">
                            <h3>${data.results[i].title}</h3>
                            <h5>${dateFix(data.results[i].release_date)}</h5>
                        </a>
                    </div>
                `);
                box.append(newContent);
            }

        })
        .catch(err => console.log(err));
}

function dateFix(data){
    let newList = data.split('-');
    let newDate = newList[2] + '/' + newList[1] + '/' + newList[0];
    return newDate;
}