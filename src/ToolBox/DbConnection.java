package ToolBox;

import Models.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.sql.*;

public class DbConnection {
    public static Connection connection;
    public static Statement statement;
    public static String articleDbName = "article";
    public static ResultSet rs;

    public static void createConnection() {
        rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/stockManagement";
            connection = DriverManager.getConnection(dbURL, "root", "");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println(" Echec !");
            e.printStackTrace();
        }
    }

    //region Articles
    public static ObservableList<Article> getTableArticle() {
        rs = null;
        ObservableList<Article> articles = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + articleDbName + ";");
            while (rs.next()) {
                articles.add(new Article(rs.getInt(1) + "", rs.getString(2), rs.getFloat(3) + "", rs.getInt(4) + ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public static void addArticle(String nArticle, String label, String price, String minStock, TableView<Article> tableView) {
        rs = null;
        try {
            //Checking if the Article doesn't already exist...
            rs = statement.executeQuery("SELECT * FROM " +
                    articleDbName + " WHERE NArticle = " + nArticle + ";");
            if (!rs.next()) {
                //Inserting new article into database...
                statement.executeUpdate("INSERT INTO `" + articleDbName + "`(`NArticle`, `Label`, `Price`, `MinStock`)" +
                        " VALUES (" + nArticle + ",'" + label + "'," + price + "," + minStock + ")");
                tableView.setItems(getTableArticle());
                Utilities.warningPannel("Félicitation", "Element bien ajoutée!", "", Alert.AlertType.INFORMATION);
            } else { //if article exists already
                Utilities.warningPannel("Erreur!", "Cet article existe déja!", "Veuillez vérifier le code introduit...", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteArticle(String nArticle, TableView<Article> tableView) {
        try {
            statement.executeUpdate("DELETE FROM article WHERE NArticle = " + nArticle);
            tableView.setItems(getTableArticle());
            Utilities.warningPannel("Félicitation", "Element bien supprimé!", "", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateArticle(String nArticle, String columnName, String newValue, boolean isText, TableView tableView) {
        try {
            //Checking if the Article doesn't already exist...
            statement.executeUpdate(isText ? "UPDATE " + articleDbName + " SET " + columnName + " = '" + newValue + "' Where NArticle = " + nArticle + ";"
                    : "UPDATE " + articleDbName + " SET " + columnName + " = " + newValue + " Where NArticle = " + nArticle + ";");
            tableView.setItems(getTableArticle());
        } catch (SQLException e) {
            System.out.println("UPDATE " + articleDbName + " SET " + columnName + " = " + newValue + " Where NArticle = " + nArticle + ";");
            e.printStackTrace();
        }
    }
    //endregion
}