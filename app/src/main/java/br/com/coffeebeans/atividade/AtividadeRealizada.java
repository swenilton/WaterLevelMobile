package br.com.coffeebeans.atividade;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import br.com.coffeebeans.usuario.Usuario;

@XmlRootElement
public class AtividadeRealizada {

	private int id;
	private Atividade atividade;
	private Date dataHoraInicio;
	private Date dataHoraFim;
	private Usuario usuario;
	private int idUsuario;
	private int idAtividade;
	private double gasto;
	private String dateHoraInicio;
	private String dateHoraFim;
	
	
	public AtividadeRealizada(Atividade atividade, Date dataHoraInicio,
			Date dataHoraFim, Usuario usuario, double gasto,String dateHoraInicio,String dateHoraFim) {
		this.atividade = atividade;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.usuario = usuario;
		this.gasto = gasto;
		this.setDateHoraInicio(dateHoraInicio);
		this.setDateHoraFim(dateHoraFim);
	}

	
	
	public AtividadeRealizada(Atividade atividade, Date dataHoraInicio,
			Date dataHoraFim, Usuario usuario, double gasto) {
		this.atividade = atividade;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.usuario = usuario;
		this.gasto = gasto;
	}

	public AtividadeRealizada(Date dataHoraInicio, Date dataHoraFim,
			int idUsuario, int idAtividade, double gasto) {
		super();
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.idUsuario = idUsuario;
		this.idAtividade = idAtividade;
		this.gasto = gasto;
	}

	public double getGasto() {
		return gasto;
	}

	public void setGasto(double gasto) {
		this.gasto = gasto;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(int idAtividade) {
		this.idAtividade = idAtividade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}
	//@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}
	public Date getDataHoraFim() {
		return dataHoraFim;
	}


	//@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "id=" + id + ", dataHoraInicio=" + dataHoraInicio
				+ ", dataHoraFim=" + dataHoraFim + ", idUsuario=" + idUsuario
				+ ", idAtividade=" + idAtividade + "\n";
	}

	public AtividadeRealizada() {

	}



	public String getDateHoraInicio() {
		return dateHoraInicio;
	}



	public void setDateHoraInicio(String dateHoraInicio) {
		this.dateHoraInicio = dateHoraInicio;
	}



	public String getDateHoraFim() {
		return dateHoraFim;
	}



	public void setDateHoraFim(String dateHoraFim) {
		this.dateHoraFim = dateHoraFim;
	}
}
