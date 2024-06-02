package frontend.controller;

import backend.data.DataSingleton;
import backend.emailclient.EmailClient;
import backend.parser.ProxmoxParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    private final Stage primaryStage;

    @FXML
    private TextField emailAdderEntry;

    @FXML
    private PasswordField emailPwEntry;

    @FXML
    private TextField smtpAddrEntry;
    @FXML
    private TextField smtpPort;

    @FXML
    private TextField emailFolderEntry;

    private DataSingleton data = DataSingleton.getInstance();

    public LoginController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void loginButtonOnClick(ActionEvent event) throws IOException {
        data.getEmailClient().login(emailAdderEntry.getText(),emailPwEntry.getText(),smtpAddrEntry.getText(), Integer.parseInt(smtpPort.getText()),true);
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
