package com.bizu.network;

import com.google.gson.Gson;

/**
 * Created by almde on 09/01/2016.
 */
public class ClassDeserializeStrategy<T> implements GsonDeserializeStrategy{

    private final Class<T> mClazz;

    public ClassDeserializeStrategy(final Class clazz) {
        mClazz = clazz;
    }

    @Override
    public T deserialize(String json, Gson gson) {
        return gson.fromJson(json, mClazz);
    }
}
