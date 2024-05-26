package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Usuario;

public class UsuarioDAO extends DAO {
	public UsuarioDAO() {
		super();
	}
	
	public boolean addUsuario(Usuario newUser) {
		boolean status = true;
		
		try {
			Statement stat = connection.createStatement();
			String sql = 	"INSERT INTO Usuario (cpf, telefone, nome, sobrenome, login, senha) " +
					 		"VALUES ('" + newUser.getCpf() + "', '" + newUser.getTelefone() + "', '" +
					 		newUser.getNome() + "', '" + newUser.getSobrenome() + "', '" +
							newUser.getLogin() + "', '" + newUser.getSenha() + "');";
			
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			status = false;
			System.out.println(err.getMessage());
		}
		
		return status;
	}
	
	public  static BigInteger criptografia(String senha) {
		MessageDigest md5;
		BigInteger resp = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(senha.getBytes(), 0, senha.length());
			resp = new BigInteger(1, md5.digest());
		} catch(NoSuchAlgorithmException ex) {
			Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}
	
	public boolean deleteUsuario(String cpf) {
		boolean status = true;
		
		try {
			Statement stat = connection.createStatement();
			String sql = "DELETE FROM Usuario WHERE cpf = " + "'" + cpf + "'";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			status = false;
		}
		
		return status;
	}
	
	public boolean updateUsuario(Usuario u) {
		boolean status = true;
		
		try {
			Statement stat = connection.createStatement();
			String sql = 	"UPDATE Usuario SET cpf = '" + u.getCpf() + "', telefone = '" + u.getTelefone() + "', " +
							"nome = '" + u.getNome() + "', sobrenome = '" + u.getSobrenome() + "', login = '" + 
							u.getLogin() + "', senha = '" + u.getSenha() + "' WHERE cpf = '" + u.getCpf() + "';";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		
		return status;
	}
	
	public Usuario getUsuario(String cpf) {
		Usuario user = null;
		
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM Usuario WHERE cpf = '" + cpf + "'";
			ResultSet r = stat.executeQuery(sql);
			if(r.next()) {				
				user = new Usuario(
						r.getString("cpf"), 
						r.getString("telefone"), 
						r.getString("nome"),
						r.getString("sobrenome"),
						r.getString("login"),
						r.getString("senha")
						);
			}
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		
		return user;
	}
	
	public Usuario getUsuario(String login, String senha) {
		Usuario user = null;
		
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM Usuario WHERE login = '" + login + "' AND senha = '" + senha + "';";
			ResultSet r = stat.executeQuery(sql);
			if(r.next()) {				
				user = new Usuario(
						r.getString("cpf"), 
						r.getString("telefone"), 
						r.getString("nome"),
						r.getString("sobrenome"),
						r.getString("login"),
						r.getString("senha")
						);
			}
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		
		return user;
	}
	
	/**
	 * Autenticacao de usuarios para o sistema de login. Recebe o nome de login e a senha.
	 * @param login = nome de login
	 * @param senha = senha do usuario
	 * @return retorna true se o login e o usuario estiverem corretos
	 * */
	public boolean autenticar(String login, String senha) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = "SELECT * FROM Usuario WHERE login = '" + login + "' AND senha = '" + senha + "';";
			ResultSet rs = stat.executeQuery(sql);
			status = rs.next();
			stat.close();
		}catch(SQLException err) {
			System.out.println(err);
		}
		return status;
	}
}
