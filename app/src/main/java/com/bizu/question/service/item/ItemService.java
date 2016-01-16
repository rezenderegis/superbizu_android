package com.bizu.question.service.item;

import android.support.annotation.NonNull;

import com.bizu.network.UpdateListener;
import com.bizu.entity.Item;

import java.util.List;

/**
 * Created by fabricio on 1/16/16.
 */
public interface ItemService {
    /**
     * Updates the question information based on backend up to date data. May be asynchronous operation.
     * @param questionToUpdate
     * @throws NullPointerException
     */
    <T> T update(@NonNull final Item questionToUpdate, final UpdateListener<Item> listener)
            throws NullPointerException;

    /**
     * Updates question information based on backend up to date data. May be asynchronous operation
     *  so {@link T} is return for third party framework.
     * @param repository for database operations.
     * @param listener for question list updates handling.
     * @param <T> possible third party framework or AsyncTask return.
     * @return as for {@link T}
     */
    <T> T updateFromServer(final ItemRepository repository, final UpdateListener<List<Item>> listener);
}
