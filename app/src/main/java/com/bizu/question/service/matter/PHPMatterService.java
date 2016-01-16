package com.bizu.question.service.matter;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.bizu.network.ClassDeserializeStrategy;
import com.bizu.network.GsonRequest;
import com.bizu.network.ListTypeTokenDeserializeStrategy;
import com.bizu.network.UpdateListener;
import com.bizu.entity.Matter;

import java.util.List;

/**
 *
 * Created by fabricio on 1/16/16.
 */
public class PHPMatterService implements MatterService {

    //    private static final String ENDERECO = "http://mysale2.hospedagemdesites.ws/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2:1080/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2/bizu/app/teste.php";
    private static final String ENDERECO = "http://www.bizu.educacao.ws/app/buscar_materias.php";

//    private static final String ENDERECO = "http://bizu.educacao.ws/app/teste.php";
    // public String ENDERECO = "http://10.0.3.2/mysale/app/teste.php";
    /**
     *
     * @param requestQueue to schedule tasks.
     */
    public PHPMatterService(final RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }

    public Request<Matter> update(@NonNull Matter matterToUpdate, final UpdateListener listener)
            throws NullPointerException {
        if (matterToUpdate == null)
            throw new IllegalArgumentException("questionToUpdate cannot be null, neither queueStrategy");
        final ServiceListenerMatter volleyListener = new ServiceListenerMatter(listener);
        final Request<Matter> volleyRequest = new GsonRequest<>(ENDERECO, new ClassDeserializeStrategy<>(Matter.class)
                , null, volleyListener, volleyListener);
        return mRequestQueue.add(volleyRequest);
    }

    public Request<List<Matter>> updateFromServer(MatterRepository repository
            , UpdateListener listener) {
        final ServiceListenerMatter<List<Matter>> volleyListener = new ServiceListenerMatter<>(listener);
        final Request<List<Matter>> volleyRequest = new GsonRequest<>(ENDERECO
                , new ListTypeTokenDeserializeStrategy<Matter>(Matter.class)
                , null, volleyListener, volleyListener);
        return mRequestQueue.add(volleyRequest);
    }

    private final RequestQueue mRequestQueue;
}
