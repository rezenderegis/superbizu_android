package com.bizu.question;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bizu.network.UpdateListener;
import com.bizu.question.item.QuestionItem;
import com.bizu.question.service.QuestionService;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Domain Class to represent a question. Contains all information to show a question and to make the
 * version control.
 * Created by andre.lmello on 11/25/15.
 */
public class Question {

    @SerializedName("ID_QUESTAO")
    private Long mId;

    @SerializedName("DESCRICAO_QUESTAO")
    private final String mName;

    private final List<QuestionItem> mItems;

    /**
     *
     * @param id
     * @param name
     * @param items
     */
    public Question(@Nullable final Long id, @NonNull final String name
            , @NonNull final List<QuestionItem> items) {
        mId = id;
        mName = name;
        mItems = items;
    }

    public Question(final String name, final List<QuestionItem> items) {
        this(null, name, items);
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

    public String getName() {
        return mName;
    }

}
