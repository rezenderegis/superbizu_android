package com.bizu.network;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by andre.lmello on 12/6/15.
 */
public class VolleyRequestFactoryImpl implements VolleyRequestFactory {
    @Override
    public <T> Request<T> newInstance(Class<T> clazz, ResponseListener listener) throws VolleyRequestFactoryException {
        return (Request<T>) new StringRequest(0, null, null, null);
    }
}
