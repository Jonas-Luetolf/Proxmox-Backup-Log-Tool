package frontend.controller;

import backend.data.Container;
import backend.parser.Parser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static java.lang.Integer.*;

public class OverviewController {
    private final Stage primaryStage;
    private Parser parser;
    @FXML
    private TilePane buttonPane;

    public OverviewController(Parser parser, Stage primaryStage){
        this.parser = parser;
        this.primaryStage = primaryStage;
    }
    @FXML
    private void initialize(){
        List<Container> containers = parser.getContainers();
        for (Container container : containers){
            Button containerButton = new Button(container.getName());
            containerButton.setId(String.valueOf((container.getId())));
            containerButton.setOnAction(event -> handelClick(containerButton));
            buttonPane.getChildren().add(containerButton);
        }



    }

    private void handelClick(Button button){
        int id = parseInt(button.getId());
        // isPresent not needed because only ids form existing containers get saved
        Container selectedContainer = parser.getContainers().stream().filter(c -> c.getId() == id).findFirst().get();

        FXMLLoader containerOverviewLoader = new FXMLLoader(getClass().getResource("/scenes/containerOverview.fxml"));
        containerOverviewLoader.setController(new ContainerOverviewController(selectedContainer));
        Parent overviewRoot;

        try {
            overviewRoot = containerOverviewLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        primaryStage.setScene(new Scene(overviewRoot));
    }


    }
