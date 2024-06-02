package frontend.controller;

import backend.data.Container;
import backend.data.Log;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Pair;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ContainerOverviewController {
    private final Container container;

    private final Stage primaryStage;
    @FXML
    private TilePane logButtonsPane;

    @FXML
    private Label containerName;

    @FXML
    private PieChart statusChart;

    @FXML
    private LineChart<Number, Number> sizeChart;

    public ContainerOverviewController (Container container, Stage primaryStage) {
        this.container = container;
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleBackButtonOnCLick(ActionEvent event) {
        FXMLLoader containerOverviewLoader = new FXMLLoader(getClass().getResource("/scenes/overview.fxml"));
        containerOverviewLoader.setController(new OverviewController(primaryStage));
        Parent overviewRoot = null;

        try {
            overviewRoot = containerOverviewLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        primaryStage.setScene(new Scene(overviewRoot));
        primaryStage.setTitle("Overview");
    }

    private void handelLogButtonOnClick (int index) throws IOException {
        Log log = container.getLogs().get(index);

        Stage logDetailsStage = new Stage();
        logDetailsStage.setTitle("Log Details");
        FXMLLoader logDetailsLoader = new FXMLLoader(getClass().getResource("/scenes/logDetails.fxml"));

        VBox content = logDetailsLoader.load();

        Label title = (Label) content.lookup("#titelLabel");
        title.setText("Log: %s %f GB %f min".formatted(log.isStatus(), log.getSize(), log.getTime()));

        TextArea logTextArea = (TextArea) content.lookup("#logTextArea");
        logTextArea.setText(log.getLogText());
        logTextArea.setEditable(false);

        logDetailsStage.setScene(new Scene(content, 600, 400));
        logDetailsStage.show();
    }

    private void initializeLogButtons(){
        List<Log> logs = container.getLogs();

        int index =0;
        Iterator<Log> logIterator = logs.iterator();

        for (Log log : logs) {
            String status = "failed";
            if (log.isStatus()) status="ok";

            Button logButton = new Button("LOG: %s %f".formatted(status, log.getSize()));
            logButton.setOnAction(event -> {
                try {
                    handelLogButtonOnClick(index);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            logButtonsPane.getChildren().add(logButton);
        }
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
        initializeLogButtons();

    }
}