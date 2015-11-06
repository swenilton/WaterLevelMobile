package br.com.coffeebeans.atividade;

import java.sql.SQLException;
import java.util.List;

import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.DAOException;

public interface IAtividadeDAO {
	
	public void cadastrar(Atividade atividade) throws SQLException, DAOException;
	
	public List<Atividade> listar() throws SQLException, DAOException;
	
	public Atividade procurar (int id) throws SQLException, DAOException;
	
	public void atualizar(Atividade atividade) throws
			SQLException, DAOException;
	
	public void excluir (int id) throws SQLException, DAOException;

	public boolean existe(String descricao) throws SQLException,DAOException;
}