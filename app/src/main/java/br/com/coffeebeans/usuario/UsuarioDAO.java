package br.com.coffeebeans.usuario;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.coffeebeans.exception.BDException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.EmailJaExistenteException;
import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.UsuarioJaExistenteException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;
import br.com.coffeebeans.util.CriarDb;

public class UsuarioDAO implements IUsuarioDAO {
    private static final String NOME_TABELA = "usuario";
    private SQLiteDatabase db;
    private static CriarDb conexao;

    //TODO //login face// login face testar se o usuario do WL está inativo

    public UsuarioDAO(Context context) throws Exception {
        conexao = CriarDb.getInstance(context);
    }

    public boolean existe(String nome) throws SQLException, DAOException {
        boolean existe = false;
        Cursor cursor = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where login= ?";

                String[] selectionArgs = {nome};

                cursor = db.rawQuery(sql, selectionArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    existe = true;
                }

                Log.i("metodo existe usuario", "o usuario existe ");
            } else {
                throw new BDException();

            }
        } catch (SQLException e) {
            Log.i("erro no metodo existir da classe usuario DAO ", e.getMessage());
            throw new SQLException(e);

        } catch (Exception e) {
            Log.i("erro no metodo existir da classe usuario DAO ", "" + e.getMessage());
            throw new DAOException(e);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return existe;
    }

    public boolean existeEmail(String email) throws SQLException, DAOException {
        boolean existe = false;
        Cursor cursor = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where EMAIL= ?";

                String[] selectionArgs = {email};

                cursor = db.rawQuery(sql, selectionArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    existe = true;
                }

                Log.i("metodo existeEmail usuario DAO", "o usuario existe ");
            } else {
                throw new BDException();

            }
        } catch (SQLException e) {
            Log.i("erro no metodo existeEmail da classe usuario DAO ", e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro no metodo existeEmail da classe usuario DAO ", "" + e.getMessage());
            throw new DAOException(e);

        } finally {
            if (cursor != null) cursor.close();
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return existe;
    }

    @Override
    public void cadastrar(Usuario usuario) throws SQLException, DAOException {
        ContentValues valores = new ContentValues();

        try {
            valores.put("NOME", usuario.getNome());
            valores.put("LOGIN", usuario.getLogin());
            valores.put("SENHA", usuario.getSenha());
            valores.put("EMAIL", usuario.getEmail());
            valores.put("TELEFONE", usuario.getTelefone());
            valores.put("ATIVO", usuario.getAtivo());
            valores.put("FOTO", usuario.getFoto());
            valores.put("PERFIL", usuario.getPerfil());

            db = conexao.openDb();

            if (db != null) {
                db.insert(NOME_TABELA, null, valores);
            } else {
                throw new BDException();
            }
        } catch (SQLException e) {
            Log.i("erro ao cadastrar usuario dao ", "" + e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro ao cadastrar usuario dao ", "" + e.getMessage());
            throw new DAOException(e);
        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }

    @Override
    public List<Usuario> getLista() throws SQLException, DAOException {
        Cursor cursor = null;
        ArrayList<Usuario> usuarios = null;

        try {
            db = conexao.openDb();
            usuarios = new ArrayList<Usuario>();
            String sql = "SELECT * from " + NOME_TABELA;

            if (db != null) {
                cursor = db.rawQuery(sql, null);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {

                    do {
                        Usuario usuario = new Usuario(cursor.getString(cursor.getColumnIndex("NOME")),
                                cursor.getString(cursor.getColumnIndex("LOGIN")), cursor.getString(cursor.getColumnIndex("SENHA")),
                                cursor.getString(cursor.getColumnIndex("EMAIL")), cursor.getString(cursor.getColumnIndex("ATIVO")),
                                cursor.getString(cursor.getColumnIndex("PERFIL")));

                        usuario.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                        usuario.setTelefone(cursor.getString(cursor.getColumnIndex("TELEFONE")));
                        usuario.setFoto(cursor.getString(cursor.getColumnIndex("FOTO")));

                        usuarios.add(usuario);

                    } while (cursor.moveToNext());

                }
            } else {
                throw new BDException();
            }
        } catch (SQLException e) {
            Log.i("erro ao listar usuario dao ", "" + e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro no metodo listar da classe usuario dao ", "" + e.getMessage());
            throw new DAOException(e);

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }

        return usuarios;
    }

    @Override
    public Usuario procurar(int id) throws SQLException, DAOException {
        Cursor cursor = null;
        Usuario usuario = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where ID= ?";
                String[] whereArgs = {String.valueOf(id)};

                cursor = db.rawQuery(sql, whereArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    usuario = new Usuario(cursor.getString(cursor.getColumnIndex("NOME")),
                            cursor.getString(cursor.getColumnIndex("LOGIN")), cursor.getString(cursor.getColumnIndex("SENHA")),
                            cursor.getString(cursor.getColumnIndex("EMAIL")), cursor.getString(cursor.getColumnIndex("ATIVO")),
                            cursor.getString(cursor.getColumnIndex("PERFIL")));

                    usuario.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                    usuario.setTelefone(cursor.getString(cursor.getColumnIndex("TELEFONE")));
                    usuario.setFoto(cursor.getString(cursor.getColumnIndex("FOTO")));

                    Log.i("procurar usuario", "usuario achado com sucesso ");
                }
            } else {
                throw new BDException();
            }
        } catch (SQLException e) {
            Log.i("erro ao procurar usuario dao ", "" + e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro no metodo procurar da classe usuarioDAO ", "" + e.getMessage());
            throw new DAOException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return usuario;
    }

    @Override
    public void atualizar(Usuario usuario) throws SQLException, DAOException, SQLiteConstraintException, UsuarioJaExistenteException, EmailJaExistenteException {
        try {

            ContentValues valores = new ContentValues();
            valores.put("NOME", usuario.getNome());
            valores.put("LOGIN", usuario.getLogin());
            valores.put("SENHA", usuario.getSenha());
            valores.put("EMAIL", usuario.getEmail());
            valores.put("TELEFONE", usuario.getTelefone());
            valores.put("ATIVO", usuario.getAtivo());
            valores.put("FOTO", usuario.getFoto());
            valores.put("PERFIL", usuario.getPerfil());

            String where = "ID =?";

            String[] whereArgs = {String.valueOf(usuario.getId())};

            db = conexao.openDb();
            if (db != null) {
                db.update(NOME_TABELA, valores, where, whereArgs);
            } else {
                throw new BDException();
            }

            //campos login e email são unicos no bd
        } catch (SQLiteConstraintException e) {
            Log.i("erro metodo atualizar usuario", "" + e.getMessage());
            if (e.getMessage().contains("LOGIN"))
                throw new UsuarioJaExistenteException();
            else if (e.getMessage().contains("EMAIL"))
                throw new EmailJaExistenteException();

            else
                throw new SQLiteConstraintException(e.getMessage());

        } catch (SQLException e) {
            Log.i("erro ao atualizar usuario dao ", "" + e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro metodo atualizar usuario dao", "" + e.getMessage());
            throw new DAOException(e);
        } finally {

            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }

    @Override
    public void excluir(int id) throws SQLException, DAOException {
        try {
            String where = " ID =?";
            String[] whereArgs = new String[]{String.valueOf(id)};

            db = conexao.openDb();
            if (db != null)
                db.delete(NOME_TABELA, where, whereArgs);
            else
                throw new BDException();

        } catch (SQLException e) {
            Log.i("erro ao excluir usuario dao ", "" + e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro metodo excluir usuarioDAO", "" + e.getMessage());
            throw new DAOException(e);

        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }

    @Override
    public boolean loginFacebook(String email) throws SQLException, DAOException {
        Cursor cursor = null;
        boolean result = false;
        Usuario inativo = null;

        try {
            String sql = "SELECT * FROM USUARIO WHERE EMAIL = ?";

            db = conexao.openDb();

            if (db != null) {

                String[] whereArgs = {email};

                cursor = db.rawQuery(sql, whereArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    if (cursor.getString(cursor.getColumnIndex("ATIVO")).equals("NAO")) {
                        inativo = procurar(cursor.getInt(cursor.getColumnIndex("ID")));
                        throw new UsuarioInativoException(inativo);
                    }
                    ContentValues valores = new ContentValues();
                    valores.put("ID", 1);
                    valores.put("ID_USUARIO", cursor.getInt(cursor.getColumnIndex("ID")));
                    db.insert("usuario_logado", null, valores);
                    result = true;
                } else {
                    result = false;
                }
            } else {
                throw new BDException();
            }
        } catch (SQLException e) {
            Log.i("erro no metodo loginFacebook da classe usuario dao ", e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro no metodo loginFacebook da classe usuario ", e.getMessage());
            throw new DAOException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return result;
    }

    @Override
    public void alterarSenha(int id, String senha)
            throws SQLException, UsuarioNaoEncontradoException, DAOException {

        ContentValues valores = new ContentValues();
        String where = "ID =?";

        try {
            valores.put("SENHA", senha);
            String[] whereArgs = {String.valueOf(id)};

            db = conexao.openDb();

            if (db != null)
                db.update(NOME_TABELA, valores, where, whereArgs);

            else
                throw new BDException();

        } catch (SQLException e) {
            Log.i("erro no metodo alterarSenha da classe usuario dao ", "" + e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro no metodo alterarSenha da classe usuario dao ", "" + e.getMessage());
            throw new DAOException(e);
        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }

    }

    @Override
    public boolean login(String usuario, String senha)
            throws UsuarioInativoException, SQLException, DAOException {
        Cursor cursor = null;
        String sql = "";
        boolean result = false;
        Usuario inativo = null;
        try {
            if (usuario.contains("@"))
                sql = "SELECT * FROM USUARIO WHERE EMAIL = ? AND SENHA = ?";
            else
                sql = "SELECT * FROM USUARIO WHERE LOGIN = ? AND SENHA = ?";

            db = conexao.openDb();

            if (db != null) {

                String[] whereArgs = {usuario, senha};

                cursor = db.rawQuery(sql, whereArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    if (cursor.getString(cursor.getColumnIndex("ATIVO")).equals("NAO")) {
                        inativo = procurar(cursor.getInt(cursor.getColumnIndex("ID")));
                        throw new UsuarioInativoException(inativo);
                    }
                    ContentValues valores = new ContentValues();
                    valores.put("ID", 1);
                    valores.put("ID_USUARIO", cursor.getInt(cursor.getColumnIndex("ID")));
                    db.insert("usuario_logado", null, valores);
                    result = true;
                } else {
                    result = false;
                }
            } else {
                throw new BDException();
            }
        } catch (SQLException e) {
            Log.i("erro no metodo login da classe usuario dao ", e.getMessage());
            throw new SQLException(e);
        } catch (UsuarioInativoException e) {
            Log.i("erro usuario inativo dao ", e.getMessage());
            throw new UsuarioInativoException(inativo);
        } catch (Exception e) {
            Log.i("erro no metodo login da classe usuario dao ", e.getMessage());
            throw new DAOException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return result;
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

    @Override
    public Usuario getUsuarioLogado() throws SQLException, DAOException {

        Cursor cursor = null;
        Usuario usuario = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "SELECT * FROM USUARIO_LOGADO WHERE ID = ?";
                String[] whereArgs = {String.valueOf(1)};

                cursor = db.rawQuery(sql, whereArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    usuario = procurar(cursor.getInt(cursor.getColumnIndex("ID_USUARIO")));
                    Log.i("procurar usuario logado", "usuario logado achado com sucesso ");
                }
            } else {
                throw new BDException();
            }

        } catch (SQLException e) {
            Log.i("erro no metodo procurar usuario logado da classe usuarioDAO ", "" + e.getMessage());
            throw new SQLException(e);

        } catch (Exception e) {
            Log.i("erro no metodo procurar usuario logado da classe usuarioDAO ", "" + e.getMessage());
            throw new DAOException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return usuario;
    }

    @Override
    public void logout() throws SQLException, DAOException {
        try {
            String where = "ID = ?";
            String[] whereArgs = new String[]{String.valueOf(1)};

            db = conexao.openDb();
            if (db != null)
                db.delete("USUARIO_LOGADO", where, whereArgs);
            else
                throw new BDException();

        } catch (SQLException e) {
            Log.i("erro no metodo logout da classe usuarioDAO ", "" + e.getMessage());
            throw new SQLException(e);

        } catch (Exception e) {
            Log.i("erro no metodo logout da classe usuarioDAO", "" + e.getMessage());
            throw new DAOException(e);

        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }
}
