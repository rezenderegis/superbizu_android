package com.bizu.question.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bizu.R;
import com.bizu.entity.Question;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.android.database.RetrieveListener;
import com.bizu.question.SQLiteQuestionRepository;
import com.bizu.question.service.questions.QuestoesAdapter;

import java.util.List;

/**
 * Created by fabricio on 1/10/16.
 */
    public class ShowAllQuestions extends Activity {

    private ListView mLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);
        mLista = (ListView) findViewById(R.id.lista);
        registerForContextMenu(mLista);
        SQLiteQuestionRepository repositoryOpenHelper =
                new SQLiteQuestionRepository(RepositoryOpenHelper.getInstance(getApplicationContext()));
        final AsyncTask asyncTask = repositoryOpenHelper.retrieveAllQuestion(new RetrieveListener<List<Question>>() {
            @Override
            public void onRetrieve(List<Question> entity, Throwable error) {
                final QuestoesAdapter questoesAdapter = new QuestoesAdapter(ShowAllQuestions.this, entity);
                mLista.setAdapter(questoesAdapter);
                mLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Question questionSelected = (Question) parent.getItemAtPosition(position);
                        final Intent goToListItens = new Intent(ShowAllQuestions.this, ItensSimpleShowActivity.class);
                        goToListItens.putExtra("questionSelected", questionSelected.getId());

                        startActivity(goToListItens);

                    }
                });
                registerForContextMenu(mLista);
            }
        });
    }
}
