package br.com.coffeebeans.leitura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.coffeebeans.exception.BDException;
import br.com.coffeebeans.exception.DAOException;
//import br.com.coffeebeans.util.Conexao;

public class LeituraDAO implements ILeituraDAO {
	
	private Connection connection = null;
	private String sistema = "mysql";
	
	public LeituraDAO() throws Exception {
		//this.connection = Conexao.conectar(sistema);
	}

	@Override
	public Double getUltimaLeitura(int idRepositorio) throws SQLException, DAOException {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		double retorno = 0;
		try {
			if (connection != null) {
				String sql = "SELECT * FROM LEITURA WHERE ID_REPOSITORIO = ? ORDER BY DATA_HORA DESC LIMIT 1";
				stmt = this.connection.prepareStatement(sql);
				stmt.setInt(1, idRepositorio);
				rs = stmt.executeQuery();
				if (rs.next()) {
					retorno = rs.getDouble("LEITURA");
				}
			} else
				throw new BDException();
		} catch (SQLException e) {
			throw new SQLException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		}

		return retorno;
	}

}
