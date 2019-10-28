package ir.vanda.hampa.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import ir.vanda.hampa.R;

/**
 * TODO: document your custom view class.
 */
@SuppressLint("AppCompatCustomView")
public class RoundedImageView extends ImageView
{
    private int radius;
    private Path path;
    private RectF rect;

    public RoundedImageView(Context context)
    {
        super(context);
        init(null, 0);
    }

    public RoundedImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        //objects
        path = new Path();

        // Load attributes
        final TypedArray typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyle, 0);

        radius = typedArray.getInt(R.styleable.RoundedImageView_radius, 50);

        typedArray.recycle();

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);

        super.onDraw(canvas);
    }
}
