package Controllers;

import Launcher.Launcher;
import Models.Article;
import Models.Client;
import Models.ReceiptApprovision;
import Models.ReceiptLivraison;
import ToolBox.DbConnection;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import static ToolBox.Utilities.warningPannel;

public class ControllerReceipt implements Initializable {

    //region Variables
    @FXML
    private JFXTextField textFieldDate, textFieldQntA;

    @FXML
    ComboBox<String> comboNumFournisseur, comboNumArticle;

    @FXML
    private TableView<ReceiptApprovision> tableReceipt;

    @FXML
    private TableColumn<ReceiptApprovision, String> colNBon, colDate, colNFournisseur;

    @FXML
    private TableView<ReceiptApprovision> tableProduit;

    @FXML
    private TableColumn<ReceiptApprovision, String> colNArticle, colNQntA, colPrix, colLabel;

    private ObservableList<ReceiptApprovision> receiptsObservableList = FXCollections.observableArrayList();
    private ObservableList<ReceiptApprovision> produitObservableList = FXCollections.observableArrayList();
    //endregion

    //region Functions
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DbConnection.articlesApp = comboNumArticle;
        DbConnection.fournisseurs = comboNumFournisseur;
        //fetching all articlesApp into observableList
        receiptsObservableList = DbConnection.getTableApprovisiont();
        if (receiptsObservableList.size() > 0)
            produitObservableList = DbConnection.getArticlesList(receiptsObservableList.get(0).getnBon());

        //Binding the columns with the model's variables
        colNBon.setCellValueFactory(new PropertyValueFactory<>("nBon"));
        colNArticle.setCellValueFactory(new PropertyValueFactory<>("nArticle"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNQntA.setCellValueFactory(new PropertyValueFactory<>("qntA"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
        colNFournisseur.setCellValueFactory(new PropertyValueFactory<>("nFournisseur"));

        //Making the columns editable
        editablesColumns(colDate, colNQntA);
        //binding the observables into the forms table
        tableReceipt.setItems(receiptsObservableList);
        //binding the observables into the products table
        tableProduit.setItems(produitObservableList);

        tableReceipt.getSelectionModel().getSelectedCells().addListener((ListChangeListener<TablePosition>) c -> {
            if (tableReceipt.getSelectionModel().getSelectedItem() != null) {
                produitObservableList = DbConnection.getArticlesList(tableReceipt.getSelectionModel().getSelectedItem().getnBon());
                tableProduit.setItems(produitObservableList);
            }
        });

        //Making the inputs accept numeric values only (limit: 10)
        numericLimitedTextField(10, textFieldQntA);
    }

    @FXML
    void addReceipt(ActionEvent event) {
        //inputChecking makes sure that all the fields are filled...
        if (inputChecking(textFieldDate) && comboNumFournisseur.getSelectionModel().getSelectedIndex() >= 0) {
            DbConnection.addApprovisiont(textFieldDate.getText(), DbConnection.getIdFournisseur(comboNumFournisseur.getSelectionModel().getSelectedItem()), tableReceipt);
            inputDeleting(textFieldDate); //Clearing out the input UI
            comboNumFournisseur.getSelectionModel().select(-1);
        } else {
            warningPannel("Erreur!", "Un ou plusieurs champs sont vide!", "Remplissez tout les champs SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void updateReceipt(TableColumn.CellEditEvent<ReceiptApprovision, String> update) {
        ReceiptApprovision receiptApprovision = tableReceipt.getSelectionModel().getSelectedItem();
        TablePosition position = tableReceipt.getSelectionModel().getSelectedCells().get(0);
        String columnName = "";
        boolean isText = false;
        switch (position.getColumn()) {
            case 2:
                columnName = "Date";
                isText = true;
                break;
            case 3:
                columnName = "QntA";
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

    @FXML
    void addProduct(ActionEvent event) {
        if (inputChecking(textFieldQntA) && comboNumArticle.getSelectionModel().getSelectedIndex() >= 0 && tableReceipt.getSelectionModel().getSelectedIndex() >= 0) {

            ReceiptApprovision bon = tableReceipt.getSelectionModel().getSelectedItem();
            int produitId = DbConnection.getIdOfArticle(comboNumArticle.getSelectionModel().getSelectedItem());
            DbConnection.addApprovisiontProduit(bon.getnBon(), produitId + "", textFieldQntA.getText(),
                    tableReceipt.getSelectionModel().getSelectedItem().getDate(), tableProduit);

            inputDeleting(textFieldQntA, textFieldDate); //Clearing out the input UI
            comboNumArticle.getSelectionModel().select(-1);
            comboNumFournisseur.getSelectionModel().select(-1);
        } else if (tableReceipt.getSelectionModel().getSelectedIndex() < 0) {
            warningPannel("Erreur!", "Aucun bon n'est selectionné!", "Selectionnez un bon SVP..", Alert.AlertType.ERROR);
        } else {
            warningPannel("Erreur!", "Un ou plusieurs champs sont vide!", "Remplissez tout les champs SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        ReceiptApprovision receiptApprovision = tableProduit.getSelectionModel().getSelectedItem();
        if (receiptApprovision != null) {
            DbConnection.deleteApprovisiontProduit(receiptApprovision.getnBon() + "", receiptApprovision.getnArticle() + "", tableProduit);
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

    @FXML
    public void stockViewSelected(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewStock.fxml"));
            Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
            Launcher.stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clientViewSelected(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewPersonel.fxml"));
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

    //endregion
}
