package com.bizu.question.item;
import com.bizu.network.ServiceListener;

import java.util.List;

/**
 * Created by andre.lmello on 11/25/15.
 */
public interface ItemService {
    /**
     * Retrieves question up to date information from the backend. May be asynchronous operation
     *  so {@link T} is return for third party framework.
     * @param listener for question list updates handling.
     * @param <T> possible third party framework or AsyncTask return.
     * @return as for {@link T}
     */
    <T> T retrieveItemsFromServer(final ServiceListener<List<Item>> listener);
}
