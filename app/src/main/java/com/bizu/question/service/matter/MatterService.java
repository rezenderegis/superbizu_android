package com.bizu.question.service.matter;

import android.support.annotation.NonNull;

import com.bizu.network.UpdateListener;
import com.bizu.entity.Matter;

import java.util.List;

/**
 * Created by fabricio on 1/16/16.
 */
public interface MatterService {
    /**
     * Updates the question information based on backend up to date data. May be asynchronous operation.
     * @param questionToUpdate
     * @throws NullPointerException
     */
    <T> T update(@NonNull final Matter matterToUpdate, final UpdateListener<Matter> listener)
            throws NullPointerException;

    /**
     * Updates question information based on backend up to date data. May be asynchronous operation
     *  so {@link T} is return for third party framework.
     * @param repository for database operations.
     * @param listener for question list updates handling.
     * @param <T> possible third party framework or AsyncTask return.
     * @return as for {@link T}
     */
    <T> T updateFromServer(final MatterRepository repository, final UpdateListener<List<Matter>> listener);
}
