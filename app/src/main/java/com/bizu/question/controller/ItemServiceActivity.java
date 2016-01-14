package com.bizu.question.controller;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bizu.network.UpdateListener;
import com.bizu.question.Item;
import com.bizu.question.Question;
import com.bizu.question.RepositoryOpenHelper;
import com.bizu.question.service.PHPQuestionService;
import com.bizu.question.service.download.MainActivity;
import com.bizu.question.service.item.ItemRepository;
import com.bizu.question.service.item.ItemService;
import com.bizu.question.service.item.PHPItemService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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

        service.updateFromServer(new RepositoryOpenHelper(this), new UpdateListener<List<Item>>() {
            @Override
            public void onResponse(List<Item> response, Throwable error) {
                final RepositoryOpenHelper repository = new RepositoryOpenHelper(ItemServiceActivity.this);
                repository.saveItem(response);
                repository.close();
                     //setListAdapter(new QuestoesAdapter(QuestoesActivity.this, response));
                    //Será executado após a sincronização das questões

                    Intent recarregarTelaInicial = new Intent(ItemServiceActivity.this, MainActivity.class);
                    startActivity(recarregarTelaInicial);
                    Toast.makeText(ItemServiceActivity.this, "Questões atualizadas com sucesso!",Toast.LENGTH_SHORT).show();



            }
        });

//        JsonObjectRequest jsObjRequest =
//                new JsonObjectRequest(
//                        Request.Method.GET,
//                        url,
//                        null,
//                        this,
//                        this);
//
//        queue.add(jsObjRequest);
    }

}
