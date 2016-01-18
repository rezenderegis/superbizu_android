package com.bizu.network;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by almde on 09/01/2016.
 */
public class ListTypeTokenDeserializeStrategy<T> implements GsonDeserializeStrategy {
    public ListTypeTokenDeserializeStrategy(final Class<T> clazz) {
        mClazz = clazz;
    }
    @Override
    public List<T> deserialize(String json, final Gson gson) {
        final JsonArray array = gson.fromJson(json, JsonArray.class);
        final Iterator<JsonElement> iterator = array.iterator();
        final ArrayList<T> parsedNodes = new ArrayList<>(array.size());
        while (iterator.hasNext()) {
            final JsonElement node = iterator.next();
            parsedNodes.add(gson.fromJson(node, mClazz));
        }
        return parsedNodes;
    }

    private Class<T> mClazz;
}
