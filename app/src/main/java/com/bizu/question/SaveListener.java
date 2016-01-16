package com.bizu.question;

import com.bizu.entity.Question;

/**
 * Created by andre.lmello on 12/9/15.
 */
public interface SaveListener {
    void onSaveFinish(final Question savedQuestion);
}
