package com.tkr.tkrtpushop.Model;

public class Products {
    private String name;
    private String Description;
    private String Price;
    private String image;
    private String category;
    private String pid;
    private String date;
    private String time;
    private String key;
    private String uid;
    public Products(){

    }

    public Products(String name, String description, String price, String image, String category, String pid, String date, String time) {
        this.name = name;
        this.Description = description;
        this.Price = price;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.uid = uid;
        this.key = key;
    }
    public String getUid(){return  uid;}

    public void setUid(String uid){this.uid = uid; }

    public String getKey(){return key;}
    public void setKey(String key){this.key = key;}

    public String getName() {
        return name;
    }

    public void setProductName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}