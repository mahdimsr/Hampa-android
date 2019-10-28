package ir.vanda.hampa.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import ir.vanda.hampa.R;

/**
 * TODO: document your custom view class.
 */
public class HampaSearchView extends RelativeLayout
{

    public HampaSearchView(Context context)
    {
        super(context);
        init(null, 0);
    }

    public HampaSearchView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public HampaSearchView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void inflate()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.component_hampa_search_view,this);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        //inflate
        inflate();

        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.HampaSearchView, defStyle, 0);


        typedArray.recycle();

    }

}
