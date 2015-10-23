package br.com.coffeebeans.usuario;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import br.com.coffeebeans.exception.DAOException;
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
            throws SQLException, UsuarioJaExistenteException, UsuarioNaoEncontradoException, RepositorioException, DAOException {

        //TODO //permissões de usuário

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario Null");
        }
        if (existe(usuario.getLogin())) {
            throw new UsuarioJaExistenteException();
        }
        iusuario.cadastrar(usuario);

    }


    public List<Usuario> getLista() throws SQLException, RepositorioException, DAOException {
        return iusuario.getLista();

    }

    public Usuario procurar(int id) throws SQLException, UsuarioNaoEncontradoException, RepositorioException, DAOException {
        if (iusuario.procurar(id) == null) {
            throw new UsuarioNaoEncontradoException();
        }

        return iusuario.procurar(id);

    }

    public void atualizar(Usuario usuarioNovo)
            throws SQLException, UsuarioNaoEncontradoException, RepositorioException, DAOException {
        if (usuarioNovo == null) {
            throw new NullPointerException();
        }

        if (iusuario.procurar(usuarioNovo.getId()) == null) {
            throw new UsuarioNaoEncontradoException();

        }
        iusuario.atualizar(usuarioNovo);
    }

    public void remover(int id) throws SQLException, UsuarioNaoEncontradoException, RepositorioException, DAOException {
        if (iusuario.procurar(id) == null) {
            throw new UsuarioNaoEncontradoException();
        }
        else {
            iusuario.excluir(id);
        }
    }

    public Usuario loginFacebook(String email) throws RepositorioException, SQLException, DAOException {
        return iusuario.loginFacebook(email);
    }

    public void alterarSenha(int id, String senha)
            throws SQLException, UsuarioNaoEncontradoException, RepositorioException, DAOException {

        iusuario.alterarSenha(id, senha);
    }

    public boolean login(String usuario, String senha)
            throws UsuarioInativoException, RepositorioException, SQLException, DAOException {
        return iusuario.login(usuario, senha);
    }

}
