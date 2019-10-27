package ir.vanda.hampa.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import ir.vanda.hampa.R;

/**
 * TODO: document your custom view class.
 * <p>
 * bottom menu children view most be linearLayout with image and text
 */
public class BottomMenu extends LinearLayout
{

    private OnBottomMenuItemClick onBottomMenuItemClick;

    private int lastClickedPosition = 100;


    public BottomMenu(Context context)
    {
        super(context);
        init(null, 0);
    }

    public BottomMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public BottomMenu(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);

        config();
    }

    private void config()
    {


        for (int i = 0; i < this.getChildCount(); i++)
        {

            final int finalI = i;

            this.getChildAt(i).setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    setSelected(finalI, (LinearLayout) v);

                    Log.i("mahdi-dev", "bottomMenu : " + v.getClass().getName());
                }
            });


        }
    }

    public void setDefaultSelected(int position)
    {
        setSelected(position, (LinearLayout) this.getChildAt(position));
    }

    private void setSelected(int position, LinearLayout layout)
    {
        if (position != lastClickedPosition)
        {
            //onclick interface
            if (onBottomMenuItemClick != null)
            {
                onBottomMenuItemClick.onItemClick(position);
            }


            //set selected layout

            ((ImageView) layout.getChildAt(0)).setColorFilter(ContextCompat.getColor(getContext(), R.color.bottomMenuSelected), PorterDuff.Mode.SRC_IN);

            ((TextView) layout.getChildAt(1)).setTextColor(ContextCompat.getColor(getContext(), R.color.bottomMenuSelected));

            //unSelect other layout
            for (int i = 0; i < this.getChildCount(); i++)
            {
                if (i != position)
                {
                    unSelected((LinearLayout) this.getChildAt(i));
                }
            }
        }
    }

    private void unSelected(LinearLayout layout)
    {
        ((ImageView) layout.getChildAt(0)).setColorFilter(ContextCompat.getColor(getContext(), R.color.bottomMenuUnSelected), PorterDuff.Mode.SRC_IN);
        ((TextView) layout.getChildAt(1)).setTextColor(ContextCompat.getColor(getContext(), R.color.bottomMenuSelected));
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BottomMenu, defStyle, 0);


        typedArray.recycle();

    }

    public void setOnBottomMenuItemClick(OnBottomMenuItemClick onBottomMenuItemClick)
    {
        this.onBottomMenuItemClick = onBottomMenuItemClick;
    }


    //onClick interface

    public interface OnBottomMenuItemClick
    {
        void onItemClick(int position);
    }
}
