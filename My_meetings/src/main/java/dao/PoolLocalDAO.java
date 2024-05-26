package dao;

import java.sql.*;
import models.PoolLocal;

public class PoolLocalDAO extends DAO {
	public PoolLocalDAO() {
		super();
	}

	public boolean createPoolLocal(PoolLocal pool) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = 	"INSERT INTO \"public\".\"PoolLocal\" (id, qtdVotos, qtdOpcoes, tempoLimite, info, opcoes, votoOpcao)" +
							"VALUES (" + pool.getId() + ", '" + pool.getQtdVotos() + "', '" + pool.getQtdOpcoes() + "', " +
							pool.getData().toString() + ", " + pool.getInfo() + ", '" + pool.getOpcoes() + "', '" +
							pool.getVotoOpcao() + "');";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		return status;
	}

	public boolean updateEvento(PoolLocal pool) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = 	"UPDATE \"public\".\"PoolLocal\" SET id = " + pool.getId() + ", qtdVotos = '" +
					pool.getQtdVotos() + "', qtdOpcoes = '" + pool.getQtdOpcoes() + "', tempoLimite = " +
					pool.getData().toString() + ", info = " + pool.getInfo() +
							", opcoes = '" + pool.getOpcoes() + "', votoOpcao = '" + pool.getVotoOpcao() +
							"' WHERE id = " + pool.getId() + ";";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		return status;
	}

	public boolean deletePool(int id) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = "DELETE FROM \"public\".\"PoolLocal\" WHERE id = " + id + ";";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		return status;
	}
}