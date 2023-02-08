package models;

public class Usuario {
	
	private String cpf;
	private String telefone;
	private String pNome, uNome;
	private String login, senha;
	
	public Usuario(String cpf, String telefone, String pNome, String uNome, String login, String senha) {
		this.cpf = cpf;
		this.telefone = telefone;
		this.pNome = pNome;
		this.uNome = uNome;
		this.login = login;
		this.senha = senha;
	}
	
	//getters
	public String getCpf() {
		return cpf;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public String getNome() {
		return pNome;
	}
	
	public String getSobrenome() {
		return uNome;
	}
	
	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}
	
	//setters
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
