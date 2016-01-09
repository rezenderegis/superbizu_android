package com.bizu.question.service.download;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bizu.R;
import com.bizu.question.controller.FullscreenActivity;
import com.bizu.question.service.questions.QuestoesActivity;


public class MainActivity extends Activity {

    private Button questoes;
    private Button telaQuestoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_inicial);


        questoes = (Button) findViewById(R.id.questoes);
        telaQuestoes = (Button) findViewById(R.id.tela_questoes);

        questoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent irParaQuestoes = new Intent(MainActivity.this, QuestoesActivity.class);
                startActivity(irParaQuestoes);
            }
        });

        telaQuestoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParaTelaQuestoes = new Intent(MainActivity.this, FullscreenActivity.class);
                startActivity(irParaTelaQuestoes);
            }
        });

    }
}