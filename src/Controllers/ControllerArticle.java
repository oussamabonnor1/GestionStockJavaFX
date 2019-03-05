package Controllers;

import Launcher.Launcher;
import Models.Article;
import ToolBox.DbConnection;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ToolBox.Utilities.*;

public class ControllerArticle implements Initializable {

    //region Variables
    @FXML
    private TableView<Article> tableArticle;

    @FXML
    private TableColumn<Article, String> colnArticle, colLabel, colPrice, colMinStock;

    @FXML
    private JFXTextField textFieldNArticle, textFieldLabel, textFieldPrice, textFieldMinStock;

    private ObservableList<Article> articleObservableList = FXCollections.observableArrayList();
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DbConnection.createConnection();
        //fetching all articles into observableList
        articleObservableList = DbConnection.getTableArticle();
        //Binding the columns with the model's variables
        colnArticle.setCellValueFactory(new PropertyValueFactory<>("nArticle"));
        colLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colMinStock.setCellValueFactory(new PropertyValueFactory<>("minStock"));
        //Making the columns editable
        editablesColumns(colLabel, colPrice, colMinStock);
        //binding the observables into the table
        tableArticle.setItems(articleObservableList);
        //Making the inputs accept numeric values only (limit: 10)
        numericLimitedTextField(10, textFieldNArticle, textFieldPrice, textFieldMinStock);
    }

    //region Methods
    @FXML
    void addArticle(ActionEvent event) {
        //inputChecking makes sure that all the fields are filled...
        if (inputChecking(textFieldNArticle, textFieldPrice, textFieldLabel, textFieldMinStock)) {
            DbConnection.addArticle(textFieldNArticle.getText(), textFieldLabel.getText(), textFieldPrice.getText(), textFieldMinStock.getText(), tableArticle);
            inputDeleting(textFieldNArticle, textFieldPrice, textFieldLabel, textFieldMinStock); //Clearing out the input UI
        } else {
            warningPannel("Erreur!", "Un ou plusieurs champs sont vide!", "Remplissez tout les champs SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void deleteArticle(ActionEvent event) {
        Article articleToDelete = tableArticle.getSelectionModel().getSelectedItem();
        if (articleToDelete != null) {
            DbConnection.deleteArticle(articleToDelete.getnArticle() + "", tableArticle);
        } else {
            warningPannel("Erreur!", "Aucun élément n'est séléctionné!", "Selectionnez un article SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void updateArticle(TableColumn.CellEditEvent<Article, String> update) {
        Article tempArticle = tableArticle.getSelectionModel().getSelectedItem();
        TablePosition position = tableArticle.getSelectionModel().getSelectedCells().get(0);
        String columnName = "";
        boolean isText = false;
        switch (position.getColumn()) {
            case 1:
                columnName = "Label";
                isText = true;
                break;
            case 2:
                columnName = "Price";
                break;
            case 3:
                columnName = "MinStock";
                break;
        }
        DbConnection.updateArticle(tempArticle.getnArticle(), columnName,
                isText ? "'" + update.getNewValue() + "'"
                        : update.getNewValue() //adding a literal or numeric value?
                , tableArticle);
    }

    @FXML
    void clientViewSelected(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewPersonel.fxml"));
            Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
            Launcher.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
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

    public void receiptViewSelected(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewReceipt.fxml"));
            Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
            Launcher.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deliveryViewSelected(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewSelling.fxml"));
            Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
            Launcher.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion

}
