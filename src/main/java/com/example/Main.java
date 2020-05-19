package com.example;

import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Double> seller = new ArrayList<>();
    private static List<Double> buyer  = new ArrayList<>();

    public static void main(String[] args) {

        mockInputVariables();



    }

    private static void mockInputVariables() {
        for (int price = 100; price <= 900; price++) {
            for (int warranty = 0; warranty <= 36; warranty++) {
                for (int sendDuration = 1; sendDuration <= 21; sendDuration++) {
                    for (int returnProduct = 0; returnProduct <= 1; returnProduct++) {
                        double sellerUtilityResult = calculateUtility(price, 100, 900, 100) * 0.5
                                + (calculateUtility(36, warranty, 36, 0) * 0.2)
                                + (calculateUtility(sendDuration, 1, 21, 1) * 0.1)
                                + ((1 - returnProduct) * 0.2);

                        double buyerUtilityResult = calculateUtility(900, price, 900, 100) * 0.1
                                + calculateUtility(warranty, 0, 36, 0) * 0.1
                                + calculateUtility(21, sendDuration, 21, 1) * 0.6
                                + returnProduct * 0.2;


                        seller.add(sellerUtilityResult);
                        buyer.add(buyerUtilityResult);
                    }
                }
            }
        }
    }

    private static double calculateUtility(double first, double second, double max, double min) {
        return (first - second) / (max - min);
    }

}
