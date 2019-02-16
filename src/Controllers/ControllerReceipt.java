package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class ControllerReceipt {

    @FXML
    private TableView<?> tableClient;

    @FXML
    private TableColumn<?, ?> colNBon;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colNFournisseur;

    @FXML
    private TableView<?> tableFournisseur;

    @FXML
    private TableColumn<?, ?> colFournisseurNum;

    @FXML
    private TableColumn<?, ?> colNameFournisseur;

    @FXML
    private TableColumn<?, ?> colAdresseFournisseur;

    @FXML
    private TableColumn<?, ?> colTelephoneFournisseur;

    @FXML
    private TableColumn<?, ?> colFaxFournisseur;

    @FXML
    void articleViewSelected(MouseEvent event) {

    }

    @FXML
    void stockViewSelected(MouseEvent event) {

    }

    @FXML
    void updateClient(ActionEvent event) {

    }

    @FXML
    void updateFournisseur(ActionEvent event) {

    }

}
