package com.bizu.network;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface UpdateListener<T> {

    public void onResponse(T response, Throwable error);

}
