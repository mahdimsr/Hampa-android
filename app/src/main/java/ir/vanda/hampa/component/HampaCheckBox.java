package ir.vanda.hampa.component;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import ir.vanda.hampa.R;

/**
 * TODO: document your custom view class.
 */
public class HampaCheckBox extends RelativeLayout
{
    private RelativeLayout parent;
    private ImageView      icon;
    private VandaTextView  title;
    private Boolean        isChecked = false;
    private Drawable       activeBackground, defaultBackground;

    public HampaCheckBox(Context context)
    {
        super(context);
        init(null, 0);
    }

    public HampaCheckBox(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public HampaCheckBox(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void inflate()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.component_hampa_check_box, this);

        parent            = findViewById(R.id.parentLayout);
        defaultBackground = parent.getBackground();

        icon  = findViewById(R.id.icon);
        title = findViewById(R.id.title);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        //inflate
        inflate();

        //onClick
        this.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                check();
            }
        });


        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HampaCheckBox, defStyle, 0);

        String titleText = typedArray.getString(R.styleable.HampaCheckBox_titleText);
        title.setText(titleText);

        activeBackground = typedArray.getDrawable(R.styleable.HampaCheckBox_bgDrawable);

        ColorStateList tint = typedArray.getColorStateList(R.styleable.HampaCheckBox_tintColor);
        icon.setImageTintList(tint);


        typedArray.recycle();

    }

    private void check()
    {
        isChecked = !isChecked;

        if (isChecked)
        {
            icon.setImageResource(R.drawable.ic_check);
            parent.setBackground(activeBackground);
        }
        else
        {
            icon.setImageDrawable(null);
            parent.setBackground(defaultBackground);
        }
    }

    public void setCheck(boolean check)
    {
        isChecked = check;

        check();
    }

    public Boolean isChecked()
    {
        return isChecked;
    }

}
