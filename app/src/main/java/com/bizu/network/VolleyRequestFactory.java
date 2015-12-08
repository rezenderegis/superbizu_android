package com.bizu.network;

import com.android.volley.Request;

/**
 * Factory for {@link Request}.
 * Created by andre.lmello on 12/2/15.
 * @see Request
 * @see com.android.volley.RequestQueue
 */
public interface VolleyRequestFactory {
    /**
     * Creates a Volley {@link Request}
     * @param clazz generic parameter
     * @param listener response listener
     * @param <T> generic parameter from clazz
     * @return new {@link Request}
     * @throws VolleyRequestFactoryException for any original exception.
     */
    <T> Request<T> newInstance(Class<T> clazz, final ResponseListener listener)
            throws VolleyRequestFactoryException;
}
