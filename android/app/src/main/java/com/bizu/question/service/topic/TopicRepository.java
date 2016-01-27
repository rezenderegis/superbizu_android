package com.bizu.question.service.topic;

import com.bizu.android.database.SaveListener;
import com.bizu.entity.Topic;

import java.util.List;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface TopicRepository {

    public <T> T save(final Topic topic, final SaveListener<Topic> listener);
    public <T> T save(final List<Topic> topics, final SaveListener<List<Topic>> listener);

}
