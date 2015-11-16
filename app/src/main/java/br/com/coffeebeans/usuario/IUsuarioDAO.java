package br.com.coffeebeans.usuario;

import android.database.sqlite.SQLiteConstraintException;

import java.sql.SQLException;
import java.util.List;

import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.EmailJaExistenteException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.UsuarioJaExistenteException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;

public interface IUsuarioDAO {
    public void cadastrar(Usuario usuario) throws SQLException,
            UsuarioJaExistenteException, DAOException;

    public List<Usuario> getLista() throws SQLException, DAOException;

    public Usuario procurar(int id) throws SQLException, DAOException;

    public void atualizar(Usuario usuario)
            throws UsuarioNaoEncontradoException, SQLException, DAOException, SQLiteConstraintException, UsuarioJaExistenteException, EmailJaExistenteException;

    public void excluir(int id) throws SQLException,
            UsuarioNaoEncontradoException, DAOException;

    public boolean loginFacebook(String email) throws SQLException, DAOException;

    public void alterarSenha(int id, String senha)
            throws UsuarioNaoEncontradoException, SQLException,
            DAOException;

    public boolean login(String usuario, String senha) throws UsuarioInativoException, SQLException, DAOException;

    public boolean existe(String descricao) throws SQLException, DAOException;

    public boolean existeEmail(String email) throws SQLException, DAOException;

    Usuario getUsuarioLogado() throws SQLException, DAOException;

    void logout() throws SQLException, DAOException;
}
