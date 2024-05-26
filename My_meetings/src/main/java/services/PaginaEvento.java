package services;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;

import models.Evento;
import models.Usuario;
import dao.EventoDAO;
import dao.ControleEvento;
import dao.ControleSessao;

import spark.Request;
import spark.Response;

public class PaginaEvento {
	
	public static String createPaginaEvento(Request req, Response res) {
		ControleEvento contE = new ControleEvento();
		ControleSessao contS = new ControleSessao();
		EventoDAO eDao = new EventoDAO();
		Evento e = eDao.getEvento(Integer.parseInt(req.params("id")));
		Usuario u = contS.recuperarUsuario(Integer.parseInt(req.cookie("key")));
		String userH = "<h5>" + u.getNome() + " " + u.getSobrenome() + "</h5>";
		contS.disconnect();
		
		String page = "";
		
		try {
			Scanner scan = new Scanner(new File("src/main/resources/public/evento.html"));
			while(scan.hasNextLine()) {
				page += scan.nextLine() + "\n";
			}
		}catch(FileNotFoundException err){
			System.out.println(err.getMessage());
		}
		
		//insersao das informacoes do evento
		String titulo = e.getNome();
		String tipo = "" + (e.getPrivacidade() ? "PÃºblico" : "Privado");
		String linkChat = "<a href=\"/chat/" + e.getId() + "\" class=\"btn btn-custom\">Chat</a>";
		String linkConfirmados = "<a href=\"/confirmados/" + e.getId() + "\" class=\"btn btn-custom\">Lista de confirmados</a>";
		String desc = e.getDescricao();
		String data = e.getData();
		String[] dataParts = data.split("-");
		data = dataParts[2] + "/" + dataParts[1] + "/" + dataParts[0];
		String hora = e.getHorario();
		String local = e.getEndereco();
		String count = "" + e.getQtdParticipantes() + "/" + e.getMaxParticipantes();
		
		page = page.replaceFirst("<NOME-EVENTO>",titulo);
		page = page.replaceFirst("<TIPO>",tipo);
		page = page.replaceFirst("<DESC>",desc);
		page = page.replaceFirst("<DATA>", data);
		page = page.replaceFirst("<HORA>",hora);
		page = page.replaceFirst("<LOCAL>",local);
		page = page.replaceFirst("<COUNT>", count);
		page = page.replaceFirst("<LOGIN-BUTTON>", "<li class=\"nav-item\"><a id=\"user-view\"><i class=\"fa-solid fa-circle-user\"></i></a></li>");
		page = page.replaceFirst("<USER-NAME>", userH);
		
		//checa se o evento e publico
		if(e.getPrivacidade()) {
			page = page.replaceFirst("<BOTAO-PARTICIPAR>", "<a href=\"/entrarEvento/" + e.getId() + "\" class=\"btn btn-custom\">Participar</a>");
			page = page.replaceFirst("<LINK-CHAT>", linkChat);
			page = page.replaceFirst("<LINK-CONFIRMADOS>", linkConfirmados);
		}else {
			//checa se é o criador do evento ou está participando do evento
			if(contE.checarParticipacao(e.getId(), u.getCpf())) {
				System.out.println("Está participando do evento privado");
				page = page.replaceFirst("<BOTAO-PARTICIPAR>", "<a href=\"/entrarEvento/" + e.getId() + "\" class=\"btn btn-custom\">Participar</a>");
				page = page.replaceFirst("<LINK-CHAT>", linkChat);
				page = page.replaceFirst("<LINK-CONFIRMADOS>", linkConfirmados);
			}
			else if(e.getDono().equals(u.getCpf())) {
				System.out.println("É o dono");
				page = page.replaceFirst("<BOTAO-PARTICIPAR>", "<a href=\"/entrarEvento/" + e.getId() + "\" class=\"btn btn-custom\">Participar</a>");
				page = page.replaceFirst("<LINK-CHAT>", linkChat);
				page = page.replaceFirst("<LINK-CONFIRMADOS>", linkConfirmados);
			}
		}
		
		
		//cria a lista de participantes
		String listaPartic = "";
		List<Usuario> users = contE.recuperarParticipantes(Integer.parseInt(req.params("id")));
		
		listaPartic = "<ul class=\"list-group\">\n";
		for(int i = 0; i < users.size(); i++) {
			listaPartic += "<li class=\"list-group-item\">" + "<div>" + users.get(i).getNome() + " " + users.get(i).getSobrenome() + "</div><div><a href=\"/addAmizade/" + users.get(i).getCpf() + "\">" + "<i class=\"fa-solid fa-user-plus\"></i>" + "</a></div>" + "</li>\n";
		}
		listaPartic += "</ul>\n";
		page = page.replaceFirst("<LISTA-PARTICIPANTES>", listaPartic);
		
		return page;
	}
}
