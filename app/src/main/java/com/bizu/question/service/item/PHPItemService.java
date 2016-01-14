package com.bizu.question.service.item;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.bizu.network.ClassDeserializeStrategy;
import com.bizu.network.GsonRequest;
import com.bizu.network.ListTypeTokenDeserializeStrategy;
import com.bizu.network.UpdateListener;
import com.bizu.question.Item;

import java.util.List;

/**
 *
 * Created by andre.lmello on 11/25/15.
 */
public class PHPItemService implements ItemService {

    //    private static final String ENDERECO = "http://mysale2.hospedagemdesites.ws/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2:1080/mysale/app/teste.php";
    //private static final String ENDERECO = "http://10.0.3.2/bizu/app/teste.php";
    private static final String ENDERECO = "http://www.bizu.educacao.ws/app/busca_itens_questao.php";

//    private static final String ENDERECO = "http://bizu.educacao.ws/app/teste.php";
    // public String ENDERECO = "http://10.0.3.2/mysale/app/teste.php";
    /**
     *
     * @param requestQueue to schedule tasks.
     */
    public PHPItemService(final RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }

    public Request<Item> update(@NonNull Item questionToUpdate, final UpdateListener listener)
            throws NullPointerException {
        if (questionToUpdate == null)
            throw new IllegalArgumentException("questionToUpdate cannot be null, neither queueStrategy");
        final ServiceListenerItem volleyListener = new ServiceListenerItem(listener);
        final Request<Item> volleyRequest = new GsonRequest<>(ENDERECO, new ClassDeserializeStrategy<>(Item.class)
                , null, volleyListener, volleyListener);
        return mRequestQueue.add(volleyRequest);
    }

    public Request<List<Item>> updateFromServer(ItemRepository repository
            , UpdateListener listener) {
        final ServiceListenerItem<List<Item>> volleyListener = new ServiceListenerItem<>(listener);
        final Request<List<Item>> volleyRequest = new GsonRequest<>(ENDERECO
                , new ListTypeTokenDeserializeStrategy<Item>(Item.class)
                , null, volleyListener, volleyListener);
        return mRequestQueue.add(volleyRequest);
    }

    private final RequestQueue mRequestQueue;
}
