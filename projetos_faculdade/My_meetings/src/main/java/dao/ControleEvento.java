package dao;

import java.sql.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import models.Usuario;
import models.Evento;
import models.RelacaoEvento;
import dao.UsuarioDAO;
import dao.EventoDAO;

public class ControleEvento extends DAO{
	public ControleEvento() {
		super();
	}
	
	/**
	 * Cria uma id para a relacao.
	 * */
	public int gerarID() {
		Random rand = new Random();
		int id = -1;
		
		boolean valid = false;
		while(!valid) {
			id = rand.nextInt(9000) + 1000;
			try {
				Statement stat = connection.createStatement();
				String sql = "SELECT * FROM ControladorEvento WHERE id = " + id + ";";
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
	 * Adiciona uma relacao que determina qual participante esta participando de qual evento
	 * @param chaveEvento = id do evento
	 * @param cpf = cpf do usuario
	 * */
	public boolean adicionarRelacao(int chaveEvento, String cpf) {
		boolean status = true;
		int code = gerarID();
		try {
			Statement stat = connection.createStatement();
			String sql = 	"INSERT INTO ControladorEvento (id, evento, participante, confirmado)" +
							"VALUES (" + code + ", " + chaveEvento + ", '" + cpf + "', false);";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		
		return status;
	}
	
	/**
	 * Remove a relacao de participacao
	 * @param chaveEvento = id do evento
	 * @param cpf = cpf do usuario
	 * */
	public boolean removerRelacao(int chaveEvento, String cpf) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = "DELETE FROM ControladorEvento WHERE evento = " + chaveEvento + "AND participante = '" + cpf + "';";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		
		return status;
	}
	
	public boolean atualizarRelacao(int id, boolean state) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = "UPDATE ControladorEvento SET confirmado = " + state + " WHERE id =" + id + ";";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		
		return status;
	}
	
	public RelacaoEvento getRelacao(int chaveEvento, String cpf) {
		RelacaoEvento rel = null;
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM ControladorEvento WHERE evento = " + chaveEvento + "AND participante = '" + cpf + "';";
			ResultSet rs = stat.executeQuery(sql);
			if(rs.next()) {
				rel = new RelacaoEvento(rs.getInt("id"), rs.getInt("evento"), rs.getString("participante"), rs.getBoolean("confirmado"));
			}
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		
		return rel;
	}
	
	public boolean checarParticipacao(int chaveEvento, String cpf) {
		boolean status = false;
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM ControladorEvento WHERE evento = " + chaveEvento + " AND participante = '" + cpf + "';";
			ResultSet rs = stat.executeQuery(sql);
			if(rs.next()) {
				status = true;
			}
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		return status;
	}
	
	/**
	 * Recupera todos os participantes de um evento
	 * @return Retorna uma lista com todos os usuarios que participam do evento
	 * */
	public List<Usuario> recuperarParticipantes(int chaveEvento){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		List<String> cpfs = new ArrayList<String>();
		UsuarioDAO uDao = new UsuarioDAO();
		
		//recupera os participantes
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM ControladorEvento WHERE evento = " + chaveEvento + ";";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				cpfs.add(rs.getString("participante"));
			}
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		
		//cria os usuarios dos cpfs coletados
		for(int i = 0; i < cpfs.size(); i++) {
			Usuario newUser = uDao.getUsuario(cpfs.get(i));
			usuarios.add(newUser);
		}
		
		return usuarios;
	}
	
	/**
	 * Recupera todos os eventos onde o usuário é um participante
	 * @param cpf - identificador do usuário
	 * */
	public List<Evento> recuperarEventosDaPessoa(String cpf){
		List<Evento> eventos = new ArrayList<Evento>();
		EventoDAO eDao = new EventoDAO();
		
		//recupera os participantes
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM ControladorEvento WHERE participante = '" + cpf + "';";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				Evento e = eDao.getEvento(rs.getInt("evento"));
				eventos.add(e);
			}
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		
		return eventos;
	}
}
