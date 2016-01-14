package com.bizu.question.service.matter;

import com.bizu.android.database.SaveListener;
import com.bizu.entity.Matter;

import java.util.List;

/**
 * Created by fabricio on 1/16/16.
 */
public interface MatterRepository {

    public <T> T save(final Matter matter, final SaveListener<Matter> listener);
    public <T> T save(final List<Matter> matters, final SaveListener<List<Matter>> listener);

}
