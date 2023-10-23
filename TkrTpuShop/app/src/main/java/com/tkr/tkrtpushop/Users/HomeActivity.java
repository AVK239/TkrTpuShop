package com.tkr.tkrtpushop.Users;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.tkr.tkrtpushop.Admin.AdminAddNewProductActivity;
import com.tkr.tkrtpushop.Admin.AdminCategoryActivity;

import com.tkr.tkrtpushop.LoginActivity;
import com.tkr.tkrtpushop.Model.Products;

import com.tkr.tkrtpushop.Prevalent.Prevalent;
import com.tkr.tkrtpushop.R;
import com.tkr.tkrtpushop.ViewHolder.Category;
import com.tkr.tkrtpushop.ViewHolder.CategoryAdapter;
import com.tkr.tkrtpushop.ViewHolder.ProductAdapter;
import com.tkr.tkrtpushop.ViewHolder.ProductViewHolder;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DatabaseReference ProductsRef;

    DatabaseReference CartsRef;
    RecyclerView recyclerView;
    RecyclerView categoryRecycler;
    RecyclerView.LayoutManager layoutManager;

    CategoryAdapter categoryAdapter;

    Button ButtonComTomPol, ButtonMissionEng, ButtonTHC, Cart;


    Button addToCartButton;
    private DatabaseReference cartRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        CartsRef = FirebaseDatabase.getInstance().getReference().child("carts");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Меню");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

        };
        ProductAdapter.clickListener = new ProductAdapter.OnAddToCartClickListener() {
            @Override
            public void onAddToCartClick(String pid, String name, String price) {

                    // Получите TextView, где будет отображаться счетчик
                    TextView cartItemCountTextView = findViewById(R.id.quantity);

                    // Получите текущее значение счетчика
                    int currentCount = Integer.parseInt(cartItemCountTextView.getText().toString());

                    // Увеличьте счетчик
                    currentCount++;

                    // Обновите TextView с новым значением счетчика
                    cartItemCountTextView.setText(String.valueOf(currentCount));

                    // Добавьте товар в корзину (ваша логика добавления товара)
                }

            };




        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(Prevalent.currentOnlineUser.getName());




        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }





    public static void showProductsByCategory(int category){

    }
    private FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter;
    private String categoryName = "";


    private String defaultCategory = null; // категория по умолчанию - пустая строка





    public void filterCategory(final String category) {
        Query query;
        if (category.equals(defaultCategory)) {
            // если выбрана категория по умолчанию, фильтрация не производится
            query = ProductsRef;
        } else {
            // в противном случае выбираются товары из указанной категории
            query = ProductsRef.orderByChild("category").equalTo(category);
        }



        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(query, Products.class)
                .build();

        adapter.updateOptions(options);
        adapter.notifyDataSetChanged(); // обновление RecyclerView
    }



        @Override
    protected void onStart() {
        super.onStart();




        Button buttonColTomPol = findViewById(R.id.buttonColTomPol);

        buttonColTomPol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = "ColTomPol";
                filterCategory(categoryName);

            }
        });

        Button buttonMissionEng = findViewById(R.id.buttonMissionEng);
        buttonMissionEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = "MissionEng";
                filterCategory(categoryName);
            }
        });

        Button buttonTHC = findViewById(R.id.buttonTHC);
        buttonTHC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = "THC";
                filterCategory(categoryName);
            }
        });

        Button Cart = findViewById(R.id.fab);
        String uid = getIntent().getExtras().get("uid").toString();
        TextView Id = findViewById(R.id.textIDView);
        Id.setText("Ваш Id" + uid);


        Cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent cartIntent = new Intent(HomeActivity.this, CartActivity.class);
                    cartIntent.putExtra("uid", uid);


                    startActivity(cartIntent);


                }



            });




            // Определяем FirebaseRecyclerOptions и FirebaseRecyclerAdapter с категорией по умолчанию
            Query query = ProductsRef;


            FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef, Products.class).build();

            adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {


            @Override
            protected void onBindViewHolder(ProductViewHolder holder, int position, Products model) {

                holder.setUid(uid); // Установите uid в ProductViewHolder
                holder.setProduct(model);

                if (model.getCategory().equals(categoryName)) {
                holder.txtProductName.setText(model.getName());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductPrice.setText("Стоимость - " + model.getPrice() + " рублей");
                String url1 = model.getImage();
                Picasso.get().load(url1).into(holder.imageView);}


                };





            @NonNull
            @NotNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                return new ProductViewHolder(view);
            }
        };


            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long l) {
                }

                @Override
                public void onFinish() {
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                    filterCategory(categoryName);
                }
            }.start();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_cart){

        } else if(id == R.id.nav_orders){

        } else if(id == R.id.nav_categories){

        } else if(id == R.id.nav_settings){
            Intent loginIntent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(loginIntent);

        } else if(id == R.id.nav_logout){
            Paper.book().destroy();
            Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }


}
