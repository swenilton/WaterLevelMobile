package br.com.coffeebeans.bomba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.coffeebeans.exception.BombaNaoEncontradaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.ViolacaoChaveEstrangeiraException;

public class BombaDAO implements IBombaDAO {
	private Connection connection = null;
	private String sistema = "mysql";

	public BombaDAO() throws Exception {
		//this.connection = Conexao.conectar(sistema);
	}

	public void cadastrar(Bomba bomba) throws SQLException,
			ViolacaoChaveEstrangeiraException, RepositorioException {
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO BOMBA (DESCRICAO,STATUS,POTENCIA,VAZAO,ACIONAMENTO,ID_REPOSITORIO_ENC,ID_REPOSITORIO_SEC)"
					+ "VALUES(?,?,?,?,?,?,?)";
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, bomba.getDescricao());
			stmt.setString(2, bomba.getStatus());
			stmt.setDouble(3, bomba.getPotencia());
			stmt.setDouble(4, bomba.getVazao());
			stmt.setString(5, bomba.getAcionamento());
			stmt.setInt(6, bomba.getRepositorioEnche().getId());
			stmt.setInt(7, bomba.getRepositorioSeca().getId());
			stmt.execute();
		} catch (Exception e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
		}
	}

	public ArrayList<Bomba> listar() throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Bomba bomba = null;
		ArrayList<Bomba> bombas = new ArrayList<Bomba>();
		try {
			String sql = "SELECT * FROM BOMBA";
			stmt = this.connection.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				bomba = new Bomba(rs.getString("DESCRICAO"),
						rs.getString("STATUS"), rs.getDouble("POTENCIA"),
						rs.getDouble("VAZAO"), rs.getString("ACIONAMENTO"),
						rs.getInt("ID_REPOSITORIO_ENC"));
				bomba.setCodigo(rs.getInt("ID"));
				bomba.setIdRepositorioSeca(rs.getInt("ID_REPOSITORIO_SEC"));
				bombas.add(bomba);

			}

		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			stmt.close();
			rs.close();
		}
		return bombas;
	}

	public Bomba procurar(String descricao) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Bomba bomba = null;
		try {
			String sql = "SELECT * FROM BOMBA WHERE DESCRICAO=?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, descricao);
			rs = stmt.executeQuery();

			if (rs.next()) {
				bomba = new Bomba(rs.getString("DESCRICAO"),
						rs.getString("STATUS"), rs.getDouble("POTENCIA"),
						rs.getDouble("VAZAO"), rs.getString("ACIONAMENTO"),
						rs.getInt("ID_REPOSITORIO_ENC"));
				bomba.setCodigo(rs.getInt("ID"));
				bomba.setIdRepositorioSeca(rs.getInt("ID_REPOSITORIO_SEC"));

			}

		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			stmt.close();
			rs.close();
		}
		return bomba;
	}

	public Bomba procurar(int id) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Bomba bomba = null;
		try {
			String sql = "SELECT * FROM BOMBA WHERE ID=?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				bomba = new Bomba(rs.getString("DESCRICAO"),
						rs.getString("STATUS"), rs.getDouble("POTENCIA"),
						rs.getDouble("VAZAO"), rs.getString("ACIONAMENTO"),
						rs.getInt("ID_REPOSITORIO_ENC"));
				bomba.setCodigo(rs.getInt("ID"));
				bomba.setIdRepositorioSeca(rs.getInt("ID_REPOSITORIO_SEC"));
			}
		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			stmt.close();
			rs.close();
		}
		return bomba;
	}
	
	public Bomba procurarPorRepositorio(int idRepositorio) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Bomba bomba = null;
		try {
			String sql = "SELECT * FROM BOMBA WHERE ID_REPOSITORIO_ENC = ?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, idRepositorio);
			rs = stmt.executeQuery();
			if (rs.next()) {
				bomba = new Bomba(rs.getString("DESCRICAO"),
						rs.getString("STATUS"), rs.getDouble("POTENCIA"),
						rs.getDouble("VAZAO"), rs.getString("ACIONAMENTO"),
						rs.getInt("ID_REPOSITORIO_ENC"));
				bomba.setCodigo(rs.getInt("ID"));
				bomba.setIdRepositorioSeca(rs.getInt("ID_REPOSITORIO_SEC"));
			}
		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			stmt.close();
			rs.close();
		}
		return bomba;
	}

	public void atualizar(Bomba bomba) throws SQLException {
		PreparedStatement stmt = null;
		try {
			String sql = "UPDATE BOMBA SET DESCRICAO=?,STATUS=?,POTENCIA=?,VAZAO=?,ACIONAMENTO=?,ID_REPOSITORIO_ENC=?,ID_REPOSITORIO_SEC=? WHERE ID=?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, bomba.getDescricao());
			stmt.setString(2, bomba.getStatus());
			stmt.setDouble(3, bomba.getPotencia());
			stmt.setDouble(4, bomba.getVazao());
			stmt.setString(5, bomba.getAcionamento());
			stmt.setInt(6, bomba.getIdRepositorioEnche());
			stmt.setInt(7, bomba.getIdRepositorioSeca());
			stmt.setInt(8, bomba.getCodigo());

			Integer resultado = stmt.executeUpdate();
			if (resultado == 0) {
				throw new BombaNaoEncontradaException();
			}

		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			stmt.close();
		}
	}

	public void excluir(int id) throws SQLException {
		PreparedStatement stmt = null;
		try {
			String sql = "DELETE FROM BOMBA WHERE ID=?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			stmt.close();
		}
	}
}
