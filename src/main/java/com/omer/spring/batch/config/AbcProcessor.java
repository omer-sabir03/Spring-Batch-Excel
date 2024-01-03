package com.omer.spring.batch.config;

import com.omer.spring.batch.entity.AbcAnalysisCloud;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class AbcProcessor implements ItemProcessor<AbcAnalysisCloud, AbcAnalysisCloud> {

    private List<AbcAnalysisCloud> processedItems = new ArrayList<>();
    @Override
    public AbcAnalysisCloud process(AbcAnalysisCloud abc) {
        double v = Double.parseDouble(abc.getQuantityConsumed()) / 5;
         abc.setUsagePerYear(String.valueOf(v));

        double v1 = Double.parseDouble(abc.getUsagePerYear()) * Double.parseDouble(abc.getUnitRate());
        abc.setCostPerYear(String.valueOf(v1));


        processedItems.add(abc);
        return abc;
    }

    public void calculateCumulativePercentage() {
        double totalCostPerYear = processedItems.stream()
                .mapToDouble(item -> Double.parseDouble(item.getCostPerYear()))
                .sum();

        int runningCumulativePercentage = 0;

        for (AbcAnalysisCloud item : processedItems) {
            double currentCost = Double.parseDouble(item.getCostPerYear());
            int currentCumulativePercentage = (int) ((currentCost / totalCostPerYear) * 100.0);

            // Running cumulative percentage
            runningCumulativePercentage += currentCumulativePercentage;

            // Set cumulative percentage in the item
            item.setCumulativePercentage(String.valueOf(runningCumulativePercentage));
        }
    }
}
