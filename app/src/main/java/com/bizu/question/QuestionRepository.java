package com.bizu.question;

import com.bizu.entity.Question;
import com.bizu.android.database.RetrieveListener;
import com.bizu.android.database.SaveListener;
import java.util.List;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface QuestionRepository {
    public <T> T save(final Question question, final SaveListener<Question> listener);
    public <T> T save(final List<Question> question, SaveListener<List<Question>> listener);
    public <T> T retrieveAllQuestion(RetrieveListener<List<Question>> listener);
}
