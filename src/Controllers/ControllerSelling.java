package Controllers;

import Launcher.Launcher;
import Models.Client;
import Models.ReceiptLivraison;
import Models.ReceiptLivraison;
import ToolBox.DbConnection;
import com.jfoenix.controls.JFXTextField;
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

public class ControllerSelling implements Initializable {

    //region Variables
    //region Variables
    @FXML
    private JFXTextField textFieldDate, textFieldQntL;

    @FXML
    ComboBox<String> comboNumClient, comboNumArticle;

    @FXML
    private TableView<ReceiptLivraison> tableReceipt;

    @FXML
    private TableColumn<ReceiptLivraison, String> colNBon, colDate, colNClient;

    @FXML
    private TableView<ReceiptLivraison> tableProduit;

    @FXML
    private TableColumn<ReceiptLivraison, String> colNArticle, colNQntL, colPrix, colLabel;

    private ObservableList<ReceiptLivraison> receiptsObservableList = FXCollections.observableArrayList();
    private ObservableList<ReceiptLivraison> produitObservableList = FXCollections.observableArrayList();
    //endregion

    //region Functions
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DbConnection.articlesApp = comboNumArticle;
        DbConnection.clients = comboNumClient;
        //fetching all articlesApp into observableList
        receiptsObservableList = DbConnection.getTableLivraison();
        if (receiptsObservableList.size() > 0)
            produitObservableList = DbConnection.getArticlesListL(receiptsObservableList.get(0).getnBon());

        //Binding the columns with the model's variables
        colNBon.setCellValueFactory(new PropertyValueFactory<>("nBon"));
        colNArticle.setCellValueFactory(new PropertyValueFactory<>("nArticle"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNQntL.setCellValueFactory(new PropertyValueFactory<>("qntL"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
        colNClient.setCellValueFactory(new PropertyValueFactory<>("nClient"));

        //Making the columns editable
        editablesColumns(colDate, colNQntL);
        //binding the observables into the forms table
        tableReceipt.setItems(receiptsObservableList);
        //binding the observables into the products table
        tableProduit.setItems(produitObservableList);

        tableReceipt.getSelectionModel().getSelectedCells().addListener((ListChangeListener<TablePosition>) c -> {
            if (tableReceipt.getSelectionModel().getSelectedItem() != null) {
                produitObservableList = DbConnection.getArticlesListL(tableReceipt.getSelectionModel().getSelectedItem().getnBon());
                tableProduit.setItems(produitObservableList);
            }
        });

        //Making the inputs accept numeric values only (limit: 10)
        numericLimitedTextField(10, textFieldQntL);
    }

    @FXML
    void addReceipt(ActionEvent event) {
        //inputChecking makes sure that all the fields are filled...
        if (inputChecking(textFieldDate) && comboNumClient.getSelectionModel().getSelectedIndex() >= 0) {
            DbConnection.addLivraison(textFieldDate.getText(), DbConnection.getIdFournisseur(comboNumClient.getSelectionModel().getSelectedItem()), tableReceipt);
            inputDeleting(textFieldDate); //Clearing out the input UI
            comboNumClient.getSelectionModel().select(-1);
        } else {
            warningPannel("Erreur!", "Un ou plusieurs champs sont vide!", "Remplissez tout les champs SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void updateReceipt(TableColumn.CellEditEvent<ReceiptLivraison, String> update) {
        ReceiptLivraison ReceiptLivraison = tableReceipt.getSelectionModel().getSelectedItem();
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
        DbConnection.updateLivraison(ReceiptLivraison.getnBon(), columnName,
                isText ? "'" + update.getNewValue() + "'"
                        : update.getNewValue() //adding a literal or numeric value?
                , tableReceipt);
    }

    @FXML
    void deleteReceipt(ActionEvent event) {
        ReceiptLivraison ReceiptLivraison = tableReceipt.getSelectionModel().getSelectedItem();
        if (ReceiptLivraison != null) {
            DbConnection.deleteLivraison(ReceiptLivraison.getnBon() + "", tableReceipt);
        } else {
            warningPannel("Erreur!", "Aucun élément n'est séléctionné!", "Selectionnez un Element SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void addProduct(ActionEvent event) {
        if (inputChecking(textFieldQntL) && comboNumArticle.getSelectionModel().getSelectedIndex() >= 0 && tableReceipt.getSelectionModel().getSelectedIndex() >= 0) {

            ReceiptLivraison bon = tableReceipt.getSelectionModel().getSelectedItem();
            int produitId = DbConnection.getIdOfArticle(comboNumArticle.getSelectionModel().getSelectedItem());
            DbConnection.addLivraisonProduit(bon.getnBon(), produitId + "", textFieldQntL.getText(),
                    tableReceipt.getSelectionModel().getSelectedItem().getDate(), tableProduit);

            inputDeleting(textFieldQntL, textFieldDate); //Clearing out the input UI
            comboNumArticle.getSelectionModel().select(-1);
            comboNumClient.getSelectionModel().select(-1);
        } else if (tableReceipt.getSelectionModel().getSelectedIndex() < 0) {
            warningPannel("Erreur!", "Aucun bon n'est selectionné!", "Selectionnez un bon SVP..", Alert.AlertType.ERROR);
        } else {
            warningPannel("Erreur!", "Un ou plusieurs champs sont vide!", "Remplissez tout les champs SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        ReceiptLivraison ReceiptLivraison = tableProduit.getSelectionModel().getSelectedItem();
        if (ReceiptLivraison != null) {
            DbConnection.deleteLivraisonProduit(ReceiptLivraison.getnBon() + "", ReceiptLivraison.getnArticle() + "", tableProduit);
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
    public void shoppingViewSelected(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/viewReceipt.fxml"));
            Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
            Launcher.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //endregion

    //endregion
}
