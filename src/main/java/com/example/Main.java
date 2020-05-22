package com.example;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.*;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) {

        /*List<Utility> sortedUtilityResult = mockInputVariables()
                .stream()
                .sorted((utility1, utility2) -> {
                    if (utility1.getBuyerUtility() > utility2.getBuyerUtility()) {
                        return 1;
                    } else if (utility1.getBuyerUtility() == utility2.getBuyerUtility()) {
                        return Double.compare(utility1.getSellerUtility(), utility2.getSellerUtility());
                    } else {
                        return -1;
                    }
                }).collect(Collectors.toList());*/

            Set<Utility> allUtilities = mockInputVariables();
//        Set<Utility> allUtilities = new HashSet<>();
//
//        allUtilities.add(new Utility(0.1, 1.0));
//        allUtilities.add(new Utility(0.17, 1.0));
//        allUtilities.add(new Utility(0.19, 0.81));
//        allUtilities.add(new Utility(0.24, 0.81));
//        allUtilities.add(new Utility(0.30, 0.81));
//        allUtilities.add(new Utility(0.30, 0.82));
//        allUtilities.add(new Utility(0.30, 0.82));
//        allUtilities.add(new Utility(0.33, 0.63));
//        allUtilities.add(new Utility(0.46, 0.63));
//        allUtilities.add(new Utility(0.53, 0.63));
//        allUtilities.add(new Utility(0.55, 0.44));
//        allUtilities.add(new Utility(0.69, 0.44));
//        allUtilities.add(new Utility(0.75, 0.44));
//        allUtilities.add(new Utility(0.78, 0.25));
//        allUtilities.add(new Utility(0.91, 0.33));
//        allUtilities.add(new Utility(0.98, 0.33));
//        allUtilities.add(new Utility(1.0, 0.14));

        List<Utility> paretoPoints = findParetoPoints(allUtilities).stream()
                .sorted((utility1, utility2) -> {
                    if (utility1.getBuyerUtility() > utility2.getBuyerUtility()) {
                        return 1;
                    } else if (utility1.getBuyerUtility() == utility2.getBuyerUtility()) {
                        return Double.compare(utility1.getSellerUtility(), utility2.getSellerUtility());
                    } else {
                        return -1;
                    }
                }).collect(Collectors.toList());

        List<Double> sellerList = paretoPoints.stream().map(Utility::getSellerUtility).collect(Collectors.toList());
        List<Double> buyerList = paretoPoints.stream().map(Utility::getBuyerUtility).collect(Collectors.toList());

        // draw chart
        XYChart chart = QuickChart.getChart("Pareto Chart", "Seller", "Buyer",
                "pareto line", sellerList, buyerList);

        new SwingWrapper(chart).displayChart();
    }

    private static Set<Utility> findParetoPoints(Set<Utility> allUtilities) {
        Set<Utility> paretoPoints = new HashSet<>();

        for (Utility currentUtility: allUtilities) {
            boolean isCurrentUtilityPareto = true;
            // check current utility greater that other utilities in fixed buyer values
            for (Utility comparedUtility : allUtilities) {
                if (Double.compare(currentUtility.getBuyerUtility(), comparedUtility.getBuyerUtility()) == 0 &&
                    Double.compare(currentUtility.getSellerUtility(), comparedUtility.getSellerUtility()) < 0) {
                    isCurrentUtilityPareto = false;
                    break;
                }
            }
            // if current utility was greater than other utilities in fixed buyer values
            if (isCurrentUtilityPareto) {
                // check current utility greater that other utilities in fixed seller values
                for (Utility comparedUtility : allUtilities) {
                    if (Double.compare(currentUtility.getSellerUtility(), comparedUtility.getSellerUtility()) == 0 &&
                        Double.compare(currentUtility.getBuyerUtility(), comparedUtility.getBuyerUtility()) < 0) {
                        isCurrentUtilityPareto = false;
                        break;
                    }
                }
            }
            // current item is pareto point
            if (isCurrentUtilityPareto) {
                paretoPoints.add(currentUtility);
            }
        }

        return paretoPoints;
    }


    private static Set<Utility> mockInputVariables() {
        Set<Utility> utilities = new HashSet<>();

        for (int price = 100; price <= 900; price+=300) {
            for (int warranty = 0; warranty <= 36; warranty+=6) {
                for (int sendDuration = 1; sendDuration <= 21; sendDuration+=4) {
                    for (int returnProduct = 0; returnProduct <= 1; returnProduct++) {
                        double sellerUtilityResult = calculateUtility(price, 100, 900, 100) * 0.5
                                + (calculateUtility(36, warranty, 36, 0) * 0.2)
                                + (calculateUtility(sendDuration, 1, 21, 1) * 0.1)
                                + ((1 - returnProduct) * 0.2);

                        double buyerUtilityResult = calculateUtility(900, price, 900, 100) * 0.1
                                + calculateUtility(warranty, 0, 36, 0) * 0.1
                                + calculateUtility(21, sendDuration, 21, 1) * 0.6
                                + returnProduct * 0.2;

                        utilities.add(new Utility(sellerUtilityResult, buyerUtilityResult));
                    }
                }
            }
        }

        return utilities;
    }

    private static double calculateUtility(double first, double second, double max, double min) {
        return (first - second) / (max - min);
    }

}
