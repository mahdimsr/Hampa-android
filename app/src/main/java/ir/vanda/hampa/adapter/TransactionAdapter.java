package ir.vanda.hampa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vanda.hampa.R;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.model.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.Holder>
{
    private List<Transaction> transactionList;
    private Context           context;

    public TransactionAdapter(Context context, List<Transaction> transactionList)
    {
        this.context         = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.viewholder_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {
        Transaction transaction = transactionList.get(position);

        holder.type.setText(transaction.persian_itemType);
        holder.code.setText(transaction.code);
        holder.paymentPrice.setText(transaction.price + "");
    }

    @Override
    public int getItemCount()
    {
        return transactionList.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        public VandaTextView type, code, paymentPrice;

        public Holder(@NonNull View itemView)
        {
            super(itemView);

            type         = itemView.findViewById(R.id.type);
            code         = itemView.findViewById(R.id.code);
            paymentPrice = itemView.findViewById(R.id.paymentPrice);
        }
    }
}
