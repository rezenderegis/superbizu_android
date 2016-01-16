package com.bizu.question.service.item;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bizu.network.UpdateListener;

class ServiceListenerItem<T> implements Response.Listener<T>, Response.ErrorListener {

    private final UpdateListener<T> mListener;

    public ServiceListenerItem(final UpdateListener<T> listener) {
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