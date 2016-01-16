package com.bizu.question.service.item;

import com.bizu.entity.Item;
import com.bizu.question.SaveListener;

/**
 * Created by fabricio on 1/16/16.
 */
public interface ItemRepository {

    public <T> T save(final Item item, final SaveListener listener);

}
