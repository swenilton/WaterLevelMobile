package br.com.coffeebeans.usuario;

import java.sql.SQLException;
import java.util.List;

import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.UsuarioJaExistenteException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;

public interface IUsuarioDAO {
	public void cadastrar(Usuario usuario) throws SQLException,
			UsuarioJaExistenteException, RepositorioException, DAOException;

	public List<Usuario> getLista() throws SQLException, RepositorioException, DAOException;

	public Usuario procurar(int id) throws SQLException, RepositorioException, DAOException;

	public void atualizar(Usuario usuario)
			throws UsuarioNaoEncontradoException, SQLException, RepositorioException, DAOException;

	public void excluir(int id) throws SQLException,
			UsuarioNaoEncontradoException, RepositorioException, DAOException;
	
	public Usuario loginFacebook(String email) throws RepositorioException, SQLException, DAOException;
	
	public void alterarSenha(int id, String senha)
			throws UsuarioNaoEncontradoException, SQLException,
			RepositorioException, DAOException;
	
	public boolean login(String usuario, String senha) throws UsuarioInativoException, RepositorioException, SQLException, DAOException;

	boolean existe(String descricao) throws SQLException,DAOException;
}
