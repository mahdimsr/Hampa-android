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
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ir.vanda.hampa.R;

/**
 * TODO: document your custom view class.
 */
public class HampaLoader extends RelativeLayout
{

    private ImageView largeImage, mediumImage, smallImage;

    public HampaLoader(Context context)
    {
        super(context);
        init(null, 0);
    }

    public HampaLoader(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public HampaLoader(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void inflate()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.component_hampa_loader, this);

        largeImage = findViewById(R.id.largeImage);
        mediumImage = findViewById(R.id.mediumImage);
        smallImage = findViewById(R.id.smallImage);


    }

    private void init(AttributeSet attrs, int defStyle)
    {
        //inflate
        inflate();

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.HampaLoader, defStyle, 0);


        a.recycle();

    }

    public void startAnimate()
    {
        largeImage.setAnimation(loadingAnimation(360f,0));

        mediumImage.setAnimation(loadingAnimation(270f,200));

        smallImage.setAnimation(loadingAnimation(180f,300));
    }


    private Animation loadingAnimation(float toDegrees,int delay)
    {
        Animation rotate = new RotateAnimation(0f, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setDuration(2000);

        rotate.setStartOffset(delay);

        rotate.setRepeatMode(Animation.REVERSE);

        rotate.setRepeatCount(Animation.INFINITE);

        rotate.start();

        return  rotate;
    }


}
