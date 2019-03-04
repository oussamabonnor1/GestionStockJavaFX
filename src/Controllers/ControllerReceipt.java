package Controllers;

import Launcher.Launcher;
import Models.Client;
import Models.Fournisseur;
import Models.ReceiptApprovision;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ToolBox.Utilities.*;
import static ToolBox.Utilities.warningPannel;

public class ControllerReceipt implements Initializable {

    //region Variables
    @FXML
    private JFXTextField textFieldNumArticle, textFieldDate, textFieldQntA, textFieldNFournisseur;

    @FXML
    private TableView<ReceiptApprovision> tableReceipt;

    @FXML
    private TableColumn<ReceiptApprovision, String> colNBon, colNArticle, colDate, colNQntA, colNFournisseur;
    private ObservableList<ReceiptApprovision> receiptsObservableList = FXCollections.observableArrayList();
    //endregion

    //region Functions
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //fetching all articles into observableList
        receiptsObservableList = DbConnection.getTableApprovisiont();

        //Binding the columns with the model's variables
        colNBon.setCellValueFactory(new PropertyValueFactory<>("nBon"));
        colNArticle.setCellValueFactory(new PropertyValueFactory<>("nArticle"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNQntA.setCellValueFactory(new PropertyValueFactory<>("qntA"));
        colNFournisseur.setCellValueFactory(new PropertyValueFactory<>("nFournisseur"));

        //Making the columns editable
        editablesColumns(colNBon, colNArticle, colDate, colNQntA, colNFournisseur);
        //binding the observables into the table
        tableReceipt.setItems(receiptsObservableList);

        //Making the inputs accept numeric values only (limit: 10)
        numericLimitedTextField(10, textFieldNumArticle, textFieldQntA, textFieldNFournisseur);
    }

    @FXML
    void addReceipt(ActionEvent event) {
        //inputChecking makes sure that all the fields are filled...
        if (inputChecking(textFieldNumArticle, textFieldQntA, textFieldNFournisseur, textFieldDate)) {
            DbConnection.addApprovisiont(textFieldNumArticle.getText(), textFieldDate.getText(), textFieldQntA.getText(), textFieldNFournisseur.getText(), tableReceipt);
            inputDeleting(textFieldNumArticle, textFieldQntA, textFieldNFournisseur, textFieldDate); //Clearing out the input UI
        } else {
            warningPannel("Erreur!", "Un ou plusieurs champs sont vide!", "Remplissez tout les champs SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void updateReceipt(TableColumn.CellEditEvent<Client, String> update) {
        ReceiptApprovision receiptApprovision = tableReceipt.getSelectionModel().getSelectedItem();
        TablePosition position = tableReceipt.getSelectionModel().getSelectedCells().get(0);
        String columnName = "";
        boolean isText = false;
        switch (position.getColumn()) {
            case 1:
                columnName = "NArticle";
                isText = true;
                break;
            case 2:
                columnName = "Date";
                isText = true;
                break;
            case 3:
                columnName = "QntA";
                break;
            case 4:
                columnName = "NFournisseur";
                break;
        }
        DbConnection.updateApprovisiont(receiptApprovision.getnBon(), columnName,
                isText ? "'" + update.getNewValue() + "'"
                        : update.getNewValue() //adding a literal or numeric value?
                , tableReceipt);
    }

    @FXML
    void deleteReceipt(ActionEvent event) {
        ReceiptApprovision receiptApprovision = tableReceipt.getSelectionModel().getSelectedItem();
        if (receiptApprovision != null) {
            DbConnection.deleteApprovisiont(receiptApprovision.getnBon() + "", tableReceipt);
        } else {
            warningPannel("Erreur!", "Aucun élément n'est séléctionné!", "Selectionnez un Element SVP..", Alert.AlertType.ERROR);
        }
    }

    //region Navigation
    @FXML
    void articleViewSelected(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewArticle.fxml"));
            Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
            Launcher.stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stockViewSelected(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewStock.fxml"));
            Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
            Launcher.stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //endregion

    //endregion
}
