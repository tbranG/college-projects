package services;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import dao.*;
import models.*;

public class RecuperarService {

	public static void enviarInfo(Request req, Response res) {
		String cpf = req.queryParams("cpf");
		String novaSenha = req.queryParams("novaSenha");
		
		UsuarioDAO u = new UsuarioDAO();
		Usuario novoUsu = u.getUsuario(cpf);
		novoUsu.setSenha(novaSenha);
		u.updateUsuario(novoUsu);
		
		res.redirect("/login");
	}
}
