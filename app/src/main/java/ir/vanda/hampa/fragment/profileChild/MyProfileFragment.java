package ir.vanda.hampa.fragment.profileChild;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.vanda.hampa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment
{


    public MyProfileFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);
        findViews(v);


        return v;
    }


    private void findViews(View v)
    {

    }
}
