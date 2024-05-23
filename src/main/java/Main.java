import backend.emailclient.EmailClient;
import backend.parser.ProxmoxParser;
import frontend.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load the FXML file
        EmailClient emailClient = new EmailClient();
        ProxmoxParser proxmoxParser = new ProxmoxParser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/login.fxml"));
        loader.setController(new LoginController(emailClient, proxmoxParser));
        Parent root = loader.load();

        // Set up the scene
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
