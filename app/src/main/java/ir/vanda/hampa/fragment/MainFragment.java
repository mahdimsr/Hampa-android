package ir.vanda.hampa.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.fragment.mainChild.LessonExamsFragment;
import ir.vanda.hampa.retrofit.Index;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener
{


    public MainFragment()
    {
        // Required empty public constructor
    }

    private RelativeLayout lessonExamLayout;


    private LessonExamsFragment lessonExamsFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        findView(v);

        lessonExamsFragment = new LessonExamsFragment();

        Call<Index> indexCall = getService().index();

        indexCall.enqueue(new Callback<Index>()
        {
            @Override
            public void onResponse(Call<Index> call, Response<Index> response)
            {
                Response<Index> res   = response;
                Index           index = res.body();

                Log.i("indexRes", index.status + "");

            }

            @Override
            public void onFailure(Call<Index> call, Throwable t)
            {
                Log.i("indexResError", t.toString());
            }
        });


        return v;
    }

    private void findView(View v)
    {
        lessonExamLayout = v.findViewById(R.id.lessonExamLayout);
        lessonExamLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lessonExamLayout:

                showFragment(lessonExamsFragment,"lessonExamFragment");

                break;
        }
    }
}
