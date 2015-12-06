package br.com.coffeebeans.fachada;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.content.Context;

import br.com.coffeebeans.acionamento.Acionamento;
import br.com.coffeebeans.acionamento.ControladorAcionamento;
import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.atividade.AtividadeRealizada;
import br.com.coffeebeans.atividade.ControladorAtividade;
import br.com.coffeebeans.atividade.ControladorAtividadeRealizada;
import br.com.coffeebeans.bomba.Bomba;
import br.com.coffeebeans.bomba.ControladorBomba;
import br.com.coffeebeans.dispositivo.ControladorDispositivo;
import br.com.coffeebeans.dispositivo.Dispositivo;
import br.com.coffeebeans.exception.*;
import br.com.coffeebeans.repositorio.ControladorRepositorio;
import br.com.coffeebeans.repositorio.Repositorio;
import br.com.coffeebeans.usuario.ControladorUsuario;
import br.com.coffeebeans.usuario.Usuario;

public class Fachada {
    private static Fachada instance = null;
    public static Context context;
    ControladorUsuario controladorUsuario;
    ControladorAtividade controladorAtividade;
    ControladorAtividadeRealizada controladorAtividadeRealizada;
    ControladorDispositivo controladorDispositivo;
    ControladorAcionamento controladorAcionamento;
    ControladorBomba controladorBomba;
    ControladorRepositorio controladorRepositorio;

    private Fachada(Context context) throws Exception {
        Fachada.context = context;
        this.controladorAtividade = new ControladorAtividade(context);
        this.controladorUsuario = new ControladorUsuario(context);
        this.controladorAtividadeRealizada = new ControladorAtividadeRealizada(context);
        this.controladorDispositivo = new ControladorDispositivo(context);
        this.controladorAcionamento= new ControladorAcionamento(context);
        this.controladorRepositorio= new ControladorRepositorio();
    }

    public static Fachada getInstance(Context context) throws Exception {
        if (Fachada.instance == null) {
            Fachada.instance = new Fachada(context);
        }
        return Fachada.instance;
    }

    public <E> void cadastrar(E element) throws SQLException, UsuarioJaExistenteException,
            ViolacaoChaveEstrangeiraException, AtividadeJaExistenteException,
            DAOException, PermissaoException, EmailJaExistenteException, AtividadeNaoEncontradaException, RepositorioException, AtividadeRealizadaJaExistenteException, BombaNaoEncontradaException, BombaJaExistenteException, AcionamentoJaExistenteException, AcionamentoNaoEncontradoException, RepositorioJaExistenteException, RepositorioNaoEncontradoException {

        if (element instanceof Repositorio) {
            controladorRepositorio.cadastrar((Repositorio) element);
        }
       else if (element instanceof Usuario) {
            controladorUsuario.cadastrar((Usuario) element);
        } else if (element instanceof Atividade) {
            controladorAtividade.cadastrar((Atividade) element);
        } else if (element instanceof Dispositivo) {
            controladorDispositivo.cadastrar((Dispositivo) element);
        } else if (element instanceof AtividadeRealizada) {
            controladorAtividadeRealizada.cadastrar((AtividadeRealizada)
                    element);
        } else if (element instanceof Bomba) {
            controladorBomba.cadastrar((Bomba) element);
        } else if (element instanceof Acionamento) {
            controladorAcionamento.cadastrar((Acionamento) element);
        }

    }

    public <E> void atualizar(E element)
            throws SQLException, UsuarioNaoEncontradoException, AtividadeNaoEncontradaException, DAOException, PermissaoException, AtividadeJaExistenteException, UsuarioJaExistenteException, EmailJaExistenteException, DispositivoNaoEncontradoException, DispositivoJaExistenteException, RepositorioException, BombaJaExistenteException, BombaNaoEncontradaException, ViolacaoChaveEstrangeiraException, AcionamentoJaExistenteException, AcionamentoNaoEncontradoException, RepositorioJaExistenteException, RepositorioNaoEncontradoException {
        if (element instanceof Repositorio) {
            controladorRepositorio.cadastrar((Repositorio) element);
        }
        else
        if (element instanceof Usuario) {
            controladorUsuario.atualizar((Usuario) element);
        } else if (element instanceof Atividade) {
            controladorAtividade.atualizar((Atividade) element);
        } else if (element instanceof Dispositivo) {
            controladorDispositivo.atualizar((Dispositivo) element);
        } else if (element instanceof AtividadeRealizada) {
            controladorAtividadeRealizada.atualizar((AtividadeRealizada)
                    element);
        }else if (element instanceof Bomba) {
            controladorBomba.cadastrar((Bomba) element);
        } else if (element instanceof Acionamento) {
            controladorAcionamento.cadastrar((Acionamento) element);
        }

    }

    public Repositorio repositorioProcurar(int id) throws SQLException,
            RepositorioNaoEncontradoException {
        return controladorRepositorio.procurar(id);
    }

    public Repositorio repositorioProcurar(String descricao)
            throws SQLException, RepositorioNaoEncontradoException {

        return controladorRepositorio.procurar(descricao);

    }


    public void repositorioRemover(int id) throws SQLException,
            RepositorioNaoEncontradoException {
        controladorRepositorio.remover(id);
    }
    public List<Repositorio> repositorioListar() throws SQLException,
            RepositorioException {
        return controladorRepositorio.listar();

    }

    public List<Acionamento> acionamentoListar() throws SQLException,
            ListaVaziaException, RepositorioException {
        return controladorAcionamento.listar();
    }

    public List<Acionamento> getUltimosAcionamentos() throws SQLException,
            ListaVaziaException, RepositorioException {
        return controladorAcionamento.getUltimosAcionamentos();
    }

    public List<Bomba> bombaListar() throws SQLException,
            ListaVaziaException {
        return controladorBomba.listar();
    }
    public void acionamentoRemover(int id) throws SQLException,
            AcionamentoNaoEncontradoException, RepositorioException {
        controladorAcionamento.excluir(id);
    }

    public void bombaRemover(int id) throws SQLException,
            BombaNaoEncontradaException {
        controladorBomba.remover(id);
    }

    public void atividadeRealizadaRemover(int id)
            throws AtividadeNaoEncontradaException, SQLException, RepositorioException {
        controladorAtividadeRealizada.excluir(id);
    }

    public void usuarioRemover(int id) throws SQLException, UsuarioNaoEncontradoException, DAOException, PermissaoException {
        controladorUsuario.remover(id);
    }

    public void atividadeRemover(int id) throws SQLException, AtividadeNaoEncontradaException, DAOException {
        controladorAtividade.remover(id);
    }


    public List<AtividadeRealizada> atividadeRealizadaListar() throws
            SQLException, ListaVaziaException, RepositorioException {
        return
                controladorAtividadeRealizada.listar();
    }

    public List<AtividadeRealizada> atividadeRealizadaListar(int id) throws
            SQLException, ListaVaziaException, RepositorioException {
        return
                controladorAtividadeRealizada.listar(id);
    }

    public List<Atividade> atividadeListar() throws SQLException, DAOException {
        return controladorAtividade.listar();
    }

    public List<Usuario> getUsuarioLista() throws SQLException, DAOException {
        return controladorUsuario.getLista();

    }

    public Acionamento acionamentoProcurar(int id)
            throws AcionamentoNaoEncontradoException, SQLException,
            RepositorioException {
        return controladorAcionamento.procurar(id);
    }

    public Bomba bombaProcurar(int id) throws SQLException,
            BombaNaoEncontradaException {
        return controladorBomba.procurar(id);
    }

    public Bomba bombaProcurarPorRepositorio(int idRepositorio)
            throws SQLException, BombaNaoEncontradaException {
        return controladorBomba.procurarPorRepositorio(idRepositorio);
    }

    public AtividadeRealizada atividadeRealizadaProcurar(int id) throws
            SQLException, AtividadeNaoEncontradaException, RepositorioException {
        return controladorAtividadeRealizada.procurar(id);
    }

    public Usuario usuarioProcurar(int id) throws SQLException, UsuarioNaoEncontradoException, DAOException {
        return controladorUsuario.procurar(id);
    }

    public Atividade atividadeProcurar(int id)
            throws SQLException, AtividadeNaoEncontradaException, DAOException {
        return controladorAtividade.procurar(id);
    }

    public boolean loginFacebook(String email) throws SQLException, DAOException {
        return controladorUsuario.loginFacebook(email);
    }

    public void alterarSenhaUsuario(int id, String senha)
            throws SQLException, UsuarioNaoEncontradoException, DAOException {
        controladorUsuario.alterarSenha(id, senha);
    }

    public boolean login(String usuario, String senha)
            throws UsuarioInativoException, SQLException, DAOException {
        return controladorUsuario.login(usuario, senha);
    }

    public Usuario getUsuarioLogado() throws SQLException, DAOException {

        return controladorUsuario.getUsuarioLogado();
    }

    public void logout() throws SQLException, DAOException {
        controladorUsuario.logout();
    }

    public Dispositivo dispositivoProcurar(int id) throws DispositivoNaoEncontradoException, SQLException, DAOException {
        return controladorDispositivo.procurar(id);
    }

    public Dispositivo dispositivoProcurarNome(String nome) throws DispositivoNaoEncontradoException, SQLException, DAOException {
        return controladorDispositivo.procurarNome(nome);
    }

    public List<Dispositivo> dispositivoListar() throws SQLException, DAOException {
        return controladorDispositivo.listar();
    }

    public void dispositivoRemover(int id) throws DispositivoNaoEncontradoException, SQLException, DAOException {
        controladorDispositivo.remover(id);
    }

    public void setDispositivoAtivo(Dispositivo dispositivoAtivo) throws DispositivoNaoEncontradoException, SQLException, DAOException {
        controladorDispositivo.setDispositivoAtivo(dispositivoAtivo);
    }

    public Dispositivo getDispositivoAtivo() throws SQLException, DAOException {
        return controladorDispositivo.getDispositivoAtivo();
    }

    public List<AtividadeRealizada> getUltimasAtividades() throws
            RepositorioException, SQLException {
        return
                controladorAtividadeRealizada.getUltimasAtividades();
    }

    public boolean existeAtividadeRealizada(int id_usuario, int id_atividade,
                                            Date dataHoraInicio, Date dataHoraFim) throws SQLException,
            DAOException {
        return controladorAtividadeRealizada.existe(id_usuario,id_atividade,dataHoraInicio,dataHoraFim);
    }

    public boolean existeUsuario(String login) throws SQLException,
            DAOException {
        return controladorUsuario.existe(login);
    }

    public boolean existeEmail(String email) throws SQLException, DAOException {
        return controladorUsuario.existeEmail(email);
    }

   /*public String md5(String senha) throws DAOException {
        return controladorUsuario.md5(senha);
    }*/

    public boolean existeAtividade(String descricao) throws SQLException,
            DAOException {
        return controladorAtividade.existe(descricao);
    }
    public Acionamento acionamentoProcurarIni(Date date3, Date date4)
            throws SQLException, AcionamentoNaoEncontradoException,
            RepositorioException {

        return controladorAcionamento.procurarIni(date3, date4);
    }

    public Acionamento acionamentoProcurarFim(Date date3, Date date4)
            throws SQLException, AcionamentoNaoEncontradoException,
            RepositorioException {

        return controladorAcionamento.procurarFim(date3, date4);
    }

    public Bomba bombaProcurar(String descricao) throws SQLException, BombaNaoEncontradaException {
        return controladorBomba.procurar(descricao);
    }


}
