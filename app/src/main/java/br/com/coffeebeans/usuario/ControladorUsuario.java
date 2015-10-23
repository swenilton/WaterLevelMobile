package br.com.coffeebeans.usuario;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.PermissaoException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.UsuarioJaExistenteException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;

public class ControladorUsuario {
    private IUsuarioDAO iusuario;

    public ControladorUsuario(Context context) throws Exception {
        this.iusuario = new UsuarioDAO(context);
    }

    public boolean existe(String descricao) throws SQLException, DAOException {
        return iusuario.existe(descricao);
    }

    public void cadastrar(Usuario usuario)
            throws SQLException, UsuarioJaExistenteException, DAOException, PermissaoException {

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario Null");
        }
        if (!UsuarioDAO.getUsuarioLogado().getPerfil().equals("ADMINISTRADOR")) {
            throw new PermissaoException();
        }
        if (existe(usuario.getLogin())) {
            throw new UsuarioJaExistenteException();
        }

        iusuario.cadastrar(usuario);

    }


    public List<Usuario> getLista() throws SQLException, DAOException {
        return iusuario.getLista();
    }

    public Usuario procurar(int id) throws SQLException, UsuarioNaoEncontradoException, DAOException {
        if (iusuario.procurar(id) == null) {
            throw new UsuarioNaoEncontradoException();
        }

        return iusuario.procurar(id);

    }

    public void atualizar(Usuario usuarioNovo)
            throws SQLException, UsuarioNaoEncontradoException, DAOException, PermissaoException {

        if (usuarioNovo == null) {
            throw new NullPointerException();
        }
        if (!UsuarioDAO.getUsuarioLogado().getPerfil().equals("ADMINISTRADOR")) {
            throw new PermissaoException();
        }

        if (iusuario.procurar(usuarioNovo.getId()) == null) {
            throw new UsuarioNaoEncontradoException();
        }
        if (iusuario.procurar(usuarioNovo.getId()).getId() == 1) {
            throw new UnsupportedOperationException("o usuário ADMIN nao pode ser editado");
        }

        iusuario.atualizar(usuarioNovo);
    }

    public void remover(int id) throws SQLException, UsuarioNaoEncontradoException, DAOException, PermissaoException {
        if (iusuario.procurar(id) == null) {
            throw new UsuarioNaoEncontradoException();
        }
        if (!UsuarioDAO.getUsuarioLogado().getPerfil().equals("ADMINISTRADOR")) {
            throw new PermissaoException();
        }
        if (iusuario.procurar(id).getId() == 1) {
            throw new UnsupportedOperationException("o usuário ADMIN nao pode ser excluido");
        }
        iusuario.excluir(id);

    }

    public Usuario loginFacebook(String email) throws SQLException, DAOException {
        return iusuario.loginFacebook(email);
    }

    public void alterarSenha(int id, String senha)
            throws SQLException, UsuarioNaoEncontradoException, DAOException {

        iusuario.alterarSenha(id, senha);
    }

    public boolean login(String usuario, String senha)
            throws UsuarioInativoException, SQLException, DAOException {
        return iusuario.login(usuario, senha);
    }

}
