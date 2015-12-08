package com.bizu.network;

/**
 * Responsible for network async tasks management.
 * Created by andre.lmello on 11/28/15.
 */
public interface ScheduledRequest {
    /**
     * Queues any task associated with this request.
     * @param <T> return must implement {@link ScheduledRequest}
     * @return {@link ScheduledRequest} implementation.
     * @throws QueueException for any original request.
     */
    <T extends ScheduledRequest> T queue() throws QueueException, VolleyRequestFactoryException;

    /**
     * The request status from this request.
     * @return request status.
     * @see ScheduledRequestStatus
     */
    ScheduledRequestStatus getStatus();
}
