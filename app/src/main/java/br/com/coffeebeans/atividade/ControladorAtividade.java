package br.com.coffeebeans.atividade;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import br.com.coffeebeans.exception.AtividadeJaExistenteException;
import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.DAOException;

public class ControladorAtividade {
    private IAtividadeDAO iatividade;

    public ControladorAtividade(Context context) throws Exception {
        this.iatividade = new AtividadeDAOWS();
    }

    public void cadastrar(Atividade atividade) throws SQLException, DAOException, AtividadeJaExistenteException {
        if (atividade == null) {
            throw new NullPointerException();
        }
        if (existe(atividade.getDescricao())) {
            throw new AtividadeJaExistenteException();
        }
        iatividade.cadastrar(atividade);
    }

    public List<Atividade> listar() throws SQLException, DAOException {
        return iatividade.listar();
    }

    public Atividade procurar(int id) throws SQLException, AtividadeNaoEncontradaException, DAOException {
        if (iatividade.procurar(id) == null) {
            throw new AtividadeNaoEncontradaException();
        }
        return iatividade.procurar(id);
    }

    public void atualizar(Atividade atividadeNova) throws SQLException, AtividadeNaoEncontradaException, DAOException, AtividadeJaExistenteException {
        if (atividadeNova == null) {
            throw new NullPointerException();
        }
        if (iatividade.procurar(atividadeNova.getId()) == null) {
            throw new AtividadeNaoEncontradaException();
        }
        iatividade.atualizar(atividadeNova);
    }

    public void remover(int id) throws SQLException, AtividadeNaoEncontradaException, DAOException {
        if (iatividade.procurar(id) == null) {
            throw new AtividadeNaoEncontradaException();
        } else {
            iatividade.excluir(id);
        }
    }

    public boolean existe(String descricao) throws SQLException, DAOException {
        return iatividade.existe(descricao);
    }

}
