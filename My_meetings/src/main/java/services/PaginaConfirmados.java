package services;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;

import models.Usuario;
import models.RelacaoEvento;
import dao.ControleEvento;
import dao.ControleSessao;
import dao.UsuarioDAO;

import spark.Request;
import spark.Response;

public class PaginaConfirmados {

	public static String createPaginaConfirmados(Request req, Response res) {
		ControleEvento contE = new ControleEvento();
		ControleSessao contS = new ControleSessao();
		Usuario user = contS.recuperarUsuario(Integer.parseInt(req.cookie("key")));
		List<Usuario> users = contE.recuperarParticipantes(Integer.parseInt(req.params("id")));
		
		String userH = "<h5>" + user.getNome() + " " + user.getSobrenome() + "</h5>";
		String page = "";
				
		try {
			Scanner scan = new Scanner(new File("src/main/resources/public/confirmados.html"));
			while(scan.hasNextLine()) {
				page += scan.nextLine() + "\n";
			}			
		}catch(FileNotFoundException err) {
			System.out.println(err.getMessage());
		}
		
		//construindo a lista
		String div = "";
		div += "<section>\n";
		for(int i = 0; i < users.size(); i++) {
			RelacaoEvento rel = contE.getRelacao(Integer.parseInt(req.params("id")), users.get(i).getCpf());
			
			div += "<ul>\n";
			div += "<li><h6>"+ "<i class=\"fa-solid fa-circle-user\" id=\"user-display\"></i>" + users.get(i).getNome() + " " + users.get(i).getSobrenome() + "</h6></li>";
			
			if(rel.getConfirmado()) {
				div += "<li><i class=\"fa-sharp fa-solid fa-check\" id=\"icon-confirmado\"></i></li>\n";
			}else {
				div += "<li><i class=\"fa-solid fa-xmark\" id=\"icon-negado\"></i></li>\n";
			}
			
			div += "</ul>\n";
		}
		div += "</section>\n";
		
		page = page.replaceFirst("<ID>", req.params("id"));
		page = page.replaceFirst("<VALOR-STATUS>", String.valueOf(!contE.getRelacao(Integer.parseInt(req.params("id")), user.getCpf()).getConfirmado()));
		page = page.replaceFirst("<PESSOAS>", div);
		page = page.replaceFirst("<LOGIN-BUTTON>", "<li class=\"nav-item\"><a id=\"user-view\"><i class=\"fa-solid fa-circle-user\"></i></a></li>");
		page = page.replaceFirst("<USER-NAME>", userH);

		contE.disconnect();
		contS.disconnect();
		
		return page;
	}
}
