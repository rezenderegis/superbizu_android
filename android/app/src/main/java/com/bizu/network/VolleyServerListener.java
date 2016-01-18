package com.bizu.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by almde on 16/01/2016.
 */
public class VolleyServerListener<T> implements Response.Listener<T>, Response.ErrorListener {

    private final ServiceListener<T> mListener;
    private final Context mContext;

    public VolleyServerListener(final ServiceListener<T> listener, final Context context) {
        mListener = listener;
        mContext = context;
    }

    @Override
    public void onResponse(final T result) {
        mListener.onResponse(result, null);
    }

    @Override
    public void onErrorResponse(final VolleyError error) {
        mListener.onResponse(null, error);
        }
}