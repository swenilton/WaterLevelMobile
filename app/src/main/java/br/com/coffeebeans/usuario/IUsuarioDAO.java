package br.com.coffeebeans.usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.coffeebeans.exception.ListaUsuarioVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.UsuarioJaExistenteException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;

public interface IUsuarioDAO {
	public void cadastrar(Usuario usuario) throws SQLException,
			UsuarioJaExistenteException, RepositorioException;

	public List<Usuario> getLista() throws SQLException, RepositorioException;

	public Usuario procurar(int id) throws SQLException, RepositorioException;

	public void atualizar(Usuario usuario)
			throws UsuarioNaoEncontradoException, SQLException, RepositorioException;

	public void excluir(int id) throws SQLException,
			UsuarioNaoEncontradoException, RepositorioException;
	
	public Usuario loginFacebook(String email) throws RepositorioException, SQLException;
	
	public void alterarSenha(int id, String senha)
			throws UsuarioNaoEncontradoException, SQLException,
			RepositorioException;
	
	public boolean login(String usuario, String senha) throws UsuarioInativoException, RepositorioException, SQLException;

}
