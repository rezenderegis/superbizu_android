package com.bizu.question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.BaseColumns;

import com.bizu.entity.Item;
import com.bizu.entity.Question;
import com.bizu.entity.Matter;
import com.bizu.entity.Topic;
import com.bizu.entity.TopicQuestion;
import com.bizu.question.service.TopicQuestion.TopicQuestionRepository;
import com.bizu.question.service.item.ItemRepository;
import com.bizu.question.service.matter.MatterRepository;
import com.bizu.question.service.topic.TopicRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andre.lmello on 12/9/15.
 */

public class RepositoryOpenHelper extends SQLiteOpenHelper implements QuestionRepository, ItemRepository, MatterRepository, TopicQuestionRepository, TopicRepository {

    public final static String DATABASE_NAME = "Question.db";
    public final static int DATABASE_VERSION = 22;
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

    private static final String SQL_CREATE_ENTRIES = DDL_CREATER.delete(0, DDL_CREATER.length())
            .append(DDL_QUESTION).append(SEMICOLON_SEP)
            .append(DD_LOG_PROCESSAMENTO).append(SEMICOLON_SEP)
            .append(DDL_ITEM).append(SEMICOLON_SEP).toString();
    /**
     * Subject
     */
    // .append(DDL_COMPETENCIAS).append(SEMICOLON_SEP)
    // .append(DDL_HABILIDADES).append(SEMICOLON_SEP)
    //  .append(DDL_QUESTAO_HABILIDADES).append(SEMICOLON_SEP)
    //  .append(DDL_MATERIA).append(SEMICOLON_SEP)
    //   .append(DDL_ASSUNTO).append(SEMICOLON_SEP)
    //   .append(DDL_ASSUNTO_MATERIA).append(SEMICOLON_SEP).toString();

    private static final String SQL_DELETE_ENTRIES = DDL_CREATER.delete(0, DDL_CREATER.length())
            .append(DROP_TABLE).append(QuestionContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(ItemContract.TABLE_NAME).append(SEMICOLON_SEP)
                    /** Subject */
            .append(DROP_TABLE).append(TopicQuestionContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(TopicContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(SubjectContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(QuestionCompetencesContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(CompetencesContract.TABLE_NAME).append(SEMICOLON_SEP)
            .append(DROP_TABLE).append(SkillsContract.TABLE_NAME).append(SEMICOLON_SEP)
            .toString();

    @Override
    public <T> T save(Item item, SaveListener listener) {
        return null;
    }

    @Override
    public <T> T save(Matter matter, SaveListener listener) {
        return null;
    }

    @Override
    public <T> T save(TopicQuestion topicQuestion, SaveListener listener) {
        return null;
    }

    @Override
    public <T> T save(Topic topic, SaveListener listener) {
        return null;
    }


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
        //db.execSQL(SQL_CREATE_ENTRIES);
        /*db.execSQL(DDL_QUESTION);
        db.execSQL(ddlItem);
        db.execSQL(ddlCompetencias);
        db.execSQL(ddlHabilidades);
        db.execSQL(ddlAssunto);
        db.execSQL(ddlMateria);
        db.execSQL(ddlAssuntoMateria);*/
        db.execSQL(DDL_QUESTION);
        db.execSQL(DDL_ITEM);
        db.execSQL(DD_LOG_PROCESSAMENTO);
        db.execSQL(DDL_MATERIA);
        db.execSQL(DDL_ASSUNTO_QUESTAO);
        db.execSQL(DDL_ASSUNTO);



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

    public void saveMatter(List<Matter> matters) {

        ContentValues contentValues = new ContentValues();

        try {
            for (Matter matter : matters) {
                contentValues.put(SubjectContract.NOME_MATERIA, matter.getNomeMateria());
                contentValues.put(SubjectContract.ID_MATERIA, matter.getIdMateria());

                if (verifyMatterExist(matter.getIdMateria()) == false) {
                    getWritableDatabase().insert(SubjectContract.TABLE_NAME, null, contentValues);
                } else {
                    String [] matterToUpdate = new String[] {matter.getIdMateria().toString()};
                    getWritableDatabase().update(SubjectContract.TABLE_NAME,contentValues, SubjectContract.ID_MATERIA+"=?", matterToUpdate);
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveItem(List<Item> itens) {

        ContentValues contentValues = new ContentValues();

        Integer qtdInserted = 0;
        Integer qtdUptadate = 0;
        Date dateStartProcess = null;
        Date dateFinishProcess = null;


        try {
            dateStartProcess = new Date();

            for (Item item : itens) {
                contentValues.put(ItemContract.ID_ITEM, item.getIdItem());
                contentValues.put(ItemContract.ID_QUESTAO, item.getIdQuestao());
                contentValues.put(ItemContract.DESCRICAO, item.getDescricao());
                contentValues.put(ItemContract.NOME_IMAGEM_ITEM, item.getNomeImagemItem());
                contentValues.put(ItemContract.NOME_IMAGEM_ITEM_SISTEMA, item.getNomeImagemItemSistema());
                contentValues.put(ItemContract.SITUACAO_ITEM, item.getSituacaoItem());
                contentValues.put(ItemContract.LETRA_ITEM, item.getLetraItem());
                if (verifyItemExist(item.getIdItem()) == false) {
                    getWritableDatabase().insert(ItemContract.TABLE_NAME, null, contentValues);
                    qtdInserted++;
                } else {
                    String[] itemToUpdate = new String[]{item.getIdItem().toString()};
                    getWritableDatabase().update(ItemContract.TABLE_NAME, contentValues, ItemContract.ID_ITEM + "=?", itemToUpdate);
                    qtdUptadate++;
                }
                dateFinishProcess = new Date();

            }
            saveLogProcessInsertUpdate(qtdInserted, qtdUptadate, dateStartProcess, dateFinishProcess, "I");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean verifyItemExist(Integer idItem) {

        String sql = "SELECT ID_ITEM FROM TB_ITEM WHERE ID_ITEM = " + idItem;

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(sql, null);

        if (c.moveToLast()) {
            return true;
        } else {
            return false;
        }
    }



    /*
    * Fabricio:  Esse método atualiza a tabela TopicQuestion com base na chave idQuestao.
     * A questão pode possuir mais de um assunto, mas atualmente só gravamos um assunto por questão, inclusive
     * defini uma chave primária na tabela do servidor. Quando quisermos colocar mais de um assunto nessa tabela
     * teremos que remover a chave.
    *
    * */
    public void saveTopicQuestion(List<TopicQuestion> topicQuestions) {

        ContentValues contentValues = new ContentValues();

        try {

            for (TopicQuestion topicQuestion : topicQuestions) {

                contentValues.put(TopicQuestionContract.ID_ASSUNTO, topicQuestion.getIdAssunto());
                contentValues.put(TopicQuestionContract.ID_QUESTAO, topicQuestion.getIdQuestao());

                if (verifyTopicQuestion(topicQuestion.getIdQuestao()) == false) {
                    getWritableDatabase().insert(TopicQuestionContract.TABLE_NAME, null, contentValues);
                } else {
                    String [] topicQuestionForUpdate = new String[] {topicQuestion.getIdAssunto().toString()};
                    getWritableDatabase().update(TopicQuestionContract.TABLE_NAME, contentValues, TopicQuestionContract.ID_QUESTAO+"=?", topicQuestionForUpdate);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void saveTopic(List<Topic> topics) {

        ContentValues contentValues = new ContentValues();

        try{

            for (Topic topic : topics) {

                contentValues.put(TopicContract.ID_ASSUNTO, topic.getIdAssunto());
                contentValues.put(TopicContract.DESCRICAO_ASSUNTO, topic.getDescricaoAssunto());
                contentValues.put(TopicContract.ID_ASSUNTO_PAI, topic.getIdAssuntoPai());
                contentValues.put(TopicContract.ID_MATERIA, topic.getIdMateria());
                SQLiteDatabase sqLiteDatabase = getWritableDatabase();

                if (verifyTopic(topic.getIdAssunto()) == false) {

                    sqLiteDatabase.insert(TopicContract.TABLE_NAME, null, contentValues);

                } else {
                    String [] topicUpdate = new String [] {topic.getIdAssunto().toString()};
                    sqLiteDatabase.update(TopicContract.TABLE_NAME,contentValues,TopicQuestionContract.ID_QUESTAO + "=?", topicUpdate);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveQuestion(List<Question> questions) {

        ContentValues contentValues = new ContentValues();
        Integer qtdQuestionsInserted = 0;
        Integer qtdQuestionUptadate = 0;
        Date dateStartProcess = null;
        Date dateFinishProcess = null;

        try {
            dateStartProcess = new Date();

            for (Question question : questions) {

                contentValues.put(QuestionContract.ID_QUESTAO, question.getId());
                contentValues.put(QuestionContract.DESCRICAO_QUESTAO, question.getDescription());
                contentValues.put(QuestionContract.ANO_QUESTAO, question.getQuestionYear());
                contentValues.put(QuestionContract.NUMERO_QUESTAO, question.getQuestionNumber());
                contentValues.put(QuestionContract.COMANDO_QUESTAO, question.getComando_questao());
                contentValues.put(QuestionContract.PROVA, question.getProva());
                contentValues.put(QuestionContract.SITUACAO_QUESTAO, question.getSituacao_questao());
                contentValues.put(QuestionContract.IMAGEM_QUESTAO, question.getImagem_questao());
                contentValues.put(QuestionContract.COMENTARIO_QUESTAO, question.getComentario_questao());
                contentValues.put(QuestionContract.DIA_PROVA, question.getDia_prova());
                contentValues.put(QuestionContract.APLICACAO, question.getAplicacao());

                if (verifyQuestionExist(question.getId()) == false) {

                    getWritableDatabase().insert(QuestionContract.TABLE_NAME, null, contentValues);
                    qtdQuestionsInserted++;
                } else {

                    String[] questionToUpdate = new String[]{question.getId().toString()};
                    getWritableDatabase().update(QuestionContract.TABLE_NAME, contentValues, QuestionContract.ID_QUESTAO + "=?", questionToUpdate);
                    qtdQuestionUptadate++;
                }
                dateFinishProcess = new Date();
            }

            saveLogProcessInsertUpdate(qtdQuestionsInserted, qtdQuestionUptadate, dateStartProcess, dateFinishProcess, "Q");
            //System.out.println("QTD QUESTOES INSERIDAS: " + qtdQuestionsInserted);
            //System.out.println("QTD QUESTION ATUALIZADAS: "+qtdQuestionUptadate);
            //System.out.println("Data inicio processamento "+dateStartProcess.toString());
            //System.out.println("Data fim processamento: "+dateFinishProcess.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveLogProcessInsertUpdate(Integer qtdInserted, Integer qtdUptadate, Date dateStartProcess, Date dateFinishProcess, String typeTable) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(LogProcessContract.QTD_INSERIDOS, qtdInserted);
        contentValues.put(LogProcessContract.QTD_ATUALIZADOS, qtdUptadate);
        contentValues.put(LogProcessContract.DATA_INICIO_PROCESAMENTO, dateStartProcess.toString());
        contentValues.put(LogProcessContract.DATA_FIM_PROCESSAMENTO, dateFinishProcess.toString());
        contentValues.put(LogProcessContract.TIPO_TABELA_ATUALIZADA, typeTable);

        sqLiteDatabase.insert(LogProcessContract.TABLE_NAME, null, contentValues);

    }

    public boolean verifyTopic (Integer idTopic) {

        String sql = "SELECT ID_ASSUNTO FROM TB_ASSUNTO WHERE ID_ASSUNTO = "+idTopic;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(sql, null);

        if (c.moveToLast()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean verifyTopicQuestion(Integer idQuestion) {

        String sql = "SELECT ID_QUESTAO FROM TB_ASSUNTO_QUESTAO WHERE ID_QUESTAO = "+idQuestion;

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(sql, null);

        if (c.moveToLast()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean verifyMatterExist(Integer idMatter) {

        String sql = "SELECT ID_MATERIA FROM TB_MATERIA WHERE ID_MATERIA = "+idMatter;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(sql, null);
        if (c.moveToLast()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean verifyQuestionExist(Long idQuestion) {

        String sql = "SELECT ID_QUESTAO FROM TB_QUESTAO WHERE ID_QUESTAO = " + idQuestion;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(sql, null);

        if (c.moveToLast()) {

            return true;

        } else {

            return false;
        }
    }

    public List<Question> getAllQuestions() {

        String sql = "SELECT ID_QUESTAO, COMANDO_QUESTAO FROM TB_QUESTAO";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        ArrayList<Question> questions = new ArrayList<Question>();

        while (cursor.moveToNext()) {

            Question question = new Question();
            question.setId(cursor.getLong(0));
            question.setDescription(cursor.getString(1));
            questions.add(question);
        }
        return questions;
    }

    public List<Item> getAllItens(Long idQuestionSelected) {

        String sql = "SELECT DESCRICAO FROM TB_ITEM WHERE ID_QUESTAO = "+idQuestionSelected;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        ArrayList<Item> itens = new ArrayList<Item>();

        while (cursor.moveToNext()) {

            Item item = new Item();
            item.setDescricao(cursor.getString(0));
            itens.add(item);
        }
        return itens;

    }


    public List<Matter> getAllMatters() {
        String sql = "SELECT NOME_MATERIA FROM TB_MATERIA";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(sql, null);
        ArrayList<Matter> matters = new ArrayList<Matter>();
        while (c.moveToNext()) {
            Matter matter = new Matter();
            matter.setNomeMateria(c.getString(0));
        }
        return matters;
    }
    /*
    * Método utilizando enquanto estivermos fazendo testes no app
    *
    * */

    public void clearDatabaseQuestionItem() {

        String deleteQuestion = "DELETE FROM TB_QUESTAO";

        String deleteItem = "DELETE FROM TB_ITEM";
        String deleteAssuntoQuestao = "DELETE FROM TB_ASSUNTO_QUESTAO";
        String deleteMateria = "DELETE FROM TB_MATERIA";


        //db.execSQL(DD_LOG_PROCESSAMENTO);
        // String deleteSequenceQuestion = "DELETE FROM SQLITE_SEQUENCE WHERE NAME  = 'TB_QUESTAO'";
       /// String deleteSequenceiTEM = "DELETE FROM SQLITE_SEQUENCE WHERE NAME  = 'TB_ITEM'";

        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL(deleteQuestion);
        db.execSQL(deleteItem);
        db.execSQL(deleteAssuntoQuestao);
        db.execSQL(deleteMateria);


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
