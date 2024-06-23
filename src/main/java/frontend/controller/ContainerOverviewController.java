package frontend.controller;

import backend.data.Container;
import backend.data.Log;
import backend.markdownfactory.*;

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

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        Parent overviewRoot;

        try {
            overviewRoot = containerOverviewLoader.load();
            primaryStage.setScene(new Scene(overviewRoot));
            primaryStage.setTitle("Overview");
        }

        catch (IOException e) {
            System.out.println("ERROR load overview FXML failed: " + e.getMessage());
        }
    }

    /**
     * This method generates a Markdown Table containing all
     * parsed logs form the current Container.
     *
     * @return MarkdownTable with the log data
     */
    private MarkdownTable generateMarkdownTable(){
        MarkdownTable mdTable = new MarkdownTable(List.of("Status","Time","Size"), 3);
        for (Log log: container.getLogs()){
            String status = "failed";
            if (log.isStatus()) status="ok";
            mdTable.addRow(List.of(status, log.getTime() + " min",log.getSize()+" GiB"));
        }
        
        return  mdTable;
    }

    /**
     * This method generates a Markdown Pie Chart containing
     * the status statistics from the current Container.
     *
     * @return MarkdownPieChart with the status statistics
     */
    private MarkdownPieChart generateMarkdownStatusChart(){
        List<Integer> statusStatistics = container.getStatusStatistics();
        MarkdownPieChart statusPie = new MarkdownPieChart("Status");
        statusPie.addClass("OK", statusStatistics.get(0));
        statusPie.addClass("FAILED", statusStatistics.get(1));

        return statusPie;
    }

    /**
     * This method returns a Markdown2DLineChart containing
     * the size statistics from the current Container.
     *
     * @return Markdown2DLineChart with size statistics
     */
    private Markdown2DLineChart generateMarkdownSizeChart(){
        Markdown2DLineChart sizeChart = new Markdown2DLineChart("Speicherverlauf","Log nummer", "Speicher in GiB");
        for (Log log: container.getLogs()){
            sizeChart.addPoint(log.getSize());
        }

        return sizeChart;
    }

    @FXML
    private void handleSaveReportButtonOnCLick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report to File");

        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // build Markdown file
            MarkdownFile mdFile = new MarkdownFile(file);
            mdFile.addComponent(new MarkdownHeadline(container.getName() + " ID: " + container.getId(), 0));

            // add table and statistics to file
            mdFile.addComponent(generateMarkdownTable());
            mdFile.addComponent(generateMarkdownStatusChart());
            mdFile.addComponent(generateMarkdownSizeChart());

            // Write File or print error to stdout
            try {
                mdFile.writeToFile();
            }

            catch (RuntimeException e){
                System.out.println("ERROR write to file: " + e.getMessage());
            }
        }

        else {
            System.out.println("ERROR: File could not be saved");
        }
    }

    @FXML
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

    /**
     * This method initializes the Log Buttons.
     * It creates for each log a Button to open the Log-Text window.
     */
    private void initializeLogButtons(){
        List<Log> logs = container.getLogs();

        int index =0;

        for (Log log : logs) {
            String status = "failed";
            if (log.isStatus()) status="ok";

            Button logButton = new Button("LOG %d: %s".formatted(index, status));
            int finalIndex = index;

            logButton.setOnAction(event -> {
                try {
                    handelLogButtonOnClick(finalIndex);
                }

                catch (IOException e) {
                    System.out.println("ERROR load log overview: " + e.getMessage());
                }
            });

            logButtonsPane.getChildren().add(logButton);
            index +=1;
        }
    }

    /**
     * This method initializes the Status chart.
     * It fills the pie chart with the status statistics
     * from the current Container.
     */
    private void initializeStatusChart(){
        ArrayList<Integer> statusStatistics = container.getStatusStatistics();
        int total = container.getLogs().toArray().length;
        statusChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("ok %d %%".formatted((statusStatistics.get(0) * 100) / total ),statusStatistics.get(0)),
                new PieChart.Data("failed %d %%".formatted((statusStatistics.get(1) * 100) / total),statusStatistics.get(1))
        ));
    }

    /**
     * This method initializes the SizeChart.
     * It fills the XY-Chart with the size statistics
     * from the current Container.
     */
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