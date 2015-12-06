package br.com.coffeebeans.repositorio;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonTypeInfo;
@XmlRootElement
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

public class RepositorioCircular extends Repositorio {
	private Double diametroMedio;

	public RepositorioCircular(String descricao, Double profundidade,
			Double limiteMinimo, Double limiteMaximo, Double diametroMedio) {
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

	public RepositorioCircular() {
	}

}
