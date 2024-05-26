package dao;

import java.sql.*;
import models.PoolData;

public class PoolDataDAO extends DAO {
	public PoolDataDAO() {
		super();
	}

	public boolean createPoolData(PoolData pool) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = 	"INSERT INTO \"public\".\"PoolData\" (id, qtdVotos, qtdOpcoes, tempoLimite, info, opcoes, votoOpcao)" +
							"VALUES (" + pool.getId() + ", '" + pool.getQtdVotos() + "', '" + pool.getQtdOpcoes() + "', " +
							pool.getData().toString() + ", " + pool.getInfo() + ", '" + pool.getOpcoes().toString() + "', '" +
							pool.getVotoOpcao() + "');";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		return status;
	}

	public boolean updateEvento(PoolData pool) {
		boolean status = true;
		try {
			Statement stat = connection.createStatement();
			String sql = 	"UPDATE \"public\".\"PoolData\" SET id = " + pool.getId() + ", qtdVotos = '" +
					pool.getQtdVotos() + "', qtdOpcoes = '" + pool.getQtdOpcoes() + "', tempoLimite = " +
					pool.getData().toString() + ", info = " + pool.getInfo() +
							", opcoes = '" + pool.getOpcoes().toString() + "', votoOpcao = '" + pool.getVotoOpcao() +
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
			String sql = "DELETE FROM \"public\".\"PoolData\" WHERE id = " + id + ";";
			stat.executeUpdate(sql);
			stat.close();
		}catch(SQLException err) {
			System.out.println(err.getMessage());
			status = false;
		}
		return status;
	}
}