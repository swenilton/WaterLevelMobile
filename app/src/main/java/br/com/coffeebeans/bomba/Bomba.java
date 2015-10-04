package br.com.coffeebeans.bomba;

import br.com.coffeebeans.repositorio.Repositorio;

public class Bomba {
	private int codigo;
	private String descricao;
	private String status;
	private Double potencia;
	private Double vazao;
	private String acionamento;
	private Repositorio repositorioEnche;
	private Repositorio repositorioSeca;
	private int idRepositorioEnche;
	private int idRepositorioSeca;
	public static final String ACIONAMENTO_MANUAL = "MANUAL";
	public static final String ACIONAMENTO_AUTOMATICO = "AUTOMATICO";
	public static final String ON = "LIGADA";
	public static final String OFF = "DESLIGADA";

	public Bomba(String descricao, Double potencia,
			Double vazao, String acionamento)
			throws IllegalArgumentException {
		this.descricao = descricao;
		this.potencia = potencia;
		this.vazao = vazao;
		this.acionamento = acionamento;
	}

	public Bomba(String descricao, String status, Double potencia,
			Double vazao, String acionamento, int idRepositorioEnche)
			throws IllegalArgumentException {
		this.descricao = descricao;
		this.status = status;
		this.potencia = potencia;
		this.vazao = vazao;
		this.acionamento = acionamento;
	}

	public int getIdRepositorioEnche() {
		return idRepositorioEnche;
	}

	public void setIdRepositorioEnche(int idRepositorioEnche) {
		this.idRepositorioEnche = idRepositorioEnche;
	}

	public int getIdRepositorioSeca() {
		return idRepositorioSeca;
	}

	public void setIdRepositorioSeca(int idRepositorioSeca) {
		this.idRepositorioSeca = idRepositorioSeca;
	}

	public String getAcionamento() {
		return acionamento;
	}

	public void setAcionamento(String acionamento) {
		this.acionamento = acionamento;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getPotencia() {
		return potencia;
	}

	public void setPotencia(Double potencia) {
		this.potencia = potencia;
	}

	public Double getVazao() {
		return vazao;
	}

	public void setVazao(Double vazao) {
		this.vazao = vazao;
	}

	public Repositorio getRepositorioEnche() {
		return repositorioEnche;
	}

	public void setRepositorioEnche(Repositorio repositorioEnche) {
		this.repositorioEnche = repositorioEnche;
	}

	public Repositorio getRepositorioSeca() {
		return repositorioSeca;
	}

	public void setRepositorioSeca(Repositorio repositorioSeca) {
		this.repositorioSeca = repositorioSeca;
	}

	@Override
	public String toString() {
		return "Bomba [codigo=" + codigo + ", descricao=" + descricao
				+ ", status=" + status + ", potencia=" + potencia + ", vazao="
				+ vazao + ", acionamento=" + acionamento
				+ ", idRepositorioEnche=" + idRepositorioEnche
				+ ", idRepositorioSeca=" + idRepositorioSeca + "]" + "\n";
	}
	
}
