package com.bizu.question.service.TopicQuestion;

import com.bizu.entity.TopicQuestion;
import com.bizu.network.ServiceListener;

import java.util.List;

/**
 * Created by andre.lmello on 11/25/15.
 */
public interface TopicQuestionService {
    <T> T retrieveFromServer(final ServiceListener<List<TopicQuestion>> listener);
}
