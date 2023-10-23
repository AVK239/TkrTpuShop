package com.tkr.tkrtpushop.Users;

import static com.tkr.tkrtpushop.R.id;
import static com.tkr.tkrtpushop.R.layout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Application;
import com.stripe.model.Card;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.Token;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.TokenCreateParams;
import com.stripe.param.issuing.CardCreateParams;
import com.tkr.tkrtpushop.Model.Products;
import com.tkr.tkrtpushop.ViewHolder.CartViewHolder;

import org.threeten.bp.ZoneId;
import org.threeten.bp.zone.TzdbZoneRulesProvider;

import io.reactivex.rxjava3.annotations.NonNull;



public class CartActivity extends AppCompatActivity {

    private DatabaseReference cartRef;
    private RecyclerView cartRecyclerView;
    private FirebaseRecyclerAdapter<Products, CartViewHolder> cartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout.activity_cart);


        cartRef = FirebaseDatabase.getInstance().getReference().child("carts");

        RecyclerView cartRecyclerView = findViewById(id.cartRecyclerView);
        Button payButton = findViewById(id.payButton);
        String uid = getIntent().getExtras().get("uid").toString();
        TextView Id = findViewById(id.textIDсView);
        Id.setText("Ваш Id" + uid);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Создайте токен платежа через Stripe API
                try {
                    Stripe.apiKey = "sk_test_VePHdqKTYQjKNInc7u56JBrQ";


                    ProductCreateParams productParams =
                            ProductCreateParams.builder()
                                    .setName("Starter Subscription")
                                    .setDescription("$12/Month subscription")
                                    .build();
                    Product product = Product.create(productParams);
                    System.out.println("Success! Here is your starter subscription product id: " + product.getId());

                    PriceCreateParams params =
                            PriceCreateParams
                                    .builder()
                                    .setProduct(product.getId())
                                    .setCurrency("usd")
                                    .setUnitAmount(1200L)
                                    .setRecurring(
                                            PriceCreateParams.Recurring
                                                    .builder()
                                                    .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                                    .build())
                                    .build();
                    Price price = Price.create(params);
                    System.out.println("Success! Here is your starter subscription price id: " + price.getId());
                } catch (StripeException e) {
                    e.printStackTrace();
                    // Обработайте ошибку создания токена
                }
            }
        });








        // Настройка RecyclerView
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = cartRef.orderByChild("category").equalTo(uid);

        // Определение FirebaseRecyclerOptions для корзины
        FirebaseRecyclerOptions<Products> cartOptions = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(query, Products.class)
                .build();

        // Создан адаптер для корзины
        cartAdapter = new FirebaseRecyclerAdapter<Products, CartViewHolder>(cartOptions) {

            @Override
            protected void onBindViewHolder(CartViewHolder holder, int position, Products model) {
                // Здесь заполните данные для элемента корзины
                holder.txtProductName1.setText(model.getName());
                holder.txtProductDescription1.setText(model.getDescription());
                holder.txtProductPrice1.setText("Стоимость - " + model.getPrice() + " рублей");
                String url1 = model.getImage();
                Picasso.get().load(url1).into(holder.imageView1);
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Создание ViewHolder для элемента корзины
                View view = LayoutInflater.from(parent.getContext()).inflate(layout.cart_items_layout, parent, false);
                return new CartViewHolder(view);
            }
        };

        // Установка адаптера в RecyclerView
        cartRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();


        cartAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();


        cartAdapter.stopListening();
    }
}
