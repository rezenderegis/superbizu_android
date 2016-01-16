package com.bizu.question.service.matter;

import com.bizu.question.SaveListener;
import com.bizu.entity.Matter;

/**
 * Created by fabricio on 1/16/16.
 */
public interface MatterRepository {

    public <T> T save(final Matter matter, final SaveListener listener);


}
