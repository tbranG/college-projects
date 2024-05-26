package app;

import static spark.Spark.*;

import dao.*;
import models.*;
import services.*;

public class Programa {
	
	public static void main(String[] args) {
		staticFiles.location("/public");
		port(4567);
		
		//PÃ�GINA PRINCIPAL
		//--------------------------------------------------------------------------------+
		
		//raiz, pÃ¡gina principal do site
		get("/", (req, res) -> HomeService.buildHomepage(req, res));
		
		
		//LOGIN E CONTROLE DE SESSÃƒO
		//--------------------------------------------------------------------------------+
		
		//pagina de login do site
		get("/login", (req, res) -> {
			res.redirect("login.html");
			return null;
		});
		
		
		//requisicao para autenticar o usuario
		get("/autenticar", (req, res) -> {
			AutenticarService.autenticarUsuario(req, res);
			return null;
		});
		
		//chamada para deslogar um usuario
		get("/logout", (req, res) -> {
			res.removeCookie("key");
			res.redirect("/");
			return null;
		});
		
		
		//RECUPERAR A SENHA
		//--------------------------------------------------------------------------------+
		
		//pagina para recuperar a senha do usuario
		get("/recuperar", (req, res) -> {
			res.redirect("recUsuario.html");
			return null;
		});
		
		//requisicao para enviar a nova senha
		get("/recuperar/send", (req, res) -> {
			RecuperarService.enviarInfo(req, res);
			return null;
		});
		
		
		//CADASTRO DE USUÃ�RIOS
		//--------------------------------------------------------------------------------+
		
		//pagina de cadastro de usuario
		get("/cadastro", (req, res) -> {
			res.redirect("cadastro.html");
			return null;
		});
		
		//requisicao para enviar o novo usuario para o banco de dados
		get("/cadastro/send", (req, res) -> {
			CadastroService.enviarInfo(req, res);
			return null;
		});
		
		
		//CRIACAO DE EVENTOS E MANIPULACAO
		//--------------------------------------------------------------------------------+
		
		//pagina para criacao de eventos
		get("/criarEvento", (req, res) -> {
			//testa se o usuÃ¡rio estÃ¡ logado
			ControleSessao cont = new ControleSessao();
			boolean status = cont.validarSessao(req, res);
			cont.disconnect();
			
			if(status) {				
				res.redirect("criarEvento.html");
			}else {
				res.redirect("/login");
			}
			return null;
		});
		
		//requisicao que envia o evento para o banco de dados
		get("/criarEvento/send", (req, res) -> {
			EventoService.criarEvento(req, res);
			return null;
		});
		
		//pagina que exibe o evento
		get("/evento/:id", (req, res) -> PaginaEvento.createPaginaEvento(req, res));
		
		//requisicao para participar de um evento
		get("/entrarEvento/:id", (req, res) ->{
			//testa se o usuÃ¡rio estÃ¡ logado
			ControleSessao cont = new ControleSessao();
			boolean status = cont.validarSessao(req, res);
			cont.disconnect();

			if(status) {				
				try {
					EventoService.entrarEvento(req, res);
				}catch(Exception err) {
					System.out.println("Evento lotado!");
				}
			}else {
				res.redirect("/login");
			}
			return null;
		});
		
		//adiciona um participante a um evento por meio de um convite
		get("/convite/:cpf", (req, res) -> {
			try {
				EventoService.convidarParaEvento(req, res);
			}catch(Exception err) {
				System.out.println(err.getMessage());
			}
			return null;
		});
		
		//CHAT DOS EVENTOS
		//--------------------------------------------------------------------------------+
		
		//pagina do chat do evento
		get("/chat/:id", (req, res) -> {
			//testa se o usuÃ¡rio estÃ¡ logado
			ControleSessao cont = new ControleSessao();
			boolean status = cont.validarSessao(req, res);
			cont.disconnect();
			
			if(status) {
				return PaginaChat.createChat(req, res);
			}else {
				res.redirect("/login");
				return null;
			}
		});
		
		//requisicao para enviar uma mensagem para o chat
		get("/chat/:id/send", (req, res) -> {
			ChatService.enviarMensagem(req, res);
			res.redirect("/chat/" + req.params("id"));
			return null;
		});
		
		//AGENDA
		//--------------------------------------------------------------------------------+
		
		//pagina da agenda do usuario
		get("/agenda", (req, res) -> {
			//testa se o usuÃ¡rio estÃ¡ logado
			ControleSessao cont = new ControleSessao();
			boolean status = cont.validarSessao(req, res);
			cont.disconnect();
			
			if(status) {
				return PaginaAgenda.criarAgenda(req, res);
			}else {
				res.redirect("/login");
				return null;
			}
		});
	
		//LISTA DE EVENTOS
		//--------------------------------------------------------------------------------+
		
		//pagina que lista todos os eventos
		get("/listaEventos", (req, res) -> {
			//testa se o usuÃ¡rio estÃ¡ logado
			ControleSessao cont = new ControleSessao();
			boolean status = cont.validarSessao(req, res);
			cont.disconnect();
			
			if(status) {
				return PaginaListaEventos.createListaEventos(req, res);
			}else {
				res.redirect("/login");
				return null;
			}
		});
		
		//pagina que lista os eventos criados pelo usuario
		get("/meusEventos", (req, res) -> {
			//testa se o usuÃ¡rio estÃ¡ logado
			ControleSessao cont = new ControleSessao();
			boolean status = cont.validarSessao(req, res);
			cont.disconnect();
			
			if(status) {
				return PaginaMeusEventos.createMeusEventos(req, res);
			}else {
				res.redirect("/login");
				return null;
			}
		});
		
		//requsicao que deleta o evento selecionado
		get("/deleteEvento/:id", (req, res) -> {
			EventoService.deletarEvento(req, res);
			return null;
		});
		
		get("/updateEvento/:id", (req, res) -> PaginaUpdateEvento.createUpdateEvento(req, res));
		
		get("/sendEvento/:id", (req, res) -> {
			EventoService.atualizarEvento(req, res);
			return null;
		});
		
		//LISTA DE CONFIRMADOS
		//--------------------------------------------------------------------------------+

		get("/confirmados/:id", (req, res) -> PaginaConfirmados.createPaginaConfirmados(req, res));
		
		get("/sendConfirmacao/:id", (req, res) -> {
			ConfirmarService.confirmarParticip(req, res);
			return null;
		});
		
		//LISTA DE AMIGOS
		//--------------------------------------------------------------------------------+
		
		get("/amigos", (req, res) -> PaginaAmigos.criarPaginaAmigos(req, res));
		
		get("/addAmizade/:cpf", (req, res) -> {
			AmizadeService.adicionarAmizade(req, res);
			return null;
		});
		
		//DEBUG
		get("/removeKey", (req, res) -> {
			res.removeCookie("key");
			res.redirect("/");
			return null;
		});
	}
}
