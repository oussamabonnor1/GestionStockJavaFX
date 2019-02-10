package ToolBox;

import Models.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.sql.*;

import ToolBox.Utilities;

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

    public static ObservableList<Article> getTableArticle() {
        rs = null;
        ObservableList<Article> articles = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + articleDbName + ";");
            while (rs.next()) {
                articles.add(new Article(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4)));
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
                    articleDbName + " WHERE " + articleDbName + ".NArticle = " + nArticle + ";");
            if (!rs.next()) {
                //Inserting new article into database...
                statement.executeUpdate("INSERT INTO `" + articleDbName + "`(`NArticle`, `Label`, `Price`, `MinStock`)" +
                        " VALUES (" + nArticle + ",'" + label + "'," + price + "," + minStock + ")");
                tableView.setItems(getTableArticle());
                Utilities.warningPannel("Félicitation", "Element bien ajoutée!", "", Alert.AlertType.INFORMATION);
            } else { //if article exists already
                //TODO: Popup info displaying error 10/02/2019
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteArticle(String nArticle) {
        try {
            statement.executeUpdate("DELETE FROM `article` WHERE " + nArticle);
            System.out.println("Item deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}