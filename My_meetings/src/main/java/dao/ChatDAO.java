package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dao.UsuarioDAO;
import dao.EventoDAO;

import models.Mensagem;


public class ChatDAO extends DAO {
	public ChatDAO() {
		super();
	}
	
	/**
	 * Cria uma id para a mensagem
	 * */
	public int gerarID() {
		Random rand = new Random();
		int id = -1;
		
		boolean valid = false;
		while(!valid) {
			id = rand.nextInt(9000) + 1000;
			try {
				Statement stat = connection.createStatement();
				String sql = "SELECT * FROM Chat WHERE id = " + id + ";";
				ResultSet rs = stat.executeQuery(sql);
				if(!rs.next()) {
					valid = true;
				}
				stat.close();
			}catch(SQLException err) {
				System.out.println(err.getMessage());
			}
		}
		return id;
	}
	
	/**
	 * Recupera a lista de mensagens de uma pessoa.
	 * */
	public List<Mensagem> recuperarMensagensPessoa(int eventoId, String cpf) {
		List<Mensagem> lista = new ArrayList<Mensagem>();
		try {
			Statement stat = connection.createStatement();
			String sql = 	"SELECT * FROM Chat WHERE evento = " + eventoId + " AND " +
							"pessoa = '" + cpf + "';";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				Mensagem msg = new Mensagem(rs.getInt("id"), rs.getInt("evento"), rs.getString("pessoa"), rs.getString("conteudo"));
				lista.add(msg);
			}
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		
		return lista;
	}

	/**
	 * Recupera a lista de mensagens do evento.
	 * */
	public List<Mensagem> recuperarMensagensEvento(int eventoId) {
		List<Mensagem> lista = new ArrayList<Mensagem>();
		try {
			Statement stat = connection.createStatement();
			String sql = 	"SELECT * FROM Chat WHERE evento = " + eventoId + ";";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				Mensagem msg = new Mensagem(rs.getInt("id"), rs.getInt("evento"), rs.getString("pessoa"), rs.getString("conteudo"));
				lista.add(msg);
			}
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		
		return lista;
	}

	public boolean enviarMensagem(Mensagem msg) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = 	"INSERT INTO Chat (id, evento, pessoa, conteudo) " +
							"VALUES (" + msg.getId() + ", " + msg.getEventoId() + ", '" + msg.getPessoa() +
							"', '" + msg.getConteudo() + "');";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		return status;
	}
}
