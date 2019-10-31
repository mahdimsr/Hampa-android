package ir.vanda.hampa;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.vanda.hampa.activity.MainActivity;
import ir.vanda.hampa.retrofit.Service;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment
{


    public BaseFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }


    public Service getService()
    {
        return ((MainActivity) getContext()).getService();
    }

}
