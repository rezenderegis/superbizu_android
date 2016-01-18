package com.bizu.question.service.matter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.bizu.android.database.AsyncResult;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.android.database.RetrieveListener;
import com.bizu.android.database.SaveListener;
import com.bizu.entity.Matter;
import com.bizu.entity.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by almde on 16/01/2016.
 */
public class SQLiteMatterRepository implements MatterRepository {
    private final RepositoryOpenHelper mOpenHelper;

    public SQLiteMatterRepository(final RepositoryOpenHelper openHelper) {
        mOpenHelper = openHelper;
    }
    @Override
    public <T> T save(Matter matter, SaveListener<Matter> listener) {
        return null;
    }

    @Override
    public <T> T save(List<Matter> matters, SaveListener<List<Matter>> listener) {
        return (T) new AsyncMatterSave(mOpenHelper, listener).execute(matters);
    }
}

class AsyncMatterRetrieve
        extends AsyncTask<Void, Integer, AsyncResult<List<Matter>>> {
    final private RepositoryOpenHelper mOpenHelper;
    final private RetrieveListener<List<Matter>> mAllMatterListener;

    public AsyncMatterRetrieve(final RepositoryOpenHelper openHelper
            , final RetrieveListener<List<Matter>> listener) {
        mOpenHelper = openHelper;
        mAllMatterListener = listener;
    }

    @Override
    protected AsyncResult<List<Matter>> doInBackground(Void... params) {
        Cursor cursor = null;
        try {
            String sql = "SELECT NOME_MATERIA FROM TB_MATERIA";
            SQLiteDatabase sqLiteDatabase = mOpenHelper.getWritableDatabase();
            cursor = sqLiteDatabase.rawQuery(sql, null);
            ArrayList<Matter> matters = new ArrayList<Matter>();
            while (cursor.moveToNext()) {
                Matter matter = new Matter();
                matter.setNomeMateria(cursor.getString(0));
            }
            return new AsyncResult<List<Matter>>(matters);
        } catch (RuntimeException e) {
            return new AsyncResult<List<Matter>>(e);
        } finally {
            if (cursor != null) cursor.close();
            mOpenHelper.close();
        }
    }

    @Override
    protected void onPostExecute(AsyncResult<List<Matter>> listAsyncResult) {
        mAllMatterListener.onRetrieve(listAsyncResult.getResult(), listAsyncResult.getError());
    }
}

class AsyncMatterSave extends AsyncTask<List<Matter>, Integer, AsyncResult<List<Matter>>> {
    final private RepositoryOpenHelper mOpenHelper;
    final private SaveListener<List<Matter>> mSaveListListener;

    public AsyncMatterSave(final RepositoryOpenHelper openHelper, final SaveListener<List<Matter>> saveListListener) {
        mOpenHelper = openHelper;
        mSaveListListener = saveListListener;
    }
    @Override
    protected AsyncResult<List<Matter>> doInBackground(List<Matter>... matters) {
        AsyncResult<List<Matter>> result;
        try {
            mOpenHelper.getWritableDatabase().beginTransaction();
            for (Matter matter : matters[0]) {
                final ContentValues contentValues = new ContentValues();
                contentValues.put(RepositoryOpenHelper.SubjectContract.NOME_MATERIA, matter.getNomeMateria());
                contentValues.put(RepositoryOpenHelper.SubjectContract.ID_MATERIA, matter.getIdMateria());

                if (verifyMatterExist(matter.getIdMateria()) == false) {
                    mOpenHelper.getWritableDatabase().insert(RepositoryOpenHelper.SubjectContract.TABLE_NAME, null, contentValues);
                } else {
                    String [] matterToUpdate = new String[] {matter.getIdMateria().toString()};
                    mOpenHelper.getWritableDatabase().update(RepositoryOpenHelper.SubjectContract.TABLE_NAME
                            ,contentValues, RepositoryOpenHelper.SubjectContract.ID_MATERIA+"=?", matterToUpdate);
                }

            }
            mOpenHelper.getWritableDatabase().setTransactionSuccessful();
            result = new AsyncResult<List<Matter>>(matters[0]);
        } catch (RuntimeException e) {
            result = new AsyncResult<List<Matter>>(e);
        } finally {
            mOpenHelper.getWritableDatabase().endTransaction();
            mOpenHelper.close();
        }
        return result;
    }

    @Override
    protected void onPostExecute(final AsyncResult<List<Matter>> result) {
        mSaveListListener.onSaveFinish(result.getResult(), result.getError());
    }

    public boolean verifyMatterExist(Integer idMatter) {
        Cursor c = null;
        try {
            String sql = "SELECT ID_MATERIA FROM TB_MATERIA WHERE ID_MATERIA = "+idMatter;
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
}