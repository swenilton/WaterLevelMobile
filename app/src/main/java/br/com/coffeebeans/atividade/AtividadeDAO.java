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
import br.com.coffeebeans.util.CriarDb;

public class AtividadeDAO implements IAtividadeDAO {
	private static final String NOME_TABELA = "atividade";
	private SQLiteDatabase db;
	private CriarDb conexao;

	public AtividadeDAO(Context context) {
		conexao = CriarDb.getInstance(context);
		db = conexao.openDb();
	}

	public boolean existe(String descricao) throws SQLException {
		boolean existe = false;

		Cursor rs = null;
		try {
			if (db != null) {

				String sql = "Select * from " + NOME_TABELA + "where descricao= ?";
				
				String[] selectionArgs={descricao};
				
				rs = db.rawQuery(sql, selectionArgs);

				rs.moveToFirst();

				if (rs.getCount() > 0 && rs != null) {
					existe = true;
				}

				Log.i("metodo existe atividade", "atividade retornada com sucesso ");
			} else {
				Log.i("Alerta", "banco nao existe ou nao foi aberto");
			}
		} catch (Exception e) {
			Log.i("erro no metodo existir da classe atividade ", e.getMessage());
		}
		return existe;
	}

	@Override
	public void cadastrar(Atividade atividade) throws SQLException {
		ContentValues valores = new ContentValues();
		// valores.put("ID", atividade.getId());
		valores.put("DESCRICAO", atividade.getDescricao());

		try {
			if (db != null) {

				// TODO //atividade ja existente
				db.insert(NOME_TABELA, null, valores);
			} else {
				Log.i("Alerta", "banco nao existe ou nao foi aberto");
			}

		} catch (Exception e) {
			Log.i("erro ao cadastrar atividade ", "" + e.getMessage());
		}
	}

	@Override
	public List<Atividade> listar() throws SQLException {
		Cursor rs = null;
		ArrayList<Atividade> atividades = null;

		try {
			atividades = new ArrayList<Atividade>();
			String sql = "SELECT * from " + NOME_TABELA;

			if (db != null) {
				rs = db.rawQuery(sql, null);

				rs.moveToFirst();

				if (rs.getCount() > 0 && rs != null) {

					do {
						Atividade atividade = new Atividade(rs.getString(rs.getColumnIndex("DESCRICAO")));
						atividade.setId(rs.getInt(rs.getColumnIndex("ID")));

						atividades.add(atividade);

					} while (rs.moveToNext());

				}
			} else {
				Log.i("Alerta", "banco nao existe ou nao foi aberto");
			}

		} catch (Exception e) {
			Log.i("erro no metodo listar da classe atividade ", "" + e.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		return atividades;
	}

	@Override
	public Atividade procurar(int id) throws SQLException, AtividadeNaoEncontradaException {
		Cursor rs = null;
		Atividade atividade = null;
		try {
			if (db != null) {

				String sql = "Select * from " + NOME_TABELA + " where ID= ?";
				String[] whereArgs = {String.valueOf(id)};

				rs = db.rawQuery(sql, whereArgs);

				rs.moveToFirst();

				if (rs.getCount() > 0 && rs != null) {
					atividade = new Atividade(rs.getString(rs.getColumnIndex("DESCRICAO")));
					atividade.setId(rs.getInt(rs.getColumnIndex("ID")));

					Log.i("procurar atividade", "atividade achada com sucesso ");
				}
			} else {
				Log.i("Alerta", "banco nao existe ou nao foi aberto");
			}
		} catch (Exception e) {
			Log.i("erro no metodo procurar da classe atividade ", "" + e.getMessage());
		}
		return atividade;
	}

	@Override
	public void atualizar(Atividade atividade) throws AtividadeNaoEncontradaException, SQLException {
		try {
			//TODO //atividade ja existente
			ContentValues valores = new ContentValues();
			
			valores.put("DESCRICAO", atividade.getDescricao());

			String where = "ID =?";

			String[] whereArgs ={String.valueOf(atividade.getId())};

			db.update(NOME_TABELA, valores, where, whereArgs);
		} catch (Exception e) {
			Log.i("erro metodo atualizar atividade", "" + e.getMessage());
		}
	}

	@Override
	public void excluir(int id) throws SQLException, AtividadeNaoEncontradaException {
		try {
			
			String where = " ID =?";
			String[] whereArgs = new String[] { String.valueOf(id) };
			db.delete(NOME_TABELA, where, whereArgs);
			Log.i("delete atividade", "atividade deletada com sucesso ");
		} catch (Exception e) {
			Log.i("erro metodo excluir atividade", "" + e.getMessage());
		}
	}
}
