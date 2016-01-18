package com.bizu.question;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.bizu.android.database.AsyncResult;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.android.database.RetrieveListener;
import com.bizu.android.database.SaveListener;
import com.bizu.entity.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by almde on 12/01/2016.
 */
public class SQLiteQuestionRepository implements QuestionRepository {
    private final RepositoryOpenHelper mOpenHelper;

    public SQLiteQuestionRepository(final RepositoryOpenHelper openHelper) {
        mOpenHelper = openHelper;
    }

    @Override
    public <T> T save(final Question question, final SaveListener<Question> listener) {
        return null;
    }

    @Override
    public <T> T save(final List<Question> questions, final SaveListener<List<Question>> listener) {
        return (T) new AsyncQuestionSave(mOpenHelper, listener).execute(questions);
    }

    @Override
    public <T> T retrieveAllQuestion(RetrieveListener<List<Question>> listener) {
        return (T) new AsyncQuestionRetrieve(mOpenHelper, listener).execute();
    }
}

class AsyncQuestionRetrieve
        extends AsyncTask<Void, Integer, AsyncResult<List<Question>>> {
    final private RepositoryOpenHelper mOpenHelper;
    final private RetrieveListener<List<Question>> mAllQuestionListener;

    public AsyncQuestionRetrieve(final RepositoryOpenHelper openHelper
            , final RetrieveListener<List<Question>> listener) {
        mOpenHelper = openHelper;
        mAllQuestionListener = listener;
    }

    @Override
    protected AsyncResult<List<Question>> doInBackground(Void... params) {
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

            return new AsyncResult<List<Question>>(questions);
        } catch (RuntimeException e) {
            return new AsyncResult<List<Question>>(e);
        } finally {
            if (cursor != null) cursor.close();
            mOpenHelper.close();
        }
    }

    @Override
    protected void onPostExecute(AsyncResult<List<Question>> listAsyncResult) {
        mAllQuestionListener.onRetrieve(listAsyncResult.getResult(), listAsyncResult.getError());
    }
}

class AsyncQuestionSave extends AsyncTask<List<Question>, Integer, AsyncResult<List<Question>>> {
    final private RepositoryOpenHelper mOpenHelper;
    final private SaveListener<List<Question>> mSaveListListener;

    public AsyncQuestionSave(final RepositoryOpenHelper openHelper, final SaveListener<List<Question>> saveListListener) {
        mOpenHelper = openHelper;
        mSaveListListener = saveListListener;
    }
    @Override
    protected AsyncResult<List<Question>> doInBackground(List<Question>... questions) {
        AsyncResult<List<Question>> result;
        try {
            Integer qtdQuestionsInserted = 0;
            Integer qtdQuestionUptadate = 0;
            Date dateStartProcess = new Date();
            Date dateFinishProcess = null;
            mOpenHelper.getWritableDatabase().beginTransaction();
            for (final Question question : questions[0]) {
                final ContentValues contentValues = new ContentValues();
                contentValues.put(RepositoryOpenHelper.QuestionContract.ID_QUESTAO, question.getId());
                contentValues.put(RepositoryOpenHelper.QuestionContract.DESCRICAO_QUESTAO, question.getDescription());
                contentValues.put(RepositoryOpenHelper.QuestionContract.ANO_QUESTAO, question.getQuestionYear());
                contentValues.put(RepositoryOpenHelper.QuestionContract.NUMERO_QUESTAO, question.getQuestionNumber());
                contentValues.put(RepositoryOpenHelper.QuestionContract.COMANDO_QUESTAO, question.getComandoQuestao());
                contentValues.put(RepositoryOpenHelper.QuestionContract.PROVA, question.getProva());
                contentValues.put(RepositoryOpenHelper.QuestionContract.SITUACAO_QUESTAO, question.getSituacao_questao());
                contentValues.put(RepositoryOpenHelper.QuestionContract.IMAGEM_QUESTAO, question.getImagem_questao());
                contentValues.put(RepositoryOpenHelper.QuestionContract.COMENTARIO_QUESTAO, question.getComentario_questao());
                contentValues.put(RepositoryOpenHelper.QuestionContract.DIA_PROVA, question.getDia_prova());
                contentValues.put(RepositoryOpenHelper.QuestionContract.APLICACAO, question.getAplicacao());

                if (verifyQuestionExist(question.getId()) == false) {
                    question.setId(mOpenHelper.getWritableDatabase().insert(RepositoryOpenHelper.QuestionContract.TABLE_NAME
                            , null, contentValues));
                    qtdQuestionsInserted++;
                } else {
                    String [] questionToUpdate = new String[]{question.getId().toString()};
                    mOpenHelper.getWritableDatabase().update(RepositoryOpenHelper.QuestionContract.TABLE_NAME
                            , contentValues, RepositoryOpenHelper.QuestionContract.ID_QUESTAO+"=?", questionToUpdate);
                    qtdQuestionUptadate++;
                }
            }
            mOpenHelper.getWritableDatabase().setTransactionSuccessful();
            result = new AsyncResult<List<Question>>(questions[0]);
            dateFinishProcess = new Date();
            mOpenHelper.saveLogProcessInsertUpdate(qtdQuestionsInserted, qtdQuestionUptadate, dateStartProcess
                    , dateFinishProcess, "Q");
        } catch (RuntimeException e) {
            result = new AsyncResult<List<Question>>(e);
        } finally {
            mOpenHelper.getWritableDatabase().endTransaction();
            mOpenHelper.close();
        }
        return result;
    }

    @Override
    protected void onPostExecute(final AsyncResult<List<Question>> result) {
        mSaveListListener.onSaveFinish(result.getResult(), result.getError());
    }

    public boolean verifyQuestionExist(Long idQuestion) {

        Cursor c = null;

        try {
            String sql = "SELECT ID_QUESTAO FROM TB_QUESTAO WHERE ID_QUESTAO = "+idQuestion;
            SQLiteDatabase sqLiteDatabase = mOpenHelper.getReadableDatabase();
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
}
