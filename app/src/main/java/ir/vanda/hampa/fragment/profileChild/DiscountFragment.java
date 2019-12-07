package ir.vanda.hampa.fragment.profileChild;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.adapter.DiscountAdapter;
import ir.vanda.hampa.retrofit.DiscountCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscountFragment extends BaseFragment
{


    public DiscountFragment()
    {
        // Required empty public constructor
    }

    private ImageView       closeImage;
    private RecyclerView    recyclerView;
    private DiscountAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_discount, container, false);
        findViews(v);


        Call<DiscountCode> discountCodeCall = getService().discountCodes();
        discountCodeCall.enqueue(new Callback<DiscountCode>()
        {
            @Override
            public void onResponse(Call<DiscountCode> call, Response<DiscountCode> response)
            {
                Response<DiscountCode> res = response;

                DiscountCode body = res.body();

                if (res.isSuccessful())
                {
                    if (body.discounts.isEmpty())
                    {
                        makeToast("کد تخفیفی وجود ندارد");
                    }
                    else
                    {
                        adapter = new DiscountAdapter(getContext(), body.discounts);
                        recyclerView.setAdapter(adapter);
                    }

                }

            }

            @Override
            public void onFailure(Call<DiscountCode> call, Throwable t)
            {

            }
        });


        closeImage.setOnClickListener(new View.OnClickListener()
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
        closeImage = v.findViewById(R.id.closeImage);

        recyclerView = v.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

}
