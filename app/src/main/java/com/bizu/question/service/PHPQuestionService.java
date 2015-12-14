package com.bizu.question.service;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bizu.network.GsonRequest;
import com.bizu.network.UpdateListener;
import com.bizu.question.Question;

/**
 *
 * Created by andre.lmello on 11/25/15.
 */
public class PHPQuestionService implements QuestionService {

    private Request<Question> mVolleyRequest;

    /**
     *
     * @param requestQueue to schedule tasks.
     */
    public PHPQuestionService(final RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }

    @Override
    public Request<Question> update(@NonNull Question questionToUpdate, final UpdateListener listener)
            throws NullPointerException {
        if (questionToUpdate == null)
            throw new IllegalArgumentException("questionToUpdate cannot be null, neither queueStrategy");
        final Listener volleyListener = new Listener(listener);
        mVolleyRequest = new GsonRequest<>("http://192.168.56.1:1080/rest", Question.class
                , null, volleyListener, volleyListener);
        mVolleyRequest = mRequestQueue.add(mVolleyRequest);
        return mVolleyRequest;
    }

    private final RequestQueue mRequestQueue;
}

class Listener implements Response.Listener<Question>, Response.ErrorListener {

    private final UpdateListener<Question> mListener;

    public Listener(final UpdateListener<Question> listener) {
        mListener = listener;
    }

    @Override
    public void onResponse(Question response) {
        mListener.onResponse(response, null);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onResponse(null, error);
    }
}