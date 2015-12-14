package com.bizu.question;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.BaseColumns;

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
        db.execSQL(SQL_CREATE_ENTRIES);
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
