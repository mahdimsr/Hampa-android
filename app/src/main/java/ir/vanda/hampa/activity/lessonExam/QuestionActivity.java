package ir.vanda.hampa.activity.lessonExam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import java.util.List;

import ir.vanda.hampa.BaseActivity;
import ir.vanda.hampa.R;
import ir.vanda.hampa.adapter.QuestionAdapter;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.model.Question;
import ir.vanda.hampa.retrofit.LessonExamQuestions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends BaseActivity
{
    private RecyclerView    recyclerView;
    private QuestionAdapter questionAdapter;
    private VandaTextView   countDown;
    private HampaLoader     hampaLoader;
    private CountDownTimer  countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_lesson_exam);
        findViews();

        hampaLoader.startAnimate();


        Call<LessonExamQuestions> lessonExamQuestionsCall = getService().lessonExamQuestions(1);
        lessonExamQuestionsCall.enqueue(new Callback<LessonExamQuestions>()
        {
            @Override
            public void onResponse(Call<LessonExamQuestions> call, Response<LessonExamQuestions> response)
            {
                Response<LessonExamQuestions> res = response;

                Log.i("questions", res.toString() + "");

                hampaLoader.setVisibility(View.GONE);


                if (res.isSuccessful())
                {
                    List<Question> questionList = res.body().lessonExam.questions;

                    questionAdapter = new QuestionAdapter(QuestionActivity.this, questionList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(QuestionActivity.this, LinearLayoutManager.VERTICAL, false);

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(questionAdapter);

                    recyclerView.setVisibility(View.VISIBLE);

                    inintializeCountdown(5000);

                }
                else
                {
                    makeToast("خطا در اتصال");
                }

            }

            @Override
            public void onFailure(Call<LessonExamQuestions> call, Throwable t)
            {
                hampaLoader.setVisibility(View.GONE);

                Log.i("questionsError", t.toString() + "");
            }
        });


    }

    private void findViews()
    {
        recyclerView = findViewById(R.id.recyclerView);

        countDown = findViewById(R.id.countDown);


        hampaLoader = findViewById(R.id.hampaLoader);
    }

    private void inintializeCountdown(int duration)
    {
        countDownTimer = new CountDownTimer(duration,1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                countDown.setText(millisUntilFinished/1000 + "");
            }

            @Override
            public void onFinish()
            {
                makeToast("زمان به پایان رسید.");
            }
        }.start();
    }
}
