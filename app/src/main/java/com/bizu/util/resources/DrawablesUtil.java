package com.bizu.util.resources;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import codetail.graphics.drawables.LollipopDrawablesCompat;

/**
 * Created by andre.lmello on 03/11/15.
 */
public final class DrawablesUtil {
    final public static Drawable getDrawable(final int id, final Resources resources,
                                             final Resources.Theme theme) {
        return LollipopDrawablesCompat.getDrawable(resources, id, theme);
    }
}
