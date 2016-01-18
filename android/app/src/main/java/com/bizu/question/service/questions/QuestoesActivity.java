package com.bizu.question.service.questions;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bizu.entity.Question;
import com.bizu.network.ServiceListener;
import com.bizu.android.database.RepositoryOpenHelper;
import com.bizu.question.SQLiteQuestionRepository;
import com.bizu.android.database.SaveListener;
import com.bizu.question.service.PHPQuestionService;
import com.bizu.question.service.download.MainActivity;

import java.util.List;

/**
 * Created by fabricio on 1/6/16.
 */
public class QuestoesActivity extends ListActivity {
    public final static String TAG = QuestoesActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final PHPQuestionService questionService = new PHPQuestionService(queue, this);
        questionService.retrieveQuestionFromServer(new ServiceListener<List<Question>>() {
            @Override
            public void onResponse(List<Question> response, Throwable error) {
                if (error == null && response != null) {
                    final SQLiteQuestionRepository questionRepository =
                            new SQLiteQuestionRepository(RepositoryOpenHelper.getInstance(getApplicationContext()));
                    questionRepository.save(response, new SaveListener<List<Question>>() {
                        @Override
                        public void onSaveFinish(List<Question> saved, Throwable e) {
                            if (e == null && saved != null) {
                                final Intent recarregarTelaInicial = new Intent(QuestoesActivity.this, MainActivity.class);
                                Toast.makeText(QuestoesActivity.this, "Questões atualizadas com sucesso!"
                                        , Toast.LENGTH_SHORT).show();
                                startActivity(recarregarTelaInicial);
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
}
