package com.bizu.question.service.item;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bizu.network.ClassDeserializeStrategy;
import com.bizu.network.GsonRequest;
import com.bizu.network.ListTypeTokenDeserializeStrategy;
import com.bizu.network.UpdateListener;
import com.bizu.question.Item;

import java.util.List;

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