package services;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import dao.*;
import models.*;

public class HomeService {
	
	/**
	 * Cria a pagina principal da aplicacao para usuarios logados ou nao logados.
	 * @return Retorna o html do site 
	 * */
	public static String buildHomepage(Request req, Response res) {
		//testa se o usuário está logado
		ControleSessao cont = new ControleSessao();
		boolean status = cont.validarSessao(req, res);
		//
		
		if(status) {
			Usuario user = cont.recuperarUsuario(Integer.parseInt(req.cookie("key")));
			cont.disconnect();
			return PaginaHome.createPageLogged(user);
		}else {		
			cont.disconnect();
			return PaginaHome.createPageUnlogged();
		}
	}
}
