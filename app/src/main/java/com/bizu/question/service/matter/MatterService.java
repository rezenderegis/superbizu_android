package com.bizu.question.service.matter;

import android.support.annotation.NonNull;

import com.bizu.network.ServiceListener;
import com.bizu.entity.Matter;

import java.util.List;

/**
 * Created by fabricio on 1/16/16.
 */
public interface MatterService {
    /**
     * Updates question information based on backend up to date data. May be asynchronous operation
     *  so {@link T} is return for third party framework.
     * @param repository for database operations.
     * @param listener for question list updates handling.
     * @param <T> possible third party framework or AsyncTask return.
     * @return as for {@link T}
     */
    <T> T retrieveFromServer(final ServiceListener<List<Matter>> listener);
}
