package Launcher;

import ToolBox.DbConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/viewStock.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1280, 760));
        DbConnection.createConnection();
        DbConnection.getTableArticle();
        DbConnection.addArticle("1", "Sabona", "15000", "15");
        //DbConnection.deleteArticle("2");
        //DbConnection.getTableArticle();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
