package com.bizu.question;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface QuestionRepository {

    public <T> T save(final Question question, final SaveListener listener);

}