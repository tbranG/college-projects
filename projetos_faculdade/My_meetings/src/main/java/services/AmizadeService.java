package services;

import dao.AmizadeDAO;
import dao.ControleSessao;
import models.Amizade;
import models.Usuario;

import spark.Request;
import spark.Response;

public class AmizadeService {
	
	public static void adicionarAmizade(Request req, Response res) {
		ControleSessao contS = new ControleSessao();
		AmizadeDAO amDao = new AmizadeDAO();
		
		Usuario user = contS.recuperarUsuario(Integer.parseInt(req.cookie("key")));
		String otherCpf = req.params("cpf");
		
		//checa se o cpf do amigo é diferente do cpf do usuário
		//também checa se a amizade entre os usuários já existe
		if(!user.getCpf().equals(otherCpf)) {			
			if(!amDao.checarRelacao(user.getCpf(), otherCpf)) {			
				amDao.inserirRelacao(user.getCpf(), otherCpf);
			}
		}
	
		user = null;
		amDao.disconnect();
		contS.disconnect();
		
		res.redirect("/amigos");
	}
}
