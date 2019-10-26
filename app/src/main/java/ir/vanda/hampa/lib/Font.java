package ir.vanda.hampa.lib;

import android.content.Context;
import android.graphics.Typeface;

public class Font
{
    private static String path = "fonts/";

    public static Typeface iranSans(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(), path + "IRANSans.ttf");
    }

    public static Typeface iranSans_bold(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(), path + "IRANSans_Bold.ttf");
    }

    public static Typeface iranSans_medium(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(), path + "IRANSans_Medium.ttf");
    }

    public static Typeface iranSans_ultraLight(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(), path + "IRANSans_UltraLight.ttf");
    }

    public static Typeface iranSans_light(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(), path + "IRANSansLight.ttf");
    }

}
