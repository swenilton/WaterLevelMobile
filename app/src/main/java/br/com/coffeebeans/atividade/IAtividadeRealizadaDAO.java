package br.com.coffeebeans.atividade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.coffeebeans.exception.AtividadeJaExistenteException;
import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.ViolacaoChaveEstrangeiraException;
import br.com.coffeebeans.usuario.Usuario;

public interface IAtividadeRealizadaDAO {

	public void cadastrar(AtividadeRealizada atividadeRealizada)
			throws SQLException, AtividadeJaExistenteException,
			ViolacaoChaveEstrangeiraException, RepositorioException;

	public List<AtividadeRealizada> listar() throws SQLException,
			ListaVaziaException, RepositorioException;

	public AtividadeRealizada procurar(int id) throws SQLException,
			AtividadeNaoEncontradaException, RepositorioException;

	public List<AtividadeRealizada> procurar(String descricao) throws SQLException,
			AtividadeNaoEncontradaException, RepositorioException;

	public void atualizar(AtividadeRealizada atividadeRealizada)
			throws AtividadeNaoEncontradaException, SQLException, RepositorioException;

	public void excluir(int id) throws AtividadeNaoEncontradaException,
			SQLException, RepositorioException;
	
	public List<AtividadeRealizada> getUltimasAtividades() throws RepositorioException, SQLException;

	public List<AtividadeRealizada> listar(int id) throws SQLException,
			ListaVaziaException, RepositorioException;

	boolean existe(int id_usuario, int id_atividade, Date dataHoraInicio,
				   Date dataHoraFim) throws SQLException, DAOException;
}
