package services;

import java.util.Scanner;

import dao.EventoDAO;
import models.Evento;
import models.Usuario;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import dao.ControleSessao;

public class PaginaHome {

	/**
	 * Cria os cards dos eventos.
	 * Limite de 3 cards 
	 * */
	private static String createCards(String currentPage) {
		String[] cards = new String[3];
		EventoDAO eDao = new EventoDAO();
		List<Evento> eventos = eDao.getAllEventos();
		
		for(int i = 0; i < eventos.size(); i++) {
			if(i==3) break;
			cards[i] = "";
			cards[i] += "<div class=\"col-md-3 card-custom\">\n";
			cards[i] += "<h1>" + eventos.get(i).getNome() + "</h1>\n";
            cards[i] += "<h5 class=\"text-primary\">Tipo: " + (eventos.get(i).getPrivacidade() ? "Público" : "Privado") + "</h5>\n";
            cards[i] += "<h3>Descrição:</h3>\n";
            cards[i] += "<p class=\"text-dark\">" + eventos.get(i).getDescricao() + "</p>\n";
            cards[i] += "<a href=\"/evento/"+ eventos.get(i).getId() +"\" class=\"btn btn-outline-light btn-custom\">Mais informações</a>\n";
            cards[i] += "</div>\n";
            
            String name = "<CARD-" + (i+1) +">";
            currentPage = currentPage.replaceFirst(name, cards[i]);
		}
		
		return currentPage;
	}
	
	/**
	 * Cria a pagina principal para usuarios nao logados
	 * */
	public static String createPageUnlogged() {
		String page = "";
		
		try {			
			Scanner scan = new Scanner(new File("src/main/resources/public/home.html"));
			while(scan.hasNext()) {
				page += scan.nextLine() + "\n";
			}
			page = page.replaceAll("<LOGIN-BUTTON>", "<li class=\"nav-item\"><a href=\"/login\" class=\"btn btn-outline-light bg-secondary ml-4\"> Login </a></li>");
		}catch(FileNotFoundException err){
			System.out.println(err.getMessage());
		}		
		
		page = createCards(page);
		return page;
	}
	
	/**
	 * Cria a pagina principal para usuarios logados
	 * */
	public static String createPageLogged(Usuario user) {
		String page = "";
		String userH = "<h5>" + user.getNome() + " " + user.getSobrenome() + "</h5>";
		
		try {			
			Scanner scan = new Scanner(new File("src/main/resources/public/home.html"));
			while(scan.hasNext()) {
				page += scan.nextLine() + "\n";
			}
			page = page.replaceFirst("<LOGIN-BUTTON>", "<li class=\"nav-item\"><a id=\"user-view\"><i class=\"fa-solid fa-circle-user\"></i></a></li>");
			page = page.replaceFirst("<USER-NAME>", userH);
		}catch(FileNotFoundException err){
			System.out.println(err.getMessage());
		}
		
		page = createCards(page);
		return page;
	}
	
}
