package com.bizu.question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre.lmello on 12/9/15.
 */
public class AsyncQuestionRepository extends SQLiteOpenHelper implements QuestionRepository {

    public final static String DATABASE_NAME = "Question.db";
    public final static int DATABASE_VERSION = 1;
//    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QuestionContract.TABLE_NAME + " (" +
                    QuestionContract._ID + " INTEGER PRIMARY KEY" +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QuestionContract.TABLE_NAME;

    public static abstract class QuestionContract implements BaseColumns {
        public static final String TABLE_NAME = "question";
    }

    public AsyncQuestionRepository(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public AsyncSave save(final Question question, final SaveListener listener) {
        return (AsyncSave) new AsyncSave(listener, this).execute(question);
    }

    Question save(final Question question) {
        question.setId(1L);
        return question;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddlQuestao = "CREATE TABLE IF NOT EXISTS TB_QUESTAO (" +
                "  ID_QUESTAO INT NOT NULL," +
                "  ANO_QUESTAO INT NULL," +
                "  NUMERO_QUESTAO INT NULL," +
                "  DESCRICAO_QUESTAO VARCHAR(2000) NULL," +
                "  COMANDO_QUESTAO VARCHAR(2000) NULL," +
                "  PROVA INT NULL," +
                "  SITUACAO_QUESTAO INT NULL," +
                "  IMAGEM_QUESTAO VARCHAR(100) NULL," +
                "  PRIMARY KEY (ID_QUESTAO));";

        String ddlItem = "CREATE TABLE IF NOT EXISTS TB_ITEM (" +
                "  ID_ITEM INT NOT NULL," +
                "  ID_QUESTAO INT NOT NULL," +
                "  DESCRICAO VARCHAR(2000) NULL," +
                "  SITUACAO_ITEM INT NULL," +
                "  IMAGEM_ITEM VARCHAR(100) NULL," +
                "  PRIMARY KEY (ID_ITEM)," +
                "  CONSTRAINT fk_TB_ITEM_TB_QUESTAO" +
                "    FOREIGN KEY (ID_QUESTAO)" +
                "    REFERENCES TB_QUESTAO (ID_QUESTAO));";

        String ddlCompetencias = "CREATE TABLE IF NOT EXISTS TB_COMPETENCIAS (" +
                "  ID_COMPETENCIA INT NOT NULL," +
                "  NOME_COMPETENCIA VARCHAR(45) NULL," +
                "  PRIMARY KEY (ID_COMPETENCIA));";

        String ddlHabilidades = "CREATE TABLE IF NOT EXISTS TB_HABILIDADES (" +
                "  ID_HABILIDADE INT NOT NULL," +
                "  TB_COMPETENCIAS_ID_COMPETENCIA INT NOT NULL," +
                "  DESCRICAO_HABILIDADE VARCHAR(500) NULL," +
                "  PRIMARY KEY (ID_HABILIDADE)," +
                "  CONSTRAINT fk_TB_HABILIDADES_TB_COMPETENCIAS1" +
                "    FOREIGN KEY (TB_COMPETENCIAS_ID_COMPETENCIA)" +
                "    REFERENCES TB_COMPETENCIAS (ID_COMPETENCIA));";

        String ddlAssunto = "CREATE TABLE IF NOT EXISTS TB_ASSUNTO (" +
                "  ID_ITEM INT NOT NULL," +
                "  DESCRICAO_ASSUNTO VARCHAR(2000) NULL," +
                "  ID_MATERIA INT NOT NULL," +
                "  PRIMARY KEY (ID_ITEM)," +
                "    FOREIGN KEY (ID_MATERIA)" +
                "    REFERENCES TB_MATERIA (ID_MATERIA));";

        String ddlMateria = "CREATE TABLE IF NOT EXISTS TB_MATERIA (" +
                "  ID_MATERIA INT NOT NULL," +
                "  NOME_MATERIA VARCHAR(100) NULL," +
                "  PRIMARY KEY (ID_MATERIA));";

        String ddlAssuntoMateria = "CREATE TABLE IF NOT EXISTS TB_ASSUNTO_MATERIA (" +
                "  TB_ASSUNTO_ID_ITEM INT NOT NULL," +
                "  TB_QUESTAO_ID_QUESTAO INT NOT NULL," +
                "  CONSTRAINT fk_TB_ASSUNTO_MATERIA_TB_ASSUNTO1" +
                "    FOREIGN KEY (TB_ASSUNTO_ID_ITEM)" +
                "    REFERENCES TB_ASSUNTO (ID_ITEM)" +
                "    ON DELETE NO ACTION" +
                "    ON UPDATE NO ACTION," +
                "  CONSTRAINT fk_TB_ASSUNTO_MATERIA_TB_QUESTAO1" +
                "    FOREIGN KEY (TB_QUESTAO_ID_QUESTAO)" +
                "    REFERENCES TB_QUESTAO (ID_QUESTAO));";


        db.execSQL(ddlQuestao);
        db.execSQL(ddlItem);
        db.execSQL(ddlCompetencias);
        db.execSQL(ddlHabilidades);
        db.execSQL(ddlAssunto);
        db.execSQL(ddlMateria);
        db.execSQL(ddlAssuntoMateria);






    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void inserirDados() {

        ContentValues values = new ContentValues();

        values.put("ID_MATERIA", 1);
        values.put("NOME_MATERIA", "MATEMÁTICA");


        getWritableDatabase().insert("TB_MATERIA", null, values);

    }



}

class AsyncSave extends AsyncTask<Question, Integer, Question> {

    private final SaveListener mListener;
    private final AsyncQuestionRepository mRepository;

    public AsyncSave(final SaveListener listener, final AsyncQuestionRepository repository) {
        mListener = listener;
        mRepository = repository;
    }

    @Override
    protected Question doInBackground(Question[] params) {
        return mRepository.save(params[0]);
    }

    @Override
    protected void onPostExecute(Question question) {
        mListener.onSaveFinish(question);
    }
}
