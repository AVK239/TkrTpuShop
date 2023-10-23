package com.tkr.tkrtpushop.Cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tkr.tkrtpushop.R;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class  CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartProduct> cartProducts;

    public CartAdapter(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartProduct cartProduct = cartProducts.get(position);
        holder.bind(cartProduct);
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView productNameTextView;
        private TextView productPriceTextView;
        private TextView quantityTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.product_name1);
            productPriceTextView = itemView.findViewById(R.id.product_price1);
            quantityTextView = itemView.findViewById(R.id.quantity);
        }

        public void bind(CartProduct cartProduct) {
            productNameTextView.setText(cartProduct.getTitle());
            productPriceTextView.setText(cartProduct.getPrice());
            quantityTextView.setText(String.valueOf(cartProduct.getQuantity()));
        }
    }
}

