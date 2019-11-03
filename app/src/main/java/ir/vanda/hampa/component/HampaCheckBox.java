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
    private String         value;
    private Boolean        isChecked = false;
    private Drawable       activeBackground, defaultBackground;
    private OnCheckListener onCheckListener;

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
                check(null);

                if (onCheckListener != null)
                {
                    onCheckListener.OnCheck();
                }
            }
        });


        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HampaCheckBox, defStyle, 0);

        String titleText = typedArray.getString(R.styleable.HampaCheckBox_titleText);
        title.setText(titleText);

        value = typedArray.getString(R.styleable.HampaCheckBox_value);

        activeBackground = typedArray.getDrawable(R.styleable.HampaCheckBox_bgDrawable);

        ColorStateList tint = typedArray.getColorStateList(R.styleable.HampaCheckBox_tintColor);
        icon.setImageTintList(tint);


        typedArray.recycle();

    }

    private void check(Boolean check)
    {
        if (check == null)
        {
            isChecked = !isChecked;
        }

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

        check(check);
    }


    public String getValue()
    {
        return value;
    }

    public Boolean isChecked()
    {
        return isChecked;
    }

    public void setOnCheckListener(OnCheckListener onCheckListener)
    {
        this.onCheckListener = onCheckListener;
    }


    public interface OnCheckListener
    {
        void OnCheck();
    }
}
