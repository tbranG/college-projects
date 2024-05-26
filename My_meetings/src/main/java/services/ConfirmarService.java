package services;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import dao.*;
import models.*;

public class ConfirmarService {
	
	public static void confirmarParticip(Request req, Response res) {
		ControleSessao contS = new ControleSessao();
		ControleEvento contE = new ControleEvento();
		
		Usuario usu = contS.recuperarUsuario(Integer.parseInt(req.cookie("key")));
		int idEvento = Integer.parseInt(req.params("id"));
		boolean status = Boolean.valueOf(req.queryParams("status"));
		
		
		RelacaoEvento rel = contE.getRelacao(idEvento, usu.getCpf());
		rel.setConfirmado(status);
		
		contE.atualizarRelacao(rel.getId(), rel.getConfirmado());
		contS.disconnect();
		contE.disconnect();
		res.redirect("/confirmados/" + idEvento);
	}
}
