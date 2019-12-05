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
    private VandaTextView wrongAnswerRowValue, correctAnswerRowValue, allQuestionsRowValue;

    private LessonExam lessonExam;

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

                    wrongAnswerRowValue.setText(result.wrongAnswers + "");
                    correctAnswerRowValue.setText(result.correctAnswers + "");
                    allQuestionsRowValue.setText(result.getQuestionCount() + "");

                    wrongAnswerValue.setText(result.wrongAnswers + "");
                    correctAnswerValue.setText(result.correctAnswers + "");
                    noAnswerTitle.setText(result.blankAnswers + "");
                    allQuestionsValue.setText(result.getQuestionCount() + "");


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

        wrongAnswerRowValue   = v.findViewById(R.id.wrongAnswerRowValue);
        correctAnswerRowValue = v.findViewById(R.id.correctAnswerRowValue);
        allQuestionsRowValue  = v.findViewById(R.id.allQuestionsRowValue);
    }


}
