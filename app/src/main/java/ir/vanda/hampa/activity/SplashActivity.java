package ir.vanda.hampa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Random;

import ir.vanda.hampa.BaseActivity;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.HampaLoader;

public class SplashActivity extends BaseActivity
{

    private HampaLoader loader;
    private ImageView   smallGreen, lightBlue, purple, red;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViews();
        loader.startAnimate();


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (getStorage().has("student"))
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        }, 2000);
    }

    private void randomTransition(final View v)
    {
        Random random = new Random();

        float randomX = random.nextFloat() * 50;
        float randomY = random.nextFloat() * 100;

        Log.i("mahdi-dev", "random: " + randomX);


        final Animation a = new TranslateAnimation(0, randomX, 0, randomY);

        a.setDuration(5000);

        a.setRepeatCount(Animation.INFINITE);

        a.setRepeatMode(Animation.REVERSE);


        v.startAnimation(a);

    }

    private void findViews()
    {
        loader     = findViewById(R.id.hampaLoader);
        lightBlue  = findViewById(R.id.circleLightBlue);
        smallGreen = findViewById(R.id.circleSmallGreen);
        purple     = findViewById(R.id.circlePurple);
        red        = findViewById(R.id.circleRed);

        randomTransition(lightBlue);
        randomTransition(smallGreen);
        randomTransition(purple);
        randomTransition(red);
    }

}
