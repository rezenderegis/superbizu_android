package com.bizu.question.service.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bizu.R;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.android.database.SaveListener;
import com.bizu.entity.Matter;
import com.bizu.entity.Question;
import com.bizu.entity.Topic;
import com.bizu.entity.TopicQuestion;
import com.bizu.network.ServiceListener;
import com.bizu.question.SQLiteQuestionRepository;
import com.bizu.question.controller.FullscreenActivity;
import com.bizu.question.controller.ShowAllQuestions;
import com.bizu.question.item.Item;
import com.bizu.question.item.PHPItemService;
import com.bizu.question.item.SQLiteItemRepository;
import com.bizu.question.service.PHPQuestionService;
import com.bizu.question.service.TopicQuestion.PHPTopicQuestionService;
import com.bizu.question.service.TopicQuestion.SQLiteTopicQuestionRepository;
import com.bizu.question.service.matter.PHPMatterService;
import com.bizu.question.service.matter.SQLiteMatterRepository;
import com.bizu.question.service.topic.PHPTopicService;
import com.bizu.question.service.topic.SQLiteTopicRepository;

import java.util.List;


public class MainActivity extends Activity {

    public final static String TAG = MainActivity.class.getSimpleName();

    private ImageButton questoes;
    private ImageButton telaQuestoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_inicial);



        questoes = (ImageButton) findViewById(R.id.questoes);
        telaQuestoes = (ImageButton) findViewById(R.id.tela_questoes);

        questoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent irParaQuestoes = new Intent(MainActivity.this, ShowAllQuestions.class);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.default_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selectedItem = item.getItemId();

        switch (selectedItem) {
            case R.id.menu_sync_questions:
                syncItens();
                syncQuestionFromServer();
                syncMatterFromServer();
                syncTopicQuestion();
                syncTopic();
                Toast.makeText(MainActivity.this, "Questões atualizadas com sucesso!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_delete_questions:
                RepositoryOpenHelper repositoryOpenHelper = RepositoryOpenHelper.getInstance(getApplicationContext());
                repositoryOpenHelper.clearDatabaseQuestionItem();
                Toast.makeText(MainActivity.this, "Questões apagadas com sucesso!",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void syncItens() {

        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPItemService service = new PHPItemService(queue);

        service.retrieveItemsFromServer(new ServiceListener<List<Item>>() {
            @Override
            public void onResponse(List<Item> response, Throwable error) {
                if (error == null && response != null) {
                    final SQLiteItemRepository repository =
                            new SQLiteItemRepository(RepositoryOpenHelper.getInstance(getApplicationContext()));
                    repository.save(response, new SaveListener<List<Item>>() {
                        @Override
                        public void onSaveFinish(List<Item> saved, Throwable e) {
                            //TODO: tratar
                        }
                    });
                } else if (error != null) {
                    Log.e(TAG, "Erro ao salvar itens de questão.", error);
                } else {
                    throw new RuntimeException("What the fock!");
                }
            }
        });


    }

    public void syncQuestionFromServer() {

        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPQuestionService service = new PHPQuestionService(queue, this);
        service.retrieveQuestionFromServer(new ServiceListener<List<Question>>() {
            @Override
            public void onResponse(List<Question> response, Throwable error) {
                if (error == null && response != null) {
                    final SQLiteQuestionRepository questionRepository =
                            new SQLiteQuestionRepository(RepositoryOpenHelper.getInstance(getApplicationContext()));
                    questionRepository.save(response, new SaveListener<List<Question>>() {
                        @Override
                        public void onSaveFinish(List<Question> saved, Throwable e) {
                            if (e == null && saved != null) {
                                Toast.makeText(MainActivity.this, "Questões atualizadas com sucesso!"
                                        , Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "Erro ao salvar", e);
                            }
                        }
                    });
                } else {
                    Log.e(TAG, "Erro ao salvar", error);
                }
            }
        });


    }

    public void syncMatterFromServer() {

        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPMatterService service = new PHPMatterService(queue, this);
        service.retrieveFromServer(new ServiceListener<List<Matter>>() {
            @Override
            public void onResponse(List<Matter> response, Throwable error) {
                final SQLiteMatterRepository repository =
                        new SQLiteMatterRepository(RepositoryOpenHelper.getInstance(getApplicationContext()));
                repository.save(response, new SaveListener<List<Matter>>() {
                    @Override
                    public void onSaveFinish(List<Matter> saved, Throwable e) {
                        //TODO: tratar após o salvamento
                    }
                });

            }
        });


    }

    public void syncTopicQuestion(){

        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPTopicQuestionService service = new PHPTopicQuestionService(queue, this);
        service.retrieveFromServer(new ServiceListener<List<TopicQuestion>>() {
            @Override
            public void onResponse(List<TopicQuestion> response, Throwable error) {
                final SQLiteTopicQuestionRepository repository =
                        new SQLiteTopicQuestionRepository(RepositoryOpenHelper.getInstance(getApplicationContext()));
                repository.save(response, new SaveListener<List<TopicQuestion>>() {
                    @Override
                    public void onSaveFinish(List<TopicQuestion> saved, Throwable e) {
                        //TODO: tratar o salvamento.
                    }
                });
            }
        });

    }

    public void syncTopic() {

        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPTopicService phpTopicService = new PHPTopicService(queue, this);
        phpTopicService.retrieveFromServer(new ServiceListener<List<Topic>>() {

            @Override
            public void onResponse(List<Topic> response, Throwable error) {
                final SQLiteTopicRepository repository =
                        new SQLiteTopicRepository(RepositoryOpenHelper.getInstance(getApplicationContext()));
                repository.save(response, new SaveListener<List<Topic>>() {
                    @Override
                    public void onSaveFinish(List<Topic> saved, Throwable e) {
                        //TODO: tratar o salvemento.
                    }
                });
            }
        });

    }

    //buscar_assunto_questao.php
    //buscar_assuntos.php
    //buscar_materias.php
}