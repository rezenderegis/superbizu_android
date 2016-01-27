package com.bizu.question.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bizu.R;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.android.database.RetrieveListener;
import com.bizu.question.item.Item;
import com.bizu.question.item.SQLiteItemRepository;

import java.util.List;

/**
 * Created by fabricio on 1/14/16.
 */
public class ItensSimpleShowActivity extends Activity {

    public final static String TAG = ItensSimpleShowActivity.class.getSimpleName();

    int layout;
    ListView lista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_itens_symple);

        layout = android.R.layout.simple_list_item_1;
        lista = (ListView) findViewById(R.id.lista_item);

        registerForContextMenu(lista);

    }

    public void showList() {
        final SQLiteItemRepository repository =
                new SQLiteItemRepository(RepositoryOpenHelper.getInstance(getApplicationContext()));
        final Intent intent = getIntent();
        final Long idQuestionSelected = (Long) intent.getSerializableExtra("questionSelected");

        repository.retrieveAllItems(new RetrieveListener<List<Item>>() {
            @Override
            public void onRetrieve(List<Item> entity, Throwable error) {
                if (error == null && entity != null) {
                    ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(
                            ItensSimpleShowActivity.this, layout, entity);

                    adapter.notifyDataSetChanged();
                    lista.setAdapter(adapter);
                } else if (error != null) {
                    Log.e(TAG, "Erro ao recuperar todos os itens", error);
                } else {
                    throw new RuntimeException("What the fock!");
                }
            }
        }, idQuestionSelected);

    }

    @Override
    protected void onResume() {
        super.onResume();

        showList();
    }
}
