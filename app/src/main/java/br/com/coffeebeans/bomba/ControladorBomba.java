package br.com.coffeebeans.bomba;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.coffeebeans.exception.BombaJaExistenteException;
import br.com.coffeebeans.exception.BombaNaoEncontradaException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.ViolacaoChaveEstrangeiraException;

public class ControladorBomba {
	private IBombaDAO iBomba;

	public ControladorBomba() throws Exception {
		this.iBomba = new BombaDAO();
	}

	public void cadastrar(Bomba bomba) throws SQLException,
			BombaJaExistenteException, BombaNaoEncontradaException,
			ViolacaoChaveEstrangeiraException, RepositorioException {
		if (bomba == null) {
			throw new NullPointerException();
		}
		if (iBomba.procurar(bomba.getDescricao()) != null) {
			throw new BombaJaExistenteException();
		}
		if (!(bomba.getAcionamento().equals(Bomba.ACIONAMENTO_AUTOMATICO))
				&& (!bomba.getAcionamento().equals(Bomba.ACIONAMENTO_MANUAL))) {
			throw new IllegalArgumentException("Modo de acionamento inválido");
		}
		iBomba.cadastrar(bomba);

	}

	public ArrayList<Bomba> listar() throws SQLException, ListaVaziaException {
		return iBomba.listar();

	}

	public Bomba procurar(int id) throws SQLException,
			BombaNaoEncontradaException {
		if (iBomba.procurar(id) == null) {
			throw new BombaNaoEncontradaException();
		}

		return iBomba.procurar(id);
	}

	public Bomba procurarPorRepositorio(int idRepositorio) throws SQLException,
			BombaNaoEncontradaException {
		return iBomba.procurarPorRepositorio(idRepositorio);
	}

	public Bomba procurar(String descricao) throws SQLException,
			BombaNaoEncontradaException {
		if (iBomba.procurar(descricao) == null) {
			throw new BombaNaoEncontradaException();
		}

		return iBomba.procurar(descricao);

	}

	public void atualizar(Bomba bomba) throws SQLException,
			BombaNaoEncontradaException {
		if (bomba == null) {
			throw new NullPointerException();
		}

		if (iBomba.procurar(bomba.getCodigo()) == null) {
			throw new BombaNaoEncontradaException();

		}
		if (!(bomba.getAcionamento().equals(Bomba.ACIONAMENTO_AUTOMATICO))
				&& (!bomba.getAcionamento().equals(Bomba.ACIONAMENTO_MANUAL))) {
			throw new IllegalArgumentException("Modo de acionamento inv�lido");
		}

		iBomba.atualizar(bomba);
	}

	public void remover(int id) throws SQLException,
			BombaNaoEncontradaException {
		if (iBomba.procurar(id) == null) {
			throw new BombaNaoEncontradaException();
		} else {
			iBomba.excluir(id);
		}
	}
}
