package com.example;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ehsan Maddahi
 */
public class Main {

    public static void main(String[] args) {
        // Initial pareto points array
        Utility[] paretoPoints = new Utility[100000001];

        // Find pareto points of dataset
        paretoValues(paretoPoints);

        // Get x values of pareto utilities
        List<Double> x = Arrays
                .stream(paretoPoints)
                .filter(Objects::nonNull)
                .map(Utility::getSellerUtility)
                .collect(Collectors.toList());

        // Get y values og pareto utilities
        List<Double> y = Arrays
                .stream(paretoPoints)
                .filter(Objects::nonNull)
                .map(Utility::getBuyerUtility)
                .collect(Collectors.toList());


        // Draw Pareto Curve
        XYChart chart = QuickChart.getChart("Pareto Chart", "Seller", "Buyer", "pareto curve", x, y);

        new SwingWrapper(chart).displayChart();
    }

    /**
     * Check New point is pareto and store in array or not.
     *
     * @param paretoPoints array of pareto points
     * @param newUtility new point
     */
    private static void checkIsPareto(Utility[] paretoPoints, Utility newUtility) {

        int index = (int) (Math.floor(newUtility.getSellerUtility() * 100000000));
        if (paretoPoints[index] == null || paretoPoints[index].getBuyerUtility() < newUtility.getBuyerUtility()) {
            paretoPoints[index] = new Utility(newUtility.getSellerUtility(), newUtility.getBuyerUtility());
        } else {
            if (paretoPoints[index].getBuyerUtility() == newUtility.getBuyerUtility()) {
                paretoPoints[index] =
                        new Utility(Math.max(newUtility.getSellerUtility(), paretoPoints[index].getSellerUtility()),
                                newUtility.getBuyerUtility());
            }
        }
    }

    /**
     * Find pareto points
     *
     * @param paretoPoints
     */
    private static void paretoValues(Utility[] paretoPoints) {

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

                        Utility newUtility = new Utility(sellerUtilityResult, buyerUtilityResult);
                        checkIsPareto(paretoPoints, newUtility);
                    }
                }
            }
        }
    }

    /**
     * Calculate utility point value
     *
     * @param first
     * @param second
     * @param max
     * @param min
     * @return utility value of current point
     */
    private static double calculateUtility(double first, double second, double max, double min) {
        return (first - second) / (max - min);
    }

}
