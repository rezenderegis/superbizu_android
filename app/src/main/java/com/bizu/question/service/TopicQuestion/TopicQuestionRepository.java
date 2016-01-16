package com.bizu.question.service.TopicQuestion;

import com.bizu.entity.Item;
import com.bizu.entity.TopicQuestion;
import com.bizu.question.SaveListener;

import java.util.List;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface TopicQuestionRepository {

    public <T> T save(final TopicQuestion topicQuestion, final SaveListener listener);


}
