package Controllers;

import Models.Article;
import ToolBox.DbConnection;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static ToolBox.Utilities.*;

public class ControllerArticleView implements Initializable {

    //region Variables

    @FXML
    private TableView<Article> tableArticle;

    @FXML
    private TableColumn<Article, String> colId, colLabel, colPrice, colMinStock;

    @FXML
    private JFXTextField textFieldNArticle, textFieldLabel, textFieldPrice, textFieldMinStock;

    private ObservableList<Article> articleObservableList = FXCollections.observableArrayList();
    //endregion


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DbConnection.createConnection();
        //fetching all articles into observableList
        articleObservableList = DbConnection.getTableArticle();

        colId.setCellValueFactory(new PropertyValueFactory<>("nArticle"));
        colLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colMinStock.setCellValueFactory(new PropertyValueFactory<>("minStock"));
        tableArticle.setItems(articleObservableList); //binding the observables into the table

        numericLimitedTextField(10, textFieldNArticle, textFieldPrice, textFieldMinStock);
    }

    //region Methods
    @FXML
    void addArticle(ActionEvent event) {
        //inputChecking makes sure that all the fields are filled...
        if (inputChecking(textFieldNArticle, textFieldPrice, textFieldLabel, textFieldMinStock)) {
            DbConnection.addArticle(textFieldNArticle.getText(), textFieldLabel.getText(), textFieldPrice.getText(), textFieldMinStock.getText(), tableArticle);
        } else {
            warningPannel("Erreur!", "Un ou plusieurs champs sont vide!", "Remplissez tout les champs SVP..", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void clientViewSelected(MouseEvent event) {

    }

    @FXML
    void deleteArticle(ActionEvent event) {

    }

    @FXML
    void shopViewSelected(MouseEvent event) {

    }

    @FXML
    void updateArticle(ActionEvent event) {

    }

    //endregion

}
