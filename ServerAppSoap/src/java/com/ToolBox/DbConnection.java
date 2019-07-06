package com.ToolBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import com.Models.Article;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.*;
import javax.jws.WebMethod;
import javax.jws.WebService;

public class DbConnection {

    private static Connection connection;
    private static Statement statement;
    //narticle, label, price, minstock
    public static String articleDbName = "article";
    //nclient, name, adresse, telephone, fax
    public static String clientDbName = "client";
    //nFournisseur, name, adresse, telephone, fax
    public static String fournisseurDbName = "fournisseur";
    //NArticle , Date , QntA, Qntl, Stock
    public static String stockDbName = "stock";
    //NBonA, Date, NFournisseur
    public static String approvisiontDbName = "approvisiont";
    //NBonA, NArticle, QntA
    public static String detailAppDbName = "detail_app";
    //NBonL, Date, NClient
    public static String livraisonDbName = "livraison";
    //NBonL, NArticle, QntL
    public static String detailLivDbName = "detail_liv";
    public static ResultSet rs;

    public static ComboBox<String> articlesApp, fournisseurs, articlesLiv, clients;

    public static void createConnection() {
        rs = null;
        if (statement == null) {
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

    public static void addArticle(String nArticle, String label, String price, String minStock) {
        rs = null;
        try {
            //Checking if the Article doesn't already exist...
            rs = statement.executeQuery("SELECT * FROM "
                    + articleDbName + " WHERE NArticle = " + nArticle + ";");
            System.out.println(rs);
            if (!rs.next()) {
                //Inserting new article into database...
                statement.executeUpdate("INSERT INTO `" + articleDbName + "`(`NArticle`, `Label`, `Price`, `MinStock`)"
                        + " VALUES (" + nArticle + ",'" + label + "'," + price + "," + minStock + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteArticle(String nArticle) {
        try {
            statement.executeUpdate("DELETE FROM article WHERE NArticle = " + nArticle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateArticle(String nArticle, String columnName, String newValue) {
        try {
            statement.executeUpdate("UPDATE " + articleDbName + " SET " + columnName + " = " + newValue + " Where NArticle = " + nArticle + ";");
        } catch (SQLException e) {
            System.out.println("UPDATE " + articleDbName + " SET " + columnName + " = " + newValue + " Where NArticle = " + nArticle + ";");
            e.printStackTrace();
        }
    }

    public static ObservableList<Article> searchArticles(String bon, String date) {
        rs = null;
        ObservableList<Article> articles = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("SELECT * FROM " + articleDbName + ", " + detailAppDbName
                    + ", " + approvisiontDbName + " "
                    + " WHERE detail_app.NBonA= " + bon + " AND approvisiont.Date = '" + date + "'"
                    + " AND approvisiont.NBonA = " + bon + " AND article.NArticle = detail_app.NArticle;");
            while (rs.next()) {
                articles.add(new Article(rs.getInt(1) + "", rs.getString(2),
                        rs.getFloat(3) + "", rs.getInt(4) + ""));
            }
        } catch (SQLException e) {
            System.out.println("SELECT NArticle, Label, Price, MinStock FROM " + articleDbName + ", " + detailAppDbName
                    + "," + approvisiontDbName + " "
                    + " WHERE detail_app.NBonA=" + bon + " AND approvisiont.Date = '" + date + "'"
                    + " AND approvisiont.NBonA = " + bon + " AND article.NArticle = detail_app.NArticle;");
            e.printStackTrace();
        }
        return articles;
    }

    public static Article getArticle(String nArticle) {
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM " + articleDbName + " WHERE narticle = " + nArticle + ";");
            rs.first();
            Article article = new Article(rs.getInt(1) + "", rs.getString(2) + "",
                    rs.getFloat(3) + "", rs.getInt(4) + "");
            return article;
        } catch (SQLException e) {
            System.out.println("SELECT * FROM " + articleDbName + " WHERE narticle = " + nArticle + ";");
            e.printStackTrace();
        }
        return null;
    }

    public static int getIdOfArticle(String label) {
        rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM " + articleDbName + " WHERE Label = '" + label + "';");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("SELECT * FROM " + articleDbName + " WHERE Label = '" + label + "';");
            e.printStackTrace();
        }
        return -1;
    }

    //endregion
}
