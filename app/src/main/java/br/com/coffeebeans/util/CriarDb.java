package br.com.coffeebeans.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.com.coffeebeans.exception.BDException;

public class CriarDb extends SQLiteOpenHelper {
    /*private static final String createTableAtividade = "CREATE TABLE IF NOT EXISTS atividade ( ID INTEGER(11) NOT NULL ,DESCRICAO varchar(45) NOT NULL, "
            + "PRIMARY KEY  (ID))";
    */
    private static final String createTableAtividade = "CREATE TABLE IF NOT EXISTS atividade ( ID INTEGER PRIMARY KEY AUTOINCREMENT,DESCRICAO varchar(45) NOT NULL)";


    private static final String createTableUsuario = "CREATE TABLE IF NOT EXISTS usuario (ID INTEGER PRIMARY KEY AUTOINCREMENT,NOME varchar(100) NOT NULL,LOGIN "
            + "varchar(45) NOT NULL,SENHA varchar(45) NOT NULL,EMAIL varchar(100) NOT NULL,TELEFONE varchar(45) default NULL,"
            + "ATIVO varchar(3) NOT NULL, FOTO varchar(200) default NULL, PERFIL varchar(45) NOT NULL)";

    private static CriarDb instance;
    private static SQLiteDatabase db;

    public static CriarDb getInstance(Context context) throws Exception {
        try {
            if (instance == null) {
                instance = new CriarDb(context, ConfigDb.NOME_BANCO, null, ConfigDb.DATABASE_VERSION);
            }
        }catch (Exception e) {
          throw new Exception(e);
        }
        return instance;
    }

    private CriarDb(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) throws NullPointerException {

        try {
            //db.execSQL("drop table atividade");
            Log.i("oncreate","entrou no oncreate CriarDb");
            db.execSQL(createTableAtividade);
            db.execSQL(createTableUsuario);
            db.execSQL("insert into usuario values(1,'ADMIN','admin','admin','admin@exemplo.com',NULL,'SIM',NULL,'ADMINISTRADOR')");

        } catch (NullPointerException e) {
            Log.i("erro NullPointerException classe criar db", "" + e.getMessage());
            throw new NullPointerException("");
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase openDb() throws Exception {
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
            Log.i("erro open db ", "" + e.getMessage());
            throw new Exception(e);
        }
        return db;
    }

   /* public void closeDb() throws Exception {
        try {
            db.close();
        } catch (Exception e) {
            Log.i("erro closeDb ", "" + e.getMessage());
            throw new Exception(e);
        }
    } */

}
