package Controllers;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static ToolBox.Utilities.numericLimitedTextField;

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
        //binding the observables into the table
        tableClient.setItems(clientObservableList);

        //Making the inputs accept numeric values only (limit: 10)
        numericLimitedTextField(10, textFieldNumero, textFieldTelephone, textFieldFax);
    }

    //region Functions
    @FXML
    void AddPerson(ActionEvent event) {

    }

    @FXML
    void articleViewSelected(MouseEvent event) {

    }

    @FXML
    void deletePerson(ActionEvent event) {

    }

    @FXML
    void shopViewSelected(MouseEvent event) {

    }

    @FXML
    void updateArticle(ActionEvent event) {

    }
    //endregion

}
