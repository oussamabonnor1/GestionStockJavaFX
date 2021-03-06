package Controllers;

import Launcher.Launcher;
import Models.Stock;
import ToolBox.DbConnection;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ToolBox.Utilities.warningPannel;

public class ControllerStock implements Initializable {

    //region Variables
    @FXML
    private TableView<Stock> tableStock;

    @FXML
    private TableColumn<Stock, String> colnArticle, colDate, colQntA, colQntL, colStock;

    @FXML
    private JFXToggleButton toggle;

    @FXML
    private JFXTextField textFieldStartDate, textFieldEndDate;

    private ObservableList<Stock> stockObservableList = FXCollections.observableArrayList();
    //endregion

    //region Functions
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //fetching all stocks into observableList
        stockObservableList = DbConnection.getTableStock();

        //Binding the columns with the model's variables
        colnArticle.setCellValueFactory(new PropertyValueFactory<>("nArticle"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQntA.setCellValueFactory(new PropertyValueFactory<>("qntA"));
        colQntL.setCellValueFactory(new PropertyValueFactory<>("qntL"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        //binding the observables into the table
        tableStock.setItems(stockObservableList);

        textFieldEndDate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.matches("")) tableStock.setItems(DbConnection.getTableStock());
                else{
                    if(!textFieldStartDate.getText().matches("")){
                        tableStock.setItems(DbConnection.stockSearchDate(textFieldStartDate.getText(), textFieldEndDate.getText()));
                    }
                }
            }
        });
    }

    @FXML
    public void toggle(ActionEvent event) {
        if (toggle.isSelected()) {
            tableStock.setItems(DbConnection.shortStock());
        } else {
            tableStock.setItems(DbConnection.getTableStock());
        }
    }

    public void updateStock(TableColumn.CellEditEvent<Stock, String> cellEditEvent) {
        /*Stock tempStock = tableStock.getSelectionModel().getSelectedItem();
        TablePosition position = tableStock.getSelectionModel().getSelectedCells().get(0);
        String columnName = "";
        boolean isText = false;
        switch (position.getColumn()) {
            case 1:
                columnName = "Date";
                isText = true;
                break;
            case 2:
                columnName = "QntA";
                break;
            case 3:
                columnName = "QntA";
                break;
            case 4:
                columnName = "Stock";
                break;
        }
        DbConnection.updateStock(tempStock.getnArticle(), columnName,
                isText ? "'" + cellEditEvent.getNewValue() + "'"
                        : cellEditEvent.getNewValue() //adding a literal or numeric value?
                , tableStock);
                */
    }

    public void deleteStock(ActionEvent event) {
        Stock stockToDelete = tableStock.getSelectionModel().getSelectedItem();
        if (stockToDelete != null) {
            DbConnection.deleteStock(stockToDelete.getnArticle() + "", tableStock);
        } else {
            warningPannel("Erreur!", "Aucun élément n'est séléctionné!", "Selectionnez un stock SVP..", Alert.AlertType.ERROR);
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
    //endregion
}