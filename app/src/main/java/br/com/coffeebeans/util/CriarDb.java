package br.com.coffeebeans.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.LoggingMXBean;

public class CriarDb extends SQLiteOpenHelper {
    /*private static final String createTableUserLogado = "CREATE TABLE IF NOT EXISTS usuario_logado (ID INTEGER PRIMARY KEY AUTOINCREMENT,ID_USUARIO INTEGER,FOREIGN KEY(ID_USUARIO)  REFERENCES usuario (ID))";
    private static final String createTableLogUserLogado = "CREATE TABLE IF NOT EXISTS log_user (ID INTEGER PRIMARY KEY,ID_USER_LOGADO INTEGER,DATA_HORA TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,FOREIGN KEY(ID_USER_LOGADO) REFERENCES usuario_logado(ID))";
   */
    private static final String createTableUserLogado = "CREATE TABLE IF NOT EXISTS USUARIO_LOGADO (ID INTEGER PRIMARY KEY,ID_USUARIO INTEGER NOT NULL)";
    private static final String createTableDispositivoAtivo = "CREATE TABLE IF NOT EXISTS DISPOSITIVO_ATIVO (ID INTEGER PRIMARY KEY,ID_DISPOSITIVO INTEGER NOT NULL)";
    private static final String createDispositivo = "CREATE TABLE IF NOT EXISTS DISPOSITIVO (ID INTEGER PRIMARY KEY AUTOINCREMENT, HOST varchar(45) NOT NULL,PORTA INTEGER NOT NULL, NOME varchar(45) NOT NULL)";
    private static final String createTableAtividade = "CREATE TABLE IF NOT EXISTS atividade (ID INTEGER PRIMARY KEY AUTOINCREMENT,DESCRICAO varchar(45) NOT NULL)";
    private static final String createTableUsuario = "CREATE TABLE IF NOT EXISTS usuario (ID INTEGER PRIMARY KEY AUTOINCREMENT,NOME varchar(100) NOT NULL,LOGIN "
            + "varchar(45) NOT NULL UNIQUE,SENHA varchar(45) NOT NULL,EMAIL varchar(100) NOT NULL UNIQUE,TELEFONE varchar(45) default NULL,"
            + "ATIVO varchar(3) NOT NULL, FOTO varchar(200) default NULL, PERFIL varchar(45) NOT NULL)";

    private static CriarDb instance;
    private static SQLiteDatabase db;

    public static CriarDb getInstance(Context context) throws Exception {
        try {
            if (instance == null) {
                instance = new CriarDb(context, ConfigDb.NOME_BANCO, null, ConfigDb.DATABASE_VERSION);
            }
        } catch (Exception e) {
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
            Log.i("oncreate", "entrou no oncreate CriarDb");
            db.execSQL(createTableAtividade);
            db.execSQL(createTableUsuario);
            db.execSQL(createTableUserLogado);
            db.execSQL(createDispositivo);
            db.execSQL(createTableDispositivoAtivo);
            db.execSQL("insert into usuario values(1,'ADMIN','admin','admin','admin@exemplo.com',NULL,'SIM',NULL,'ADMINISTRADOR')");
        } catch (NullPointerException e) {
            Log.i("erro NullPointerException classe criar db", e.getMessage());
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
}
