package com.bizu.question.service;

import android.support.annotation.NonNull;

import com.bizu.network.ScheduledRequest;
import com.bizu.question.Question;

/**
 * Created by andre.lmello on 11/25/15.
 */
public interface QuestionService {
    /**
     * Updates the question information based on backend up to date data. May be asynchronous operation.
     * @param questionToUpdate
     * @throws NullPointerException
     */
    <T extends ScheduledRequest> T update(@NonNull final Question questionToUpdate)
            throws NullPointerException;
}
