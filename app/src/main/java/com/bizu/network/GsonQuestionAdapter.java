package com.bizu.network;

import com.bizu.question.Question;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by almde on 10/01/2016.
 */
public class GsonQuestionAdapter implements JsonDeserializer<Question>, JsonSerializer<Question> {
    @Override
    public Question deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Question src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
