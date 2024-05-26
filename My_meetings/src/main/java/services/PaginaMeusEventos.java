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

public class PaginaMeusEventos {
	
	public static String createMeusEventos(Request req, Response res) {
		ControleSessao contS = new ControleSessao();
		EventoDAO eDao = new EventoDAO();
		Usuario user = contS.recuperarUsuario(Integer.parseInt(req.cookie("key")));
		String page = "";
		String userH = "<h5>" + user.getNome() + " " + user.getSobrenome() + "</h5>";
		contS.disconnect();
		
		List<Evento> meusEventos = eDao.getAllEventosPessoa(user.getCpf());
		eDao.disconnect();
		
		try {
			Scanner scan = new Scanner(new File("src/main/resources/public/meusEventos.html"));
			while(scan.hasNextLine()) {
				page += scan.nextLine() + "\n";
			}
			scan.close();
		}catch(FileNotFoundException err) {
			System.out.println(err.getMessage());
		}
		
		String list = "<div>\n";
		for(int i = 0; i < meusEventos.size(); i++) {
			String ul = "<div class=\"evento-grupo\">\n";
			ul += "<h4 class=\"evento-titulo\">" + meusEventos.get(i).getNome() + "</h4>\n";
			ul += "<div>\n";
			ul += "<a href=\"/updateEvento/" + meusEventos.get(i).getId() + "\">" + "<i class=\"fa-solid fa-pen\"></i>" + "</a>\n";
			ul += "<a href=\"/deleteEvento/" + meusEventos.get(i).getId() + "\">" + "<i class=\"fa-solid fa-circle-xmark\"></i>" + "</a>\n";
			ul += "</div>\n";
			ul += "</div>\n";
			
			list += ul;
		}
		list += "</div>\n";
		page = page.replaceFirst("<LOGIN-BUTTON>", "<li class=\"nav-item\"><a id=\"user-view\"><i class=\"fa-solid fa-circle-user\"></i></a></li>");
		page = page.replaceFirst("<USER-NAME>", userH);
		page = page.replaceFirst("<EVENTOS>", list);
		
		return page;
	}
}
