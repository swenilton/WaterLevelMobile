package br.com.coffeebeans.atividade;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.coffeebeans.exception.AtividadeJaExistenteException;
import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.ViolacaoChaveEstrangeiraException;

/**
 * Created by AndréFillipe on 05/12/2015.
 */
public class AtividadeRealizadaDaoWs implements IAtividadeRealizadaDAO {
    @Override
    public void cadastrar(AtividadeRealizada atividadeRealizada) throws SQLException, AtividadeJaExistenteException, ViolacaoChaveEstrangeiraException, RepositorioException {

    }

    @Override
    public List<AtividadeRealizada> listar() throws SQLException, ListaVaziaException, RepositorioException {
        return null;
    }

    @Override
    public AtividadeRealizada procurar(int id) throws SQLException, AtividadeNaoEncontradaException, RepositorioException {
        return null;
    }

    @Override
    public List<AtividadeRealizada> procurar(String descricao) throws SQLException, AtividadeNaoEncontradaException, RepositorioException {
        return null;
    }


    @Override
    public void atualizar(AtividadeRealizada atividadeRealizada) throws AtividadeNaoEncontradaException, SQLException, RepositorioException {

    }

    @Override
    public void excluir(int id) throws AtividadeNaoEncontradaException, SQLException, RepositorioException {

    }

    @Override
    public List<AtividadeRealizada> getUltimasAtividades() throws RepositorioException, SQLException {
        return null;
    }

    @Override
    public List<AtividadeRealizada> listar(int id) throws SQLException, ListaVaziaException, RepositorioException {
        return null;
    }

    @Override
    public boolean existe(int id_usuario, int id_atividade, Date dataHoraInicio, Date dataHoraFim) throws SQLException, DAOException {
        return false;
    }
}
