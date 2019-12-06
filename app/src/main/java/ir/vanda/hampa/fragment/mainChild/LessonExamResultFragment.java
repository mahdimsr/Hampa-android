package ir.vanda.hampa.fragment.mainChild;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.model.LessonExam;
import ir.vanda.hampa.model.Result;
import ir.vanda.hampa.retrofit.LessonExamResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonExamResultFragment extends BaseFragment
{


    public LessonExamResultFragment()
    {
        // Required empty public constructor
    }

    private VandaTextView title;

    private VandaTextView wrongAnswerTitle, correctAnswerTitle, noAnswerTitle, allQuestionsTitle;
    private VandaTextView wrongAnswerValue, correctAnswerValue, noAnswerValue, allQuestionsValue;
    private VandaTextView resultCreatedAt;

    private LessonExam lessonExam;

    private Boolean infoIsShow = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lesson_exam_result, container, false);
        findViews(v);

        final Bundle b = getArguments();

        lessonExam = (LessonExam) b.getSerializable("lessonExam");

        title.setText("نتیجه آزمون" + " " + lessonExam.exm);

        Call<LessonExamResult> resultCall = getService().result(lessonExam.id);

        resultCall.enqueue(new Callback<LessonExamResult>()
        {
            @Override
            public void onResponse(Call<LessonExamResult> call, Response<LessonExamResult> response)
            {
                Response<LessonExamResult> res = response;

                if (res.isSuccessful())
                {
                    LessonExamResult body   = res.body();
                    Result           result = body.result;


                    wrongAnswerValue.setText(result.wrongAnswers + "");
                    correctAnswerValue.setText(result.correctAnswers + "");
                    noAnswerValue.setText(result.blankAnswers + "");
                    allQuestionsValue.setText(result.getQuestionCount() + "");

                    resultCreatedAt.setText(result.persianCreatedAt);

                }
                else
                {
                    makeToast("خطا در اتصال");
                }
            }

            @Override
            public void onFailure(Call<LessonExamResult> call, Throwable t)
            {
                makeToast("خطا در اتصال");
            }
        });


        return v;
    }

    private void findViews(View v)
    {
        title = v.findViewById(R.id.title);

        wrongAnswerTitle   = v.findViewById(R.id.wrongAnswerTitle);
        correctAnswerTitle = v.findViewById(R.id.correctAnswerTitle);
        noAnswerTitle      = v.findViewById(R.id.noAnswerTitle);
        allQuestionsTitle  = v.findViewById(R.id.allQuestionsTitle);

        wrongAnswerValue   = v.findViewById(R.id.wrongAnswerValue);
        correctAnswerValue = v.findViewById(R.id.correctAnswerValue);
        noAnswerValue      = v.findViewById(R.id.noAnswerValue);
        allQuestionsValue  = v.findViewById(R.id.allQuestionsValue);

        resultCreatedAt = v.findViewById(R.id.resultCreatedAt);
    }

    private void showAndHideInfo()
    {
        if (infoIsShow)
        {
            allQuestionsValue.setVisibility(View.GONE);
            allQuestionsTitle.setVisibility(View.VISIBLE);

            correctAnswerValue.setVisibility(View.GONE);
            correctAnswerTitle.setVisibility(View.VISIBLE);

            wrongAnswerValue.setVisibility(View.GONE);
            wrongAnswerTitle.setVisibility(View.VISIBLE);

            noAnswerValue.setVisibility(View.GONE);
            noAnswerTitle.setVisibility(View.VISIBLE);
        }
        else
        {
            allQuestionsValue.setVisibility(View.VISIBLE);
            allQuestionsTitle.setVisibility(View.GONE);

            correctAnswerValue.setVisibility(View.VISIBLE);
            correctAnswerTitle.setVisibility(View.GONE);

            wrongAnswerValue.setVisibility(View.VISIBLE);
            wrongAnswerTitle.setVisibility(View.GONE);

            noAnswerValue.setVisibility(View.VISIBLE);
            noAnswerTitle.setVisibility(View.GONE);
        }

        infoIsShow = !infoIsShow;
    }

}
