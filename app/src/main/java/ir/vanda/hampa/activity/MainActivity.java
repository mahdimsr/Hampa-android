package ir.vanda.hampa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import ir.vanda.hampa.BasicActivity;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.BottomMenu;
import ir.vanda.hampa.fragment.MainFragment;
import ir.vanda.hampa.fragment.ProfileFragment;

public class MainActivity extends BasicActivity
{
    private BottomMenu bottomMenu;

    private MainFragment    mainFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        //create objects

        mainFragment    = new MainFragment();
        profileFragment = new ProfileFragment();

        bottomMenu.setOnBottomMenuItemClick(new BottomMenu.OnBottomMenuItemClick()
        {
            @Override
            public void onItemClick(int position)
            {
                switch (position)
                {
                    case 0:

                        showFragment(mainFragment, "mainFragment");

                        break;

                    case 2:

                        showFragment(profileFragment, "profileFragment");

                        break;
                }

                Log.i("bottomMenu-position", "position : " + position);
            }
        });

        bottomMenu.setDefaultSelected(2);

    }

    private void findViews()
    {
        bottomMenu = findViewById(R.id.bottomMenu);
    }


    private void showFragment(Fragment fragment, String tag)
    {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //hide last fragment

        if (getVisibleFragment() != null)
        {
            ft.hide(getVisibleFragment());
        }

        //show current fragment

        if (fragment.isAdded())
        {
            ft.show(fragment);
        }
        else
        {
            ft.add(R.id.frameLayout, fragment);

            ft.addToBackStack(tag);
        }

        ft.commit();

    }

    private Fragment getVisibleFragment()
    {
        FragmentManager fm = getSupportFragmentManager();

        for (Fragment fragment : fm.getFragments())
        {
            if (fragment != null && fragment.isVisible())
            {
                return fragment;
            }
        }

        return null;
    }
}
