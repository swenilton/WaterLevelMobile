package br.com.coffeebeans.atividade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.RepositorioException;

public interface IAtividadeDAO {
	
	public void cadastrar(Atividade atividade) throws SQLException,  
	RepositorioException;
	
	public List<Atividade> listar() throws SQLException, RepositorioException;
	
	public Atividade procurar (int id) throws SQLException, RepositorioException;
	
	public void atualizar(Atividade atividade) throws AtividadeNaoEncontradaException,
	SQLException, RepositorioException;
	
	public void excluir (int id) throws SQLException, AtividadeNaoEncontradaException,
	RepositorioException;
}