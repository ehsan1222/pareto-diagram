package com.example;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utility utility = (Utility) o;
        return Double.compare(utility.sellerUtility, sellerUtility) == 0 &&
                Double.compare(utility.buyerUtility, buyerUtility) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerUtility, buyerUtility);
    }
}
