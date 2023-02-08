package models;

import java.util.Date;

public class PoolData {
	private int id;
	private int qtdVotos;
	private int qtdOpcoes;
	private Date tempoLimite;
	private String info;
	private Date[] opcoes;
	private int[] votoOpcao;

	public PoolData(int id, Date tempoLimite, String info, Date[] opcoes) {
		this.id = id;
		this.qtdVotos = 0;
		this.qtdOpcoes = opcoes.length;
		this.tempoLimite = tempoLimite;
		this.info = info;
		this.opcoes = opcoes;
		this.votoOpcao = new int[opcoes.length];
	}

	/**
	 * {@summary Adiciona um voto em uma das op��es da pool.}
	 * 
	 * @param opcao = opcao a,b,c ... da pool.
	 */
	public void addVoto(int opcao) {
		if (opcao >= votoOpcao.length)
			throw new ArrayIndexOutOfBoundsException();
		qtdVotos++;
		votoOpcao[opcao] += 1;
	}

	// getters
	/**
	 * Retorna o �ndice da op��o com mais votos na pool.
	 */
	public int getResultado() {
		int maior = -1;
		for (int i = 0; i < qtdOpcoes; i++) {
			if (votoOpcao[i] > maior) {
				maior = i;
			}
		}
		return maior;
	}

	public int getId() {
		return id;
	}

	public Date getOpcao(int i) {
		if (i >= votoOpcao.length)
			throw new ArrayIndexOutOfBoundsException();
		return opcoes[i];
	}

	public Date getData() {
		return tempoLimite;
	}

	public int getQtdVotos() {
		return qtdVotos;
	}

	public int getQtdOpcoes() {
		return qtdOpcoes;
	}

	public String getInfo() {
		return info;
	}

	public Date[] getOpcoes() {
		return opcoes;
	}

	public int[] getVotoOpcao() {
		return votoOpcao;
	}

	/**
	 * Checagem da data limite da pool com data atual
	 * 
	 * @return true se a data atual for maior do que o tempo limite
	 */
	public boolean checaData() {
		boolean resp = false;
		Date atual = new Date(System.currentTimeMillis());
		if (this.tempoLimite.before(atual)) {
			resp = true;
		}
		return resp;
	}
}