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
import com.bizu.question.AsyncQuestionRepository;
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

        String url="http://10.0.3.2/bizu/app/teste.php";

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
        AsyncQuestionRepository repository = new AsyncQuestionRepository(this);

        try {
            JSONArray questoesDoServidor = response.getJSONArray("questoes");
            System.out.println(questoesDoServidor.toString());
            for (int i=0; i<questoesDoServidor.length(); i++) {
                JSONObject item = questoesDoServidor.getJSONObject(i);
                String descricao_questao = item.getString("DESCRICAO_QUESTAO");
                Integer id_questao =  Integer.parseInt(item.getString("ID_QUESTAO"));
                Integer ano_questao = Integer.parseInt(item.getString("ANO_QUESTAO"));
                Integer numero_questao = Integer.parseInt(item.getString("NUMERO_QUESTAO"));
                String comando_questao = item.getString("COMANDO_QUESTAO");
                Integer prova = Integer.parseInt(item.getString("PROVA"));
                Integer situacao_questao = Integer.parseInt(item.getString("SITUACAO_QUESTAO"));
                String nome_imagem_questao = item.getString("NOME_IMAGEM_QUESTAO");
                String comentario_questao = item.getString("COMENTARIO_QUESTAO");
                String nome_imagem_questao_sistema = item.getString("NOME_IMAGEM_QUESTAO_SISTEMA");
                String letra_item_correto = item.getString("LETRA_ITEM_CORRETO");
                Integer dia_prova = Integer.parseInt(item.getString("DIA_PROVA"));
                Integer aplicacao = Integer.parseInt(item.getString("APLICACAO"));

                Question questao = new Question();
                questao.setName(descricao_questao);
                questao.setId(new Long(id_questao));
                questao.setAno_questao(ano_questao);
                questao.setNumero_questao(numero_questao);
                questao.setComando_questao(comando_questao);
                questao.setProva(prova);
                questao.setSituacao_questao(situacao_questao);
                questao.setImagem_questao(nome_imagem_questao_sistema);
                questao.setComentario_questao(comentario_questao);
                questao.setLetra_item_correto(letra_item_correto);
                questao.setDia_prova(dia_prova);
                questao.setAplicacao(aplicacao);

                questoes.add(questao);


            }
            repository.saveQuestion(questoes);

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
