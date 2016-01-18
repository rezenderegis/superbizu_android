package com.bizu.android.database;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface SaveListener<E> {
    void onSaveFinish(final E saved, final Throwable e);
}
