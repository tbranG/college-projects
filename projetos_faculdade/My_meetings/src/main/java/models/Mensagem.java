package models;

public class Mensagem {
	private int id;
	private int eventoId;
	private String pessoa;
	private String conteudo;
	
	public Mensagem(int id, int eventoId, String pessoa, String conteudo) {
		this.id = id;
		this.eventoId = eventoId;
		this.pessoa = pessoa;
		this.conteudo = conteudo;
	}
	
	//getters
	public int getId() {
		return id;
	}
	
	public int getEventoId() {
		return eventoId;
	}
	
	public String getPessoa() {
		return pessoa;
	}
	
	public String getConteudo() {
		return conteudo;
	}
}
