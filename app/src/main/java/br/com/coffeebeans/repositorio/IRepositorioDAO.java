package br.com.coffeebeans.repositorio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.RepositorioJaExistenteException;
import br.com.coffeebeans.exception.RepositorioNaoEncontradoException;

public interface IRepositorioDAO {

	public void cadastrar(Repositorio repositorio) throws SQLException, RepositorioJaExistenteException, RepositorioException;

	public List<Repositorio> listar() throws SQLException, RepositorioException;

	public Repositorio procurar(int id) throws SQLException,RepositorioNaoEncontradoException;

	public void atualizar(Repositorio repositorio) throws RepositorioNaoEncontradoException, SQLException;

	public void excluir(int id) throws SQLException,RepositorioNaoEncontradoException;

	public Repositorio procurar(String descricao) throws SQLException,RepositorioNaoEncontradoException;
}