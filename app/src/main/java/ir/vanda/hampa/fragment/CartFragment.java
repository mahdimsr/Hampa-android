package ir.vanda.hampa.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.adapter.CartAdapter;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.lib.Converter;
import ir.vanda.hampa.model.Cart;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseFragment
{


    public CartFragment()
    {
        // Required empty public constructor
    }

    private ConstraintLayout parentLayout;
    private RecyclerView     cartRecyclerView;
    private CardView         cardPayment;
    private VandaTextView    payButton;
    private CartAdapter      cartAdapter;
    private List<Cart>       cartList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        findViews(v);

        //initialize
        cartList = new ArrayList<>();

        for (int i = 0; i < 15; i++)
        {
            cartList.add(new Cart());
        }

        cartAdapter = new CartAdapter(getContext(), cartList);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);

        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setAdapter(cartAdapter);


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

                try
                {
                    int bottomMenuHeight  = getBottomMenuHeight();
                    int cardPaymentHeight = cardPayment.getMeasuredHeight();
                    int payButtonHeight   = payButton.getMeasuredHeight();

                    int parentPaddingBottom   = bottomMenuHeight;
                    int recyclerPaddingBottom = bottomMenuHeight + cardPaymentHeight + parentPaddingBottom ;

                    parentLayout.setPadding(0, 0, 0, parentPaddingBottom);

                    cartRecyclerView.setPadding(0,0,0,recyclerPaddingBottom);


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                return true;
            }
        });

    }

    private void findViews(View v)
    {
        parentLayout = v.findViewById(R.id.parentLayout);

        cartRecyclerView = v.findViewById(R.id.cartRecyclerView);

        cardPayment = v.findViewById(R.id.cardPayment);
        payButton   = v.findViewById(R.id.payButton);
    }

}
