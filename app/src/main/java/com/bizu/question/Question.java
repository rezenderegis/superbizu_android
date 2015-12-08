package com.bizu.question;

/**
 * Domain Class to represent a question. Contains all information to show a question and to make the
 * version control.
 * Created by andre.lmello on 11/25/15.
 */
public class Question {

    private Long mId;

    public Question(final Long id) {
        mId = id;
    }

    public Long getId() {
        return mId;
    }

    public void setId(final Long id) {
        this.mId = id;
    }
}
