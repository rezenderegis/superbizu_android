package com.bizu.android.database;

/**
 * Created by almde on 13/01/2016.
 */
public interface RetrieveListener<E> {
    void onRetrieve(E entity, Throwable error);
}
