package br.com.coffeebeans.repositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.RepositorioJaExistenteException;
import br.com.coffeebeans.exception.RepositorioNaoEncontradoException;

public class ControladorRepositorio {

	private IRepositorioDAO irepositorio;

	public ControladorRepositorio() throws Exception {
		this.irepositorio = new RepositorioDAO();
	}

	public void cadastrar(Repositorio repositorio) throws SQLException,
			RepositorioJaExistenteException, RepositorioNaoEncontradoException, RepositorioException {
		if (repositorio == null) {
			throw new NullPointerException();
		}
		if (irepositorio.procurar(repositorio.getDescricao()) != null) {
			throw new RepositorioJaExistenteException();
		}
		irepositorio.cadastrar(repositorio);

	}

	public ArrayList<Repositorio> listar() throws SQLException, RepositorioException{
		return irepositorio.listar();

	}

	public Repositorio procurar(int id) throws SQLException,
			RepositorioNaoEncontradoException {
		if (irepositorio.procurar(id) == null) {
			throw new RepositorioNaoEncontradoException();
		}

		return irepositorio.procurar(id);

	}

	public Repositorio procurar(String descricao) throws SQLException,
			RepositorioNaoEncontradoException {
		if (irepositorio.procurar(descricao) == null) {
			throw new RepositorioNaoEncontradoException();
		}

		return irepositorio.procurar(descricao);

	}

	public void atualizar(Repositorio repositorioNovo) throws SQLException,
			RepositorioNaoEncontradoException {
		if (repositorioNovo == null) {
			throw new NullPointerException();
		}

		if (irepositorio.procurar(repositorioNovo.getId()) == null) {
			throw new RepositorioNaoEncontradoException();

		}
		irepositorio.atualizar(repositorioNovo);
	}

	public void remover(int id) throws SQLException,
			RepositorioNaoEncontradoException {
		if (irepositorio.procurar(id) == null) {
			throw new RepositorioNaoEncontradoException();
		} else {
			irepositorio.excluir(id);
		}
	}
}
