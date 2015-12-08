package com.bizu.question.service;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.bizu.network.ScheduledRequest;
import com.bizu.network.ScheduledRequestStatus;
import com.bizu.network.VolleyRequestFactory;
import com.bizu.network.VolleyRequestFactoryException;
import com.bizu.question.Question;

/**
 * Represents an async scheduling from a question.
 * Created by andre.lmello on 11/26/15.
 */
public class UpdateSchedule implements ScheduledRequest {

    private final Long mId;
    private final RequestQueue mQueue;
    private final VolleyRequestFactory mFactory;
    private ScheduledRequestStatus mStatus;
    private Request<Question> mVolleyRequest;

    public UpdateSchedule(final Long id, final RequestQueue queue, final VolleyRequestFactory factory) {
        mFactory = factory;
        mQueue = queue;
        mId = id;
        mStatus = ScheduledRequestStatus.LOADING;
    }

    public Long getId() {
        return mId;
    }

    public ScheduledRequestStatus getStatus() {
        return mStatus;
    }

    @Override
    public UpdateSchedule queue() throws VolleyRequestFactoryException {
        mVolleyRequest = mFactory.newInstance(Question.class, null);
        mVolleyRequest = mQueue.add(mVolleyRequest);
        mStatus = ScheduledRequestStatus.RUNNING;
        return this;
    }

    public Request<Question> getVolleyRequest() {
        return mVolleyRequest;
    }
}
