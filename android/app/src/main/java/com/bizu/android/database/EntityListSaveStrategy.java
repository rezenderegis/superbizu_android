package com.bizu.android.database;

import java.util.List;

/**
 * Created by almde on 13/01/2016.
 */
public interface EntityListSaveStrategy<T> {
    List<T> save(final List<T> entityList, final RepositoryOpenHelper helper);
}
