package com.tkr.tkrtpushop.ViewHolder;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.tkr.tkrtpushop.Interface.ItemClickListner;
import com.tkr.tkrtpushop.Model.Products;
import com.tkr.tkrtpushop.R;
import com.tkr.tkrtpushop.Users.HomeActivity;

import java.util.HashMap;

import io.reactivex.rxjava3.annotations.NonNull;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView, imageView1;
    public ItemClickListner listner;

    String  categoryName;

    private Button addToCartButton;

    private String uid;

    public TextView txtProductName1, txtProductDescription1, txtProductPrice1;

    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("carts").push();





    public ProductViewHolder(View itemView)
    {
        super(itemView);
        addToCartButton = itemView.findViewById(R.id.CartListener);
        imageView = itemView.findViewById(R.id.product_image);
        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductDescription = itemView.findViewById(R.id.product_description);
        txtProductPrice = itemView.findViewById(R.id.product_price);


    }

    public void setProduct(Products model)  {

        txtProductName.setText(model.getName());
        txtProductDescription.setText(model.getDescription());
        txtProductPrice.setText("Стоимость - " + model.getPrice() + " рублей");

        // Установите данные продукта в соответствующие элементы интерфейса


        String url1 = model.getImage();

        Picasso.get().load(url1).into(imageView);


        // Установка обработчика нажатия кнопки "Добавить в корзину"
        addToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                // Создайте объект корзины для этого продукта
                HashMap<String, Object> cartItem = new HashMap<>();

                cartItem.put("name", model.getName());
                cartItem.put("price", model.getPrice());
                cartItem.put("image", model.getImage());
                cartItem.put("category", uid);

                // Добавьте этот объект корзины в базу данных с использованием pid в качестве ключа
                cartRef.updateChildren(cartItem);

            }

        });
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }

}
