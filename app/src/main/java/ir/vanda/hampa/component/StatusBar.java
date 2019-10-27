package ir.vanda.hampa.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class StatusBar extends RelativeLayout
{
    public StatusBar(Context context)
    {
        super(context);
        init();
    }

    public StatusBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public StatusBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    public StatusBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init()
    {
        setMinimumHeight(getStatusBarHeight());
    }

    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
