package br.com.coffeebeans.atividade;

public class Atividade {
	
	private int id;
	private String descricao;
	
	public Atividade(String descricao) {
		this.descricao = descricao;
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

	@Override
	public String toString() {
		return "Atividade [id=" + id + ", descricao=" + descricao +"]";
	}	

}
