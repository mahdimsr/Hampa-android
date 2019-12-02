package ir.vanda.hampa.fragment.mainChild;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.adapter.QuestionAdapter;
import ir.vanda.hampa.component.HampaDialog;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.component.QuestionView;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.model.LessonExam;
import ir.vanda.hampa.model.Question;
import ir.vanda.hampa.retrofit.Answer;
import ir.vanda.hampa.retrofit.FinishExam;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonExamQuestionFragment extends BaseFragment
{


    public LessonExamQuestionFragment()
    {
        // Required empty public constructor
    }

    private RecyclerView    questionRecycler;
    private QuestionAdapter adapter;
    private List<Question>  questionList;
    private List<Answer>    answerList;
    private LessonExam      lessonExam;
    private HampaLoader     hampaLoader;
    private VandaTextView   doneButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lesson_question_exam, container, false);
        findViews(v);

        hampaLoader.startAnimate();

        answerList = new ArrayList<>();

        final Bundle b = getArguments();

        lessonExam   = (LessonExam) b.getSerializable("lessonExam");
        questionList = lessonExam.questions;

        adapter = new QuestionAdapter(getContext(), questionList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        questionRecycler.setLayoutManager(layoutManager);
        questionRecycler.setAdapter(adapter);

        hampaLoader.setVisibility(View.GONE);
        questionRecycler.setVisibility(View.VISIBLE);


        doneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                JsonArray jsonArray = new JsonArray();

                for (int i = 0; i < adapter.getQuestionViewList().size(); i++)
                {
                    JsonObject jsonObject = new JsonObject();

                    QuestionView q = adapter.getQuestionViewList().get(i);


                    jsonObject.addProperty("id", q.getQuestion().id);
                    jsonObject.addProperty("answer", q.getAnswer());


                    jsonArray.add(jsonObject);
                }

                JsonObject body = new JsonObject();


                body.add("questions", jsonArray);


                Log.i("answer", body.toString());


                Call<FinishExam> finishExamCall = getService().finishExam(lessonExam.id, body);
                finishExamCall.enqueue(new Callback<FinishExam>()
                {
                    @Override
                    public void onResponse(Call<FinishExam> call, Response<FinishExam> response)
                    {
                        Response<FinishExam> res = response;

                        FinishExam body = res.body();

                        if (res.isSuccessful())
                        {
                            if (body.status.equals("OK"))
                            {
                                if (body.isDone)
                                {
                                    makeToast(body.message);
                                }
                            }
                        }
                        else
                        {
                            try
                            {
                                JSONObject jObjError = new JSONObject(res.errorBody().string());

                                makeToast(jObjError.getJSONObject("error").getString("message") + "");
                            }
                            catch (IOException | JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FinishExam> call, Throwable t)
                    {
                        Log.i("answersError", t.toString());
                    }
                });
            }
        });

        return v;
    }


    private void findViews(View v)
    {
        hampaLoader      = v.findViewById(R.id.hampaLoader);
        questionRecycler = v.findViewById(R.id.recyclerView);

        doneButton = v.findViewById(R.id.doneButton);
    }

}
