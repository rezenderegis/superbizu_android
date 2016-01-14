package com.bizu.question.service.TopicQuestion;

import com.bizu.android.database.SaveListener;
import com.bizu.entity.TopicQuestion;

import java.util.List;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface TopicQuestionRepository {

    public <T> T save(final TopicQuestion topicQuestion, final SaveListener<TopicQuestion> listener);
    public <T> T save(final List<TopicQuestion> topicQuestion, final SaveListener<List<TopicQuestion>> listener);

}
