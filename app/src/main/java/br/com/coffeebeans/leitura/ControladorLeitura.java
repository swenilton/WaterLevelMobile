package br.com.coffeebeans.leitura;

import java.sql.SQLException;

import br.com.coffeebeans.exception.DAOException;

public class ControladorLeitura {
	
	private ILeituraDAO leituraDAO;
	
	public ControladorLeitura() throws Exception {
		this.leituraDAO = new LeituraDAO();
	}
	
	public Double getUltimaLeitura(int idRepositorio) throws SQLException, DAOException {
		return this.leituraDAO.getUltimaLeitura(idRepositorio);
	}

}
