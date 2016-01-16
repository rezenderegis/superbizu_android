package com.bizu.question.service.download;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bizu.R;
import com.bizu.entity.Topic;
import com.bizu.entity.TopicQuestion;
import com.bizu.network.UpdateListener;
import com.bizu.entity.Item;
import com.bizu.entity.Question;
import com.bizu.question.RepositoryOpenHelper;
import com.bizu.question.controller.FullscreenActivity;
import com.bizu.question.controller.ShowAllQuestions;
import com.bizu.entity.Matter;
import com.bizu.question.service.PHPQuestionService;
import com.bizu.question.service.TopicQuestion.PHPTopicQuestionService;
import com.bizu.question.service.item.PHPItemService;
import com.bizu.question.service.matter.PHPMatterService;
import com.bizu.question.service.topic.PHPTopicService;


public class MainActivity extends Activity {

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
                RepositoryOpenHelper repositoryOpenHelper = new RepositoryOpenHelper(MainActivity.this);
               repositoryOpenHelper.clearDatabaseQuestionItem();
                repositoryOpenHelper.close();
                Toast.makeText(MainActivity.this, "Questões apagadas com sucesso!",Toast.LENGTH_SHORT).show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void syncItens() {

        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPItemService service = new PHPItemService(queue);

        service.updateFromServer(new RepositoryOpenHelper(this), new UpdateListener<List<Item>>() {
            @Override
            public void onResponse(List<Item> response, Throwable error) {
                final RepositoryOpenHelper repository = new RepositoryOpenHelper(MainActivity.this);
                repository.saveItem(response);
                repository.close();

            }
        });


    }


    public void syncQuestionFromServer() {

        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPQuestionService service = new PHPQuestionService(queue);
        service.updateFromServer(new RepositoryOpenHelper(this), new UpdateListener<List<Question>>() {
            @Override
            public void onResponse(List<Question> response, Throwable error) {
                final RepositoryOpenHelper repository = new RepositoryOpenHelper(MainActivity.this);
                repository.saveQuestion(response);
                repository.close();

            }
        });


    }

    public void syncMatterFromServer() {

        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPMatterService service = new PHPMatterService(queue);
        service.updateFromServer(new RepositoryOpenHelper(this), new UpdateListener<List<Matter>>() {
            @Override
            public void onResponse(List<Matter> response, Throwable error) {
                final RepositoryOpenHelper repository = new RepositoryOpenHelper(MainActivity.this);
                repository.saveMatter(response);
                repository.close();

            }
        });


    }

    public void syncTopicQuestion(){

        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPTopicQuestionService service = new PHPTopicQuestionService(queue);
        service.updateFromServer(new RepositoryOpenHelper(this), new UpdateListener<List<TopicQuestion>>() {
            @Override
            public void onResponse(List<TopicQuestion> response, Throwable error) {
                final RepositoryOpenHelper repositoryOpenHelper = new RepositoryOpenHelper(MainActivity.this);
                repositoryOpenHelper.saveTopicQuestion(response);
                repositoryOpenHelper.close();
            }
        });




    }

    public void syncTopic() {

        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPTopicService phpTopicService = new PHPTopicService(queue);
        phpTopicService.updateFromServer(new RepositoryOpenHelper(this), new UpdateListener<List< Topic>>() {

            @Override
            public void onResponse(List<Topic> response, Throwable error) {
                final RepositoryOpenHelper repositoryOpenHelper = new RepositoryOpenHelper(MainActivity.this);
                repositoryOpenHelper.saveTopic(response);
                repositoryOpenHelper.close();
            }
        });


    }

    //buscar_assunto_questao.php
    //buscar_assuntos.php
    //buscar_materias.php
}