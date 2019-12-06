package ir.vanda.hampa.fragment.profileChild;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends BaseFragment
{


    public AboutUsFragment()
    {
        // Required empty public constructor
    }

    private ImageView closeImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about_us, container, false);
        findViews(v);


        closeImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().onBackPressed();
            }
        });

        return v;
    }

    private void findViews(View v)
    {
        closeImageView = v.findViewById(R.id.closeImage);
    }

}
