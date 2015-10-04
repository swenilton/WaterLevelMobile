package br.com.coffeebeans.acionamento;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import br.com.coffeebeans.exception.AcionamentoJaExistenteException;
import br.com.coffeebeans.exception.AcionamentoNaoEncontradoException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;

public interface IAcionamentoDAO {
	public void cadastrar(Acionamento acionamento) throws SQLException,
			AcionamentoJaExistenteException, RepositorioException;

	public ArrayList<Acionamento> listar() throws SQLException,
			ListaVaziaException, RepositorioException;

	public Acionamento procurar(int id) throws SQLException,
			AcionamentoNaoEncontradoException, RepositorioException;

	public Acionamento procurarIni(Date data1, Date data2)
			throws SQLException, AcionamentoNaoEncontradoException, RepositorioException;

	public Acionamento procurarFim(Timestamp data1, Timestamp data2)
			throws SQLException, AcionamentoNaoEncontradoException, RepositorioException;

	public void atualizar(Acionamento acionamento)
			throws AcionamentoNaoEncontradoException, SQLException;

	public void excluir(int id) throws SQLException,
			AcionamentoNaoEncontradoException, RepositorioException;

	public ArrayList<Acionamento> getUltimosAcionamentos() throws SQLException,
			ListaVaziaException, RepositorioException;

}
