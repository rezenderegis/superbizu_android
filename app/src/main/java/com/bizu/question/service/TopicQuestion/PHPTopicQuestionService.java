package com.bizu.question.service.TopicQuestion;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.bizu.entity.Item;
import com.bizu.entity.TopicQuestion;
import com.bizu.network.ClassDeserializeStrategy;
import com.bizu.network.GsonRequest;
import com.bizu.network.ListTypeTokenDeserializeStrategy;
import com.bizu.network.UpdateListener;

import java.util.List;

/**
 *
 * Created by andre.lmello on 11/25/15.
 */
public class PHPTopicQuestionService implements TopicQuestionService {

    //    private static final String ENDERECO = "http://mysale2.hospedagemdesites.ws/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2:1080/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2/bizu/app/teste.php";
    private static final String ENDERECO = "http://www.bizu.educacao.ws/app/buscar_assunto_questao.php";

//    private static final String ENDERECO = "http://bizu.educacao.ws/app/teste.php";
    // public String ENDERECO = "http://10.0.3.2/mysale/app/teste.php";
    /**
     *
     * @param requestQueue to schedule tasks.
     */
    public PHPTopicQuestionService(final RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }

    public Request<TopicQuestion> update(@NonNull TopicQuestion forUpdate, final UpdateListener listener)
            throws NullPointerException {
        if (forUpdate == null)
            throw new IllegalArgumentException("questionToUpdate cannot be null, neither queueStrategy");
        final ServiceListenerTopicQuestion volleyListener = new ServiceListenerTopicQuestion(listener);
        final Request<TopicQuestion> volleyRequest = new GsonRequest<>(ENDERECO, new ClassDeserializeStrategy<>(TopicQuestion.class)
                , null, volleyListener, volleyListener);
        return mRequestQueue.add(volleyRequest);
    }

    public Request<List<TopicQuestion>> updateFromServer(TopicQuestionRepository repository
            , UpdateListener listener) {
        final ServiceListenerTopicQuestion<List<TopicQuestion>> volleyListener = new ServiceListenerTopicQuestion<>(listener);
        final Request<List<TopicQuestion>> volleyRequest = new GsonRequest<>(ENDERECO
                , new ListTypeTokenDeserializeStrategy<TopicQuestion>(TopicQuestion.class)
                , null, volleyListener, volleyListener);
        return mRequestQueue.add(volleyRequest);
    }

    private final RequestQueue mRequestQueue;
}
