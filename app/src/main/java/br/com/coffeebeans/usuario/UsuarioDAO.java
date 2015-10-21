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

import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;
import br.com.coffeebeans.util.CriarDb;

public class UsuarioDAO implements IUsuarioDAO {
    private static final String NOME_TABELA = "usuario";
    private SQLiteDatabase db;
    private static CriarDb conexao;
    private static Usuario usuarioLogado;

    //TODO //usuario nao encontrado ao não logar, cadastrar admin através de método, nao poder excluir user adm


    public UsuarioDAO(Context context) throws Exception {
        conexao = CriarDb.getInstance(context);

    }

    public boolean existe(String nome) throws SQLException {
        boolean existe = false;

        Cursor rs = null;
        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where NOME= ?";

                String[] selectionArgs = {nome};

                rs = db.rawQuery(sql, selectionArgs);

                rs.moveToFirst();

                if (rs.getCount() > 0 && rs != null) {
                    existe = true;
                }

                Log.i("metodo existe usuario", "o usuario existe ");
            } else {
                Log.i("Alerta", "banco nao existe ou nao foi aberto");
            }
        } catch (Exception e) {
            Log.i("erro no metodo existir da classe usuario ", "" + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return existe;
    }

    @Override
    public void cadastrar(Usuario usuario) throws SQLException {
        ContentValues valores = new ContentValues();
        // valores.put("ID", atividade.getId());


        try {
            valores.put("NOME", usuario.getNome());
            valores.put("LOGIN", usuario.getLogin());
            valores.put("SENHA", usuario.getSenha());
            valores.put("EMAIL", usuario.getEmail());
            //if (!usuario.getTelefone().equals(""))
            valores.put("TELEFONE", usuario.getTelefone());
            valores.put("ATIVO", usuario.getAtivo());
            //if (!usuario.getFoto().equals(""))
            valores.put("FOTO", usuario.getFoto());
            valores.put("PERFIL", usuario.getPerfil());

            db = conexao.openDb();

            if (db != null) {

                // TODO //usuario ja existente
                db.insert(NOME_TABELA, null, valores);
            } else {
                Log.i("Alerta", "banco nao existe ou nao foi aberto");
            }

        } catch (Exception e) {
            Log.i("erro ao cadastrar usuario ", "" + e.getMessage());
        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }

    @Override
    public List<Usuario> getLista() throws SQLException, RepositorioException {
        Cursor rs = null;
        ArrayList<Usuario> usuarios = null;

        try {
            db = conexao.openDb();
            usuarios = new ArrayList<Usuario>();
            String sql = "SELECT * from " + NOME_TABELA;

            if (db != null) {
                rs = db.rawQuery(sql, null);

                rs.moveToFirst();

                if (rs.getCount() > 0 && rs != null) {

                    do {
                        Usuario usuario = new Usuario(rs.getString(rs.getColumnIndex("NOME")),
                                rs.getString(rs.getColumnIndex("LOGIN")), rs.getString(rs.getColumnIndex("SENHA")),
                                rs.getString(rs.getColumnIndex("EMAIL")), rs.getString(rs.getColumnIndex("ATIVO")),
                                rs.getString(rs.getColumnIndex("PERFIL")));

                        usuario.setId(rs.getInt(rs.getColumnIndex("ID")));
                        usuario.setTelefone(rs.getString(rs.getColumnIndex("TELEFONE")));
                        usuario.setFoto(rs.getString(rs.getColumnIndex("FOTO")));

                        usuarios.add(usuario);

                    } while (rs.moveToNext());

                }
            } else {
                Log.i("Alerta", "banco nao existe ou nao foi aberto");
            }

        } catch (Exception e) {
            Log.i("erro no metodo listar da classe usuario ", "" + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }

        return usuarios;
    }

    @Override
    public Usuario procurar(int id) throws SQLException {
        Cursor rs = null;
        Usuario usuario = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where ID= ?";
                String[] whereArgs = {String.valueOf(id)};

                rs = db.rawQuery(sql, whereArgs);

                rs.moveToFirst();

                if (rs.getCount() > 0 && rs != null) {
                    usuario = new Usuario(rs.getString(rs.getColumnIndex("NOME")),
                            rs.getString(rs.getColumnIndex("LOGIN")), rs.getString(rs.getColumnIndex("SENHA")),
                            rs.getString(rs.getColumnIndex("EMAIL")), rs.getString(rs.getColumnIndex("ATIVO")),
                            rs.getString(rs.getColumnIndex("PERFIL")));

                    usuario.setId(rs.getInt(rs.getColumnIndex("ID")));
                    usuario.setTelefone(rs.getString(rs.getColumnIndex("TELEFONE")));
                    usuario.setFoto(rs.getString(rs.getColumnIndex("FOTO")));

                    Log.i("procurar usuario", "usuario achado com sucesso ");
                }
            } else {
                Log.i("Alerta", "banco nao existe ou nao foi aberto");
            }
        } catch (Exception e) {
            Log.i("erro no meodo procurar da classe usuario ", "" + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return usuario;
    }

    @Override
    public void atualizar(Usuario usuario) throws SQLException {
        try {
            // TODO //usuario ja existente
            ContentValues valores = new ContentValues();
            valores.put("NOME", usuario.getNome());
            valores.put("LOGIN", usuario.getLogin());
            valores.put("SENHA", usuario.getSenha());
            valores.put("EMAIL", usuario.getEmail());
            //if (!usuario.getTelefone().equals(""))
            valores.put("TELEFONE", usuario.getTelefone());
            valores.put("ATIVO", usuario.getAtivo());
            //if (!usuario.getFoto().equals(""))
            valores.put("FOTO", usuario.getFoto());
            valores.put("PERFIL", usuario.getPerfil());

            String where = "ID =?";

            String[] whereArgs = {String.valueOf(usuario.getId())};

            db = conexao.openDb();
            if (db != null) {
                db.update(NOME_TABELA, valores, where, whereArgs);
            }
        } catch (Exception e) {
            Log.i("erro metodo atualizar usuario", "" + e.getMessage());
        } finally {

            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        try {

            String where = " ID =?";
            String[] whereArgs = new String[]{String.valueOf(id)};

            db = conexao.openDb();
            if (db != null)
                db.delete(NOME_TABELA, where, whereArgs);
        } catch (Exception e) {
            Log.i("erro metodo excluir usuario", "" + e.getMessage());
        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }

    @Override
    public Usuario loginFacebook(String email) throws RepositorioException, SQLException {
        Usuario usuario = null;
        Cursor rs = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where EMAIL= ?";
                String[] whereArgs = {email};

                rs = db.rawQuery(sql, whereArgs);

                rs.moveToFirst();

                if (rs.getCount() > 0 && rs != null) {
                    usuario = new Usuario(rs.getString(rs.getColumnIndex("NOME")),
                            rs.getString(rs.getColumnIndex("LOGIN")), rs.getString(rs.getColumnIndex("SENHA")),
                            rs.getString(rs.getColumnIndex("EMAIL")), rs.getString(rs.getColumnIndex("ATIVO")),
                            rs.getString(rs.getColumnIndex("PERFIL")));

                    usuario.setId(rs.getInt(rs.getColumnIndex("ID")));
                    usuario.setTelefone(rs.getString(rs.getColumnIndex("TELEFONE")));
                    usuario.setFoto(rs.getString(rs.getColumnIndex("FOTO")));

                    Log.i("procurar usuario", "usuario achado com sucesso ");
                }
            } else {
                Log.i("Alerta", "banco nao existe ou nao foi aberto");
            }
        } catch (Exception e) {
            Log.i("erro no metodo loginFacebook da classe usuario ", "" + e.getMessage());
        } finally {

            if (rs != null) {
                rs.close();
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
            throws UsuarioNaoEncontradoException, SQLException, RepositorioException {

        ContentValues valores = new ContentValues();
        String where = "ID =?";

        try {
            valores.put("SENHA", senha);
            String[] whereArgs = {String.valueOf(id)};

            db = conexao.openDb();

            if (db != null)
                db.update(NOME_TABELA, valores, where, whereArgs);

        } catch (Exception e) {
            Log.i("erro no metodo alterarSenha da classe usuario ", "" + e.getMessage());

        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }

    }

    @Override
    public boolean login(String usuario, String senha)
            throws UsuarioInativoException, RepositorioException, SQLException {
        Cursor rs = null;
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

                rs = db.rawQuery(sql, whereArgs);

                rs.moveToFirst();

                if (rs.getCount() > 0 && rs != null) {
                    if (rs.getString(rs.getColumnIndex("ATIVO")).equals("NAO"))
                        throw new UsuarioInativoException(procurar(rs.getInt(rs.getColumnIndex("id"))));
                    this.usuarioLogado = procurar(rs.getInt(rs.getColumnIndex("ID")));
                    result = true;
                } else {
                    result = false;
                }
            } else {
                Log.i("Alerta", "banco nao existe ou nao foi aberto");
            }
        } catch (Exception e) {
            Log.i("erro no metodo login da classe usuario ", "" + e.getMessage());
            throw new RepositorioException(e);
        } finally {
            if (rs != null) {
                rs.close();
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

    public static CriarDb getConexao() {
        return conexao;
    }

}
