package com.bizu.question.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bizu.R;
import com.bizu.question.Item;
import com.bizu.question.RepositoryOpenHelper;

import java.util.List;

/**
 * Created by fabricio on 1/14/16.
 */
public class ItensSimpleShowActivity extends Activity {
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

        RepositoryOpenHelper repositoryOpenHelper = new RepositoryOpenHelper(ItensSimpleShowActivity.this);
        Intent intent = getIntent();

        final Long idQuestionSelected = (Long) intent.getSerializableExtra("questionSelected");

        List<Item> itens = repositoryOpenHelper.getAllItens(idQuestionSelected);
        repositoryOpenHelper.close();

        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(
                this, layout, itens);

        lista.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        showList();
    }
}
