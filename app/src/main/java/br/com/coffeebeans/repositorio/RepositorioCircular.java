package br.com.coffeebeans.repositorio;

public class RepositorioCircular extends Repositorio {
	private Double diametroMedio;

	public RepositorioCircular(String descricao, Double profundidade, Double limiteMinimo, Double limiteMaximo,
			Double diametroMedio) {
		super(descricao, calcularCapacidade(diametroMedio, profundidade),
				profundidade, limiteMinimo, limiteMaximo);
		this.diametroMedio = diametroMedio;
	}

	@Override
	public String toString() {
		return super.toString() + " diametroMedio=" + diametroMedio + "\n";
	}

	public Double getDiametroMedio() {
		return diametroMedio;
	}

	public void setDiametroMedio(Double diametroMedio) {
		this.diametroMedio = diametroMedio;
	}

	protected static double calcularCapacidade(double diametro,
			double profundidade) {
		return Math.PI * Math.pow((diametro / 2), 2) * profundidade;
	}

}
