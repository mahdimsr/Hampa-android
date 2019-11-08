package ir.vanda.hampa.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import ir.vanda.hampa.BaseActivity;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.BottomMenu;
import ir.vanda.hampa.component.BottomMenuItem;
import ir.vanda.hampa.fragment.CartFragment;
import ir.vanda.hampa.fragment.MainFragment;
import ir.vanda.hampa.fragment.ProfileFragment;
import ir.vanda.hampa.model.Cart;

public class MainActivity extends BaseActivity
{
    private BottomMenu     bottomMenu;
    private BottomMenuItem cartItem;

    private FragmentManager     fm;
    private FragmentTransaction ft;

    private MainFragment    mainFragment;
    private ProfileFragment profileFragment;
    private CartFragment    cartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        //create objects

        fm = getSupportFragmentManager();


        mainFragment    = new MainFragment();
        profileFragment = new ProfileFragment();
        cartFragment    = new CartFragment();

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

                    case 1:

                        showFragment(cartFragment, "cartFragment");

                        break;

                    case 2:

                        showFragment(profileFragment, "profileFragment");

                        break;
                }

                Log.i("bottomMenu-position", "position : " + position);
            }
        });

        bottomMenu.setDefaultSelected(0);

    }

    private void findViews()
    {
        bottomMenu = findViewById(R.id.bottomMenu);
        cartItem   = findViewById(R.id.cartItem);
    }


    public void showFragment(Fragment fragment, String tag)
    {
        ft = getSupportFragmentManager().beginTransaction();

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
            ft.add(R.id.frameLayout, fragment, tag);
            ft.addToBackStack(null);

        }


        ft.commit();

    }


    public void showFragmentByAnim(Fragment fragment, String tag, boolean isNewSection)
    {

        ft = getSupportFragmentManager().beginTransaction();

        if (isNewSection)
        {
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else
        {
            ft.setCustomAnimations(R.anim.slide_in_top, R.anim.hold, R.anim.hold, R.anim.slide_out_bottom);
        }


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

        for (Fragment fragment : fm.getFragments())
        {
            if (fragment != null && fragment.isVisible())
            {
                return fragment;
            }
        }

        return null;
    }


    public int getBottomMenuHeight()
    {
        return bottomMenu.getMeasuredHeight();
    }


    public void setCartCount(int count)
    {
        if (count != 0)
        {
            cartItem.setItemCount(count + "");
        }
        else
        {
            cartItem.setNoCount();
        }
    }


    public void bottomMenuAnimate(String mode)
    {
        bottomMenu.animate(mode);
    }

}
