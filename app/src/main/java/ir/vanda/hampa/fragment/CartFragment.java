package ir.vanda.hampa.fragment;


import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.activity.ErrorActivity;
import ir.vanda.hampa.adapter.CartAdapter;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.fragment.profileChild.TransactionsFragment;
import ir.vanda.hampa.lib.Converter;
import ir.vanda.hampa.model.Cart;
import ir.vanda.hampa.model.Student;
import ir.vanda.hampa.retrofit.IndexCart;
import ir.vanda.hampa.retrofit.Purchase;
import ir.vanda.hampa.retrofit.RemoveCart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener
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
    private EditText         discountInput;
    private LinearLayout     emptyLayout;

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

        getCartList();

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
        payButton.setOnClickListener(this);

        discountInput = v.findViewById(R.id.discountInput);
        emptyLayout   = v.findViewById(R.id.emptyLayout);
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);

        if (!hidden)
        {
            getCartList();
        }

    }


    private void getCartList()
    {
        hampaLoader.setVisibility(View.VISIBLE);

        Call<IndexCart> indexCartCall = getService().indexCart();
        indexCartCall.enqueue(new Callback<IndexCart>()
        {
            @Override
            public void onResponse(Call<IndexCart> call, Response<IndexCart> response)
            {
                Response<IndexCart> res       = response;
                IndexCart           indexCart = res.body();

                if (res.isSuccessful())
                {
                    if (indexCart.status.equals("OK"))
                    {
                        if (indexCart.carts.isEmpty())
                        {
                            ifCartEmpty(true);
                        }
                        else
                        {
                            ifCartEmpty(false);

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
                }
                else
                {
                    Intent i = new Intent(getContext(), ErrorActivity.class);

                    i.putExtra("title", "خطای سبد خرید من");
                    i.putExtra("type", res.message());
                    i.putExtra("code", res.code());

                    startActivity(i);
                }

                hampaLoader.setVisibility(View.GONE);

                Log.i("indexCartRes", indexCart.status);
            }

            @Override
            public void onFailure(Call<IndexCart> call, Throwable t)
            {
                Log.i("indexCartResError", t.toString());

                Intent i = new Intent(getContext(), ErrorActivity.class);

                i.putExtra("title", "خطای سبد خرید من");
                i.putExtra("type", t.toString());
                i.putExtra("code", t.getMessage());

                startActivity(i);
            }
        });
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

                                if (body.cartCount != null)
                                {
                                    setCartCount(body.cartCount);
                                }
                                else
                                {
                                    setCartCount(0);
                                }
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
                        Toast.makeText(getContext(), "درخواست به خطا دارد.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.payButton:

                HashMap<String, List<Integer>> data = new HashMap<>();
                List<Integer> cartIdList = new ArrayList<>();

                for (Cart cart : cartList)
                {
                    cartIdList.add(cart.id);
                }

                data.put("cartId", cartIdList);


                Call<Purchase> purchaseCall = getService().purchase(data);
                purchaseCall.enqueue(new Callback<Purchase>()
                {
                    @Override
                    public void onResponse(Call<Purchase> call, Response<Purchase> response)
                    {
                        Response<Purchase> res      = response;
                        Purchase           purchase = res.body();

                        Log.i("purchaseRes", purchase.status);

                        if (purchase.status.equals("OK"))
                        {

                            Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();

                            TransactionsFragment transactionsFragment = new TransactionsFragment();

                            showFragment(transactionsFragment, "transactionFragment");

                            setCartCount(0);

                        }
                        else if (purchase.status.equals("ERROR"))
                        {
                            Toast.makeText(getContext(), purchase.errorMessage, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Purchase> call, Throwable t)
                    {
                        Log.e("purchaseResError", t.toString());
                    }
                });


                break;
        }
    }


    private void ifCartEmpty(boolean isEmpty)
    {
        if (isEmpty)
        {
            cardPayment.setVisibility(View.GONE);
            payButton.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.GONE);

            emptyLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            cardPayment.setVisibility(View.VISIBLE);
            payButton.setVisibility(View.VISIBLE);
            cartRecyclerView.setVisibility(View.VISIBLE);

            emptyLayout.setVisibility(View.GONE);
        }
    }
}
