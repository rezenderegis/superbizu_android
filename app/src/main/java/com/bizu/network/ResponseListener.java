package com.bizu.network;

/**
 * Created by andre.lmello on 12/5/15.
 */
public interface ResponseListener<T> {
    void onResponse(T response);
}
