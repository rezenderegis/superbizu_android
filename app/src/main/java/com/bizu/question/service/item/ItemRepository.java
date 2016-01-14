package com.bizu.question.service.item;

import com.bizu.question.Item;
import com.bizu.question.SaveListener;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface ItemRepository {

    public <T> T save(final Item item, final SaveListener listener);

}
