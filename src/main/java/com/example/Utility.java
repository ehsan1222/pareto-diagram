package com.example;

public class Utility {
    private double sellerUtility;
    private double buyerUtility;


    public Utility(double sellerUtility, double buyerUtility) {
        this.sellerUtility = sellerUtility;
        this.buyerUtility = buyerUtility;
    }


    public double getSellerUtility() {
        return sellerUtility;
    }

    public void setSellerUtility(double sellerUtility) {
        this.sellerUtility = sellerUtility;
    }

    public double getBuyerUtility() {
        return buyerUtility;
    }

    public void setBuyerUtility(double buyerUtility) {
        this.buyerUtility = buyerUtility;
    }


}
