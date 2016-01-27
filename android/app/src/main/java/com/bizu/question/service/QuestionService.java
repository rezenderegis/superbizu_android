package com.bizu.question.service;

import com.bizu.entity.Question;

import com.bizu.network.ServiceListener;

import java.util.List;

/**
 * Created by andre.lmello on 11/25/15.
 */
public interface QuestionService {

    /**
     * Retrieves question up to date information from the backend. May be asynchronous operation
     *  so {@link T} is return for third party framework.
     * @param listener for question list updates handling.
     * @param <T> possible third party framework or AsyncTask return.
     * @return as for {@link T}
     */
    <T> T retrieveQuestionFromServer(final ServiceListener<List<Question>> listener);
}
