package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import javafx.event.ActionEvent;

public class ControllerShop {

    @FXML
    private TableView<?> tableStock;

    @FXML
    private TableColumn<?, ?> colnArticle;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colQntA;

    @FXML
    private TableColumn<?, ?> colQntL;

    @FXML
    private TableColumn<?, ?> colStock;

    @FXML
    void articleViewSelected(MouseEvent event) {

    }

    @FXML
    void clientViewSelected(MouseEvent event) {

    }

    public void updateStock(TableColumn.CellEditEvent<?, ?> cellEditEvent) {

    }

    public void deleteStock(ActionEvent event) {

    }
}


