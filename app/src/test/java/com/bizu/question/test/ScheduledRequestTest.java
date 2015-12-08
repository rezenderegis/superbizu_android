package com.bizu.question.test;

import com.bizu.network.QueueException;
import com.bizu.network.ScheduledRequest;
import com.bizu.network.ScheduledRequestStatus;
import com.bizu.network.VolleyRequestFactoryException;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by andre.lmello on 12/1/15.
 */
public class ScheduledRequestTest {
    @Test
    public void testSchedule_HappyPath() throws QueueException, VolleyRequestFactoryException {
        final ScheduledRequest request = mock(ScheduledRequest.class);
        when(request.queue()).thenReturn(request);
        when(request.getStatus()).thenReturn(Arrays.asList(ScheduledRequestStatus.values()).get(new Random().nextInt(2)));

        final ScheduledRequest scheduledRequest = request.queue();
        assertNotNull("A valid schedule must always return one ScheduledRequest", scheduledRequest);
        assertSame("No new request is needed to be created, for better tracing"
                , request, scheduledRequest);
        assertNotNull("Is expected every request to have a status.", request.getStatus());
        assertTrue("After schedule, status can be loading or running task, due to the async " +
                "property of the that.", request.getStatus() == ScheduledRequestStatus.RUNNING
        || request.getStatus() == ScheduledRequestStatus.LOADING);
    }

    @Test
    public void testSchedule_ExpceptionPath() throws QueueException, VolleyRequestFactoryException {
        final ScheduledRequest request = mock(ScheduledRequest.class);
        when(request.queue()).thenThrow(QueueException.class);

        ScheduledRequest scheduledRequest = null;
        try {
            scheduledRequest = request.queue();
        } catch (QueueException e) {
            assertNull("For invalid request, no request must be returned.", scheduledRequest);
        }

    }

}
