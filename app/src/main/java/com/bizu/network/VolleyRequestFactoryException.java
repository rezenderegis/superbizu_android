package com.bizu.network;

/**
 * Thrown when any error happens in Volley {@link com.android.volley.Request} creation.
 * Created by andre.lmello on 12/2/15.
 * @see VolleyRequestFactory
 */
public class VolleyRequestFactoryException extends Exception {

    /**
     * Throw this as original error.
     * @param message short message.
     */
    public VolleyRequestFactoryException(final String message) {
        super(message);
    }

    /**
     * Throw this with a original exception.
     * @param message short message.
     * @param cause to throw this exception.
     */
    public VolleyRequestFactoryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
