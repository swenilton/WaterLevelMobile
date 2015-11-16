package br.com.coffeebeans.dispositivo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.coffeebeans.exception.BDException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.util.CriarDb;

public class DispositivoDAO implements IDispositivoDAO {
    private static final String NOME_TABELA = "dispositivo";
    private SQLiteDatabase db;
    private CriarDb conexao;

    public DispositivoDAO(Context context) throws Exception {
        conexao = CriarDb.getInstance(context);
    }

    @Override
    public boolean existe(String nome) throws SQLException, DAOException {
        boolean existe = false;
        Cursor cursor = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where nome= ?";

                String[] selectionArgs = {nome};

                cursor = db.rawQuery(sql, selectionArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    existe = true;
                }

                Log.i("metodo existe dispositivo", "dispositivo retornado com sucesso ");

            } else {
                throw new BDException();
            }
        } catch (SQLException e) {
            Log.i("erro no metodo existir da classe dispositivo DAO ", e.getMessage());
            throw new SQLException(e);

        } catch (Exception e) {
            Log.i("erro no metodo existir da classe dispositivo DAO ", e.getMessage());
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
    public void cadastrar(Dispositivo dispositivo) throws SQLException, DAOException {
        ContentValues valores = new ContentValues();

        try {

            valores.put("HOST", dispositivo.getHost());
            valores.put("PORTA", dispositivo.getPorta());
            valores.put("NOME", dispositivo.getNome());

            db = conexao.openDb();
            //Log.i("db get version", String.valueOf(db.getVersion()));

            if (db != null) {

                db.insert(NOME_TABELA, null, valores);
            } else {
                throw new BDException();
            }

        } catch (SQLException e) {
            Log.i("erro ao cadastrar dispositivo dao ", e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro ao cadastrar dispositivo dao ", e.getMessage());
            throw new DAOException(e);
        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }

    }

    @Override
    public List<Dispositivo> listar() throws SQLException, DAOException {
        Cursor cursor = null;
        List<Dispositivo> dispositivos = null;

        try {
            db = conexao.openDb();
            dispositivos = new ArrayList<Dispositivo>();
            String sql = "SELECT * from " + NOME_TABELA;

            if (db != null) {
                cursor = db.rawQuery(sql, null);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {

                    do {
                        Dispositivo dispositivo = procurar(cursor.getInt(cursor.getColumnIndex("ID")));

                        dispositivos.add(dispositivo);

                    } while (cursor.moveToNext());

                }
            } else {
                throw new BDException();
            }

        } catch (SQLException e) {
            Log.i("erro no metodo listar da classe dispositivo dao ", e.getMessage());
            throw new SQLException(e);

        } catch (Exception e) {
            Log.i("erro no metodo listar da classe dispositivo dao ", e.getMessage());
            throw new DAOException(e);

        } finally {
            if (cursor != null)
                cursor.close();

            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }

        return dispositivos;
    }

    @Override
    public Dispositivo procurar(int id) throws SQLException, DAOException {
        Cursor cursor = null;
        Dispositivo dispositivo = null;
        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where ID= ?";
                String[] whereArgs = {String.valueOf(id)};

                cursor = db.rawQuery(sql, whereArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    dispositivo = new Dispositivo(cursor.getString(cursor.getColumnIndex("HOST")), cursor.getInt(cursor.getColumnIndex("PORTA")), cursor.getString(cursor.getColumnIndex("NOME")));
                    dispositivo.setId(cursor.getInt(cursor.getColumnIndex("ID")));

                    Log.i("procurar dispositivo", "dispositivo achada com sucesso ");
                }
            } else {
                throw new BDException();
            }
        } catch (SQLException e) {
            Log.i("erro no metodo procurar da classe dispositivo dao ", e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro no metodo procurar da classe dispositivo dao ", e.getMessage());
            throw new DAOException(e);
        } finally {
            if (cursor != null)
                cursor.close();

            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return dispositivo;
    }

    @Override
    public Dispositivo procurarNome(String nome) throws SQLException, DAOException {
        Cursor cursor = null;
        Dispositivo dispositivo = null;
        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where NOME = ?";
                String[] whereArgs = {nome};

                cursor = db.rawQuery(sql, whereArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    dispositivo = procurar(cursor.getInt(cursor.getColumnIndex("ID")));
                    Log.i("procurar dispositivo", "dispositivo achada com sucesso ");
                }
            } else {
                throw new BDException();
            }
        } catch (SQLException e) {
            Log.i("erro no metodo procurarNome da classe dispositivo dao ", e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro no metodo procurarNome da classe dispositivo dao ", e.getMessage());
            throw new DAOException(e);
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return dispositivo;
    }

    @Override
    public void atualizar(Dispositivo dispositivo) throws  SQLException, DAOException {

        ContentValues valores = new ContentValues();
        String where = "ID =?";
        if (dispositivo != null) {
            try {

                valores.put("HOST", dispositivo.getHost());
                valores.put("PORTA", dispositivo.getPorta());
                valores.put("NOME", dispositivo.getNome());

                String[] whereArgs = {String.valueOf(dispositivo.getId())};

                db = conexao.openDb();
                if (db != null) {
                    db.update(NOME_TABELA, valores, where, whereArgs);
                } else {
                    throw new BDException();
                }
            } catch (SQLException e) {
                Log.i("erro no metodo atualizar da classe dispositivo dao ", e.getMessage());
                throw new SQLException(e);
            } catch (Exception e) {
                Log.i("erro no metodo atualizar da classe dispositivo dao", e.getMessage());
                throw new DAOException(e);
            } finally {
                if (db != null) {
                    if (db.isOpen())
                        db.close();
                }
            }
        }
    }

    @Override
    public void excluir(int id) throws SQLException,DAOException {
        String where = " ID = ?";
        try {

            String[] whereArgs = new String[]{String.valueOf(id)};
            db = conexao.openDb();
            if (db != null)
                db.delete(NOME_TABELA, where, whereArgs);

            else
                throw new BDException();

        } catch (SQLException e) {
            Log.i("erro no metodo excluir da classe dispositivo dao ", e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            Log.i("erro metodo excluir dispositivo dao", e.getMessage());
            throw new DAOException(e);
        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }

    @Override
    public Dispositivo getDispositivoAtivo() throws DAOException, SQLException {
        Cursor cursor = null;
        Dispositivo dispositivo = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "SELECT * FROM DISPOSITIVO_ATIVO WHERE ID = ?";
                String[] whereArgs = {String.valueOf(1)};

                cursor = db.rawQuery(sql, whereArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    dispositivo = procurar(cursor.getInt(cursor.getColumnIndex("ID_DISPOSITIVO")));
                    Log.i("procurar dispositivo ativo", "dispositivo ativo achado com sucesso ");
                }
            } else {
                throw new BDException();
            }

        } catch (SQLException e) {
            Log.i("erro no metodo procurar dispositivo ativo da classe dispositivoDAO ", e.getMessage());
            throw new SQLException(e);

        } catch (Exception e) {
            Log.i("erro no metodo procurar dispositivo ativo da classe dispositivoDAO ", e.getMessage());
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
        return dispositivo;
    }

    @Override
    public void setDispositivoAtivo(Dispositivo dispositivoAtivo) throws SQLException, DAOException {

        try {
            String where = "ID = ?";
            String[] whereArgs = new String[]{String.valueOf(1)};
            db = conexao.openDb();
            if (db != null)
                db.delete("DISPOSITIVO_ATIVO", where, whereArgs);
            else
                throw new BDException();
            ContentValues valores = new ContentValues();
            valores.put("ID", 1);
            valores.put("ID_DISPOSITIVO", dispositivoAtivo.getId());
            db.insert("dispositivo_ativo", null, valores);
        } catch (Exception e) {
            Log.i("erro no metodo setDispositivoAtivo da classe dispositivo dao ", e.getMessage());
            throw new DAOException(e);
        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }
}
