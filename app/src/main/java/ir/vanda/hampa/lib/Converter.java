package ir.vanda.hampa.lib;

import android.content.Context;
import android.util.DisplayMetrics;

public class Converter
{
    public static float pxToDp(int px, Context context)
    {
        return px / ((float) context.getApplicationContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int dpToPx(float dp, Context context)
    {
        return (int) (dp * ((float) context.getApplicationContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
