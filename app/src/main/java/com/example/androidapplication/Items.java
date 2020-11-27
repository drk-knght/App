package com.example.androidapplication;

public class Items {
    private String itemname;
    private String itemcategory;
    private String itemprice;
    private String itembarcode;


    public Items() {

    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setItemcategory(String itemcategory) {
        this.itemcategory = itemcategory;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public void setItembarcode(String itembarcode) {
        this.itembarcode = itembarcode;
    }

    public Items(String itemname, String itemcategory, String itemprice, String itembarcode){

        this.itemname=itemname;
        this.itemcategory=itemcategory;
        this.itemprice=itemprice;
        this.itembarcode= itembarcode;
    }

    public String getItemname() {
        return itemname;
    }

    public String getItemcategory() {
        return itemcategory;
    }

    public String getItemprice() {
        return itemprice;
    }

    public String getItembarcode() {
        return itembarcode;
    }
}
