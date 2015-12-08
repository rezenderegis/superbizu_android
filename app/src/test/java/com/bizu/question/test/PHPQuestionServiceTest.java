package com.bizu.question.test;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.bizu.network.ScheduledRequest;
import com.bizu.question.Question;
import com.bizu.question.service.PHPQuestionService;
import com.bizu.question.service.QuestionService;
import com.bizu.question.service.UpdateSchedule;
import com.bizu.network.ScheduledRequestStatus;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by andre.lmello on 11/25/15.
 */
public class PHPQuestionServiceTest {

    @BeforeClass
    public static void setUp() {
        final RequestQueue requestQueue = mock(RequestQueue.class);
        final Request volleyRequest = any(Request.class);
        when(requestQueue.add(volleyRequest)).thenReturn(volleyRequest);
        sService = new PHPQuestionService(requestQueue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEntryDataValidation_IllegalArgumentForNullQuestion(){
        sService.update(null);
        fail("must throw an Exception");
    }

    @Test
    public void testUpdateSchedule_HappyPath() {
        final Question validQuestion = new Question(1L);
        final UpdateSchedule schedule = sService.update(validQuestion);

        assertNotNull("Invalid or valid schedule attempt must be different from null.", schedule);
        assertNotNull("Schedule id can not be null.", schedule.getId());
        assertNotNull("UpdateSchedule status can not be null.", schedule.getStatus());
        statusValidationOnSchedule(schedule.getStatus());
        assertTrue("UpdateSchedule must be assigned from ScheduledRequest.",
                ScheduledRequest.class.isAssignableFrom(schedule.getClass()));
    }

    private void statusValidationOnSchedule(final ScheduledRequestStatus status) {
        assertTrue("Async behavior. Expected to be running or loading on happy path use case",
                ScheduledRequestStatus.LOADING == status || ScheduledRequestStatus.RUNNING == status);
    }

    private static QuestionService sService;
}
