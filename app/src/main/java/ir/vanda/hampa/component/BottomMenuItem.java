package ir.vanda.hampa.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import ir.vanda.hampa.R;

/**
 * TODO: document your custom view class.
 */
public class BottomMenuItem extends RelativeLayout
{
    private ImageView icon, dot;
    private VandaTextView title, itemCount;
    private boolean isActive = false, hasCount = false;

    public BottomMenuItem(Context context)
    {
        super(context);
        init(null, 0);
    }

    public BottomMenuItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public BottomMenuItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void inflate()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.component_bottom_menu_item, this);

        icon = findViewById(R.id.icon);
        dot  = findViewById(R.id.dot);

        title     = findViewById(R.id.title);
        itemCount = findViewById(R.id.itemCount);

    }

    private void init(AttributeSet attrs, int defStyle)
    {
        //inflate
        inflate();


        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BottomMenuItem, defStyle, 0);

        int iconRes = typedArray.getResourceId(R.styleable.BottomMenuItem_icon, 0);

        if (iconRes == 0)
        {
            icon.setImageResource(R.drawable.ic_home);
        }
        else
        {
            icon.setImageResource(iconRes);
        }

        String titleText = typedArray.getString(R.styleable.BottomMenuItem_title);

        if (titleText != null)
        {
            title.setText(titleText);
        }
        else
        {
            title.setText("عنوان منو");
        }

        typedArray.recycle();

    }

    public void active()
    {
        icon.setVisibility(GONE);
        title.setVisibility(VISIBLE);
        dot.setVisibility(VISIBLE);

        if (hasCount)
        {
            itemCount.setVisibility(GONE);
        }


        if (isActive)
        {
            YoYo.with(Techniques.Wobble).duration(200).playOn(title);

            Log.i("itemAnimation", "item is animated");
        }

        isActive = true;
    }


    public void deActive()
    {
        icon.setVisibility(VISIBLE);
        title.setVisibility(GONE);
        dot.setVisibility(GONE);

        if (hasCount)
        {
            itemCount.setVisibility(VISIBLE);
        }


        isActive = false;
    }

    public void setItemCount(String count)
    {
        itemCount.setText(count);
        itemCount.setVisibility(VISIBLE);

        hasCount = true;
    }


    public void setNoCount()
    {
        hasCount = false;
    }

}
