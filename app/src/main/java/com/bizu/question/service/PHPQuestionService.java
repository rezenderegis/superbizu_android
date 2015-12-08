package com.bizu.question.service;

import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.bizu.question.Question;

/**
 *
 * Created by andre.lmello on 11/25/15.
 */
public class PHPQuestionService implements QuestionService {

    /**
     *
     * @param requestQueue to schedule tasks.
     */
    public PHPQuestionService(final RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }

    @Override
    public UpdateSchedule update(@NonNull Question questionToUpdate)
            throws NullPointerException {
        if (questionToUpdate == null)
            throw new IllegalArgumentException("questionToUpdate cannot be null, neither queueStrategy");
        final UpdateSchedule scheduledTask = new UpdateSchedule(questionToUpdate.getId(), mRequestQueue, null);
        return scheduledTask;
    }

    private final RequestQueue mRequestQueue;
}
