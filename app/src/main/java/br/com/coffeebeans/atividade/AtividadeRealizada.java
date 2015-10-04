package br.com.coffeebeans.atividade;

import java.util.Date;

import br.com.coffeebeans.usuario.Usuario;

public class AtividadeRealizada {

	private int id;
	private Atividade atividade;
	private Date dataHoraInicio;
	private Date dataHoraFim;
	private Usuario usuario;
	private int idUsuario;
	private int idAtividade;
	private double gasto;

	public AtividadeRealizada(Atividade atividade, Date dataHoraInicio,
			Date dataHoraFim, Usuario usuario, double gasto) {
		super();
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

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getDataHoraFim() {
		return dataHoraFim;
	}

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

}
