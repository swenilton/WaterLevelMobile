package br.com.coffeebeans.repositorio;

public abstract class Repositorio {
	private int id;
	private String descricao;
	private Double capacidade;
	private Double profundidade;
	private Double limiteMaximo;
	private Double limiteMinimo;
	

	public Repositorio(String descricao, double capacidade, Double profundidade, Double limiteMinimo, Double limiteMaximo) {
		this.descricao = descricao;
		this.capacidade = capacidade;
		this.profundidade = profundidade;
		this.limiteMinimo = limiteMinimo;
		this.limiteMaximo = limiteMaximo;

	}

	@Override
	public String toString() {
		return "Id="+id + ", Descricao=" + descricao + ", capacidade=" + capacidade
				+ ", profundidade= " + profundidade + " limiteMaximo="
				+ limiteMaximo + ", limiteMinimo=" + limiteMinimo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;

	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Double capacidade) {
		this.capacidade = capacidade;
	}

	public Double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}

	public Double getLimiteMaximo() {
		return limiteMaximo;
	}

	public void setLimiteMaximo(Double limiteMaximo) {
		this.limiteMaximo = limiteMaximo;
	}

	public Double getLimiteMinimo() {
		return limiteMinimo;
	}

	public void setLimiteMinimo(Double limiteMinimo) {
		this.limiteMinimo = limiteMinimo;
	}
}
