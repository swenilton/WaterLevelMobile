package br.com.coffeebeans.bomba;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.coffeebeans.exception.BombaJaExistenteException;
import br.com.coffeebeans.exception.BombaNaoEncontradaException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.ViolacaoChaveEstrangeiraException;

public interface IBombaDAO {
	public void cadastrar(Bomba bomba) throws SQLException,
			BombaJaExistenteException, ViolacaoChaveEstrangeiraException,
			RepositorioException;

	public List<Bomba> listar() throws SQLException, ListaVaziaException;

	public Bomba procurar(int id) throws SQLException,
			BombaNaoEncontradaException;

	public Bomba procurarPorRepositorio(int idRepositorio) throws SQLException,
			BombaNaoEncontradaException;

	public void atualizar(Bomba bomba) throws BombaNaoEncontradaException,
			SQLException;

	public void excluir(int id) throws SQLException,
			BombaNaoEncontradaException;

	public Bomba procurar(String descricao) throws SQLException,
			BombaNaoEncontradaException;

}
