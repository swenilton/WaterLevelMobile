package br.com.coffeebeans.acionamento;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.coffeebeans.exception.AcionamentoJaExistenteException;
import br.com.coffeebeans.exception.AcionamentoNaoEncontradoException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;

public interface IAcionamentoDAO {
	public void cadastrar(Acionamento acionamento) throws SQLException,
			AcionamentoJaExistenteException, RepositorioException;

	public List<Acionamento> listar() throws SQLException,
			ListaVaziaException, RepositorioException;

	public Acionamento procurar(int id) throws SQLException,
			AcionamentoNaoEncontradoException, RepositorioException;

	public Acionamento procurarIni(Date data1, Date data2)
			throws SQLException, AcionamentoNaoEncontradoException, RepositorioException;

	public Acionamento procurarFim(Date date3, Date date4)
			throws SQLException, AcionamentoNaoEncontradoException, RepositorioException;

	public void atualizar(Acionamento acionamento)
			throws AcionamentoNaoEncontradoException, SQLException;

	public void excluir(int id) throws SQLException,
			AcionamentoNaoEncontradoException, RepositorioException;

	public List<Acionamento> getUltimosAcionamentos() throws SQLException,
			ListaVaziaException, RepositorioException;

}
