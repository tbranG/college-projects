package dao;

import java.sql.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import models.Evento;


public class EventoDAO extends DAO {
	public EventoDAO() {
		super();
	}
	
	/**
	 * Cria uma id para o evento.
	 * */
	public int gerarID() {
		Random rand = new Random();
		int id = -1;
		
		boolean valid = false;
		while(!valid) {
			id = rand.nextInt(9000) + 1000;
			try {
				Statement stat = connection.createStatement();
				String sql = "SELECT * FROM Evento WHERE id = " + id + ";";
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
	
	public boolean createEvento(Evento evento) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = 	"INSERT INTO Evento (id, dono, nome, qtd_participantes, max_participantes, data, horario, endereco, descricao, publico)" +
							"VALUES (" + evento.getId() + ", '" + evento.getDono() + "', '" + evento.getNome() + "', " +
							evento.getQtdParticipantes() + ", " + evento.getMaxParticipantes() + ", '" + evento.getData().toString() + "', '" +
							evento.getHorario() + "', '" + evento.getEndereco() + "', '" + evento.getDescricao() + "', " + evento.getPrivacidade() + ");";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		return status;
	}
	
	public boolean updateEvento(Evento newEvento) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = 	"UPDATE Evento SET id = " + newEvento.getId() + ", dono = '" +
							newEvento.getDono() + "', nome = '" + newEvento.getNome() + "', qtd_participantes = " +
							newEvento.getQtdParticipantes() + ", max_participantes = " + newEvento.getMaxParticipantes() +
							", data = '" + newEvento.getData().toString() +  "', horario = '" + newEvento.getHorario() + 
							"', endereco = '" + newEvento.getEndereco() +
							"', descricao = '" + newEvento.getDescricao() + "', publico = " + newEvento.getPrivacidade() +  " WHERE id = " + newEvento.getId() + ";";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		return status;
	}
	
	public boolean deleteEvento(int id) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = "DELETE FROM Evento WHERE id = " + id + ";";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		return status;
	}
	
	public Evento getEvento(int id) {
		Evento evento = null;
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM Evento WHERE id = " + id + ";";
			ResultSet rs = stat.executeQuery(sql);
			if(rs.next()) {
				evento = new Evento(
						id,
						rs.getString("dono"),
						rs.getString("nome"),
						rs.getInt("qtd_participantes"),
						rs.getInt("max_participantes"),
						rs.getString("data"),
						rs.getString("horario"),
						rs.getString("endereco"),
						rs.getString("descricao"),
						rs.getBoolean("publico")
						);
			}
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		return evento;
	}
	
	public List<Evento> getAllEventos(){
		List<Evento> lista = new ArrayList<Evento>();
		
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM Evento";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				Evento evento = new Evento(
						rs.getInt("id"),
						rs.getString("dono"),
						rs.getString("nome"),
						rs.getInt("qtd_participantes"),
						rs.getInt("max_participantes"),
						rs.getString("data"),
						rs.getString("horario"),
						rs.getString("endereco"),
						rs.getString("descricao"),
						rs.getBoolean("publico")
						);
				lista.add(evento);
			}
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		return lista;
	}
	
	/**
	 * Recupera todos os eventos onde o usuário é o dono
	 * @param cpf - Identificador do usuário
	 * */
	public List<Evento> getAllEventosPessoa(String cpf){
		List<Evento> lista = new ArrayList<Evento>();
		
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM Evento WHERE dono = '" + cpf + "';";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				Evento evento = new Evento(
						rs.getInt("id"),
						rs.getString("dono"),
						rs.getString("nome"),
						rs.getInt("qtd_participantes"),
						rs.getInt("max_participantes"),
						rs.getString("data"),
						rs.getString("horario"),
						rs.getString("endereco"),
						rs.getString("descricao"),
						rs.getBoolean("publico")
						);
				lista.add(evento);
			}
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		return lista;
	}
}
