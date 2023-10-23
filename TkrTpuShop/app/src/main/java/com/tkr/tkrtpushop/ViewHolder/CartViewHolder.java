package com.tkr.tkrtpushop.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.tkr.tkrtpushop.R;

public class CartViewHolder extends RecyclerView.ViewHolder
{
    public ImageView imageView1; // Добавлен ImageView
    public TextView txtProductName1, txtProductDescription1, txtProductPrice1;

    public CartViewHolder(View itemView)
    {
        super(itemView);
        imageView1 = itemView.findViewById(R.id.product_image1); // Привязка ImageView к макету
        txtProductName1 = itemView.findViewById(R.id.product_name1);
        txtProductDescription1 = itemView.findViewById(R.id.product_description1);
        txtProductPrice1 = itemView.findViewById(R.id.product_price1);
    }
}