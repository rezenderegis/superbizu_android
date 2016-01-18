package com.bizu.question.service.topic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.bizu.android.database.AsyncResult;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.android.database.RetrieveListener;
import com.bizu.android.database.SaveListener;
import com.bizu.entity.Question;
import com.bizu.entity.Topic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by almde on 16/01/2016.
 */
public class SQLiteTopicRepository implements TopicRepository {
    private final RepositoryOpenHelper mOpenHelper;

    public SQLiteTopicRepository (final RepositoryOpenHelper openHelper) {
        mOpenHelper = openHelper;
    }
    @Override
    public <T> T save(Topic topic, SaveListener<Topic> listener) {
        return null;
    }

    @Override
    public <T> T save(List<Topic> topics, SaveListener<List<Topic>> listener) {
        return (T) new AsyncTopicSave(mOpenHelper, listener).execute(topics);
    }
}

/*class AsyncTopicRetrieve
        extends AsyncTask<Void, Integer, AsyncResult<List<Topic>>> {
    final private RepositoryOpenHelper mOpenHelper;
    final private RetrieveListener<List<Topic>> mAllTopicListener;

    public AsyncTopicRetrieve(final RepositoryOpenHelper openHelper
            , final RetrieveListener<List<Topic>> listener) {
        mOpenHelper = openHelper;
        mAllTopicListener = listener;
    }

    @Override
    protected AsyncResult<List<Topic>> doInBackground(Void... params) {
        Cursor cursor = null;
        try {
            String sql = "SELECT ID_QUESTAO, COMANDO_QUESTAO FROM TB_QUESTAO";
            SQLiteDatabase sqLiteDatabase = mOpenHelper.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery(sql, null);
            ArrayList<Question> questions = new ArrayList<Question>();

            while (cursor.moveToNext()) {

                Question question = new Question();
                question.setId(cursor.getLong(0));
                question.setDescription(cursor.getString(1));
                questions.add(question);
            }

            return new AsyncResult<List<Topic>>(questions);
        } catch (RuntimeException e) {
            return new AsyncResult<List<Topic>>(e);
        } finally {
            if (cursor != null) cursor.close();
            mOpenHelper.close();
        }
    }

    @Override
    protected void onPostExecute(AsyncResult<List<Topic>> listAsyncResult) {
        mAllTopicListener.onRetrieve(listAsyncResult.getResult(), listAsyncResult.getError());
    }
}*/

class AsyncTopicSave extends AsyncTask<List<Topic>, Integer, AsyncResult<List<Topic>>> {
    final private RepositoryOpenHelper mOpenHelper;
    final private SaveListener<List<Topic>> mSaveListListener;

    public AsyncTopicSave(final RepositoryOpenHelper openHelper, final SaveListener<List<Topic>> saveListListener) {
        mOpenHelper = openHelper;
        mSaveListListener = saveListListener;
    }
    @Override
    protected AsyncResult<List<Topic>> doInBackground(List<Topic>... topics) {
        AsyncResult<List<Topic>> result;
        try {
            mOpenHelper.getWritableDatabase().beginTransaction();
            for (Topic topic : topics[0]) {
                final ContentValues contentValues = new ContentValues();
                contentValues.put(RepositoryOpenHelper.TopicContract.ID_ASSUNTO, topic.getIdAssunto());
                contentValues.put(RepositoryOpenHelper.TopicContract.DESCRICAO_ASSUNTO, topic.getDescricaoAssunto());
                contentValues.put(RepositoryOpenHelper.TopicContract.ID_ASSUNTO_PAI, topic.getIdAssuntoPai());
                contentValues.put(RepositoryOpenHelper.TopicContract.ID_MATERIA, topic.getIdMateria());
                SQLiteDatabase sqLiteDatabase = mOpenHelper.getWritableDatabase();

                if (verifyTopic(topic.getIdAssunto()) == false) {

                    sqLiteDatabase.insert(RepositoryOpenHelper.TopicContract.TABLE_NAME, null, contentValues);

                } else {
                    String [] topicUpdate = new String [] {topic.getIdAssunto().toString()};
                    sqLiteDatabase.update(RepositoryOpenHelper.TopicContract.TABLE_NAME,contentValues
                            ,RepositoryOpenHelper.TopicQuestionContract.ID_QUESTAO + "=?", topicUpdate);
                }

            }
            mOpenHelper.getWritableDatabase().setTransactionSuccessful();
            result = new AsyncResult<List<Topic>>(topics[0]);
        } catch (RuntimeException e) {
            result = new AsyncResult<List<Topic>>(e);
        } finally {
            mOpenHelper.getWritableDatabase().endTransaction();
            mOpenHelper.close();
        }
        return result;
    }

    public boolean verifyTopic (Integer idTopic) {

        String sql = "SELECT ID_ASSUNTO FROM TB_ASSUNTO WHERE ID_ASSUNTO = "+idTopic;
        SQLiteDatabase sqLiteDatabase = mOpenHelper.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(sql, null);

        if (c.moveToLast()) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void onPostExecute(final AsyncResult<List<Topic>> result) {
        mSaveListListener.onSaveFinish(result.getResult(), result.getError());
    }

}