package com.bizu.question.service.TopicQuestion;

import android.support.annotation.NonNull;

import com.bizu.entity.Item;
import com.bizu.entity.TopicQuestion;
import com.bizu.network.UpdateListener;

import java.util.List;

/**
 * Created by andre.lmello on 11/25/15.
 */
public interface TopicQuestionService {
    /**
     * Updates the question information based on backend up to date data. May be asynchronous operation.
     * @param questionToUpdate
     * @throws NullPointerException
     */
    <T> T update(@NonNull final TopicQuestion forUpdate, final UpdateListener<TopicQuestion> listener)
            throws NullPointerException;

    /**
     * Updates question information based on backend up to date data. May be asynchronous operation
     *  so {@link T} is return for third party framework.
     * @param repository for database operations.
     * @param listener for question list updates handling.
     * @param <T> possible third party framework or AsyncTask return.
     * @return as for {@link T}
     */
    <T> T updateFromServer(final TopicQuestionRepository repository, final UpdateListener<List<TopicQuestion>> listener);
}
