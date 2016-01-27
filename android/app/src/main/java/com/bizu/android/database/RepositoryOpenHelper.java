package com.bizu.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by andre.lmello on 12/9/15.
 */
public class RepositoryOpenHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "Question.db";
    public final static int DATABASE_VERSION = 1;
    private static RepositoryOpenHelper sRepositoryOpenHelper;
    private static final String COMMA_SEP = ",";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static final String SEMICOLON_SEP = ";";
    private static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";
    private static final StringBuilder DDL_CREATER = new StringBuilder();
    private static final String DDL_QUESTION = DDL_CREATER
            .append(CREATE_TABLE_IF_NOT_EXISTS).append(QuestionContract.TABLE_NAME)
            .append(" (").append(QuestionContract.ID_QUESTAO).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
            .append(QuestionContract.ANO_QUESTAO).append(" INT NULL").append(COMMA_SEP)
            .append(QuestionContract.NUMERO_QUESTAO).append(" INT NULL").append(COMMA_SEP)
            .append(QuestionContract.DESCRICAO_QUESTAO).append(" VARCHAR(2000) NULL").append(COMMA_SEP)
            .append(QuestionContract.COMANDO_QUESTAO).append(" VARCHAR(1000) NULL").append(COMMA_SEP)
            .append(QuestionContract.PROVA).append(" INT NULL").append(COMMA_SEP)
            .append(QuestionContract.SITUACAO_QUESTAO).append(" INT NULL").append(COMMA_SEP)
            .append(QuestionContract.IMAGEM_QUESTAO).append(" VARCHAR(100) NULL").append(COMMA_SEP)
            .append(QuestionContract.COMENTARIO_QUESTAO).append(" VARCHAR(3000) NULL").append(COMMA_SEP)
            .append(QuestionContract.LETRA_ITEM_CORRETO).append(" VARCHAR(1) NULL").append(COMMA_SEP)
            .append(QuestionContract.DIA_PROVA).append(" INT(1) NULL").append(COMMA_SEP)
            .append(QuestionContract.APLICACAO).append(" INT(1) NULL)").toString();

    private static final String DD_LOG_PROCESSAMENTO = DDL_CREATER.delete(0, DDL_CREATER.length())
            .append(CREATE_TABLE_IF_NOT_EXISTS).append(LogProcessContract.TABLE_NAME)
            .append(" (").append(LogProcessContract.ID_LOG_PROCESSAMENTO).append(" INTEGER PRIMARY KEY AUTOINCREMENT").append(COMMA_SEP)
            .append(LogProcessContract.DATA_INICIO_PROCESAMENTO).append(" DATE NULL").append(COMMA_SEP)
            .append(LogProcessContract.DATA_FIM_PROCESSAMENTO).append(" DATE NULL").append(COMMA_SEP)
            .append(LogProcessContract.QTD_ATUALIZADOS).append(" INTEGER NULL").append(COMMA_SEP)
            .append(LogProcessContract.QTD_INSERIDOS).append(" INTEGER NULL").append(COMMA_SEP)
            .append(LogProcessContract.TIPO_TABELA_ATUALIZADA).append(" TEXT NULL)").toString();

    private static final String DDL_ITEM = DDL_CREATER.delete(0, DDL_CREATER.length())
            .append(CREATE_TABLE_IF_NOT_EXISTS).append(ItemContract.TABLE_NAME)
            .append(" (").append(ItemContract.ID_ITEM).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
            .append(ItemContract.ID_QUESTAO).append(" INT NOT NULL").append(COMMA_SEP)
            .append(ItemContract.DESCRICAO).append(" VARCHAR(1000) NULL").append(COMMA_SEP)
            .append(ItemContract.SITUACAO_ITEM).append(" INT NULL").append(COMMA_SEP)
            .append(ItemContract.LETRA_ITEM).append(" VARCHAR(1)").append(COMMA_SEP)
            .append(ItemContract.NOME_IMAGEM_ITEM).append(" VARCHAR(50)").append(COMMA_SEP)
            .append(ItemContract.NOME_IMAGEM_ITEM_SISTEMA).append(" VARCHAR(100)").append(")").toString();
    //   .append(" CONSTRAINT fk_TB_ITEM_TB_QUESTAO FOREIGN KEY ").append(QuestionContract.ID_QUESTAO)
    // .append(" REFERENCES ").append(QuestionContract.TABLE_NAME)
    //.append(" (").append(QuestionContract.ID_QUESTAO).append("))").toString();

    private static final String DDL_QUESTAO_HABILIDADES = DDL_CREATER.delete(0, DDL_CREATER.length())
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
            .append(" (").append(SubjectContract.ID_MATERIA).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
            .append(SubjectContract.NOME_MATERIA).append(" VARCHAR(100) NULL").append(")").toString();

    private static final String DDL_ASSUNTO = DDL_CREATER.delete(0, DDL_CREATER.length())
            .append(CREATE_TABLE_IF_NOT_EXISTS).append(TopicContract.TABLE_NAME)
            .append(" (").append(TopicContract.ID_ASSUNTO).append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
            .append(TopicContract.DESCRICAO_ASSUNTO).append(" VARCHAR(2000) NULL").append(COMMA_SEP)
            .append(TopicContract.ID_ASSUNTO_PAI).append(" INT NOT NULL").append(COMMA_SEP)
            .append(TopicContract.ID_MATERIA).append(" INT NOT NULL").append(")").toString();
           // .append("FOREIGN KEY (").append(TopicContract.ID_MATERIA_TOPIC).append(") REFERENCES ")
           // .append(SubjectContract.TABLE_NAME).append("(").append(SubjectContract._ID).append("))")
           // .toString();

    private static final String DDL_ASSUNTO_QUESTAO = DDL_CREATER.delete(0, DDL_CREATER.length())
            .append(CREATE_TABLE_IF_NOT_EXISTS).append(TopicQuestionContract.TABLE_NAME)
            .append("(").append(TopicQuestionContract.ID_ASSUNTO).append(" INT NOT NULL").append(COMMA_SEP)
            .append(TopicQuestionContract.ID_QUESTAO).append(" INT NOT NULL").append(")").toString();
          //  .append("CONSTRAINT fk_TB_ASSUNTO_MATERIA_TB_ASSUNTO1 ").append(" FOREIGN KEY (")
          //  .append(TopicSubjectContract.TB_ASSUNTO_ID_ITEM).append(") ")
          //  .append("REFERENCES ").append(TopicContract.TABLE_NAME).append("(")
          //  .append(TopicContract._ID).append(")")
         //   .append(" ON DELETE NO ACTION ON UPDATE NO ACTION").append(")").toString();

    public static abstract class QuestionContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_QUESTAO";
        public static final String ID_QUESTAO = "ID_QUESTAO";
        public static final String ANO_QUESTAO = "ANO_QUESTAO";
        public static final String NUMERO_QUESTAO = "NUMERO_QUESTAO";
        public static final String DESCRICAO_QUESTAO = "DESCRICAO_QUESTAO";
        public static final String COMANDO_QUESTAO = "COMANDO_QUESTAO";
        public static final String PROVA = "PROVA";
        public static final String SITUACAO_QUESTAO = "SITUACAO_QUESTAO";
        public static final String IMAGEM_QUESTAO = "IMAGEM_QUESTAO";
        public static final String COMENTARIO_QUESTAO = "COMENTARIO_QUESTAO";
        public static final String LETRA_ITEM_CORRETO = "LETRA_ITEM_CORRETO";
        public static final String DIA_PROVA = "DIA_PROVA";
        public static final String APLICACAO = "APLICACAO";
    }

    public static abstract class ItemContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_ITEM";
        public static final String ID_ITEM = "ID_ITEM";
        public static final String ID_QUESTAO = "ID_QUESTAO";
        public static final String DESCRICAO = "DESCRICAO";
        public static final String SITUACAO_ITEM = "SITUACAO_ITEM";
        public static final String NOME_IMAGEM_ITEM = "NOME_IMAGEM_ITEM";
        public static final String LETRA_ITEM = "LETRA_ITEM";
        public static final String NOME_IMAGEM_ITEM_SISTEMA = "NOME_IMAGEM_ITEM_SISTEMA";
    }

    /**
     * Subject
     */
    public static abstract class SubjectContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_MATERIA";
        public static final String NOME_MATERIA = "NOME_MATERIA";
        public static final String ID_MATERIA = "ID_MATERIA";
    }

    public static abstract class TopicContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_ASSUNTO";
        public static final String ID_ASSUNTO = "ID_ASSUNTO";
        public static final String DESCRICAO_ASSUNTO = "DESCRICAO_ASSUNTO";
        public static final String ID_MATERIA = "ID_MATERIA";
        public static final String ID_ASSUNTO_PAI = "ID_ASSUNTO_PAI";
    }

    public static abstract class TopicQuestionContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_ASSUNTO_QUESTAO";
        public static final String ID_ASSUNTO = "ID_ASSUNTO";
        public static final String ID_QUESTAO = "ID_QUESTAO";
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

    public static abstract class LogProcessContract implements BaseColumns {
        public static final String TABLE_NAME = "TB_LOG_PROCESSAMENTO";
        public static final String ID_LOG_PROCESSAMENTO = "ID_LOG_PROCESSAMENTO";
        public static final String DATA_INICIO_PROCESAMENTO = "DATA_INICIO_PROCESAMENTO";
        public static final String DATA_FIM_PROCESSAMENTO = "DATA_FIM_PROCESSAMENTO";
        public static final String QTD_ATUALIZADOS = "QTD_ATUALIZADOS";
        public static final String QTD_INSERIDOS = "QTD_INSERIDOS";
        public static final String TIPO_TABELA_ATUALIZADA = "TIPO_TABELA_ATUALIZADA";

    }

    /**
     * Constructor
     */
    private RepositoryOpenHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static RepositoryOpenHelper getInstance(final Context context) {
        if (sRepositoryOpenHelper == null) sRepositoryOpenHelper = new RepositoryOpenHelper(context);
        return sRepositoryOpenHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DDL_QUESTION);
        db.execSQL(DDL_ITEM);
        db.execSQL(DDL_COMPETENCIAS);
        db.execSQL(DDL_COMPETENCIAS);
        db.execSQL(DDL_HABILIDADES);
        db.execSQL(DDL_MATERIA);
        db.execSQL(DDL_ASSUNTO_QUESTAO);
        db.execSQL(DDL_ASSUNTO);
        db.execSQL(DD_LOG_PROCESSAMENTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DDL_CREATER.delete(0, DDL_CREATER.length()).append(DROP_TABLE).append(QuestionContract.TABLE_NAME).toString());
        db.execSQL(DDL_CREATER.delete(0, DDL_CREATER.length()).append(DROP_TABLE).append(ItemContract.TABLE_NAME).toString());
        db.execSQL(DDL_CREATER.delete(0, DDL_CREATER.length()).append(DROP_TABLE).append(CompetencesContract.TABLE_NAME).toString());
        db.execSQL(DDL_CREATER.delete(0, DDL_CREATER.length()).append(DROP_TABLE).append(SkillsContract.TABLE_NAME).toString());
        db.execSQL(DDL_CREATER.delete(0, DDL_CREATER.length()).append(DROP_TABLE).append(QuestionCompetencesContract.TABLE_NAME).toString());
        db.execSQL(DDL_CREATER.delete(0, DDL_CREATER.length()).append(DROP_TABLE).append(TopicContract.TABLE_NAME).toString());
        db.execSQL(DDL_CREATER.delete(0, DDL_CREATER.length()).append(DROP_TABLE).append(SubjectContract.TABLE_NAME).toString());
//        db.execSQL(DDL_CREATER.delete(0, DDL_CREATER.length()).append(DROP_TABLE).append(TopicSubjectContract.TABLE_NAME).toString());
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void saveLogProcessInsertUpdate(Integer qtdInserted, Integer qtdUptadate, Date dateStartProcess
            , Date dateFinishProcess, String typeTable) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(RepositoryOpenHelper.LogProcessContract.QTD_INSERIDOS, qtdInserted);
        contentValues.put(RepositoryOpenHelper.LogProcessContract.QTD_ATUALIZADOS, qtdUptadate);
        contentValues.put(RepositoryOpenHelper.LogProcessContract.DATA_INICIO_PROCESAMENTO, dateStartProcess.toString());
        contentValues.put(RepositoryOpenHelper.LogProcessContract.DATA_FIM_PROCESSAMENTO, dateFinishProcess.toString());
        contentValues.put(RepositoryOpenHelper.LogProcessContract.TIPO_TABELA_ATUALIZADA, typeTable);

        sqLiteDatabase.insert(RepositoryOpenHelper.LogProcessContract.TABLE_NAME, null, contentValues);

    }

    /*
    * Método utilizando enquanto estivermos fazendo testes no app
    *
    * */
    //FIXME: remover ASAP (As soon as possible)
    public void clearDatabaseQuestionItem() {

        String deleteQuestion = "DELETE FROM TB_QUESTAO";

        String deleteItem = "DELETE FROM TB_ITEM";
        String deleteAssuntoQuestao = "DELETE FROM TB_ASSUNTO_QUESTAO";
        String deleteMateria = "DELETE FROM TB_MATERIA";


        //db.execSQL(DD_LOG_PROCESSAMENTO);
        // String deleteSequenceQuestion = "DELETE FROM SQLITE_SEQUENCE WHERE NAME  = 'TB_QUESTAO'";
       /// String deleteSequenceiTEM = "DELETE FROM SQLITE_SEQUENCE WHERE NAME  = 'TB_ITEM'";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(deleteQuestion);
        db.execSQL(deleteItem);
        db.execSQL(deleteAssuntoQuestao);
        db.execSQL(deleteMateria);
        close();
      //  db.execSQL(deleteSequenceQuestion);
       // db.execSQL(deleteSequenceiTEM);
    }
}