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
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.coffeebeans.exception.BDException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;
import br.com.coffeebeans.util.CriarDb;

public class UsuarioDAO implements IUsuarioDAO {
    private static final String NOME_TABELA = "usuario";
    private SQLiteDatabase db;
    private static CriarDb conexao;
    private static Usuario usuarioLogado;

    //TODO // login facebook testar se o usuario do WL está inativo  //verificar excecoes da entidade //usuario nao encontrado ao não logar

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
        } catch (Exception e) {
            Log.i("erro no metodo existir da classe usuario ", "" + e.getMessage());
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
        // valores.put("ID", atividade.getId());
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

        } catch (Exception e) {
            Log.i("erro ao cadastrar usuario ", "" + e.getMessage());
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
    public void atualizar(Usuario usuario) throws SQLException, DAOException {
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
        } catch (Exception e) {
            Log.i("erro metodo atualizar usuario", "" + e.getMessage());
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
    public Usuario loginFacebook(String email) throws SQLException, DAOException {
        Usuario usuario = null;
        Cursor cursor = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where EMAIL= ?";
                String[] whereArgs = {email};

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
        } catch (Exception e) {
            Log.i("erro no metodo loginFacebook da classe usuario ", "" + e.getMessage());
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
        } catch (Exception e) {
            Log.i("erro no metodo alterarSenha da classe usuario ", "" + e.getMessage());
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
                    if (cursor.getString(cursor.getColumnIndex("ATIVO")).equals("NAO"))
                        throw new UsuarioInativoException(procurar(cursor.getInt(cursor.getColumnIndex("ID"))));
                    this.usuarioLogado = procurar(cursor.getInt(cursor.getColumnIndex("ID")));
                    result = true;
                } else {
                    result = false;
                }
            } else {
                throw new BDException();
            }
        } catch (Exception e) {
            Log.i("erro no metodo login da classe usuario ", "" + e.getMessage());
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

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

}
