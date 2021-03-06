package com.bizu.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by andre.lmello on 12/9/15.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson mGson;
    private final GsonDeserializeStrategy mStrategy;
    private final Map<String, String> mHeaders;
    private final Response.Listener<T> mListener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param strategy deserialize strategy used by Gson.
     * @param headers Map of request mHeaders
     */
    public GsonRequest(String url, GsonDeserializeStrategy strategy, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        final GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(Question.class, new GsonQuestionAdapter());
        mGson = builder.create();
        this.mStrategy = strategy;
        this.mHeaders = headers;
        this.mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success((T)
                    mStrategy.deserialize(json, mGson),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

}
