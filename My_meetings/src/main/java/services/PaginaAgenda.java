package services;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

import spark.Request;
import spark.Response;

import dao.*;
import models.*;

public class PaginaAgenda {
	
	public static String criarAgenda(Request req, Response res) {
		String page = "";
		String agenda = "";
		ControleSessao contS = new ControleSessao();
		ControleEvento contE = new ControleEvento();
		Usuario u = contS.recuperarUsuario(Integer.parseInt(req.cookie("key")));
		String userH = "<h5>" + u.getNome() + " " + u.getSobrenome() + "</h5>";
		contS.disconnect();
		
		try {
			Scanner scan = new Scanner(new File("src/main/resources/public/agenda.html"));
			while(scan.hasNextLine()) {
				page += scan.nextLine() + "\n";
			}
			scan.close();
		}catch(FileNotFoundException err) {
			System.out.println(err.getMessage());
		}
		
		//construindo a agenda
		List<Evento> eventos = contE.recuperarEventosDaPessoa(u.getCpf());
		contE.disconnect();
		
		//ordenando as datas
		for(int i = 0; i < eventos.size() - 1; i++) {
			String[] dateA = eventos.get(i).getData().split("-");
			for(int j = i + 1; j < eventos.size(); j++) {
				String[] dateB = eventos.get(j).getData().split("-");
				if(Integer.parseInt(dateB[2]) < Integer.parseInt(dateA[2]) || Integer.parseInt(dateB[1]) < Integer.parseInt(dateA[1]) || Integer.parseInt(dateB[0]) < Integer.parseInt(dateA[0])) {
					Evento tmp = eventos.get(i);
					eventos.set(i, eventos.get(j));
					eventos.set(j, tmp);
				}
			}
		}
		
		//definindo as datas
		agenda = "<div class=\"agenda\">\n";
		for(int i = 0; i < eventos.size(); i++) {
			String data = eventos.get(i).getData();
			String[] dataParts = data.split("-");
			data = dataParts[2] + "/" + dataParts[1] + "/" + dataParts[0];
			
			String tmp = "<div class=\"data-grupo\">\n";
			tmp += "<h2>" + data + "</h2>\n";
			tmp += 	"<div></i><a href=\"/evento/" + eventos.get(i).getId() + "\">" + 
					"<i class=\"fa-solid fa-star\"></i>" + eventos.get(i).getNome() + "</a></div>\n";
			tmp += "</div>\n";
			agenda += tmp;
		}
		agenda += "</div>\n";
		
		page = page.replaceFirst("<LOGIN-BUTTON>", "<li class=\"nav-item\"><a id=\"user-view\"><i class=\"fa-solid fa-circle-user\"></i></a></li>");
		page = page.replaceFirst("<USER-NAME>", userH);
		page = page.replaceFirst("<DATAS>", agenda);
		
		return page;
	}
}
