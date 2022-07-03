
addEventListener('load', () => {
    addEventListener('resize', setMenu);
    document.querySelector('.menu-toggle').onclick = toggleMenu;
});

function setMenu(){
    let menuDiv = $('.menu-search > nav');
    if($(window).width() >= 681){   
        menuDiv.removeClass('menu-mobile');
        menuDiv.addClass('menu');
        
        //correção de bug do menu
        menuDiv.css('visibility', 'visible');
        
    }else{
        menuDiv.removeClass('menu');
        menuDiv.addClass('menu-mobile');
        
        //forçar menu a iniciar fechado
        menuDiv.css('visibility', 'hidden');
    }
}

function toggleMenu(){
    let menuDiv = $('.menu-search > nav');
    let rule = menuDiv.css('visibility');
    
    if(rule === 'hidden'){
        menuDiv.css('visibility', 'visible');
    }else{        
        menuDiv.css('visibility', 'hidden');
    }
}