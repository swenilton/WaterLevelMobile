package br.com.coffeebeans.dispositivo;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.exception.AtividadeJaExistenteException;
import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.DispositivoJaExistenteException;
import br.com.coffeebeans.exception.DispositivoNaoEncontradoException;

public class ControladorDispositivo {
    private IDispositivoDAO iDispositivoDAO;

    public ControladorDispositivo(Context context) throws Exception {
        this.iDispositivoDAO = new DispositivoDAO(context);
    }

    public void cadastrar(Dispositivo dispositivo) throws SQLException, DAOException, AtividadeJaExistenteException {
        if (dispositivo == null) {
            throw new NullPointerException();
        }
        if (existe(dispositivo.getNome())) {
            throw new AtividadeJaExistenteException();
        }
        iDispositivoDAO.cadastrar(dispositivo);
    }

    public List<Dispositivo> listar() throws SQLException, DAOException {
        return iDispositivoDAO.listar();
    }

    public Dispositivo procurar(int id) throws SQLException, DAOException, DispositivoNaoEncontradoException {
        if (iDispositivoDAO.procurar(id) == null) {
            throw new DispositivoNaoEncontradoException();
        }
        return iDispositivoDAO.procurar(id);
    }

    public Dispositivo procurarNome(String nome) throws SQLException, DAOException, DispositivoNaoEncontradoException {
        return iDispositivoDAO.procurarNome(nome);
    }

    public void atualizar(Dispositivo dispositivo) throws SQLException, DispositivoNaoEncontradoException, DAOException, DispositivoJaExistenteException {
        if (dispositivo == null) {
            throw new NullPointerException();
        }

        if (iDispositivoDAO.procurar(dispositivo.getId()) == null) {
            throw new DispositivoNaoEncontradoException();
        }
        if (existe(dispositivo.getNome())) {
            throw new DispositivoJaExistenteException();
        }

        iDispositivoDAO.atualizar(dispositivo);
    }

    public void remover(int id) throws SQLException, DispositivoNaoEncontradoException, DAOException {
        if (iDispositivoDAO.procurar(id) == null) {
            throw new DispositivoNaoEncontradoException();
        } else {
            iDispositivoDAO.excluir(id);
        }
    }

    public boolean existe(String nome) throws SQLException, DAOException {
        return iDispositivoDAO.existe(nome);
    }

    public void setDispositivoAtivo(Dispositivo dispositivoAtivo) throws SQLException, DAOException {
        iDispositivoDAO.setDispositivoAtivo(dispositivoAtivo);
    }

    public Dispositivo getDispositivoAtivo() throws SQLException, DAOException {
        return iDispositivoDAO.getDispositivoAtivo();
    }

}
