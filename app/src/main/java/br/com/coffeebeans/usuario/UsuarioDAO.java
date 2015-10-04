package br.com.coffeebeans.usuario;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.UsuarioJaExistenteException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;
import br.com.coffeebeans.util.Conexao;

public class UsuarioDAO implements IUsuarioDAO {

	private Connection connection = null;
	private String sistema = "mysql";
	private static Usuario usuarioLogado;

	public UsuarioDAO() throws Exception {
		this.connection = Conexao.conectar(sistema);
	}

	@Override
	public void cadastrar(Usuario usuario) throws SQLException,
			UsuarioJaExistenteException, RepositorioException {
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO USUARIO(NOME, LOGIN, SENHA, EMAIL, TELEFONE, ATIVO, FOTO, PERFIL) VALUES"
					+ " (?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getLogin());
			stmt.setString(3, md5(usuario.getSenha()));
			stmt.setString(4, usuario.getEmail());
			stmt.setString(5, usuario.getTelefone());
			stmt.setString(6, usuario.getAtivo());
			stmt.setString(7, usuario.getFoto());
			stmt.setString(8, usuario.getPerfil());
			stmt.execute();

		} catch (SQLException e) {
			throw new RepositorioException(e);
		} catch (Exception e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
		}
	}

	@Override
	public List<Usuario> getLista() throws SQLException, RepositorioException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM USUARIO";
			stmt = this.connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("NOME"),
						rs.getString("LOGIN"), rs.getString("SENHA"),
						rs.getString("EMAIL"), rs.getString("ATIVO"),
						rs.getString("PERFIL"));
				usuario.setId(rs.getInt("ID"));
				usuario.setFoto(rs.getString("FOTO"));
				usuario.setTelefone(rs.getString("TELEFONE"));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			throw new RepositorioException(e);
		} catch (Exception e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
			rs.close();
		}
		return usuarios;
	}

	@Override
	public Usuario procurar(int id) throws SQLException, RepositorioException {
		Usuario usuario = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM USUARIO WHERE ID = ?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				usuario = new Usuario(rs.getString("NOME"),
						rs.getString("LOGIN"), rs.getString("SENHA"),
						rs.getString("EMAIL"), rs.getString("ATIVO"),
						rs.getString("PERFIL"));
				usuario.setId(rs.getInt("ID"));
				usuario.setFoto(rs.getString("FOTO"));
				usuario.setTelefone(rs.getString("TELEFONE"));
			} else {
				throw new IllegalArgumentException("usuario não encontrado");
			}
		} catch (SQLException e) {
			throw new RepositorioException(e);
		} catch (Exception e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
			rs.close();
		}
		return usuario;
	}

	@Override
	public void atualizar(Usuario usuario)
			throws UsuarioNaoEncontradoException, SQLException,
			RepositorioException {
		PreparedStatement stmt = null;
		try {
			if (usuario != null) {
				try {
					String sql = "UPDATE USUARIO SET LOGIN= ?, NOME = ?, EMAIL = ?, TELEFONE = ?, FOTO = ?, ATIVO = ?, PERFIL = ? WHERE ID = ?";
					stmt = this.connection.prepareStatement(sql);
					stmt.setString(1, usuario.getLogin());
					stmt.setString(2, usuario.getNome());
					stmt.setString(3, usuario.getEmail());
					stmt.setString(4, usuario.getTelefone());
					stmt.setString(5, usuario.getFoto());
					stmt.setString(6, usuario.getAtivo());
					stmt.setString(7, usuario.getPerfil());
					stmt.setInt(8, usuario.getId());
					Integer resultado = stmt.executeUpdate();
					if (resultado == 0)
						throw new UsuarioNaoEncontradoException();
				} catch (SQLException e) {
					throw new RepositorioException(e);
				}
			}
		} finally {
			stmt.close();
		}
	}

	public void alterarSenha(int id, String senha)
			throws UsuarioNaoEncontradoException, SQLException,
			RepositorioException {
		PreparedStatement stmt = null;
		try {
			if (id != 0) {
				try {
					String sql = "UPDATE USUARIO SET SENHA= ? WHERE ID = ?";
					stmt = this.connection.prepareStatement(sql);
					stmt.setString(1, md5(senha));
					stmt.setInt(2, id);
					Integer resultado = stmt.executeUpdate();
					if (resultado == 0)
						throw new UsuarioNaoEncontradoException();
				} catch (SQLException e) {
					throw new RepositorioException(e);
				}
			}
		} finally {
			stmt.close();
		}
	}

	@Override
	public void excluir(int id) throws SQLException,
			UsuarioNaoEncontradoException, RepositorioException {
		PreparedStatement stmt = null;
		try {
			String sql = "DELETE FROM USUARIO WHERE ID = ?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		} catch (SQLException e) {
			throw new RepositorioException(e);
		} catch (Exception e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
		}

	}

	public Usuario loginFacebook(String email) throws RepositorioException,
			SQLException {
		Usuario usuario = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM USUARIO WHERE EMAIL = ?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (rs.next()) {
				usuario = new Usuario(rs.getString("NOME"),
						rs.getString("LOGIN"), rs.getString("SENHA"),
						rs.getString("EMAIL"), rs.getString("ATIVO"),
						rs.getString("PERFIL"));
				usuario.setId(rs.getInt("ID"));
				usuario.setFoto(rs.getString("FOTO"));
				usuario.setTelefone(rs.getString("TELEFONE"));
			}
		} catch (SQLException e) {
			throw new RepositorioException(e);
		} catch (Exception e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
			rs.close();
		}
		return usuario;
	}

	public boolean login(String usuario, String senha)
			throws UsuarioInativoException, RepositorioException, SQLException {
		PreparedStatement stmt = null;
		ResultSet rs;
		String sql = "";
		try {
			if(usuario.contains("@")) sql = "SELECT * FROM USUARIO WHERE EMAIL = ? AND SENHA = ?";
			else sql = "SELECT * FROM USUARIO WHERE LOGIN = ? AND SENHA = ?";
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, usuario);
			stmt.setString(2, md5(senha));
			rs = stmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("ATIVO").equals("Nao"))
					throw new UsuarioInativoException(procurar(rs.getInt("id")));
				this.usuarioLogado = procurar(rs.getInt("id"));
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new RepositorioException(e);
		} finally {
			stmt.close();
		}
	}
	
	public String md5(String senha) {
		String sen = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
		sen = hash.toString(16);
		return sen;
	}


	public static Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

}
