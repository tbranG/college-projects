package services;
import java.math.BigInteger;
import static spark.Spark.*;
import spark.Request;
import spark.Response;

import dao.*;
import models.*;

public class CadastroService {
	
	public static void enviarInfo(Request req, Response res) {
		BigInteger senha;
		senha = UsuarioDAO.criptografia(req.queryParams("senha"));
		Usuario newUser = new Usuario
				(req.queryParams("cpf"), 
				req.queryParams("telefone"),
				req.queryParams("pnome"), 
				req.queryParams("unome"),
				req.queryParams("login"),
				senha.toString());		
		
		UsuarioDAO access = new UsuarioDAO();
		boolean status = access.addUsuario(newUser);
		newUser = null;
		
		if(status) {
			res.redirect("/login");
		}else {
			res.redirect("/cadastro");
		}
	}
}
