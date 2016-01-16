package com.bizu.question.service.topic;

import android.support.annotation.NonNull;

import com.bizu.entity.Topic;
import com.bizu.entity.TopicQuestion;
import com.bizu.network.UpdateListener;

import java.util.List;

/**
 * Created by andre.lmello on 11/25/15.
 */
public interface TopicService {
    /**
     * Updates the question information based on backend up to date data. May be asynchronous operation.
     * @param questionToUpdate
     * @throws NullPointerException
     */
    <T> T update(@NonNull final Topic forUpdate, final UpdateListener<Topic> listener)
            throws NullPointerException;

    /**
     * Updates question information based on backend up to date data. May be asynchronous operation
     *  so {@link T} is return for third party framework.
     * @param repository for database operations.
     * @param listener for question list updates handling.
     * @param <T> possible third party framework or AsyncTask return.
     * @return as for {@link T}
     */
    <T> T updateFromServer(final TopicRepository repository, final UpdateListener<List<Topic>> listener);
}
