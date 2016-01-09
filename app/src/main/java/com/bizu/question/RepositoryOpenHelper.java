package com.bizu.question;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.BaseColumns;

/**
 * Created by andre.lmello on 12/9/15.
 */
public class RepositoryOpenHelper extends SQLiteOpenHelper implements QuestionRepository {

    public final static String DATABASE_NAME = "Question.db";
    public final static int DATABASE_VERSION = 1;
    private static final String COMMA_SEP = ",";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static final String SEMICOLON_SEP = ";";
    private static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";
    private static final StringBuilder DDL_CREATER = new StringBuilder();
    private static final String DDL_QUESTION = DDL_CREATER
        .append(CREATE_TABLE_IF_NOT_EXISTS).append(QuestionContract.TABLE_NAME)
        .append(" (").append(QuestionContract._ID).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
        .append(QuestionContract.ANO_QUESTAO).append(" INT NULL").append(COMMA_SEP)
        .append(QuestionContract.NUMERO_QUESTAO).append(" INT NULL").append(COMMA_SEP)
        .append(QuestionContract.DESCRICAO_QUESTAO).append(" VARCHAR(2000) NULL").append(COMMA_SEP)
        .append(QuestionContract.COMANDO_QUESTAO).append(" VARCHAR(1000) NULL").append(COMMA_SEP)
        .append(QuestionContract.PROVA).append(" INT NULL").append(COMMA_SEP)
        .append(QuestionContract.SITUACAO_QUESTAO).append(" INT NULL").append(COMMA_SEP)
        .append(QuestionContract.IMAGEM_QUESTAO).append(" VARCHAR(100) NULL)").toString();

    private static final String DDL_ITEM = DDL_CREATER.delete(0, DDL_CREATER.length())
        .append(CREATE_TABLE_IF_NOT_EXISTS).append(ItemContract.TABLE_NAME)
        .append(" (").append(ItemContract._ID).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
        .append(ItemContract.ID_QUESTAO).append(" INT NOT NULL").append(COMMA_SEP)
        .append(ItemContract.DESCRICAO).append(" VARCHAR(1000) NULL").append(COMMA_SEP)
        .append(ItemContract.SITUACAO_ITEM).append(" INT NULL").append(COMMA_SEP)
        .append(ItemContract.IMAGEM_ITEM).append(" INT NULL").append(COMMA_SEP)
        .append(" CONSTRAINT fk_TB_ITEM_TB_QUESTAO FOREIGN KEY ").append(QuestionContract._ID)
        .append(" REFERENCES ").append(QuestionContract.TABLE_NAME)
        .append(" (").append(QuestionContract._ID).append("))").toString();

    private static final String DDL_QUESTAO_HABILIDADES = DDL_CREATER
        .append(CREATE_TABLE_IF_NOT_EXISTS).append(QuestionCompetencesContract.TABLE_NAME)
        .append(" (").append(QuestionCompetencesContract._ID).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
        .append(QuestionCompetencesContract.TB_HABILIDADES_ID_HABILIDADE).append(" INT").append(COMMA_SEP)
        .append(QuestionCompetencesContract.TB_QUESTAO_ID_QUESTAO).append(" INT ")
        .append("CONSTRAINT fk_TB_HABILIDADES_ID_HABILIDADE FOREIGN KEY (")
        .append(QuestionCompetencesContract.TB_HABILIDADES_ID_HABILIDADE).append(") ")
        .append("REFERENCES ").append(CompetencesContract.TABLE_NAME).append("(")
        .append(CompetencesContract._ID).append("))").toString();

    private static final String DDL_HABILIDADES = DDL_CREATER.delete(0, DDL_CREATER.length())
        .append(CREATE_TABLE_IF_NOT_EXISTS).append(CompetencesContract.TABLE_NAME)
        .append(" (").append(CompetencesContract._ID).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
        .append(CompetencesContract.TB_COMPETENCIAS_ID_COMPETENCIA).append(" INT NOT NULL").append(COMMA_SEP)
        .append(CompetencesContract.DESCRICAO_HABILIDADE).append(" VARCHAR(500) NULL ").append(COMMA_SEP)
        .append("CONSTRAINT fk_TB_HABILIDADES_TB_COMPETENCIAS1 ").append(" FOREIGN KEY (")
        .append(CompetencesContract.TB_COMPETENCIAS_ID_COMPETENCIA).append(") ")
        .append("REFERENCES ").append(SkillsContract.TABLE_NAME).append("(")
        .append(SkillsContract._ID).append("))").toString();

    private static final String DDL_COMPETENCIAS = DDL_CREATER.delete(0, DDL_CREATER.length())
        .append(CREATE_TABLE_IF_NOT_EXISTS).append(SkillsContract.TABLE_NAME)
        .append(" (").append(SkillsContract._ID).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
        .append(SkillsContract.NOME_COMPETENCIA).append(" VARCHAR(45) NULL").append(COMMA_SEP)
        .append(CompetencesContract.DESCRICAO_HABILIDADE).append(" VARCHAR(500) NULL ").append(")")
        .toString();

    private static final String DDL_MATERIA = DDL_CREATER.delete(0, DDL_CREATER.length())
        .append(CREATE_TABLE_IF_NOT_EXISTS).append(SubjectContract.TABLE_NAME)
        .append(" (").append(SubjectContract._ID).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
        .append(SubjectContract.NOME_MATERIA).append(" VARCHAR(100) NULL").append(")").toString();

    private static final String DDL_ASSUNTO = DDL_CREATER.delete(0, DDL_CREATER.length())
        .append(CREATE_TABLE_IF_NOT_EXISTS).append(TopicContract.TABLE_NAME)
        .append(" (").append(TopicContract._ID).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
        .append(TopicContract.DESCRICAO_ASSUNTO).append(" VARCHAR(2000) NULL").append(COMMA_SEP)
        .append(TopicContract.ID_MATERIA).append(" INT NOT NULL").append(COMMA_SEP)
        .append("FOREIGN KEY (").append(TopicContract.ID_MATERIA).append(") REFERENCES ")
        .append(SubjectContract.TABLE_NAME).append("(").append(SubjectContract._ID).append("))")
        .toString();

    private static final String DDL_ASSUNTO_MATERIA = DDL_CREATER.delete(0, DDL_CREATER.length())
        .append(CREATE_TABLE_IF_NOT_EXISTS).append(TopicSubjectContract.TABLE_NAME)
        .append(" (").append(TopicSubjectContract._ID).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
        .append(TopicSubjectContract.TB_ASSUNTO_ID_ITEM).append(" INT NOT NULL").append(COMMA_SEP)
        .append(TopicSubjectContract.TB_QUESTAO_ID_QUESTAO).append(" INT NOT NULL").append(COMMA_SEP)
        .append("CONSTRAINT fk_TB_ASSUNTO_MATERIA_TB_ASSUNTO1 ").append(" FOREIGN KEY (")
        .append(TopicSubjectContract.TB_ASSUNTO_ID_ITEM).append(") ")
        .append("REFERENCES ").append(TopicContract.TABLE_NAME).append("(")
        .append(TopicContract._ID).append(")")
        .append(" ON DELETE NO ACTION ON UPDATE NO ACTION").append(")").toString();

    private static final String SQL_CREATE_ENTRIES = DDL_CREATER.delete(0, DDL_CREATER.length())
        .append(DDL_QUESTION).append(SEMICOLON_SEP)
        .append(DDL_ITEM).append(SEMICOLON_SEP)
        /** Subject */
        .append(DDL_COMPETENCIAS).append(SEMICOLON_SEP)
        .append(DDL_HABILIDADES).append(SEMICOLON_SEP)
        .append(DDL_QUESTAO_HABILIDADES).append(SEMICOLON_SEP)
        .append(DDL_MATERIA).append(SEMICOLON_SEP)
        .append(DDL_ASSUNTO).append(SEMICOLON_SEP)
        .append(DDL_ASSUNTO_MATERIA).append(SEMICOLON_SEP).toString();

    private static final String SQL_DELETE_ENTRIES = DDL_CREATER.delete(0, DDL_CREATER.length())
        .append(DROP_TABLE).append(QuestionContract.TABLE_NAME).append(SEMICOLON_SEP)
        .append(DROP_TABLE).append(ItemContract.TABLE_NAME).append(SEMICOLON_SEP)
        /** Subject */
        .append(DROP_TABLE).append(TopicSubjectContract.TABLE_NAME).append(SEMICOLON_SEP)
        .append(DROP_TABLE).append(TopicContract.TABLE_NAME).append(SEMICOLON_SEP)
        .append(DROP_TABLE).append(SubjectContract.TABLE_NAME).append(SEMICOLON_SEP)
        .append(DROP_TABLE).append(QuestionCompetencesContract.TABLE_NAME).append(SEMICOLON_SEP)
        .append(DROP_TABLE).append(CompetencesContract.TABLE_NAME).append(SEMICOLON_SEP)
        .append(DROP_TABLE).append(SkillsContract.TABLE_NAME).append(SEMICOLON_SEP)
        .toString();

    public static abstract class QuestionContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_QUESTAO";
        public static final String ANO_QUESTAO = "ANO_QUESTAO";
        public static final String NUMERO_QUESTAO = "NUMERO_QUESTAO";
        public static final String DESCRICAO_QUESTAO = "DESCRICAO_QUESTAO";
        public static final String COMANDO_QUESTAO = "COMANDO_QUESTAO";
        public static final String PROVA = "PROVA";
        public static final String SITUACAO_QUESTAO = "SITUACAO_QUESTAO";
        public static final String IMAGEM_QUESTAO = "IMAGEM_QUESTAO";
    }

    public static abstract class ItemContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_ITEM";
        public static final String ID_QUESTAO = "ID_QUESTAO";
        public static final String DESCRICAO = "DESCRICAO";
        public static final String SITUACAO_ITEM = "SITUACAO_ITEM";
        public static final String IMAGEM_ITEM = "IMAGEM_ITEM";
    }

    /** Subject */
    public static abstract class SubjectContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_MATERIA";
        public static final String NOME_MATERIA = "NOME_MATERIA";
    }

    public static abstract class TopicContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_ASSUNTO";
        public static final String DESCRICAO_ASSUNTO = "DESCRICAO_ASSUNTO";
        public static final String ID_MATERIA = "ID_MATERIA";
    }

    public static abstract class TopicSubjectContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_ASSUNTO_MATERIA";
        public static final String TB_ASSUNTO_ID_ITEM = "TB_ASSUNTO_ID_ITEM";
        public static final String TB_QUESTAO_ID_QUESTAO = "TB_QUESTAO_ID_QUESTAO";
    }

    public static abstract class QuestionCompetencesContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_QUESTAO_HABILIDADES";
        public static final String TB_HABILIDADES_ID_HABILIDADE = "TB_HABILIDADES_ID_HABILIDADE";
        public static final String TB_QUESTAO_ID_QUESTAO = "TB_QUESTAO_ID_QUESTAO";
    }

    public static abstract class CompetencesContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_HABILIDADES";
        public static final String TB_COMPETENCIAS_ID_COMPETENCIA = "TB_COMPETENCIAS_ID_COMPETENCIA";
        public static final String DESCRICAO_HABILIDADE = "DESCRICAO_HABILIDADE";
    }

    public static abstract class SkillsContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_COMPETENCIAS";
        public static final String NOME_COMPETENCIA = "NOME_COMPETENCIA";
    }

    /** Constructor */
    public RepositoryOpenHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public AsyncSave save(final Question question, final SaveListener listener) {
        return (AsyncSave) new AsyncSave(listener, this).execute(question);
    }

    Question save(final Question question) {
        getWritableDatabase();
        question.setId(1L);
        return question;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        /*db.execSQL(DDL_QUESTION);
        db.execSQL(ddlItem);
        db.execSQL(ddlCompetencias);
        db.execSQL(ddlHabilidades);
        db.execSQL(ddlAssunto);
        db.execSQL(ddlMateria);
        db.execSQL(ddlAssuntoMateria);*/
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
}

class AsyncSave extends AsyncTask<Question, Integer, Question> {

    private final SaveListener mListener;
    private final RepositoryOpenHelper mRepository;

    public AsyncSave(final SaveListener listener, final RepositoryOpenHelper repository) {
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
