package com.bizu.question.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.bizu.R;
import com.bizu.question.Question;
import com.bizu.question.RepositoryOpenHelper;
import com.bizu.question.service.questions.QuestoesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabricio on 1/10/16.
 */
    public class ShowAllQuestions extends Activity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lista);

        lista = (ListView) findViewById(R.id.lista);



        RepositoryOpenHelper repositoryOpenHelper = new RepositoryOpenHelper(ShowAllQuestions.this);
        List<Question> questoes = repositoryOpenHelper.getAllQuestions();
        QuestoesAdapter questoesAdapter = new QuestoesAdapter(ShowAllQuestions.this,questoes);

        lista.setAdapter(questoesAdapter);
        registerForContextMenu(lista);
    }
}
