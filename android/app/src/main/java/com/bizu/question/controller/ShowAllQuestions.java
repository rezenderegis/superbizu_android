package com.bizu.question.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bizu.R;
import com.bizu.entity.Question;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.android.database.RetrieveListener;
import com.bizu.question.SQLiteQuestionRepository;
import com.bizu.question.item.Item;
import com.bizu.question.item.SQLiteItemRepository;
import com.bizu.question.service.questions.QuestoesAdapter;

import java.util.List;

/**
 * Created by fabricio on 1/10/16.
 */
    public class ShowAllQuestions extends Activity {

        public final static String TAG = ShowAllQuestions.class.getSimpleName();

        private ListView mLista;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.lista);
            mLista = (ListView) findViewById(R.id.lista);
            registerForContextMenu(mLista);
            final SQLiteQuestionRepository repositoryOpenHelper =
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
                        final SQLiteItemRepository repository =
                                new SQLiteItemRepository(RepositoryOpenHelper.getInstance(getApplicationContext()));
                        repository.retrieveAllItems(new RetrieveListener<List<Item>>() {
                            @Override
                            public void onRetrieve(List<Item> entity, Throwable error) {
                                if (error == null && entity != null) {
                                    final Intent questionFullScreen =
                                            new Intent(ShowAllQuestions.this, SwipeQuestionResolutionActivity.class);
                                    questionSelected.setItems(entity);
                                    questionFullScreen.putExtra(FullscreenActivity.PARAMETER_QUESTION, questionSelected);
                                    startActivity(questionFullScreen);
                                } else if (error != null) {
                                    Log.e(TAG, "Erro ao recuperar todos os itens", error);
                                } else {
                                    throw new RuntimeException("What the fock!");
                                }
                            }
                        }, questionSelected.getId());
                    }
                });
            registerForContextMenu(mLista);
            }
        });
    }
}
