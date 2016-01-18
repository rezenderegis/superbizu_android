package com.bizu.question.service.topic;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.bizu.entity.Topic;
import com.bizu.network.GsonRequest;
import com.bizu.network.ListTypeTokenDeserializeStrategy;
import com.bizu.network.ServiceListener;
import com.bizu.network.VolleyServerListener;

import java.util.List;

/**
 *
 * Created by andre.lmello on 11/25/15.
 */
public class PHPTopicService implements TopicService {

    //    private static final String ENDERECO = "http://mysale2.hospedagemdesites.ws/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2:1080/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2/bizu/app/teste.php";
    private static final String ENDERECO = "http://www.bizu.educacao.ws/app/buscar_assuntos.php";
    private final Context mContext;

//    private static final String ENDERECO = "http://bizu.educacao.ws/app/teste.php";
    // public String ENDERECO = "http://10.0.3.2/mysale/app/teste.php";
    /**
     *
     * @param requestQueue to schedule tasks.
     */
    public PHPTopicService(final RequestQueue requestQueue, final Context context) {
        mRequestQueue = requestQueue;
        mContext = context;
    }

    public Request<List<Topic>> retrieveFromServer(ServiceListener listener) {
        final VolleyServerListener<List<Topic>> volleyListener =
                new VolleyServerListener<>(listener, mContext);
        final Request<List<Topic>> volleyRequest = new GsonRequest<>(ENDERECO
                , new ListTypeTokenDeserializeStrategy<Topic>(Topic.class)
                , null, volleyListener, volleyListener);
        return mRequestQueue.add(volleyRequest);
    }

    private final RequestQueue mRequestQueue;
}
