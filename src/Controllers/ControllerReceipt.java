package Controllers;

import Launcher.Launcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ControllerReceipt {

    @FXML
    private TableView<?> tableClient;

    @FXML
    private TableColumn<?, ?> colNBon, colDate, colNFournisseur;

    @FXML
    private TableView<?> tableFournisseur;

    @FXML
    private TableColumn<?, ?> colFournisseurNum, colNameFournisseur, colAdresseFournisseur, colTelephoneFournisseur, colFaxFournisseur;

    @FXML
    void articleViewSelected(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewArticle.fxml"));
            Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
            Launcher.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("hello");
        }
    }

    @FXML
    void stockViewSelected(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewStock.fxml"));
            Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
            Launcher.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateClient(ActionEvent event) {

    }

    @FXML
    void updateFournisseur(ActionEvent event) {

    }

    public void clientViewSelected(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewPersonel.fxml"));
            Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
            Launcher.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
