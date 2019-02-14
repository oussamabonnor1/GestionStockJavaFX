package Launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/viewArticle.fxml"));
        stage = primaryStage;
        stage.setTitle("Stock management");
        stage.setScene(new Scene(root, 1360, 680));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
