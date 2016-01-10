package com.bizu.network;

import com.google.gson.Gson;

/**
 * Created by almde on 09/01/2016.
 */
public interface GsonDeserializeStrategy {
    <T> T deserialize(final String json, final Gson gson);
}
