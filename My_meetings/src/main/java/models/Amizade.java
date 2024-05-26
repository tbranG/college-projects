package models;

public class Amizade {
	private int id;
	private String usuario1, usuario2;
	
	public Amizade(int id, String usuario1, String usuario2) {
		this.id = id;
		this.usuario1 = usuario1;
		this.usuario2 = usuario2;
	}
	
	//getters
	public int getId() {
		return id;
	}
	public String getUsuario1() {
		return usuario1;
	}
	public String getUsuario2() {
		return usuario2;
	}
	
	public boolean checarPessoa(String cpf) {
		return usuario1.equals(cpf) || usuario2.equals(cpf);
	}
}
