package br.com.coffeebeans.atividade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.ListaUsuarioVaziaException;
import br.com.coffeebeans.exception.RepositorioException;

public class ControladorAtividade {
	private IAtividadeDAO iatividade;
	
	public ControladorAtividade() throws Exception {
		this.iatividade = new AtividadeDAO();
	}
	
	public void cadastrar (Atividade atividade) throws SQLException,
		RepositorioException {
		if (atividade == null) {
			throw new IllegalArgumentException("Atividade null");
		}
		iatividade.cadastrar(atividade);
	}
	
	public List<Atividade> listar() throws SQLException, RepositorioException,
		ListaUsuarioVaziaException {
		return iatividade.listar();
	}
	
	public Atividade procurar (int id) throws SQLException,
		AtividadeNaoEncontradaException, RepositorioException {		
		return iatividade.procurar(id);
	}
	
	public void atualizar (Atividade atividadeNova) throws SQLException,
		AtividadeNaoEncontradaException, RepositorioException {
		if (atividadeNova == null) {
			throw new NullPointerException();
		}
		
		if (iatividade.procurar(atividadeNova.getId()) == null) {
			throw new AtividadeNaoEncontradaException();
		}
		iatividade.atualizar(atividadeNova);
	}
	
	public void remover(int id) throws SQLException,
		AtividadeNaoEncontradaException, RepositorioException {
		if (iatividade.procurar(id) == null) {
			throw new AtividadeNaoEncontradaException();
		} else {
			iatividade.excluir(id);
		}
	}

}
