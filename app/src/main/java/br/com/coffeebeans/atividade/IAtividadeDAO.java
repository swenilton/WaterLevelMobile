package br.com.coffeebeans.atividade;

import java.sql.SQLException;
import java.util.List;

import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;

public interface IAtividadeDAO {
	
	public void cadastrar(Atividade atividade) throws SQLException;
	
	public List<Atividade> listar() throws SQLException;
	
	public Atividade procurar (int id) throws SQLException,AtividadeNaoEncontradaException;
	
	public void atualizar(Atividade atividade) throws AtividadeNaoEncontradaException,
	SQLException;
	
	public void excluir (int id) throws SQLException, AtividadeNaoEncontradaException;
}