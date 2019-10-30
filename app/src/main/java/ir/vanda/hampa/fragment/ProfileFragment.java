package ir.vanda.hampa.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ir.vanda.hampa.R;
import ir.vanda.hampa.activity.MainActivity;
import ir.vanda.hampa.component.StatusBar;
import ir.vanda.hampa.component.VandaTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
{


    public ProfileFragment()
    {
        // Required empty public constructor
    }

    private StatusBar      statusBar;
    private RelativeLayout toolbarLayout, toolbarContent, profileLayout;
    private ImageView     profileImageView;
    private VandaTextView fullNameTextView, locationTextView;
    private NestedScrollView nestedScrollView;
    private View             fgBanner, toolbarHover;

    //animations variables
    private int fgHoverHeight, toolbarHeight, profileLayoutHeight, statusBarHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        finViews(v);

        //scroll event
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {

                int p = statusBarHeight + toolbarHeight;

                int d = profileLayoutHeight - p;
                int s = profileLayoutHeight - statusBarHeight;

                Log.i("scroll Y", scrollY + " " + d);

                if (scrollY >= d)
                {

                    float maxScroll = s - d;

                    float changeScroll = s - scrollY;

                    float alpha = (maxScroll - changeScroll) / maxScroll;

                    toolbarHover.setAlpha(alpha);

                    Log.i("scroll-toolbar", scrollY + "   " + alpha);

                }
                else
                {
                    toolbarHover.setAlpha(0);
                }


                profileLayout.setPadding(0, -scrollY / 2, 0, 0);


            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            @Override
            public boolean onPreDraw()
            {

                statusBarHeight     = statusBar.getStatusBarHeight();
                toolbarHeight       = toolbarLayout.getMeasuredHeight();
                profileLayoutHeight = profileLayout.getMeasuredHeight();

                fgHoverHeight = fgBanner.getMeasuredHeight();

                Log.i("toolbar", toolbarContent.getMeasuredHeight() + "");


                int paddingTop = statusBarHeight + ((MainActivity) getActivity()).dpToPx(getContext().getResources().getDimension(R.dimen.halfSpace));

                toolbarContent.setPadding(0, paddingTop, 0, 0);

                toolbarHover.getLayoutParams().height = toolbarContent.getMeasuredHeight();
                toolbarHover.requestLayout();

                nestedScrollView.setPadding(0, profileLayoutHeight - 30, 0, 180);

                return true;
            }
        });

    }

    private void finViews(View v)
    {
        statusBar        = v.findViewById(R.id.statusBar);
        toolbarLayout    = v.findViewById(R.id.toolbarLayout);
        toolbarContent   = v.findViewById(R.id.toolbarContent);
        profileLayout    = v.findViewById(R.id.profileLayout);
        profileImageView = v.findViewById(R.id.profileImageView);
        toolbarHover     = v.findViewById(R.id.toolbarHover);
        fgBanner         = v.findViewById(R.id.fgBanner);

        nestedScrollView = v.findViewById(R.id.nesterScroll);

        fullNameTextView = v.findViewById(R.id.fullNameTextView);
        locationTextView = v.findViewById(R.id.locationTextView);
    }

}
