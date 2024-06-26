import backend.data.DataSingleton;
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
        // add Parser and EmailClient to DataSingleton
        DataSingleton data = DataSingleton.getInstance();
        data.setEmailClient(new EmailClient());
        data.setParser(new ProxmoxParser());

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/login.fxml"));
        loader.setController(new LoginController(primaryStage));

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
