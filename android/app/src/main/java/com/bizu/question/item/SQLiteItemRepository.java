package com.bizu.question.item;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.bizu.android.database.AsyncResult;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.android.database.RetrieveListener;
import com.bizu.android.database.SaveListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by almde on 16/01/2016.
 */
public class SQLiteItemRepository implements ItemRepository {

    private final RepositoryOpenHelper mOpenHelper;

    public SQLiteItemRepository (final RepositoryOpenHelper openHelper) {
        mOpenHelper = openHelper;
    }

    @Override
    public <T> T save(Item item, SaveListener<Item> listener) {
        return null;
    }

    @Override
    public <T> T save(List<Item> items, SaveListener<List<Item>> listener) {
        return (T) new AsyncItemSave(mOpenHelper, listener).execute(items);
    }

    @Override
    public <T> T retrieveAllItems(final RetrieveListener<List<Item>> listener, final Long question) {
        return (T) new AsyncItemRetrieve(mOpenHelper, listener).execute(question);
    }
}

class AsyncItemRetrieve
        extends AsyncTask<Long, Integer, AsyncResult<List<Item>>> {
    final private RepositoryOpenHelper mOpenHelper;
    final private RetrieveListener<List<Item>> mAllItemsListener;

    public AsyncItemRetrieve(final RepositoryOpenHelper openHelper
            , final RetrieveListener<List<Item>> listener) {
        mOpenHelper = openHelper;
        mAllItemsListener = listener;
    }

    @Override
    protected AsyncResult<List<Item>> doInBackground(Long... params) {
        Cursor cursor = null;
        try {
            final String sql = "SELECT DESCRICAO FROM TB_ITEM WHERE ID_QUESTAO = " + params[0];
            final SQLiteDatabase sqLiteDatabase = mOpenHelper.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery(sql, null);
            final ArrayList<Item> items = new ArrayList<Item>();

            while (cursor.moveToNext()) {
                final Item item = new Item();
                item.setDescricao(cursor.getString(0));
                items.add(item);
            }
            return new AsyncResult<List<Item>>(items);
        } catch (RuntimeException e) {
            return new AsyncResult<List<Item>>(e);
        } finally {
            if (cursor != null) cursor.close();
            mOpenHelper.close();
        }
    }

    @Override
    protected void onPostExecute(AsyncResult<List<Item>> listAsyncResult) {
        mAllItemsListener.onRetrieve(listAsyncResult.getResult(), listAsyncResult.getError());
    }
}

class AsyncItemSave extends AsyncTask<List<Item>, Integer, AsyncResult<List<Item>>> {
    final private RepositoryOpenHelper mOpenHelper;
    final private SaveListener<List<Item>> mSaveListListener;

    public AsyncItemSave(final RepositoryOpenHelper openHelper, final SaveListener<List<Item>> saveListListener) {
        mOpenHelper = openHelper;
        mSaveListListener = saveListListener;
    }
    @Override
    protected AsyncResult<List<Item>> doInBackground(List<Item>... items) {
        AsyncResult<List<Item>> result;
        try {
            Integer qtdInserted = 0;
            Integer qtdUptadate = 0;
            Date dateStartProcess = new Date();
            Date dateFinishProcess = null;
            mOpenHelper.getWritableDatabase().beginTransaction();
            for (final Item item : items[0]) {
                final ContentValues contentValues = new ContentValues();
                contentValues.put(RepositoryOpenHelper.ItemContract.ID_ITEM, item.getIdItem());
                contentValues.put(RepositoryOpenHelper.ItemContract.ID_QUESTAO, item.getIdQuestao());
                contentValues.put(RepositoryOpenHelper.ItemContract.DESCRICAO, item.getDescricao());
                contentValues.put(RepositoryOpenHelper.ItemContract.NOME_IMAGEM_ITEM, item.getNomeImagemItem());
                contentValues.put(RepositoryOpenHelper.ItemContract.NOME_IMAGEM_ITEM_SISTEMA, item.getNomeImagemItemSistema());
                contentValues.put(RepositoryOpenHelper.ItemContract.SITUACAO_ITEM, item.getSituacaoItem());
                contentValues.put(RepositoryOpenHelper.ItemContract.LETRA_ITEM, item.getLetraItem());

                if (verifyItemExist(item.getIdItem()) == false) {
                    mOpenHelper.getWritableDatabase()
                            .insert(RepositoryOpenHelper.ItemContract.TABLE_NAME, null, contentValues);
                    qtdInserted++;
                } else {
                    String[] itemToUpdate = new String[]{item.getIdItem().toString()};
                    mOpenHelper.getWritableDatabase().update(RepositoryOpenHelper.ItemContract.TABLE_NAME, contentValues
                            , RepositoryOpenHelper.ItemContract.ID_ITEM + "=?", itemToUpdate);
                    qtdUptadate++;
                }
            }
            mOpenHelper.getWritableDatabase().setTransactionSuccessful();
            result = new AsyncResult<List<Item>>(items[0]);
            dateFinishProcess = new Date();
            mOpenHelper.saveLogProcessInsertUpdate(qtdInserted, qtdUptadate, dateStartProcess, dateFinishProcess, "I");
        } catch (RuntimeException e) {
            result = new AsyncResult<List<Item>>(e);
        } finally {
            mOpenHelper.getWritableDatabase().endTransaction();
            mOpenHelper.close();
        }
        return result;
    }

    public boolean verifyItemExist(final Integer idItem) {
        Cursor c = null;
        try  {
            final String sql = "SELECT ID_ITEM FROM TB_ITEM WHERE ID_ITEM = " + idItem;
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
    protected void onPostExecute(final AsyncResult<List<Item>> result) {
        mSaveListListener.onSaveFinish(result.getResult(), result.getError());
    }

}
