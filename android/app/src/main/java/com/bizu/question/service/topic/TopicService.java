package com.bizu.question.service.topic;

import com.bizu.entity.Topic;
import com.bizu.network.ServiceListener;

import java.util.List;

/**
 * Created by andre.lmello on 11/25/15.
 */
public interface TopicService {
    <T> T retrieveFromServer(final ServiceListener<List<Topic>> listener);
}
