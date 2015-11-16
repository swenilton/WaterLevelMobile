package br.com.coffeebeans.dispositivo;

import java.sql.SQLException;
import java.util.List;

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.exception.DAOException;

public interface IDispositivoDAO {
	
	public void cadastrar(Dispositivo dispositivo) throws SQLException, DAOException;
	
	public List<Dispositivo> listar() throws SQLException, DAOException;
	
	public Dispositivo procurar(int id) throws SQLException, DAOException;

	Dispositivo procurarNome(String nome) throws SQLException, DAOException;

	public void atualizar(Dispositivo dispositivo) throws
			SQLException, DAOException;
	
	public void excluir(int id) throws SQLException, DAOException;

	public boolean existe(String descricao) throws SQLException,DAOException;

	public Dispositivo getDispositivoAtivo() throws DAOException, SQLException;

	public void setDispositivoAtivo(Dispositivo dispositivoAtivo) throws SQLException, DAOException;
}