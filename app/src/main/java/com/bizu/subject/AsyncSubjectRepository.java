package com.bizu.subject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by dunmait on 12/28/15.
 */
public class AsyncSubjectRepository extends SQLiteOpenHelper implements SubjectRepository {

    public final static String DATABASE_NAME = "Subject.db";
    public final static int DATABASE_VERSION = 1;
    private static final String COMMA_SEP = ",";
    private static final String SEMICOLON_SEP = ";";
    private static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static final StringBuilder DDL_CREATER = new StringBuilder();
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
            .append(SubjectContract.TABLE_NAME).append("(").append(SubjectContract._ID).append("))").toString();

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
            .append(DDL_COMPETENCIAS).append(SEMICOLON_SEP)
            .append(DDL_HABILIDADES).append(SEMICOLON_SEP)
            .append(DDL_QUESTAO_HABILIDADES).append(SEMICOLON_SEP)
            .append(DDL_MATERIA).append(SEMICOLON_SEP)
            .append(DDL_ASSUNTO).append(SEMICOLON_SEP)
            .append(DDL_ASSUNTO_MATERIA).append(SEMICOLON_SEP).toString();

    private static final String SQL_DELETE_ENTRIES = DDL_CREATER.delete(0, DDL_CREATER.length())
            .append(DROP_TABLE).append(TopicSubjectContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(TopicContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(SubjectContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(QuestionCompetencesContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(CompetencesContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(SkillsContract.TABLE_NAME).append(SEMICOLON_SEP)
            .toString();

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

    public AsyncSubjectRepository(Context context, String name, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddlAssuntoMateria = "CREATE TABLE IF NOT EXISTS TB_ASSUNTO_MATERIA (" +
                "  TB_ASSUNTO_ID_ITEM INT NOT NULL," +
                "  TB_QUESTAO_ID_QUESTAO INT NOT NULL," +
                "  CONSTRAINT fk_TB_ASSUNTO_MATERIA_TB_ASSUNTO1" +
                "    FOREIGN KEY (TB_ASSUNTO_ID_ITEM)" +
                "    REFERENCES TB_ASSUNTO (ID_ITEM)" +
                "    ON DELETE NO ACTION" +
                "    ON UPDATE NO ACTION;";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
