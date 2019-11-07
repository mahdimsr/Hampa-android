package ir.vanda.hampa.fragment.profileChild;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.adapter.TransactionAdapter;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.model.Transaction;
import ir.vanda.hampa.retrofit.TransactionsList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionsFragment extends BaseFragment
{


    public TransactionsFragment()
    {
        // Required empty public constructor
    }

    private RecyclerView       transactionRecycler;
    private TransactionAdapter adapter;
    private HampaLoader        loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transactions, container, false);
        findViews(v);

        loader.startAnimate();

        Call<TransactionsList> transactionsListCall = getService().transactions();
        transactionsListCall.enqueue(new Callback<TransactionsList>()
        {
            @Override
            public void onResponse(Call<TransactionsList> call, Response<TransactionsList> response)
            {
                Response<TransactionsList> res  = response;
                TransactionsList           body = res.body();

                loader.setVisibility(View.GONE);

                if (res.isSuccessful())
                {
                    if (body.status.equals("OK"))
                    {
                        List<Transaction> transactions = body.transactions;

                        if (transactions.isEmpty())
                        {

                        }
                        else
                        {
                            adapter = new TransactionAdapter(getContext(), transactions);
                            transactionRecycler.setAdapter(adapter);

                        }
                    }

                }
                else
                {
                    Toast.makeText(getContext(), "مشکلی درسامانه پیش آمده", Toast.LENGTH_SHORT).show();
                }


                Log.i("transactionRes", body.status);
            }

            @Override
            public void onFailure(Call<TransactionsList> call, Throwable t)
            {
                Log.i("transactionResError", t.toString());
            }
        });


        return v;
    }


    private void findViews(View v)
    {
        transactionRecycler = v.findViewById(R.id.transactionRecycler);
        loader              = v.findViewById(R.id.hampaLoader);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        transactionRecycler.setLayoutManager(layoutManager);
    }

}
