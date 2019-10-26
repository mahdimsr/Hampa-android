package ir.vanda.hampa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.Random;

import ir.vanda.hampa.R;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.lib.Rotate3dAnimation;

public class AuthActivity extends AppCompatActivity
{
    private LinearLayout tabLayout;
    private ViewPager viewPager;

    private VandaTextView submit;

    private FormAdapter formAdapter;

    private String state = "login"; // login or signUp


    private ImageView smallGreen, lightBlue, purple;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        findViews();

        formAdapter = new FormAdapter();

        viewPager.setAdapter(formAdapter);
        viewPager.setCurrentItem(0);

        //tab select
        for (int i = 0; i < tabLayout.getChildCount(); i++)
        {
            final int finalI = i;

            tabLayout.getChildAt(i).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    viewPager.setCurrentItem(finalI, true);
                }
            });
        }


        //view pager change
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                // select tab
                for (int i = 0; i < tabLayout.getChildCount(); i++)
                {
                    if (i == position)
                    {
                        selectTab((VandaTextView) tabLayout.getChildAt(i));

                    } else
                    {
                        unSelectTab((VandaTextView) tabLayout.getChildAt(i));
                    }
                }

                // 0 is login ---- 1 is signUp
                if (position == 0)
                {
                    state = "login";

                } else if (position == 1)
                {
                    state = "signUp";
                }


                //config submit button
                configSubmit(state);

                Log.i("mahdi-dev", "state : " + state);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });


        //submit click
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            }
        });

    }

    private void findViews()
    {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        submit = findViewById(R.id.submit);

        lightBlue = findViewById(R.id.circleLightBlue);
        smallGreen = findViewById(R.id.circleSmallGreen);
        purple = findViewById(R.id.circlePurple);

        randomTransition(lightBlue);
        randomTransition(smallGreen);
        randomTransition(purple);
    }


    private void selectTab(VandaTextView view)
    {
        view.setTextColor(Color.WHITE);
        view.setBackgroundResource(R.drawable.bg_auth_tab_item);
    }

    private void unSelectTab(VandaTextView view)
    {
        view.setTextColor(Color.BLACK);
        view.setBackground(null);
    }

    //config submit button
    private void configSubmit(String state)
    {
        if (state.equals("login"))
        {
            //rotate -180
            Animation a = new Rotate3dAnimation(0, -180, submit.getWidth() / 2, 0, 0, true);

            a.setDuration(800);

            a.setAnimationListener(new Animation.AnimationListener()
            {
                @Override
                public void onAnimationStart(Animation animation)
                {

                }

                @Override
                public void onAnimationEnd(Animation animation)
                {
                    submit.setText(getString(R.string.login));
                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {

                }
            });

            submit.startAnimation(a);

        } else if (state.equals("signUp"))
        {
            //rotate +180
            Animation a = new Rotate3dAnimation(0, 180, submit.getWidth() / 2, 0, 0, true);

            a.setDuration(800);

            a.setAnimationListener(new Animation.AnimationListener()
            {
                @Override
                public void onAnimationStart(Animation animation)
                {

                }

                @Override
                public void onAnimationEnd(Animation animation)
                {
                    submit.setText(getString(R.string.signUp));
                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {

                }
            });

            submit.startAnimation(a);

        }
    }


    // pager adapter
    class FormAdapter extends PagerAdapter
    {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position)
        {


            return viewPager.getChildAt(position);
        }

        @Override
        public int getCount()
        {
            return viewPager.getChildCount();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
        {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
        {
            super.destroyItem(container, position, object);
        }
    }


    //circles animation
    private void randomTransition(final View v)
    {
        Random random = new Random();

        float randomX = random.nextFloat() * 400;
        float randomY = random.nextFloat() * 500;

        Log.i("mahdi-dev", "random: " + randomX);


        final Animation a = new TranslateAnimation(0,randomX,0,randomY);

        a.setDuration(5000);

        a.setRepeatCount(Animation.INFINITE);

        a.setRepeatMode(Animation.REVERSE);


        v.startAnimation(a);

    }
}
