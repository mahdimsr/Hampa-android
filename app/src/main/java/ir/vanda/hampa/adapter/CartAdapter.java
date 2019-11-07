package ir.vanda.hampa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vanda.hampa.R;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.model.Cart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder>
{
    private List<Cart>            carts;
    private Context               context;
    private OnRemoveClickListener onRemoveClickListener;

    public CartAdapter(Context context, List<Cart> carts)
    {
        this.context = context;
        this.carts   = carts;
    }


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new CartHolder(LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, final int position)
    {
        final Cart cart = carts.get(position);

        holder.title.setText(cart.lesson_exam.title);
        holder.exm.setText(cart.lesson_exam.exm);
        holder.price.setText(cart.lesson_exam.price + "");


        holder.remove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onRemoveClickListener != null)
                {
                    onRemoveClickListener.onRemove(cart, position);
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return carts.size();
    }

    public void setOnRemoveClickListener(OnRemoveClickListener onRemoveClickListener)
    {
        this.onRemoveClickListener = onRemoveClickListener;
    }

    public class CartHolder extends RecyclerView.ViewHolder
    {
        public VandaTextView title, exm, price;
        public ImageView   remove;

        public CartHolder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            exm   = itemView.findViewById(R.id.exm);
            price = itemView.findViewById(R.id.price);

            remove = itemView.findViewById(R.id.remove);

        }
    }



    public interface OnRemoveClickListener
    {
        void onRemove(Cart cart, int position);
    }

}
