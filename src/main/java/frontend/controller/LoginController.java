package frontend.controller;

import backend.data.DataSingleton;
import backend.logintemplate.LoginTemplate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.jfr.Label;

import java.io.File;
import java.io.IOException;

public class LoginController {
    private final Stage primaryStage;

    @FXML
    private TextField emailAdderEntry;

    @FXML
    private PasswordField emailPwEntry;

    @FXML
    private TextField imapAddrEntry;
    @FXML
    private TextField imapPort;

    @FXML
    private TextField emailFolderEntry;

    private final DataSingleton data = DataSingleton.getInstance();

    public LoginController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void openTemplateOnClick(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Template File");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            LoginTemplate loginTemplate = new LoginTemplate(file);

            // assign entries to data loaded from file
            emailAdderEntry.setText(loginTemplate.getEmail());
            emailPwEntry.setText(loginTemplate.getPassword());
            imapAddrEntry.setText(loginTemplate.getImapUrl());
            imapPort.setText(loginTemplate.getPort());
            emailFolderEntry.setText(loginTemplate.getFolder());
        }


    }

    @FXML
    private void loginButtonOnClick(ActionEvent event) throws IOException {
        data.getEmailClient().login(emailAdderEntry.getText(),emailPwEntry.getText(),imapAddrEntry.getText(), Integer.parseInt(imapPort.getText()),true);
        data.getParser().parse(data.getEmailClient().getEmailsFrom(emailFolderEntry.getText()));
        data.getEmailClient().logout();

        // Load Overview Page
        FXMLLoader overview = new FXMLLoader(getClass().getResource("/scenes/overview.fxml"));
        overview.setController(new OverviewController(primaryStage));
        Parent overviewRoot = overview.load();

        primaryStage.setScene(new Scene(overviewRoot));
        primaryStage.setTitle("Overview");
    }
}
