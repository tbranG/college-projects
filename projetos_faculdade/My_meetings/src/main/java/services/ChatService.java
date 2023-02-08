package services;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import dao.*;
import models.*;

public class ChatService {

	public static void enviarMensagem(Request req, Response res) {
		ControleSessao cont = new ControleSessao();
		ChatDAO cDao = new ChatDAO();
		
		//construindo a mensagem
		Usuario u = cont.recuperarUsuario(Integer.parseInt(req.cookie("key")));
		String msg = req.queryParams("mensagem");
		int idEvento = Integer.parseInt(req.params("id"));
		int id = cDao.gerarID();
		
		Mensagem m = new Mensagem(id, idEvento, u.getCpf(), msg);
		cDao.enviarMensagem(m);
		
		cont.disconnect();
		cDao.disconnect();
	}
}
