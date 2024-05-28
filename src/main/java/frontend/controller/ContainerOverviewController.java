package frontend.controller;

import backend.data.Container;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.util.Pair;

import java.util.ArrayList;

public class ContainerOverviewController {
    Container container;

    @FXML
    private Label containerName;

    @FXML
    private PieChart statusChart;

    @FXML
    private LineChart<Number, Number> sizeChart;

    public ContainerOverviewController (Container container) {
        this.container = container;

    }

    private void initializeStatusChart(){
        ArrayList<Integer> statusStatistics = container.getStatusStatistics();
        int total = container.getLogs().toArray().length;
        statusChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("ok %d %%".formatted((statusStatistics.get(0) * 100) / total ),statusStatistics.get(0)),
                new PieChart.Data("failed %d %%".formatted((statusStatistics.get(1) * 100) / total),statusStatistics.get(1))
        ));
    }

    private void initializeSizeChart(){
        ArrayList<Pair<Number, Number>> sizeStatistics = container.getSizeStatistics();
        XYChart.Series<Number, Number> sizeChartSeries = new XYChart.Series<>();

        for (Pair<Number, Number> pair : sizeStatistics) {
            Number key = pair.getKey();
            Number value = pair.getValue();
            sizeChartSeries.getData().add(new XYChart.Data<>(key, value));
        }

       sizeChart.getData().add(sizeChartSeries);

    }

    @FXML
    private void initialize() {
        containerName.setText(container.getName() + " ID: " + container.getId());

        initializeStatusChart();
        initializeSizeChart();

    }
}