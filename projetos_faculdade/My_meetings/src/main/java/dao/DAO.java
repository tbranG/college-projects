package dao;
import java.sql.*;

public class DAO {
	protected Connection connection;
	
	public DAO() {
		connect();
	}
	
	protected void connect() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "my-meetings.postgres.database.azure.com";
		String mydatabase = "mymeetings";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "adm@my-meetings";
		String password = "Firebike@36";
		
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, password);
		}catch(Exception err) {
			System.out.println(err.getMessage());
		}
	}
	
	public void disconnect() {
		try {
			connection.close();
		}catch(SQLException err) {			
			System.out.println(err.getMessage());
		}
	}
}
