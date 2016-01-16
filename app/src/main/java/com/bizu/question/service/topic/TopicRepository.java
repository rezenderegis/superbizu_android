package com.bizu.question.service.topic;

import com.bizu.entity.Topic;
import com.bizu.entity.TopicQuestion;
import com.bizu.question.SaveListener;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface TopicRepository {

    public <T> T save(final Topic topic, final SaveListener listener);


}
