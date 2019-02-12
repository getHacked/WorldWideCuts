package com.example.thomasd06.myfirstapp;

public class BarberShop {

    private String shopName, shopLocation;
    private double shopRating;
    public BarberShop(){

    }

    public BarberShop(String shopName, String shopLocation, double shopRating){
        this.shopName = shopName;

        this.shopLocation = shopLocation;
        this.shopRating = shopRating;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getShopRating() {
        return shopRating;
    }

    public void setShopRating(double shopRating) {
        this.shopRating = shopRating;
    }

    @Override
    public String toString() {
        return getShopName() + " Barbershop" + ";" + " Location:" + getShopLocation()+ "\n";
    }
}
