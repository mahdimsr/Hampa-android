package ir.vanda.hampa.fragment.profileChild;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.activity.AuthActivity;
import ir.vanda.hampa.activity.ErrorActivity;
import ir.vanda.hampa.adapter.MyExamAdapter;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.fragment.mainChild.LessonExamDetailsFragment;
import ir.vanda.hampa.model.LessonExam;
import ir.vanda.hampa.retrofit.MyExams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyExamFragment extends BaseFragment
{


    public MyExamFragment()
    {
        // Required empty public constructor
    }

    private MyExamAdapter adapter;
    private RecyclerView  myExamRecyclerView;
    private HampaLoader   hampaLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_exam, container, false);
        findViews(v);

        hampaLoader.startAnimate();

        //initialize
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        myExamRecyclerView.setLayoutManager(layoutManager);


        Call<MyExams> myExamsCall = getService().myExams();
        myExamsCall.enqueue(new Callback<MyExams>()
        {
            @Override
            public void onResponse(Call<MyExams> call, Response<MyExams> response)
            {
                Response<MyExams> res     = response;
                MyExams           myExams = res.body();

                Log.e("myExamsRes", res.code() + "");

                hampaLoader.setVisibility(View.GONE);

                if (res.isSuccessful())
                {
                    if (myExams.status.equals("OK"))
                    {
                        List<LessonExam> examList = myExams.exams;

                        adapter = new MyExamAdapter(getContext(), examList);

                        myExamRecyclerView.setAdapter(adapter);

                        adapter.setOnItemClickListener(new MyExamAdapter.OnItemClickListener()
                        {
                            @Override
                            public void onClick(LessonExam lessonExam)
                            {
                                LessonExamDetailsFragment detailsFragment = new LessonExamDetailsFragment();

                                Bundle b = new Bundle();

                                b.putSerializable("lessonExam", lessonExam);
                                b.putBoolean("takeExam", true);

                                detailsFragment.setArguments(b);

                                showFragmentByAnim(detailsFragment, "detailsLessonExam", false);
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(getContext(), "مشکلی در سامانه بوجود آمده", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Intent i = new Intent(getContext(), ErrorActivity.class);

                    i.putExtra("title", "خطای آزمون های من");
                    i.putExtra("type", res.message());
                    i.putExtra("code", res.code());

                    startActivity(i);

                    getActivity().finish();
                }


            }

            @Override
            public void onFailure(Call<MyExams> call, Throwable t)
            {
                Log.e("myExamsError", t.toString());

                hampaLoader.setVisibility(View.GONE);
            }
        });


        return v;
    }

    private void findViews(View v)
    {
        myExamRecyclerView = v.findViewById(R.id.myExamRecyclerView);
        hampaLoader        = v.findViewById(R.id.hampaLoader);
    }

}
