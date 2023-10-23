package com.tkr.tkrtpushop.Cart;

import android.widget.TextView;

public class CartProduct {
    private String pid; // Можете изменить на productId
    private String img;
    private String title;
    private String price; // Можете изменить на productPrice
    private int quantity;

    public CartProduct(String pid, String name, TextView productPrice) {
        // Конструктор по умолчанию для Firebase
    }

    public CartProduct(int pid, String img, String title, String price) {
        this.pid = String.valueOf(pid);
        this.img = img;
        this.title = title;
        this.price = price;
        this.quantity = 1; // По умолчанию 1 единица товара
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
        }
    }
}

