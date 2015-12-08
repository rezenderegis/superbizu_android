package com.bizu.question.test;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.bizu.network.ScheduledRequestStatus;
import com.bizu.network.VolleyRequestFactory;
import com.bizu.network.VolleyRequestFactoryException;
import com.bizu.question.Question;
import com.bizu.question.service.UpdateSchedule;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by andre.lmello on 12/1/15.
 */
public class UpdateScheduledRequest {
    @Test
    public void testScheduledRequest_HappyPath() throws VolleyRequestFactoryException {
        final RequestQueue queue = mock(RequestQueue.class);
        final VolleyRequestFactory requestFactory = mock(VolleyRequestFactory.class);
        when(requestFactory.newInstance(Question.class, null)).thenReturn(mock(Request.class));

        final UpdateSchedule request = new UpdateSchedule(1L, queue, requestFactory);
        final Request volleyRequest = mock(Request.class);
        when(queue.add(any(Request.class))).thenReturn(volleyRequest);

        final UpdateSchedule scheduledRequest = request.queue();
        final ScheduledRequestStatus status = scheduledRequest.getStatus();
        assertNotNull("Valid requests generates valid scheduledRequests", scheduledRequest);
        assertNotNull("Valid requests has volley requests.", scheduledRequest.getVolleyRequest());
        assertSame(volleyRequest, scheduledRequest.getVolleyRequest());
        assertNotNull("For scheduled requests, status must not be null", status);
        assertTrue("Queued requests must be RUNNING.", status.equals(ScheduledRequestStatus.RUNNING));
    }
}
