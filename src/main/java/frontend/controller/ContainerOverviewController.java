package frontend.controller;

import backend.data.Container;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

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
    @FXML
    private void initialize() {
        containerName.setText(container.getName() + " ID: " + container.getId());

    }
}
