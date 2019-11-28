package ir.vanda.hampa.fragment.mainChild;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.HashMap;
import java.util.Objects;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.activity.lessonExam.QuestionActivity;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.component.StatusBar;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.lib.Converter;
import ir.vanda.hampa.model.Grade;
import ir.vanda.hampa.model.Lesson;
import ir.vanda.hampa.model.LessonExam;
import ir.vanda.hampa.retrofit.LessonExamAdd;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonExamDetailsFragment extends BaseFragment implements View.OnClickListener
{


    public LessonExamDetailsFragment()
    {
        // Required empty public constructor
    }

    private LessonExam    lessonExam;
    private ImageView     closeImageView;
    private VandaTextView titleTextView, dateTextView, orientationTextView, exmCodeTextView, questionCountTextView, durationTextView, gradesTextView, lessonsTextView, descriptionTextView, priceTextView;
    private VandaTextView    takeExamButton;
    private NestedScrollView nestedScrollView;
    private RelativeLayout   toolbarLayout;
    private StatusBar        statusBar;
    private RelativeLayout   addToCartLayout;
    private HampaLoader      hampaLoader;
    private CardView         cartCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lesson_exam_details, container, false);
        findViews(v);

        Bundle b = getArguments();

        lessonExam = (LessonExam) b.getSerializable("lessonExam");
        Log.i("lessonExam", lessonExam.toString());

        boolean takeExam = b.getBoolean("takeExam", false);
        if (takeExam)
        {
            cartCardView.setVisibility(View.GONE);
            takeExamButton.setVisibility(View.VISIBLE);

        }

        //initialize
        titleTextView.setText(lessonExam.title);
        dateTextView.setText(lessonExam.persianCreatedAt);
        orientationTextView.setText(lessonExam.orientations.title);
        exmCodeTextView.setText(lessonExam.exm);
        questionCountTextView.setText(lessonExam.questionCount + "");
        durationTextView.setText(lessonExam.duration + "");

        StringBuilder gradesBuilder = new StringBuilder();
        for (Grade grade : lessonExam.grades)
        {
            gradesBuilder.append(grade.title + "  ");
        }

        gradesTextView.setText(gradesBuilder.toString().trim());

        StringBuilder lessonsBuilder = new StringBuilder();
        for (Lesson lesson : lessonExam.lessons)
        {
            lessonsBuilder.append(lesson.title + "  ");
        }

        lessonsTextView.setText(lessonsBuilder.toString().trim());

        descriptionTextView.setText(lessonExam.description);
        priceTextView.setText(lessonExam.price + "");

        takeExamButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Intent i = new Intent(getContext(), QuestionActivity.class);

                    Objects.requireNonNull(getContext()).startActivity(i);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            @Override
            public boolean onPreDraw()
            {
                int statusHeight  = statusBar.getStatusBarHeight();
                int toolbarHeight = toolbarLayout.getMeasuredHeight();

                try
                {

                    int paddingTop = statusHeight + toolbarHeight + Converter.dpToPx(24, Objects.requireNonNull(getContext()));

                    int paddingBottom = getBottomMenuHeight() + Converter.dpToPx(24, getContext());

                    nestedScrollView.setPadding(0, paddingTop, 0, paddingBottom);

                }
                catch (Exception e)
                {
                    e.printStackTrace();

                    Log.e("lessonExamDetailsError", e.toString());
                }


                return true;
            }
        });

    }

    private void findViews(View v)
    {
        statusBar     = v.findViewById(R.id.statusBar);
        toolbarLayout = v.findViewById(R.id.toolbarLayout);

        nestedScrollView = v.findViewById(R.id.nesterScrollView);


        closeImageView = v.findViewById(R.id.closeImage);
        closeImageView.setOnClickListener(this);

        titleTextView         = v.findViewById(R.id.titleTextView);
        dateTextView          = v.findViewById(R.id.dateTextView);
        orientationTextView   = v.findViewById(R.id.orientationTextView);
        exmCodeTextView       = v.findViewById(R.id.exmCodeTextView);
        questionCountTextView = v.findViewById(R.id.questionCountTextView);
        durationTextView      = v.findViewById(R.id.durationTextView);
        gradesTextView        = v.findViewById(R.id.gradesTextView);
        lessonsTextView       = v.findViewById(R.id.lessonsTextView);
        descriptionTextView   = v.findViewById(R.id.descriptionTextView);
        priceTextView         = v.findViewById(R.id.priceTextView);

        addToCartLayout = v.findViewById(R.id.addToCartLayout);
        addToCartLayout.setOnClickListener(this);

        hampaLoader = v.findViewById(R.id.hampaLoader);

        cartCardView   = v.findViewById(R.id.cartCardView);
        takeExamButton = v.findViewById(R.id.takeExamButton);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.closeImage:

                getActivity().onBackPressed();

                break;
            case R.id.addToCartLayout:

                addToCart();

                break;
        }
    }


    private void addToCart()
    {

        YoYo.with(Techniques.ZoomIn).duration(400).onStart(new YoYo.AnimatorCallback()
        {
            @Override
            public void call(Animator animator)
            {
                hampaLoader.setVisibility(View.VISIBLE);
                hampaLoader.startAnimate();


                addToCartLayout.setVisibility(View.GONE);
            }
        }).playOn(hampaLoader);

        HashMap<String, String> data = new HashMap<>();

        data.put("exm", lessonExam.exm);

        Call<LessonExamAdd> lessonExamAddCall = getService().lessonExamsAdd(data);
        lessonExamAddCall.enqueue(new Callback<LessonExamAdd>()
        {
            @Override
            public void onResponse(Call<LessonExamAdd> call, Response<LessonExamAdd> response)
            {
                Response<LessonExamAdd> res  = response;
                LessonExamAdd           body = res.body();

                Log.i("lessonExamDetailsRes", res.body() + "");


                if (body.status.equals("OK"))
                {
                    setCartCount(body.cartCount);

                    makeToast("آزمون به سبد خرید افزوده شد.");
                }
                else if (body.status.equals("ERROR"))
                {
                    makeToast(body.errorMessage);
                }
                else if (body.status.equals("Validation"))
                {
                    makeToast("مشکلی در سامانه وجود دارد.");
                }

                hampaLoader.setVisibility(View.GONE);

                addToCartLayout.setVisibility(View.VISIBLE);


            }

            @Override
            public void onFailure(Call<LessonExamAdd> call, Throwable t)
            {
                Log.i("lessonExamDetailsError", t.toString() + "");
            }
        });


    }

}
