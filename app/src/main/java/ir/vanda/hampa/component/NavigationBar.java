package ir.vanda.hampa.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class NavigationBar extends RelativeLayout
{
    public NavigationBar(Context context)
    {
        super(context);
        init();
    }

    public NavigationBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init()
    {
        Log.i("mahdi-dev", "navigation height : " + getNavigationBarHeight());

        setMinimumHeight(getNavigationBarHeight());
    }

    public int getNavigationBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
