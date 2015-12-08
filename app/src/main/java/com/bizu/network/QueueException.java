package com.bizu.network;

/**
 * Thrown when any exception may occur on queueing tasks.
 * Created by andre.lmello on 12/2/15.
 */
public class QueueException extends Exception {

    /**
     * When is the original exception.
     * @param message from this original exception
     */
    public QueueException(final String message) {
        super(message);
    }

    /**
     * When cause by another exception.
     * @param message to situate when it happens.
     * @param cause to proper handle.
     */
    public QueueException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
