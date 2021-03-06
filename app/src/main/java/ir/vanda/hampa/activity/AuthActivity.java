package ir.vanda.hampa.activity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import ir.vanda.hampa.BaseActivity;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.component.VandaInput;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.lib.Error;
import ir.vanda.hampa.lib.Rotate3dAnimation;
import ir.vanda.hampa.model.Student;
import ir.vanda.hampa.retrofit.Login;
import ir.vanda.hampa.retrofit.Register;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends BaseActivity
{
    private LinearLayout tabLayout;
    private ViewPager    viewPager;

    private VandaInput usernameLoginInput, passwordLoginInput;
    private VandaInput usernameRegister, passwordRegister, repeatPasswordRegister;
    private HampaLoader hampaLoader;

    private VandaTextView submit, formError;

    private FormAdapter formAdapter;

    private String state = "login"; // login or register


    private ImageView smallGreen, lightBlue, purple;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        findViews();

        if (getStorage().has("student"))
        {
            Student student = (Student) getStorage().get("student");

            usernameLoginInput.getInput().setText(student.mobile);
            passwordLoginInput.getInput().setText(student.password);
        }

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

                    }
                    else
                    {
                        unSelectTab((VandaTextView) tabLayout.getChildAt(i));
                    }
                }

                // 0 is login ---- 1 is signUp
                if (position == 0)
                {
                    state = "login";

                }
                else if (position == 1)
                {
                    state = "register";
                }


                //config submit button
                rotateSubmit(state);

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
                hampaLoader.setVisibility(View.VISIBLE);
                hampaLoader.startAnimate();

                if (state.equals("login"))
                {

                    String username = usernameLoginInput.getInput().getText().toString().trim();
                    String password = passwordLoginInput.getInput().getText().toString().trim();

                    HashMap<String, String> data = new HashMap<>();

                    data.put("mobile", username);
                    data.put("password", password);

                    Call<Login> loginCall = getService().login(data);

                    loginCall.enqueue(new Callback<Login>()
                    {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response)
                        {
                            Response<Login> res = response;

                            Login login = res.body();

                            hampaLoader.setVisibility(View.GONE);

                            Log.i("loginRes", res.toString());

                            if (res.isSuccessful())
                            {
                                if (login.status.equals("Validation"))
                                {
                                    Error error = new Error(login.errors);

                                    usernameLoginInput.setError(error.get("mobile"));
                                    passwordLoginInput.setError(error.get("password"));

                                }
                                else if (login.status.equals("ERROR"))
                                {

                                    String error = login.errorMessage;

                                    Error.setError(formError, error);

                                }
                                else if (login.status.equals("OK"))
                                {
                                    Student student = login.student;
                                    student.access_token = login.access_token;

                                    getStorage().put(student, "student");

                                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                                    finish();
                                }


                                //set make form normal
                                if (!login.status.equals("Validation"))
                                {
                                    usernameLoginInput.setNormal();
                                    passwordLoginInput.setNormal();
                                }

                                if (!login.status.equals("ERROR"))
                                {
                                    formError.setVisibility(View.GONE);
                                }

                            }
                            else
                            {
                                Intent i = new Intent(AuthActivity.this, ErrorActivity.class);

                                i.putExtra("title", "خطای صفحه لاگین و ثبت نام");
                                i.putExtra("type", res.message());
                                i.putExtra("code", res.code());

                                startActivity(i);
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t)
                        {
                            Log.i("loginResError", t.toString());
                        }
                    });

                }
                else if (state.equals("register"))
                {
                    String username       = usernameRegister.getInput().getText().toString().trim();
                    String paswword       = passwordRegister.getInput().getText().toString().trim();
                    String repeatPassword = repeatPasswordRegister.getInput().getText().toString().trim();

                    HashMap<String, String> data = new HashMap<>();

                    data.put("mobile", username);
                    data.put("password", paswword);
                    data.put("repeatPassword", repeatPassword);

                    Call<Register> registerCall = getService().register(data);

                    registerCall.enqueue(new Callback<Register>()
                    {
                        @Override
                        public void onResponse(Call<Register> call, Response<Register> response)
                        {
                            Response<Register> res      = response;
                            Register           register = res.body();

                            Log.i("registerRes", res.errorBody() + "");

                            hampaLoader.setVisibility(View.GONE);

                            if (res.isSuccessful())
                            {
                                if (register.status.equals("Validation"))
                                {
                                    Error error = new Error(register.errors);

                                    usernameRegister.setError(error.get("mobile"));
                                    passwordRegister.setError(error.get("password"));
                                    repeatPasswordRegister.setError(error.get("repeatPassword"));
                                }
                                else if (register.status.equals("OK"))
                                {
                                    Student student = register.student;
                                    student.access_token = register.access_token;

                                    getStorage().put(student, "student");

                                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                                    finish();
                                }

                                //make normal
                                if (!register.status.equals("Validation"))
                                {
                                    usernameRegister.setNormal();
                                    passwordRegister.setNormal();
                                    repeatPasswordRegister.setNormal();
                                }
                            }
                            else
                            {
                                Intent i = new Intent(AuthActivity.this, ErrorActivity.class);

                                i.putExtra("title", "خطای صفحه لاگین و ثبت نام");
                                i.putExtra("type", res.message());
                                i.putExtra("code", res.code());

                                startActivity(i);
                            }

                        }

                        @Override
                        public void onFailure(Call<Register> call, Throwable t)
                        {
                            Log.e("registerResError", t.toString() + "");
                        }
                    });
                }
            }
        });

    }

    private void findViews()
    {
        tabLayout   = findViewById(R.id.tabLayout);
        viewPager   = findViewById(R.id.viewpager);
        submit      = findViewById(R.id.submit);
        formError   = findViewById(R.id.formError);
        hampaLoader = findViewById(R.id.hampaLoader);

        usernameLoginInput = findViewById(R.id.username);
        usernameLoginInput.getInput().setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        usernameLoginInput.getInput().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);

        passwordLoginInput = findViewById(R.id.password);
        passwordLoginInput.getInput().setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        passwordLoginInput.getInput().setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        passwordLoginInput.getInput().setTransformationMethod(new PasswordTransformationMethod());

        usernameRegister = findViewById(R.id.usernameRegister);
        usernameRegister.getInput().setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        usernameRegister.getInput().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);

        passwordRegister = findViewById(R.id.passwordRegister);
        passwordRegister.getInput().setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        passwordRegister.getInput().setTransformationMethod(new PasswordTransformationMethod());

        repeatPasswordRegister = findViewById(R.id.repeatPasswordRegister);
        repeatPasswordRegister.getInput().setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        repeatPasswordRegister.getInput().setTransformationMethod(new PasswordTransformationMethod());

        lightBlue  = findViewById(R.id.circleLightBlue);
        smallGreen = findViewById(R.id.circleSmallGreen);
        purple     = findViewById(R.id.circlePurple);

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
    private void rotateSubmit(String state)
    {
        int duration = 300;

        if (state.equals("login"))
        {
            //rotate -180
            Animation a = new Rotate3dAnimation(0, -180, submit.getWidth() / 2, 0, 0, true);

            a.setDuration(duration);

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

        }
        else if (state.equals("register"))
        {
            //rotate +180
            Animation a = new Rotate3dAnimation(0, 180, submit.getWidth() / 2, 0, 0, true);

            a.setDuration(duration);

            a.setAnimationListener(new Animation.AnimationListener()
            {
                @Override
                public void onAnimationStart(Animation animation)
                {

                }

                @Override
                public void onAnimationEnd(Animation animation)
                {
                    submit.setText(getString(R.string.register));
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

        float randomX = random.nextFloat() * 200;
        float randomY = random.nextFloat() * 200;

        Log.i("mahdi-dev", "random: " + randomX);


        final Animation a = new TranslateAnimation(0, randomX, 0, randomY);

        a.setDuration(5000);

        a.setRepeatCount(Animation.INFINITE);

        a.setRepeatMode(Animation.REVERSE);


        v.startAnimation(a);

    }
}
