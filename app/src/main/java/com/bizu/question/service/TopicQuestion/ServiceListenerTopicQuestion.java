package com.bizu.question.service.TopicQuestion;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bizu.network.UpdateListener;

class ServiceListenerTopicQuestion<T> implements Response.Listener<T>, Response.ErrorListener {

    private final UpdateListener<T> mListener;

    public ServiceListenerTopicQuestion(final UpdateListener<T> listener) {
        mListener = listener;
    }

    @Override
    public void onResponse(T response) {
        mListener.onResponse(response, null);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onResponse(null, error);
    }
}