package com.bizu.question.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bizu.network.GsonRequest;
import com.bizu.network.ListTypeTokenDeserializeStrategy;
import com.bizu.entity.Question;
import com.bizu.network.ServiceListener;
import com.bizu.network.VolleyServerListener;

import java.util.List;

/**
 *
 * Created by andre.lmello on 11/25/15.
 */
public class PHPQuestionService implements QuestionService {

//    private static final String ENDERECO = "http://mysale2.hospedagemdesites.ws/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2:1080/mysale/app/teste.php";
    private static final String ENDERECO = "http://bizu.educacao.ws/app/teste.php";
   // public String ENDERECO = "http://10.0.3.2/mysale/app/teste.php";
    private final Context mContext;
    /**
     *
     * @param requestQueue to schedule tasks.
     */
    public PHPQuestionService(final RequestQueue requestQueue, final Context context) {
        mContext = context;
        mRequestQueue = requestQueue;
    }

    public Request<List<Question>> retrieveQuestionFromServer(ServiceListener listener) {
        final VolleyServerListener volleyListener = new VolleyServerListener(listener, mContext);
        final Request<List<Question>> volleyRequest = new GsonRequest<>(ENDERECO
                , new ListTypeTokenDeserializeStrategy<Question>(Question.class)
                , null, volleyListener, volleyListener);
        return mRequestQueue.add(volleyRequest);
    }

    private final RequestQueue mRequestQueue;
}
