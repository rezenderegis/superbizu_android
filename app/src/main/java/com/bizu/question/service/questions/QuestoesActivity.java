package com.bizu.question.service.questions;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bizu.network.GsonRequest;
import com.bizu.question.Question;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabricio on 1/6/16.
 */
public class QuestoesActivity extends ListActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url="http://mysale2.hospedagemdesites.ws/mysale/app/teste.php";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest =
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        this,
                        this);

        queue.add(jsObjRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        List<Question> questoes = new ArrayList<Question>();

        try {
            JSONArray questoesDoServidor = response.getJSONArray("produtos");

            for (int i=0; i<questoesDoServidor.length(); i++) {
                JSONObject item = questoesDoServidor.getJSONObject(i);
                String name = item.getString("nomeproduto");

                Question questao = new Question();
                questao.setName(name);
                questoes.add(questao);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        setListAdapter(new QuestoesAdapter(this, questoes));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Erro!",
                Toast.LENGTH_SHORT).show();
    }
}
