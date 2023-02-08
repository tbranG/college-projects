package models;

public class RelacaoEvento {
	private int id;
	private int evento;
	private String participante;
	private boolean confirmado;
	
	public RelacaoEvento(int id, int evento, String participante, boolean confirmado) {
		this.id = id;
		this.evento = evento;
		this.participante = participante;
		this.confirmado = confirmado;
	}
	
	//getters
	public int getId() {
		return id;
	}
	
	public int getEvento() {
		return evento;
	}
	
	public String getParticipante() {
		return participante;
	}
	
	public boolean getConfirmado() {
		return confirmado;
	}
	
	//setters
	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}
}
