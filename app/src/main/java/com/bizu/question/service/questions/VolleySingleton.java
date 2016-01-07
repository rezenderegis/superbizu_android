package com.bizu.question.service.questions;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by fabricio on 1/6/16.
 */
public class VolleySingleton {
    private static VolleySingleton mInstance = null;

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    private VolleySingleton(Context context){
        mRequestQueue = Volley.newRequestQueue(context);

        mImageLoader = new ImageLoader(this.mRequestQueue,
                new ImageLoader.ImageCache() {

                    private final LruCache<String, Bitmap>
                            mCache = new LruCache<String, Bitmap>(10);

                    public void putBitmap(
                            String url, Bitmap bitmap) {
                        mCache.put(url, bitmap);
                    }
                    public Bitmap getBitmap(String url) {
                        return mCache.get(url);
                    }
                });
    }

    public static VolleySingleton getInstance(
            Context context){

        if(mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }

}