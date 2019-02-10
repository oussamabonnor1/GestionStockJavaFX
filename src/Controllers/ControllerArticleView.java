package Controllers;

import Models.Article;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class ControllerArticleView {

    //region Variables

    @FXML
    private TableView<Article> tableArticle;

    @FXML
    private TableColumn<Article, String> colId, colLabel, colPrice, colMinStock, textFieldNArticle;

    @FXML
    private JFXTextField textFieldLabel, textFieldPrice, textFieldMinStock;

    //endregion

    //region Methods
    @FXML
    void addArticle(ActionEvent event) {

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
