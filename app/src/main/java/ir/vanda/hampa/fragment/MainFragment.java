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
public class MainFragment extends Fragment
{


    public MainFragment()
    {
        // Required empty public constructor
    }

    private Button        button;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        findView(v);

        mainActivity = ((MainActivity) getActivity());

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Call<Index> indexCall = mainActivity.getService().index();

                indexCall.enqueue(new Callback<Index>()
                {
                    @Override
                    public void onResponse(Call<Index> call, Response<Index> response)
                    {
                        Log.i("index", response.body() + "");
                    }

                    @Override
                    public void onFailure(Call<Index> call, Throwable t)
                    {
                        Log.i("index", "error" + t.getMessage());
                    }
                });

            }
        });


        return v;
    }

    private void findView(View v)
    {
        button = v.findViewById(R.id.test);
    }

}
