package com.tkr.tkrtpushop.Cart;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tkr.tkrtpushop.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private List<CartProduct> cartProducts;
    private CartAdapter cartAdapter;
    private RecyclerView cartRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Инициализируем RecyclerView
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Имитируем данные в корзине
        cartProducts = new ArrayList<>();
        cartProducts.add(new CartProduct("1", "Товар 1", "10.00", 2));
        cartProducts.add(new CartProduct("2", "Товар 2", "15.00", 1));

        // Инициализируем адаптер
        cartAdapter = new CartAdapter(cartProducts);
        cartRecyclerView.setAdapter(cartAdapter);
    }

    // Дополнительные методы, если нужно управлять корзиной

    // Метод для добавления товара в корзину
    public void addToCart(CartProduct product) {
        // Ваш код для добавления товара в корзину
    }

    // Метод для удаления товара из корзины
    public void removeFromCart(CartProduct product) {
        // Ваш код для удаления товара из корзины
    }
}
