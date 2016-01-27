package com.bizu.question.item;

import com.bizu.android.database.RetrieveListener;
import com.bizu.android.database.SaveListener;

import java.util.List;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface ItemRepository {
    public <T> T save(final Item item, final SaveListener<Item> listener);
    public <T> T save(final List<Item> items, SaveListener<List<Item>> listener);
    public <T> T retrieveAllItems(final RetrieveListener<List<Item>> listener, final Long question);

}
