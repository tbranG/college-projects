package dao;

import java.util.List;
import java.util.ArrayList;

import models.Amizade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class AmizadeDAO extends DAO {
	public AmizadeDAO() {
		super();
	}
	
	/**
	 * Cria um id para a relação
	 * */
	public int gerarID() {
		Random rand = new Random();
		int id = -1;
		
		boolean valid = false;
		while(!valid) {
			id = rand.nextInt(9000) + 1000;
			try {
				Statement stat = connection.createStatement();
				String sql = "SELECT * FROM Amizade WHERE id = " + id + ";";
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
	
	public boolean inserirRelacao(String cpf1, String cpf2) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = 	"INSERT INTO Amizade (id, usuario1, usuario2)"
							+ "VALUES (" + gerarID() + ", '" + cpf1 + "', '" + cpf2 + "');";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		
		return status;
	}

	public List<Amizade> recuperarAmizades(String cpf){
		List<Amizade> lista = new ArrayList<Amizade>();
		try {
			Statement stat = connection.createStatement();
			String sql = 	"SELECT * FROM Amizade WHERE usuario1 = '" + cpf + "' OR "
						 	+ "usuario2 = '" + cpf + "';";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				lista.add(new Amizade(rs.getInt("id"), rs.getString("usuario1"), rs.getString("usuario2")));
			}
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		return lista;
	}
	
	public boolean checarRelacao(String cpf1, String cpf2) {
		boolean status = false;
		try {
			Statement stat = connection.createStatement();
			String sql = 	"SELECT * FROM Amizade WHERE (usuario1 = '" + cpf1 + "' AND " +
							"usuario2 = '" + cpf2 + "') OR (usuario1 = '" + cpf2 + "' AND usuario2 = '" + cpf1 + "')";
			ResultSet rs = stat.executeQuery(sql);
			if(rs.next()) {
				status = true;
			}
		}catch(SQLException err) {
			System.out.println(err.getMessage());
		}
		return status;
	}
}
