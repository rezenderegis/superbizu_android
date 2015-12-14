package com.bizu.question;

import com.bizu.network.UpdateListener;
import com.bizu.question.service.QuestionService;
import com.google.gson.annotations.SerializedName;

/**
 * Domain Class to represent a question. Contains all information to show a question and to make the
 * version control.
 * Created by andre.lmello on 11/25/15.
 */
public class Question {

    @SerializedName("id")
    private Long mId;

    public Question() {
    }

    public Question(final Long id) {
        mId = id;
    }

    /**
     * Async
     * @param <T>
     * @return any return. created for network frameworks.
     */
    public <T> T update(final UpdateListener<Question> listener, final QuestionService service) {
        if (mId != null) {
            return service.update(this, listener);
        } else {
            throw new IllegalStateException("Cannot update questions not loaded to the data base yet.");
        }
    }

    /**
     * Async
     * @param repository
     * @param <T>
     * @return
     */
    public <T> T save(final SaveListener listener, final QuestionRepository repository) {
        return repository.save(this, listener);
    }

    public Long getId() {
        return mId;
    }

    public void setId(final Long id) {
        this.mId = id;
    }
}
