package com.tkr.tkrtpushop.ViewHolder;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tkr.tkrtpushop.Model.Product1;
import com.tkr.tkrtpushop.R;
import com.tkr.tkrtpushop.Users.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Product1ViewHolder> {

    public interface OnAddToCartClickListener {
        void onAddToCartClick(String pid, String name, String price);
    }
    private String uid;
    private Context context;
    private List<Product1> product1s;
    public static OnAddToCartClickListener clickListener;




    public ProductAdapter(Context context, List<Product1> product1s, OnAddToCartClickListener clickListener) {
        this.context = context;
        this.product1s = product1s;
        this.clickListener = clickListener;
    }

    public void setUserInfo(String userUid) {
        // Используйте этот метод для передачи информации о пользователе в адаптер
        // Здесь вы можете сохранить userUid или выполнить другие действия в адаптере
    }

    public static class Product1ViewHolder extends RecyclerView.ViewHolder {

        String pid,name,price,categoryName;




        ImageView productImage;
        TextView productTitle, productPrice;


        public Product1ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);


        }
    }




    @NonNull
    @Override
    public Product1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View productItems = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new Product1ViewHolder(productItems);
    }

    @Override
    public void onBindViewHolder(@NonNull Product1ViewHolder holder, int position) {






    }

    @Override
    public int getItemCount() {
        return product1s.size();
    }
}
