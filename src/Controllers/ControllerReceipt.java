package Controllers;

import Models.Client;
import Models.Fournisseur;
import Models.ReceiptApprovision;
import ToolBox.DbConnection;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
                columnName = "NomClient";
                isText = true;
                break;
            case 2:
                columnName = "Adresse";
                isText = true;
                break;
            case 3:
                columnName = "Telephone";
                break;
            case 4:
                columnName = "Fax";
                break;
        }
        DbConnection.updateApprovisiont(receiptApprovision.getnBon(), columnName,
                isText ? "'" + update.getNewValue() + "'"
                        : update.getNewValue() //adding a literal or numeric value?
                , tableReceipt);
    }

    @FXML
    void deleteReceipt(ActionEvent event) {
        /*Client clientToDelete = tableClient.getSelectionModel().getSelectedItem();
        Fournisseur fournisseurToDelete = tableFournisseur.getSelectionModel().getSelectedItem();
        if (clientToDelete != null) {
            DbConnection.deleteClient(clientToDelete.getnClient() + "", tableClient);
        } else if (fournisseurToDelete != null) {
            DbConnection.deleteFournisseur(fournisseurToDelete.getnFournisseur() + "", tableFournisseur);
        } else {
            warningPannel("Erreur!", "Aucun élément n'est séléctionné!", "Selectionnez un Element SVP..", Alert.AlertType.ERROR);
        }*/
    }

    //region Navigation
    @FXML
    void articleViewSelected(MouseEvent event) {

    }

    @FXML
    void clientViewSelected(MouseEvent event) {

    }

    @FXML
    void stockViewSelected(MouseEvent event) {

    }

    //endregion

    //endregion
}
