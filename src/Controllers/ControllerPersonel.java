package Controllers;

import Models.Article;
import Models.Client;
import Models.Fournisseur;
import ToolBox.DbConnection;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static ToolBox.Utilities.*;

public class ControllerPersonel implements Initializable {

    //region Variables
    @FXML
    private JFXTextField textFieldNumero, textFieldName, textFieldAdresse, textFieldTelephone, textFieldFax;

    @FXML
    private JFXComboBox<?> comboBoxType;

    @FXML
    private TableView<Client> tableClient;

    @FXML
    private TableColumn<Client, String> colCLientNum, colClientName, colAdresse, colTelephone, colFax;

    @FXML
    private TableView<?> tableFournisseur;

    @FXML
    private TableColumn<?, ?> colFournisseurNum, colNameFournisseur, colAdresseFournisseur, colTelephoneFournisseur, colFaxFournisseur;

    private ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
    private ObservableList<Fournisseur> fournisseurObservableList = FXCollections.observableArrayList();
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //fetching all articles into observableList
        clientObservableList = DbConnection.getTableClients();
        //fournisseurObservableList = DbConnection.getTableFournisseur();

        //Binding the columns with the model's variables
        colCLientNum.setCellValueFactory(new PropertyValueFactory<>("nClient"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colFax.setCellValueFactory(new PropertyValueFactory<>("fax"));
        //Making the columns editable
        editablesColumns(colClientName, colAdresse, colTelephone, colFax);
        //binding the observables into the table
        tableClient.setItems(clientObservableList);

        //Making the inputs accept numeric values only (limit: 10)
        numericLimitedTextField(10, textFieldNumero, textFieldTelephone, textFieldFax);
    }

    //region Functions
    @FXML
    void AddPerson(ActionEvent event) {
        //inputChecking makes sure that all the fields are filled...
        if (inputChecking(textFieldName, textFieldNumero, textFieldTelephone, textFieldFax)) {
            DbConnection.addClient(textFieldNumero.getText(), textFieldName.getText(), textFieldAdresse.getText(), textFieldTelephone.getText(), textFieldFax.getText(), tableClient);
            inputDeleting(textFieldName, textFieldNumero, textFieldTelephone, textFieldFax); //Clearing out the input UI
        } else {
            warningPannel("Erreur!", "Un ou plusieurs champs sont vide!", "Remplissez tout les champs SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void deletePerson(ActionEvent event) {
        Client clientToDelete = tableClient.getSelectionModel().getSelectedItem();
        if (clientToDelete != null) {
            DbConnection.deleteClient(clientToDelete.getnClient() + "", tableClient);
        } else {
            warningPannel("Erreur!", "Aucun élément n'est séléctionné!", "Selectionnez un client SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void updateClient(TableColumn.CellEditEvent<Client, String> update) {
        Client clientTemp = tableClient.getSelectionModel().getSelectedItem();
        TablePosition position = tableClient.getSelectionModel().getSelectedCells().get(0);
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
        DbConnection.updateClient(clientTemp.getnClient(), columnName,
                isText ? "'" + update.getNewValue() + "'"
                        : update.getNewValue() //adding a literal or numeric value?
                , tableClient);
    }

    @FXML
    void articleViewSelected(MouseEvent event) {

    }

    @FXML
    void shopViewSelected(MouseEvent event) {

    }

    //endregion

}
