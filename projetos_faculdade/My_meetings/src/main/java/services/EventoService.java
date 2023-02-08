package services;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import dao.*;
import models.*;

public class EventoService {

	/**
	 * Adiciona um novo evento ao banco de dados
	 * */
	public static void criarEvento(Request req, Response res) {
		//objetos de acesso
		ControleSessao contr = new ControleSessao();
		EventoDAO eDao = new EventoDAO();
		Usuario user = contr.recuperarUsuario(Integer.parseInt(req.cookie("key")));
		Evento evento = new Evento(
				eDao.gerarID(), 
				user.getCpf(),
				req.queryParams("nome"),
				Integer.parseInt(req.queryParams("maxP")),
				req.queryParams("data"),
				req.queryParams("horario"),
				req.queryParams("endereco"),
				req.queryParams("descricao"),
				Boolean.valueOf(req.queryParams("publico"))
				);
		//
		//inserindo o evento no banco de dados
		eDao.createEvento(evento);
		evento = null;
		user = null;
		
		eDao.disconnect();
		contr.disconnect();
		res.redirect("/");
	}
	
	/**
	 * Adiciona um usuario a um evento
	 * */
	public static void entrarEvento(Request req, Response res) throws Exception {
		ControleSessao contS = new ControleSessao();
		ControleEvento contE = new ControleEvento();
		EventoDAO eDao = new EventoDAO();
		
		Usuario u = contS.recuperarUsuario(Integer.parseInt(req.cookie("key")));
		int eventoId = Integer.parseInt(req.params("id"));
		Evento e = eDao.getEvento(eventoId);
		
		//checa se o usuario nao esta participando do evento
		if(!contE.checarParticipacao(eventoId, u.getCpf())) {		
			contE.adicionarRelacao(eventoId, u.getCpf());
			e.addParticipante();
			eDao.updateEvento(e);
			
			u = null;
			eDao.disconnect();
			contS.disconnect();
			contE.disconnect();
			
		}
		res.redirect("/");
	}
	
	public static void convidarParaEvento(Request req, Response res) throws Exception {
		ControleEvento contE = new ControleEvento();
		UsuarioDAO uDao = new UsuarioDAO();
		EventoDAO eDao = new EventoDAO();
		
		Usuario u = uDao.getUsuario(req.params("cpf"));
		int eventoId = Integer.parseInt(req.queryParams("evento"));
		Evento e = eDao.getEvento(eventoId);
		
		//checa se o usuario nao esta participando do evento
		if(!contE.checarParticipacao(eventoId, u.getCpf())) {		
			contE.adicionarRelacao(eventoId, u.getCpf());
			e.addParticipante();
			eDao.updateEvento(e);
			
			u = null;
			eDao.disconnect();
			contE.disconnect();
			
		}
		res.redirect("/");
	}
	
	public static void atualizarEvento(Request req, Response res) {
		EventoDAO eDao = new EventoDAO();
		Evento e = eDao.getEvento(Integer.parseInt(req.params("id")));
		
		String data = req.queryParams("data");
		String[] dParts = data.split("-");
		data = dParts[2] + "-" + dParts[1] + "-" + dParts[0];
		
		e.setNome(req.queryParams("nome"));
		e.setData(data);
		e.setHorario(req.queryParams("horario"));
		e.setEndereco(req.queryParams("endereco"));
		e.setMaxParticipantes(Integer.parseInt(req.queryParams("maxP")));
		e.setDescricao(req.queryParams("descricao"));
		e.setPrivacidade(Boolean.valueOf(req.queryParams("publico")));
		
		eDao.updateEvento(e);
		eDao.disconnect();
		res.redirect("/meusEventos");
	}
	
	public static void deletarEvento(Request req, Response res) {
		EventoDAO eDao = new EventoDAO();
		int id = Integer.parseInt(req.params("id"));
		eDao.deleteEvento(id);
		res.redirect("/meusEventos");
	}
}
