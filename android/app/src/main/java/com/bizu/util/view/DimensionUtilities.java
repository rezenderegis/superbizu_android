package com.bizu.util.view;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by andre.lmello on 10/27/15.
 */
public class DimensionUtilities {

    public static float pixelToDensityPixel(final float px, @NonNull final Context context) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float densityPixelToPixel(final float dp, @NonNull final Context context) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
