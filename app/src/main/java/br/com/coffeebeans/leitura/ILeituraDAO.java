package br.com.coffeebeans.leitura;

import java.sql.SQLException;

import br.com.coffeebeans.exception.DAOException;

public interface ILeituraDAO {
	
	public Double getUltimaLeitura(int idRepositorio) throws SQLException, DAOException;

}
