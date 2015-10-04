package br.com.coffeebeans.atividade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.util.Conexao;


public class AtividadeDAO implements IAtividadeDAO {
	
	private Connection connection = null;
	private String sistema = "mysql";
	
	public AtividadeDAO() throws Exception {
		this.connection = Conexao.conectar(sistema);
	}
	
	public void cadastrar (Atividade atividade) throws SQLException,  
		RepositorioException {
		PreparedStatement stmt = null;
		
		try {
			String sql = "INSERT INTO ATIVIDADE (DESCRICAO) VALUES (?)";
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, atividade.getDescricao());
			stmt.execute();
		} catch (SQLException e) {
			throw new RepositorioException(e);
		} catch (Exception e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
		}
	}
	
	public List<Atividade> listar() throws SQLException, RepositorioException {
		List<Atividade> atividades = new ArrayList<Atividade>();
		PreparedStatement stmt = null;
		ResultSet rs = null;		
		try {
			String sql = "SELECT * FROM ATIVIDADE";
			stmt = this.connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Atividade atividade = new Atividade (rs.getString("DESCRICAO"));
				atividade.setId(rs.getInt("ID"));
				atividades.add(atividade);
			}
		} catch (SQLException e) {
			throw new RepositorioException(e);
		} catch (Exception e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
			rs.close();
		}	
		return atividades;
	}
	
	public Atividade procurar (int id) throws SQLException, RepositorioException {
		Atividade atividade = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM ATIVIDADE WHERE ID = ?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				atividade = new Atividade(rs.getString("DESCRICAO"));
				atividade.setId(rs.getInt("ID"));
			} else {
				throw new IllegalArgumentException("Atividade não encontrada");
			}
		} catch (SQLException e) {
			throw new RepositorioException(e);
		} catch (Exception e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
			rs.close();
		}
		return atividade;
	}
	
	public void atualizar(Atividade atividade) throws AtividadeNaoEncontradaException,
		SQLException, RepositorioException {
		PreparedStatement stmt = null;
		try {
			if (atividade != null) {
				try {
					String sql = "UPDATE ATIVIDADE SET DESCRICAO = ? WHERE ID = ?";
					stmt = this.connection.prepareStatement(sql);
					stmt.setString(1, atividade.getDescricao());
					stmt.setInt(2, atividade.getId());
					Integer resultado = stmt.executeUpdate();
					if (resultado == 0)
						throw new AtividadeNaoEncontradaException();
				} catch (SQLException e) {
					throw new RepositorioException(e);
				}
			}
		} finally {
			stmt.close();
		}
	}
	
	public void excluir (int id) throws SQLException, AtividadeNaoEncontradaException,
		RepositorioException {
		PreparedStatement stmt = null;
		try {
			String sql = "DELETE FROM ATIVIDADE WHERE ID = ?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		} catch (SQLException e) {
			throw new RepositorioException(e);
		} catch (Exception e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
		}
	}
} 
