package ir.vanda.hampa.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import ir.vanda.hampa.R;
import ir.vanda.hampa.lib.Font;

/**
 * TODO: document your custom view class.
 */
@SuppressLint("AppCompatCustomView")
public class VandaTextView extends TextView
{
    public VandaTextView(Context context)
    {
        super(context);
        init(null, 0);
    }

    public VandaTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public VandaTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VandaTextView, defStyle, 0);

        Typeface typeface = null;

        int fontName = typedArray.getInt(R.styleable.VandaTextView_fontName, 5);

        switch (fontName)
        {
            case 1:

                typeface = Font.iranSans(getContext());

                break;

            case 2:

                typeface = Font.iranSans_bold(getContext());

                break;

            case 3:

                typeface = Font.iranSans_medium(getContext());

                break;

            case 4:

                typeface = Font.iranSans_light(getContext());

                break;

            case 5:

                typeface = Font.iranSans_ultraLight(getContext());

                break;
        }


        setTypeface(typeface);

        typedArray.recycle();
    }


}
