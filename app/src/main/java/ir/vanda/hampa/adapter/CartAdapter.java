package ir.vanda.hampa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vanda.hampa.R;
import ir.vanda.hampa.model.Cart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder>
{
    private List<Cart> carts;
    private Context    context;

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
    public void onBindViewHolder(@NonNull CartHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return carts.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder
    {
        public CartHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
