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
import ir.vanda.hampa.model.Discount;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.Holder>
{

    private Context        context;
    private List<Discount> discountList;

    public DiscountAdapter(Context context, List<Discount> discountList)
    {
        this.context      = context;
        this.discountList = discountList;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.viewholder_discount, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {
        Discount discount = discountList.get(position);
    }

    @Override
    public int getItemCount()
    {
        return discountList.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        public VandaTextView typeText, expireDateText, codeText, codeCountText, discountValueText;

        public Holder(@NonNull View itemView)
        {
            super(itemView);

            typeText          = itemView.findViewById(R.id.typeText);
            expireDateText    = itemView.findViewById(R.id.expireDateText);
            codeText          = itemView.findViewById(R.id.codeText);
            codeCountText     = itemView.findViewById(R.id.codeCountText);
            discountValueText = itemView.findViewById(R.id.discountValueText);

        }
    }
}
