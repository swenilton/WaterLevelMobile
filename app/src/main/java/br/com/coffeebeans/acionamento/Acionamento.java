package br.com.coffeebeans.acionamento;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.coffeebeans.bomba.Bomba;

public class Acionamento {

	private int id;
	private Bomba bomba;
	private Date dataHoraInicio;
	private Date dataHoraFim;
	private int idBomba;
	private String tempo;

	public Acionamento(Bomba bomba, Date dataHoraInicio, Date dataHoraFim) {
		this.bomba = bomba;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.tempo = calculaTempo(dataHoraInicio, dataHoraFim);
	}

	public Acionamento(Date dataHoraInicio, Date dataHoraFim, int idBomba) {
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.idBomba = idBomba;
		this.tempo = calculaTempo(dataHoraInicio, dataHoraFim);
	}

	private String calculaTempo(Date inicio, Date fim) {
		Calendar dataInicio = Calendar.getInstance();
		dataInicio.setTime(inicio);
		Calendar dataFinal = Calendar.getInstance();
		dataFinal.setTime(fim);

		long diferenca = dataFinal.getTimeInMillis()
				- dataInicio.getTimeInMillis();
		long hours = (60 * 60 * 1000);
		long minutos = (60 * 1000);
		long diffHoras = diferenca / hours;
		long diffHorasMinutos = (diferenca % hours) / (60 * 1000);
		long diffHorasMinutosSegundos = (diferenca % minutos) / (1000);
		Calendar resultado = Calendar.getInstance();
		resultado.set(Calendar.HOUR_OF_DAY,
				Integer.parseInt(Long.toString(diffHoras)));
		resultado.set(Calendar.MINUTE,
				Integer.parseInt(Long.toString(diffHorasMinutos)));
		resultado.set(Calendar.SECOND,
				Integer.parseInt(Long.toString(diffHorasMinutosSegundos)));
		return new SimpleDateFormat("HH:mm:ss").format(resultado.getTime());
	}

	public String getTempo() {
		return tempo;
	}

	public int getIdBomba() {
		return idBomba;
	}

	public void setIdBomba(int idBomba) {
		this.idBomba = idBomba;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Bomba getBomba() {
		return bomba;
	}

	public void setBomba(Bomba bomba) {
		this.bomba = bomba;
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

	@Override
	public String toString() {
		return "Acionamento [id=" + id + ", bomba=" + bomba.getDescricao()
				+ ", dataHoraInicio=" + dataHoraInicio.toString()
				+ ", dataHoraFim=" + dataHoraFim.toString() + ", idBomba="
				+ idBomba + ", tempo=" + tempo + "]";
	}

}
