package ir.vanda.hampa.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.BasicActivity;
import ir.vanda.hampa.R;
import ir.vanda.hampa.activity.MainActivity;
import ir.vanda.hampa.retrofit.Index;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment
{


    public MainFragment()
    {
        // Required empty public constructor
    }

    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        findView(v);

        mainActivity = ((MainActivity) getActivity());

        Call<Index> indexCall = getService().index();

        indexCall.enqueue(new Callback<Index>()
        {
            @Override
            public void onResponse(Call<Index> call, Response<Index> response)
            {
                Response<Index> res   = response;
                Index           index = res.body();

                Log.i("indexRes",index.status+ "");

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

    }

}
