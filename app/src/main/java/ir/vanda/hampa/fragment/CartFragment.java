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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.adapter.CartAdapter;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.lib.Converter;
import ir.vanda.hampa.model.Cart;
import ir.vanda.hampa.retrofit.IndexCart;
import ir.vanda.hampa.retrofit.RemoveCart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private HampaLoader      hampaLoader;
    private List<Cart>       cartList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        findViews(v);

        hampaLoader.startAnimate();

        //initialize
        cartList = new ArrayList<>();

        Call<IndexCart> indexCartCall = getService().indexCart();
        indexCartCall.enqueue(new Callback<IndexCart>()
        {
            @Override
            public void onResponse(Call<IndexCart> call, Response<IndexCart> response)
            {
                Response<IndexCart> res       = response;
                IndexCart           indexCart = res.body();

                if (indexCart.status.equals("OK"))
                {
                    if (indexCart.carts.isEmpty())
                    {
                        Toast.makeText(getContext(), "در سبد خرید شما آزمونی وجود ندارد.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        cartList = indexCart.carts;

                        cartAdapter = new CartAdapter(getContext(), cartList);

                        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);

                        cartRecyclerView.setLayoutManager(layoutManager);
                        cartRecyclerView.setAdapter(cartAdapter);

                        initializeCartAdapter();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "مشکلی در سامانه پیش آمده.", Toast.LENGTH_SHORT).show();
                }

                hampaLoader.setVisibility(View.GONE);

                Log.i("indexCartRes", indexCart.status);
            }

            @Override
            public void onFailure(Call<IndexCart> call, Throwable t)
            {
                Log.i("indexCartResError", t.toString());
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

                try
                {
                    int bottomMenuHeight  = getBottomMenuHeight();
                    int cardPaymentHeight = cardPayment.getMeasuredHeight();
                    int payButtonHeight   = payButton.getMeasuredHeight();

                    int parentPaddingBottom   = bottomMenuHeight;
                    int recyclerPaddingBottom = bottomMenuHeight + cardPaymentHeight + parentPaddingBottom;

                    parentLayout.setPadding(0, 0, 0, parentPaddingBottom);

                    cartRecyclerView.setPadding(0, 0, 0, recyclerPaddingBottom);


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

        hampaLoader = v.findViewById(R.id.hampaLoader);

        cartRecyclerView = v.findViewById(R.id.cartRecyclerView);

        cardPayment = v.findViewById(R.id.cardPayment);
        payButton   = v.findViewById(R.id.payButton);
    }


    private void initializeCartAdapter()
    {

        cartAdapter.setOnRemoveClickListener(new CartAdapter.OnRemoveClickListener()
        {
            @Override
            public void onRemove(Cart cart, final int position)
            {
                hampaLoader.setVisibility(View.VISIBLE);
                hampaLoader.startAnimate();

                // remove cart
                HashMap<String, String> data = new HashMap<>();

                data.put("cartId", cart.id + "");

                Call<RemoveCart> removeCartCall = getService().removeCart(data);
                removeCartCall.enqueue(new Callback<RemoveCart>()
                {
                    @Override
                    public void onResponse(Call<RemoveCart> call, Response<RemoveCart> response)
                    {
                        Response<RemoveCart> res  = response;
                        RemoveCart           body = res.body();

                        if (body.status.equals("OK"))
                        {
                            if (body.isCartRemove)
                            {
                                cartList.remove(position);
                                cartAdapter.notifyItemRemoved(position);

                                setCartCount(body.cartCount);
                            }
                        }
                        else if (body.status.equals("ERROR"))
                        {
                            Toast.makeText(getContext(), body.errorMessage, Toast.LENGTH_SHORT).show();
                        }

                        hampaLoader.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<RemoveCart> call, Throwable t)
                    {
                        Toast.makeText(getContext(), "درخواست به گا رفت.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

}
