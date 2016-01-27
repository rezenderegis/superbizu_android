package com.bizu.question.service.TopicQuestion;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.bizu.android.database.AsyncResult;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.android.database.SaveListener;
import com.bizu.entity.Topic;
import com.bizu.entity.TopicQuestion;

import java.util.List;

/**
 * Created by almde on 16/01/2016.
 */
public class SQLiteTopicQuestionRepository implements TopicQuestionRepository {

    private final RepositoryOpenHelper mOpenHelper;

    public SQLiteTopicQuestionRepository (final RepositoryOpenHelper openHelper) {
        mOpenHelper = openHelper;
    }
    @Override
    public <T> T save(TopicQuestion topicQuestion, SaveListener<TopicQuestion> listener) {
        return null;
    }

    @Override
    public <T> T save(List<TopicQuestion> topicQuestion, SaveListener<List<TopicQuestion>> listener) {
        return (T) new AsyncTopicQuestionSave(mOpenHelper, listener).execute(topicQuestion);
    }
}

class AsyncTopicQuestionSave extends AsyncTask<List<TopicQuestion>, Integer, AsyncResult<List<TopicQuestion>>> {
    final private RepositoryOpenHelper mOpenHelper;
    final private SaveListener<List<TopicQuestion>> mSaveListListener;

    public AsyncTopicQuestionSave(final RepositoryOpenHelper openHelper, final SaveListener<List<TopicQuestion>> saveListListener) {
        mOpenHelper = openHelper;
        mSaveListListener = saveListListener;
    }
    @Override
    protected AsyncResult<List<TopicQuestion>> doInBackground(List<TopicQuestion>... questionTopics) {
        AsyncResult<List<TopicQuestion>> result;
        try {
            mOpenHelper.getWritableDatabase().beginTransaction();

            /*
            * Fabricio:  Esse método atualiza a tabela TopicQuestion com base na chave idQuestao.
             * A questão pode possuir mais de um assunto, mas atualmente só gravamos um assunto por questão, inclusive
             * defini uma chave primária na tabela do servidor. Quando quisermos colocar mais de um assunto nessa tabela
             * teremos que remover a chave.
            *
            * */
            for (TopicQuestion topicQuestion : questionTopics[0]) {
                final ContentValues contentValues = new ContentValues();
                contentValues.put(RepositoryOpenHelper.TopicQuestionContract.ID_ASSUNTO, topicQuestion.getIdAssunto());
                contentValues.put(RepositoryOpenHelper.TopicQuestionContract.ID_QUESTAO, topicQuestion.getIdQuestao());

                if (verifyTopicQuestion(topicQuestion.getIdQuestao()) == false) {
                    mOpenHelper.getWritableDatabase().insert(RepositoryOpenHelper.TopicQuestionContract.TABLE_NAME, null
                            , contentValues);
                } else {
                    String [] topicQuestionForUpdate = new String[] {topicQuestion.getIdAssunto().toString()};
                    mOpenHelper.getWritableDatabase().update(RepositoryOpenHelper.TopicQuestionContract.TABLE_NAME, contentValues
                            , RepositoryOpenHelper.TopicQuestionContract.ID_QUESTAO + "=?", topicQuestionForUpdate);
                }

            }
            mOpenHelper.getWritableDatabase().setTransactionSuccessful();
            result = new AsyncResult<List<TopicQuestion>>(questionTopics[0]);
        } catch (RuntimeException e) {
            result = new AsyncResult<List<TopicQuestion>>(e);
        } finally {
            mOpenHelper.getWritableDatabase().endTransaction();
            mOpenHelper.close();
        }
        return result;
    }

    public boolean verifyTopicQuestion(Integer idQuestion) {
        Cursor c = null;
        try {
            String sql = "SELECT ID_QUESTAO FROM TB_ASSUNTO_QUESTAO WHERE ID_QUESTAO = "+idQuestion;
            SQLiteDatabase sqLiteDatabase = mOpenHelper.getWritableDatabase();
            c = sqLiteDatabase.rawQuery(sql, null);
            if (c.moveToLast()) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (c != null) c.close();
        }
    }

    @Override
    protected void onPostExecute(final AsyncResult<List<TopicQuestion>> result) {
        mSaveListListener.onSaveFinish(result.getResult(), result.getError());
    }

}