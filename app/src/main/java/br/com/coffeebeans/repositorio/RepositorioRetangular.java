package br.com.coffeebeans.repositorio;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class RepositorioRetangular extends Repositorio {
	private Double areaBase;

	public RepositorioRetangular(String descricao, Double profundidade,
			Double limiteMinimo, Double limiteMaximo, Double areaBase) {
		super(descricao, calcularCapacidade(areaBase, profundidade),
				profundidade, limiteMinimo, limiteMaximo);
		this.areaBase = areaBase;
	}

	@Override
	public String toString() {
		return super.toString() + " areaBase=" + areaBase + "\n";
	}

	public Double getAreaBase() {
		return areaBase;
	}

	public void setAreaBase(Double areaBase) {
		this.areaBase = areaBase;
	}

	protected static double calcularCapacidade(double area, double profundidade) {
		return area * profundidade;
	}

	public RepositorioRetangular() {

	}
}
