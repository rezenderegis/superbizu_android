package com.bizu.android.database;

/**
 * The transaction response is more complicated than one value. Async operations must be able to return the result or an error.
 * @param <T>
 */
public class AsyncResult<T> {
    final private T mResult;
    final private Throwable mError;

    public AsyncResult(final T result) {
        mResult = result;
        mError = null;
    }

    public AsyncResult(final Throwable error) {
        mResult = null;
        mError = error;
    }

    public T getResult() {
        return mResult;
    }

    public Throwable getError() {
        return mError;
    }

    public boolean isDoneWithoutError() {
        return mResult != null && mError == null;
    }
}
