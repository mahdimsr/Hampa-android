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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Objects;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.StatusBar;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.fragment.profileChild.MyExamFragment;
import ir.vanda.hampa.fragment.profileChild.MyProfileFragment;
import ir.vanda.hampa.fragment.profileChild.TransactionsFragment;
import ir.vanda.hampa.lib.Converter;
import ir.vanda.hampa.model.Student;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener
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
    private Student      student;
    private LinearLayout myProfileLayout, transactionLayout, myExamLayout;

    //animations variables
    private int fgHoverHeight, toolbarHeight, profileLayoutHeight, statusBarHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(v);

        fillInformation();

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


                int paddingTop = statusBarHeight + Converter.dpToPx(24, Objects.requireNonNull(getContext()));

                toolbarContent.setPadding(0, paddingTop, 0, 0);

                toolbarHover.getLayoutParams().height = toolbarContent.getMeasuredHeight();
                toolbarHover.requestLayout();

                nestedScrollView.setPadding(0, profileLayoutHeight - 30, 0, 180);

                return true;
            }
        });

    }

    private void findViews(View v)
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

        myProfileLayout = v.findViewById(R.id.myProfileLayout);
        myProfileLayout.setOnClickListener(this);

        transactionLayout = v.findViewById(R.id.transactionLayout);
        transactionLayout.setOnClickListener(this);

        myExamLayout = v.findViewById(R.id.myExamLayout);
        myExamLayout.setOnClickListener(this);
    }


    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!hidden)
        {
            bottomMenuAnimate("show");
        }
        else
        {
            fillInformation();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.myProfileLayout:

                MyProfileFragment myProfileFragment = new MyProfileFragment();

                bottomMenuAnimate("hide");
                showFragmentByAnim(myProfileFragment, "myProfile", false);

                break;

            case R.id.transactionLayout:

                TransactionsFragment transactionsFragment = new TransactionsFragment();

                bottomMenuAnimate("hide");
                showFragmentByAnim(transactionsFragment, "transactionFragment", false);

                break;

            case R.id.myExamLayout:

                MyExamFragment myExamFragment = new MyExamFragment();

                bottomMenuAnimate("hide");
                showFragmentByAnim(myExamFragment, "myExamFragment", false);

                break;
        }
    }

    private void fillInformation()
    {
        //fill information
        student = (Student) getStorage().get("student");

        if (student.name == null || student.familyName == null)
        {
            fullNameTextView.setText(R.string.incompleteFullName);
            fullNameTextView.setTextSize(15);
        }
        else
        {
            fullNameTextView.setText(student.name + " " + student.familyName);
        }

        if (student.city == null)
        {
            locationTextView.setVisibility(View.GONE);
        }
    }
}
