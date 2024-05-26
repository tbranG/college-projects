package services;

import static spark.Spark.*;
import java.math.BigInteger;
import spark.Request;
import spark.Response;

import dao.*;

public class AutenticarService {
	
	public static void autenticarUsuario(Request req, Response res) {
		UsuarioDAO u = new UsuarioDAO();
		//Criando variavel para pegar a senha e transformar com o md5
		BigInteger md5;
		md5 = UsuarioDAO.criptografia(req.queryParams("password"));
		boolean status = u.autenticar(req.queryParams("login"), md5.toString());
		if(status) {
			ControleSessao cont = new ControleSessao();
			int key = cont.iniciarSessao(u.getUsuario(req.queryParams("login"), md5.toString()));
			
			if(req.cookie("key") == null) {					
				res.cookie("/", "key", String.valueOf(key), 43200, false);
			}
			
			cont.disconnect();
			u.disconnect();
			res.redirect("/");
		}else {
			u.disconnect();
			res.redirect("/login");
		}
	}
}
