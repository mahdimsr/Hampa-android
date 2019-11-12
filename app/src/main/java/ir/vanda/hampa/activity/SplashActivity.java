package ir.vanda.hampa.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Random;

import ir.vanda.hampa.BaseActivity;
import ir.vanda.hampa.BuildConfig;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.HampaDialog;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.retrofit.CheckUpdate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends BaseActivity
{

    private HampaLoader loader;
    private ImageView   logo, smallGreen, lightBlue, purple, red;
    private String version_name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViews();
        loader.startAnimate();

        checkUpdateCallback();

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
        logo       = findViewById(R.id.logo);
        lightBlue  = findViewById(R.id.circleLightBlue);
        smallGreen = findViewById(R.id.circleSmallGreen);
        purple     = findViewById(R.id.circlePurple);
        red        = findViewById(R.id.circleRed);

        randomTransition(lightBlue);
        randomTransition(smallGreen);
        randomTransition(purple);
        randomTransition(red);
    }


    private void changeActivity()
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

    private void checkUpdateCallback()
    {
        HashMap<String, String> body = new HashMap<>();
        body.put("version", "1.0");

        final Call<CheckUpdate> checkUpdateCall = getService().checkUpdate(body);
        checkUpdateCall.enqueue(new Callback<CheckUpdate>()
        {
            @Override
            public void onResponse(final Call<CheckUpdate> call, Response<CheckUpdate> response)
            {
                Response<CheckUpdate> res  = response;
                CheckUpdate           body = res.body();

                if (res.isSuccessful())
                {
                    if (body.status.equals("OK"))
                    {
                        if (body.hasUpdate)
                        {
                            HampaDialog dialog = new HampaDialog(SplashActivity.this);
                            dialog.show();

                            if (body.forceUpdate)
                            {
                                dialog.setValues(R.drawable.bg_update, body.title, body.message, "بروزرسانی", null);

                                dialog.setOnDefaultClickLisetener(new HampaDialog.OnDefaultClickListener()
                                {
                                    @Override
                                    public void onClick(Dialog dialog)
                                    {
                                        dialog.dismiss();
                                    }
                                });

                            }
                            else
                            {
                                dialog.setValues(R.drawable.bg_update, body.title, body.message, "بروزرسانی", "بیخیال");

                                dialog.setOnDefaultClickLisetener(new HampaDialog.OnDefaultClickListener()
                                {
                                    @Override
                                    public void onClick(Dialog dialog)
                                    {

                                    }
                                });

                                dialog.setOnCancelClickLisetener(new HampaDialog.OnCancelClickListener()
                                {
                                    @Override
                                    public void onClick(Dialog dialog)
                                    {
                                        dialog.dismiss();
                                        changeActivity();
                                    }
                                });


                            }
                        }
                        else
                        {
                            new Handler().postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    changeActivity();
                                }
                            }, 200);
                        }
                    }
                }
                else
                {
                    HampaDialog failureDialog = onFailureDialog(SplashActivity.this);

                    failureDialog.setOnDefaultClickLisetener(new HampaDialog.OnDefaultClickListener()
                    {
                        @Override
                        public void onClick(Dialog dialog)
                        {
                            call.clone();
                            dialog.dismiss();
                        }
                    });

                }
            }

            @Override
            public void onFailure(final Call<CheckUpdate> call, Throwable t)
            {

                HampaDialog failureDialog = onFailureDialog(SplashActivity.this);

                failureDialog.setOnDefaultClickLisetener(new HampaDialog.OnDefaultClickListener()
                {
                    @Override
                    public void onClick(Dialog dialog)
                    {
                        checkUpdateCallback();
                        dialog.dismiss();
                    }
                });


            }
        });

    }

}
