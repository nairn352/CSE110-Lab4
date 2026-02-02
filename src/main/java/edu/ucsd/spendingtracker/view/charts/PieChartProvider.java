package edu.ucsd.spendingtracker.view.charts;

import javafx.scene.chart.PieChart;
import edu.ucsd.spendingtracker.model.Category;
import javafx.scene.Node;
import java.util.Map;

public class PieChartProvider implements IChartProvider{
    @Override
    public Node createChart(Map<Category, Double> data) {
        PieChart pieChart = new PieChart();

        data.forEach((cat, sum) -> {
            PieChart.Data slice = new PieChart.Data(cat.name(), sum);
            pieChart.getData().add(slice);
        });
        for (PieChart.Data entry : pieChart.getData()) {
            String color = Category.valueOf(entry.getName()).color;
            Node sliceNode = entry.getNode();
            if (sliceNode != null) {
                sliceNode.setStyle("-fx-pie-color: " + color + ";");
            }
        }
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(false);
        return pieChart;
    }

    @Override
    public String getDisplayName() {
        return "Pie Chart";
    }
}
