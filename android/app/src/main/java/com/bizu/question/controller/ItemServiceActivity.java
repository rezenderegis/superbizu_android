package com.bizu.question.controller;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.android.database.SaveListener;
import com.bizu.network.ServiceListener;
import com.bizu.question.item.Item;
import com.bizu.question.item.PHPItemService;
import com.bizu.question.item.SQLiteItemRepository;
import com.bizu.question.service.download.MainActivity;

import java.util.List;

/**
 * Created by fabricio on 1/6/16.
 */
public class ItemServiceActivity extends ListActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPItemService service = new PHPItemService(queue);

        service.retrieveItemsFromServer(new ServiceListener<List<Item>>() {
            @Override
            public void onResponse(List<Item> response, Throwable error) {
                final SQLiteItemRepository repository =
                        new SQLiteItemRepository(RepositoryOpenHelper.getInstance(getApplicationContext()));
                repository.save(response, new SaveListener<List<Item>>() {
                    @Override
                    public void onSaveFinish(List<Item> saved, Throwable e) {
                        Intent recarregarTelaInicial = new Intent(ItemServiceActivity.this, MainActivity.class);
                        startActivity(recarregarTelaInicial);
                        Toast.makeText(ItemServiceActivity.this, "Questões atualizadas com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
