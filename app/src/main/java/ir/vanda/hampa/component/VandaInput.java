package ir.vanda.hampa.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import ir.vanda.hampa.R;
import ir.vanda.hampa.lib.Font;

/**
 * TODO: document your custom view class.
 */
public class VandaInput extends RelativeLayout
{
    private ImageView      icon;
    private EditText       input;
    private VandaTextView  error;
    private RelativeLayout inputLayer;

    public VandaInput(Context context)
    {
        super(context);
        init(null, 0);
    }

    public VandaInput(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public VandaInput(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void inflate()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.component_vanda_input, this);

        icon = findViewById(R.id.iconImage);

        input = findViewById(R.id.input);
        input.setTypeface(Font.iranSans_light(getContext()));

        error = findViewById(R.id.error);

        inputLayer = findViewById(R.id.inputLayer);

    }

    public EditText getInput()
    {
        return input;
    }

    public ImageView getIcon()
    {
        return icon;
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        //inflate
        inflate();

        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VandaInput, defStyle, 0);

        int    iconReference = typedArray.getResourceId(R.styleable.VandaInput_iconImage, 0);
        String hintReference = typedArray.getString(R.styleable.VandaInput_hint);

        if (iconReference != 0)
        {
            icon.setImageResource(iconReference);
        }

        if (hintReference != null)
        {
            input.setHint(hintReference);
        }

        typedArray.recycle();
    }


    public void setError(String errorText)
    {
        if (errorText != null)
        {
            error.setText(errorText);

            setErrorStyle();
        }
        else
        {
            setNormal();
        }
    }


    public void setNormal()
    {
        inputLayer.setBackground(getResources().getDrawable(R.drawable.bg_vanda_input));

        error.setVisibility(GONE);
    }


    private void setErrorStyle()
    {
        YoYo.with(Techniques.Shake).duration(200).playOn(this);

        error.setTextColor(getResources().getColor(R.color.colorHasError));
        error.setVisibility(VISIBLE);

        inputLayer.setBackground(getResources().getDrawable(R.drawable.bg_vanda_input_error));
    }
}
