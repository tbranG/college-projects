package dao;

import java.sql.*;
import java.util.Random;
import models.Usuario;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class ControleSessao extends DAO {
	public ControleSessao() {
		super();
	}
	
	/**
	 * Cria uma id para a sessao do usuario.
	 * */
	private int gerarID() {
		Random rand = new Random();
		int id = -1;
		
		boolean valid = false;
		while(!valid) {
			id = rand.nextInt(9000) + 1000;
			try {
				Statement stat = connection.createStatement();
				String sql = "SELECT * FROM Sessao WHERE id = " + id + ";";
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
	 * Inicia uma sessao no banco de dados.
	 * @return o codigo da sessao
	 * */
	public int iniciarSessao(Usuario user) {
		int code = buscarSessao(user);
		if(code == -1) {			
			code = gerarID();
			try {
				Statement stat = connection.createStatement();
				String sql = "INSERT INTO Sessao (id, usuario) VALUES (" + code + ", '" + user.getCpf() + "');";
				stat.executeUpdate(sql);
				stat.close();
			}catch(SQLException err) {
				System.out.println(err.getMessage());
			}
		}
		return code;
	}
	
	/**
	 * Procura sessoes antigas de um usuario
	 * @return id da antiga sessao
	 * */
	private int buscarSessao(Usuario user) {
		int result = -1;
		
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM Sessao WHERE usuario = '" + user.getCpf() + "';";
			ResultSet rs = stat.executeQuery(sql);
			if(rs.next()) {
				result = rs.getInt("id");
			}
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		
		return result;
	}
	
	
	public boolean validarSessao(Request req, Response res) {
		boolean status = false;
		int id = -1;
		if(req.cookie("key") != null) {			
			id = Integer.parseInt(req.cookie("key"));
		}

		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM Sessao WHERE id = " + id + ";";
			ResultSet rs = stat.executeQuery(sql);
			if(rs.next()) {
				status = true;
			}
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}

		return status;
	}

	/**
	 * Recupera o usuario logado no momento
	 * @param key = Chave da sessao
	 * */
	public Usuario recuperarUsuario(int key) {
		Usuario user = null;
		
		try {
			//primeira consulta
			Statement stat1 = connection.createStatement();
			String sql_session = "SELECT * FROM Sessao WHERE id = " + key;
			ResultSet r1 = stat1.executeQuery(sql_session);
			String cpf = "";
			
			if(r1.next()) {
				cpf = r1.getString("usuario");
			}
			stat1.close();
			
			//segunda consulta
			Statement stat2 = connection.createStatement();
			String sql = "SELECT * FROM Usuario WHERE cpf = '" + cpf + "'";
			ResultSet r2 = stat2.executeQuery(sql);
			if(r2.next()) {				
				user = new Usuario(
						r2.getString("cpf"), 
						r2.getString("telefone"), 
						r2.getString("nome"),
						r2.getString("sobrenome"),
						r2.getString("login"),
						r2.getString("senha")
						);
			}
			stat2.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		
		return user;
	}
}
