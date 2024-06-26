package frontend.controller;

import backend.data.Container;
import backend.data.DataSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static java.lang.Integer.*;

public class OverviewController {
    private final Stage primaryStage;
    private final DataSingleton data = DataSingleton.getInstance();

    @FXML
    private TilePane buttonPane;

    public OverviewController(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize(){

        // creat a Button for each Container to open Container Overview
        List<Container> containers = data.getParser().getContainers();

        for (Container container : containers){
            Button containerButton = new Button(container.getName());

            containerButton.setId(String.valueOf((container.getId())));
            containerButton.setOnAction(event -> handelClick(containerButton));
            buttonPane.getChildren().add(containerButton);
        }
    }

    /**
     * This method handels a click of a containerButton created in they initialize methode.
     *
     * It opens the Container with the id specified in the Button id field.
     * @param button Button to handel the Click
     */
    private void handelClick(Button button){
        int id = parseInt(button.getId());

        // isPresent check not needed because only ids form existing containers get saved
        Container selectedContainer = data.getParser().getContainers().stream().filter(c -> c.getId() == id).findFirst().get();

        // Load containerOverview scene
        FXMLLoader containerOverviewLoader = new FXMLLoader(getClass().getResource("/scenes/containerOverview.fxml"));
        containerOverviewLoader.setController(new ContainerOverviewController(selectedContainer, primaryStage));
        Parent overviewRoot;

        try {
            overviewRoot = containerOverviewLoader.load();
            primaryStage.setScene(new Scene(overviewRoot));
        }

        catch (IOException e) {
            System.out.println("ERROR loading container overview FXML" + e.getMessage());
        }
    }
}