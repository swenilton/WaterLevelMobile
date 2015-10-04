package br.com.coffeebeans.usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.ISUB;
import com.sun.xml.internal.txw2.IllegalAnnotationException;

import br.com.coffeebeans.exception.ListaUsuarioVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.UsuarioJaExistenteException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;

public class ControladorUsuario {
	private IUsuarioDAO iusuario;

	public ControladorUsuario() throws Exception {
		this.iusuario = new UsuarioDAO();
	}

	public void cadastrar(Usuario usuario) throws SQLException,
			UsuarioJaExistenteException, UsuarioNaoEncontradoException,
			RepositorioException {
		if (usuario == null) {
			throw new IllegalAnnotationException("Usuário Null");
		}
		iusuario.cadastrar(usuario);

	}

	public List<Usuario> getLista() throws SQLException, RepositorioException {
		return iusuario.getLista();

	}

	public Usuario procurar(int id) throws SQLException,
			UsuarioNaoEncontradoException, RepositorioException {
		if (iusuario.procurar(id) == null) {
			throw new UsuarioNaoEncontradoException();
		}

		return iusuario.procurar(id);

	}

	public void atualizar(Usuario usuarioNovo) throws SQLException,
			UsuarioNaoEncontradoException, RepositorioException {
		if (usuarioNovo == null) {
			throw new NullPointerException();
		}

		if (iusuario.procurar(usuarioNovo.getId()) == null) {
			throw new UsuarioNaoEncontradoException();

		}
		iusuario.atualizar(usuarioNovo);
	}

	public void remover(int id) throws SQLException,
			UsuarioNaoEncontradoException, RepositorioException {
		if (iusuario.procurar(id) == null) {
			throw new UsuarioNaoEncontradoException();
		} else {
			iusuario.excluir(id);
		}
	}

	public Usuario loginFacebook(String email) throws RepositorioException,
			SQLException {
		return iusuario.loginFacebook(email);
	}

	public void alterarSenha(int id, String senha) throws SQLException,
			UsuarioNaoEncontradoException, RepositorioException {
		
			iusuario.alterarSenha(id, senha);		
	}
	
	public boolean login(String usuario, String senha) throws UsuarioInativoException, RepositorioException, SQLException{
		return iusuario.login(usuario, senha);
	}

}
