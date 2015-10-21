package br.com.coffeebeans.atividade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.BDException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.util.CriarDb;

public class AtividadeDAO implements IAtividadeDAO {
    private static final String NOME_TABELA = "atividade";
    private SQLiteDatabase db;
    private CriarDb conexao;

    public AtividadeDAO(Context context) throws Exception {
        //conexao.openDb();            //simulando erro
        conexao = CriarDb.getInstance(context);
        //TODO //ver ultimas atividades REALIZADAS
        //TODO //adm ver atividades REALIZADAS de todos   //user ver as proprias atividades REALIZADAS
    }

    public boolean existe(String descricao) throws SQLException, DAOException {
        boolean existe = false;
        Cursor cursor = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where descricao= ?";

                String[] selectionArgs = {descricao};

                cursor = db.rawQuery(sql, selectionArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    existe = true;
                }

                Log.i("metodo existe atividade", "atividade retornada com sucesso ");

            } else {
                throw new BDException();
            }
        } catch (Exception e) {
            Log.i("erro no metodo existir da classe atividade DAO ", e.getMessage());
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
    public void cadastrar(Atividade atividade) throws SQLException, DAOException {
        ContentValues valores = new ContentValues();
        // valores.put("ID", atividade.getId());

        try {

            valores.put("DESCRICAO", atividade.getDescricao());

            db = conexao.openDb();
            if (db != null) {

                db.insert(NOME_TABELA, null, valores);
            } else {
                throw new BDException();
            }

        } catch (Exception e) {
            Log.i("erro ao cadastrar atividade ", "" + e.getMessage());
            throw new DAOException(e);
        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }

    }

    @Override
    public List<Atividade> listar() throws SQLException, DAOException {
        Cursor cursor = null;
        ArrayList<Atividade> atividades = null;

        try {
            db = conexao.openDb();
            atividades = new ArrayList<Atividade>();
            String sql = "SELECT * from " + NOME_TABELA;

            if (db != null) {
                cursor = db.rawQuery(sql, null);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {

                    do {
                        Atividade atividade = new Atividade(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
                        atividade.setId(cursor.getInt(cursor.getColumnIndex("ID")));

                        atividades.add(atividade);

                    } while (cursor.moveToNext());

                }
            } else {
                throw new BDException();
            }

        } catch (Exception e) {
            Log.i("erro no metodo listar da classe atividade ", "" + e.getMessage());
            throw new DAOException(e);

        } finally {
            if (cursor != null)
                cursor.close();

            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }

        return atividades;
    }

    @Override
    public Atividade procurar(int id) throws SQLException, AtividadeNaoEncontradaException, DAOException {
        Cursor cursor = null;
        Atividade atividade = null;
        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "Select * from " + NOME_TABELA + " where ID= ?";
                String[] whereArgs = {String.valueOf(id)};

                cursor = db.rawQuery(sql, whereArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    atividade = new Atividade(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
                    atividade.setId(cursor.getInt(cursor.getColumnIndex("ID")));

                    Log.i("procurar atividade", "atividade achada com sucesso ");
                }
            } else {
                Log.i("Alerta", "banco nao existe ou nao foi aberto");
                throw new BDException();
            }
        } catch (Exception e) {
            Log.i("erro no metodo procurar da classe atividade ", "" + e.getMessage());
            throw new DAOException(e);
        } finally {
            if (cursor != null)
                cursor.close();

            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return atividade;
    }

    @Override
    public void atualizar(Atividade atividade) throws AtividadeNaoEncontradaException, SQLException, DAOException {

        ContentValues valores = new ContentValues();
        String where = "ID =?";

        try {

            valores.put("DESCRICAO", atividade.getDescricao());

            String[] whereArgs = {String.valueOf(atividade.getId())};

            db = conexao.openDb();
            if (db != null) {
                db.update(NOME_TABELA, valores, where, whereArgs);
            } else {
                throw new BDException();
            }
        } catch (Exception e) {
            Log.i("erro metodo atualizar atividade", "" + e.getMessage());
            throw new DAOException(e);
        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }

    @Override
    public void excluir(int id) throws SQLException, AtividadeNaoEncontradaException, DAOException {
        String where = " ID =?";
        try {

            String[] whereArgs = new String[]{String.valueOf(id)};
            db = conexao.openDb();
            if (db != null)
                db.delete(NOME_TABELA, where, whereArgs);

            else
                throw new BDException();


        } catch (Exception e) {
            Log.i("erro metodo excluir atividade", "" + e.getMessage());
            throw new DAOException(e);
        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }
}
