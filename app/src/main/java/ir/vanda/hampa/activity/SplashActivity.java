package ir.vanda.hampa.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import ir.vanda.hampa.BasicActivity;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.HampaLoader;

public class SplashActivity extends BasicActivity
{

    private HampaLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViews();

        loader.startAnimate();

    }


    private void findViews()
    {
        loader = findViewById(R.id.hampaLoader);
    }

}
