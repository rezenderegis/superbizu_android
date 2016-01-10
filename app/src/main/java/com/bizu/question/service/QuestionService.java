package com.bizu.question.service;

import android.support.annotation.NonNull;

import com.bizu.network.UpdateListener;
import com.bizu.question.Question;
import com.bizu.question.QuestionRepository;
import com.bizu.question.RepositoryOpenHelper;

import java.util.List;

/**
 * Created by andre.lmello on 11/25/15.
 */
public interface QuestionService {
    /**
     * Updates the question information based on backend up to date data. May be asynchronous operation.
     * @param questionToUpdate
     * @throws NullPointerException
     */
    <T> T update(@NonNull final Question questionToUpdate, final UpdateListener<Question> listener)
            throws NullPointerException;

    /**
     * Updates question information based on backend up to date data. May be asynchronous operation
     *  so {@link T} is return for third party framework.
     * @param repository for database operations.
     * @param listener for question list updates handling.
     * @param <T> possible third party framework or AsyncTask return.
     * @return as for {@link T}
     */
    <T> T updateFromServer(final QuestionRepository repository, final UpdateListener<List<Question>> listener);
}
