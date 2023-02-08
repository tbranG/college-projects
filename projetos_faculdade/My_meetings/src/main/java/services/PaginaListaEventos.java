package services;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

import spark.Request;
import spark.Response;

import dao.*;
import ia.RecomendadorEventos;
import models.*;

public class PaginaListaEventos {
	
	public static String createListaEventos(Request req, Response res) {
		//criando a pagina
		ControleEvento contE = new ControleEvento();
		ControleSessao contr = new ControleSessao();
		Usuario user = contr.recuperarUsuario(Integer.parseInt(req.cookie("key")));
		
		String page = "";
		String userH = "<h5>" + user.getNome() + " " + user.getSobrenome() + "</h5>";
		
		try {			
			Scanner scan = new Scanner(new File("src/main/resources/public/listaEventos.html"));
			while(scan.hasNext()) {
				page += scan.nextLine() + "\n";
			}
			page = page.replaceFirst("<LOGIN-BUTTON>", "<li class=\"nav-item\"><a id=\"user-view\"><i class=\"fa-solid fa-circle-user\"></i></a></li>");
			page = page.replaceFirst("<USER-NAME>", userH);
		}catch(FileNotFoundException err){
			System.out.println(err.getMessage());
		}
		
		//criando os cards
		EventoDAO eDao = new EventoDAO();
		List<Evento> eventos = eDao.getAllEventos();
		String[] cards = new String[eventos.size()];
		String div = "<div class=\"card-group\">\n";
		
		for(int i = 0; i < eventos.size(); i++) {
			cards[i] = "";
			cards[i] += "<div class=\"col-md-3 card-custom\">\n";
			cards[i] += "<h1>" + eventos.get(i).getNome() + "</h1>\n";
            cards[i] += "<h5 class=\"text-primary\">Tipo: " + (eventos.get(i).getPrivacidade() ? "Público" : "Privado") + "</h5>\n";
            cards[i] += "<h3>Descrição:</h3>\n";
            cards[i] += "<p class=\"text-dark\">" + eventos.get(i).getDescricao() + "</p>\n";
            cards[i] += "<a href=\"/evento/"+ eventos.get(i).getId() +"\" class=\"btn btn-outline-light btn-custom\">Mais informações</a>\n";
            cards[i] += "</div>\n";
            
            div += cards[i];
		}
		div += "</div>\n";
		
		page = page.replaceFirst("<CARDS>", div);
		
		//--- recomendações ---
		
		List<Evento> eventosUsuario = contE.recuperarEventosDaPessoa(user.getCpf());
		List<Evento> naoParticipa = new ArrayList<Evento>();
		
		//recupera os eventos que o usuario nao participa
		for(int i = 0; i < eventos.size(); i++) {
			boolean repetido = false;
			for(int j = 0; j < eventosUsuario.size(); j++) {
				if(eventos.get(i).getId() == eventosUsuario.get(j).getId()) {
					repetido = true;
				}
			}
			if(!repetido) {
				naoParticipa.add(eventos.get(i));
			}
		}
		
		//se não houver eventos
		if(naoParticipa.size() == 0) {			
			page = page.replaceFirst("<CARDS-REC>", "<h3>Não há recomendações.</h3>");
		}else {
			int recomend = 0;
			String rDiv = "<div class=\"recommend-group\">\n";
			for(int i = 0; i < naoParticipa.size(); i++) {
				if(recomend >= 3) break;
				
				double similaridade = RecomendadorEventos.verificarSimilaridade(naoParticipa.get(i), eventosUsuario);
				double amigos = RecomendadorEventos.verificarPessoas(naoParticipa.get(i), eventosUsuario);
				
				if(similaridade > 0.7 || amigos > 0.7) {
					rDiv += "<div class=\"col-md-3 card-custom-recommend\">\n";
					rDiv += "<h1>" + naoParticipa.get(i).getNome() + "</h1>\n";
		            rDiv += "<h5 class=\"text-primary\">Tipo: " + (naoParticipa.get(i).getPrivacidade() ? "Público" : "Privado") + "</h5>\n";
		            rDiv += "<h3>Descrição:</h3>\n";
		            rDiv += "<p class=\"text-dark\">" + naoParticipa.get(i).getDescricao() + "</p>\n";
		            rDiv += "<a href=\"/evento/"+ naoParticipa.get(i).getId() +"\" class=\"btn btn-outline-light btn-custom-recommend\">Mais informações</a>\n";
		            rDiv += "</div>\n";
		            recomend++;
				}
			}
			
			if(recomend <= 0) {
				page = page.replaceFirst("<CARDS-REC>", "<h3>Não há recomendações.</h3>");
			}else {
				page = page.replaceFirst("<CARDS-REC>", rDiv);
			}
		}
		
		return page;
	}
}
