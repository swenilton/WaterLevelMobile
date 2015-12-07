package br.com.coffeebeans.leitura;

import java.util.Date;

import br.com.coffeebeans.repositorio.Repositorio;

public class Leitura {
	
	private int id;
	private double leitura;
	private Date dataHora;
	private String dataHoraString;
	private Repositorio repositorio;
	private int idRepositorio;
	
	public Leitura(double leitura, Date dataHora, Repositorio repositorio) {
		super();
		this.leitura = leitura;
		this.dataHora = dataHora;
		this.repositorio = repositorio;
	}
	
	public Leitura(double leitura, String dataHoraString, int idRepositorio) {
		super();
		this.leitura = leitura;
		this.dataHoraString = dataHoraString;
		this.idRepositorio = idRepositorio;
	}
	
	public Leitura() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLeitura() {
		return leitura;
	}

	public void setLeitura(double leitura) {
		this.leitura = leitura;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getDataHoraString() {
		return dataHoraString;
	}

	public void setDataHoraString(String dataHoraString) {
		this.dataHoraString = dataHoraString;
	}

	public Repositorio getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
	}

	public int getIdRepositorio() {
		return idRepositorio;
	}

	public void setIdRepositorio(int idRepositorio) {
		this.idRepositorio = idRepositorio;
	}
}
