package frontend.controller;

import backend.emailclient.EmailClient;
import backend.parser.ProxmoxParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    private EmailClient emailclient;
    private ProxmoxParser proxmoxParser;
    @FXML
    private TextField emailAdderEntry;

    @FXML
    private  TextField emailPwEntry;

    @FXML
    private TextField smtpAddrEntry;
    @FXML
    private TextField smtpPort;

    @FXML
    private TextField emailFolderEntry;


    public LoginController(EmailClient emailClient, ProxmoxParser proxmoxParser) {
        this.emailclient = emailClient;
        this.proxmoxParser = proxmoxParser;

    }

    @FXML
    private void loginButtonOnClick(ActionEvent event) throws IOException {
        System.out.println(emailAdderEntry.getText());
        emailclient.login(emailAdderEntry.getText(),emailPwEntry.getText(),smtpAddrEntry.getText(), Integer.parseInt(smtpPort.getText()),true);
        proxmoxParser.parse(emailclient.getEmailsFrom(emailFolderEntry.getText()).subList(0,5));
        System.out.println(proxmoxParser.getContainers().get(0).getLogs().get(0).getSize());
        emailclient.logout();

        Parent overview = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/overview.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(overview));
        stage.setTitle("Overview");
    }
}
