package com.bizu.question.service;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bizu.network.ClassDeserializeStrategy;
import com.bizu.network.GsonRequest;
import com.bizu.network.ListTypeTokenDeserializeStrategy;
import com.bizu.network.UpdateListener;
import com.bizu.question.Question;
import com.bizu.question.QuestionRepository;

import java.util.List;

/**
 *
 * Created by andre.lmello on 11/25/15.
 */
public class PHPQuestionService implements QuestionService {

//    private static final String ENDERECO = "http://mysale2.hospedagemdesites.ws/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2:1080/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2/bizu/app/teste.php";
    private static final String ENDERECO = "http://www.bizu.educacao.ws/app/teste.php";

//    private static final String ENDERECO = "http://bizu.educacao.ws/app/teste.php";
   // public String ENDERECO = "http://10.0.3.2/mysale/app/teste.php";
    /**
     *
     * @param requestQueue to schedule tasks.
     */
    public PHPQuestionService(final RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }

    public Request<Question> update(@NonNull Question questionToUpdate, final UpdateListener listener)
            throws NullPointerException {
        if (questionToUpdate == null)
            throw new IllegalArgumentException("questionToUpdate cannot be null, neither queueStrategy");
        final ServiceListener volleyListener = new ServiceListener(listener);
        final Request<Question> volleyRequest = new GsonRequest<>(ENDERECO, new ClassDeserializeStrategy<>(Question.class)
                , null, volleyListener, volleyListener);
        return mRequestQueue.add(volleyRequest);
    }

    public Request<List<Question>> updateFromServer(QuestionRepository repository
            , UpdateListener listener) {
        final ServiceListener<List<Question>> volleyListener = new ServiceListener<>(listener);
        final Request<List<Question>> volleyRequest = new GsonRequest<>(ENDERECO
                , new ListTypeTokenDeserializeStrategy<Question>(Question.class)
                , null, volleyListener, volleyListener);
        return mRequestQueue.add(volleyRequest);
    }

    private final RequestQueue mRequestQueue;
}

class ServiceListener<T> implements Response.Listener<T>, Response.ErrorListener {

    private final UpdateListener<T> mListener;

    public ServiceListener(final UpdateListener<T> listener) {
        mListener = listener;
    }

    @Override
    public void onResponse(T response) {
        mListener.onResponse(response, null);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onResponse(null, error);
    }
}