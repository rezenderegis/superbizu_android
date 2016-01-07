package com.bizu.question.test;

import android.os.AsyncTask;
import android.test.AndroidTestCase;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.bizu.network.UpdateListener;
import com.bizu.question.AsyncQuestionRepository;
import com.bizu.question.Question;
import com.bizu.question.item.QuestionItem;
import com.bizu.question.SaveListener;
import com.bizu.question.service.PHPQuestionService;

import java.util.ArrayList;

/**
 * Created by andre.lmello on 12/9/15.
 */
public class QuestionAsyncTest extends AndroidTestCase {
    public void testAsyncUpdate() throws Throwable {
        mUpdateQuestion = null;
        isListenerCalled = false;
        long starTime = System.currentTimeMillis();
        final Question question = new Question(1L, "name", new ArrayList<QuestionItem>());
        final Request<Question> request = question.update(new UpdateListener<Question>() {
            @Override
            public void onResponse(Question response, Throwable error) {
                mUpdateQuestion = response;
                isListenerCalled = true;
                mAsyncUpdateThrowable = error;
            }
        }, new PHPQuestionService(Volley.newRequestQueue(getContext())));

        while (!isListenerCalled) {
            Thread.sleep(1000);
        }

        assertTrue(isListenerCalled);
        if (mAsyncUpdateThrowable != null) throw mAsyncUpdateThrowable;
        assertNotNull(mUpdateQuestion);
    }

    public void testAsyncSave() throws InterruptedException {
        isListenerCalled = false;
        final Question question = new Question("name", new ArrayList<QuestionItem>());
        final AsyncTask asyncTask = question.save(new SaveListener() {
            @Override
            public void onSaveFinish(Question savedQuestion) {
                isListenerCalled = true;
                assertNotNull(savedQuestion);
                assertTrue(savedQuestion.getId().equals(1L));
            }
        }, new AsyncQuestionRepository(getContext()));

        while(asyncTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            Thread.sleep(1000);
        }

        assertTrue(isListenerCalled);
    }

    private boolean isListenerCalled = false;
    private Question mUpdateQuestion = null;
    private Throwable mAsyncUpdateThrowable = null;
}
