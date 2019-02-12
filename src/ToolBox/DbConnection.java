package ToolBox;

import Models.Article;
import Models.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.sql.*;

public class DbConnection {
    public static Connection connection;
    public static Statement statement;
    public static String articleDbName = "article";
    //narticle, label, price, minstock
    public static String clientDbName = "client";
    //nclient, name, adresse, telephone, fax
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

    public static void updateArticle(String nArticle, String columnName, String newValue, TableView tableView) {
        try {
            statement.executeUpdate("UPDATE " + articleDbName + " SET " + columnName + " = " + newValue + " Where NArticle = " + nArticle + ";");
            tableView.setItems(getTableArticle());
        } catch (SQLException e) {
            System.out.println("UPDATE " + articleDbName + " SET " + columnName + " = " + newValue + " Where NArticle = " + nArticle + ";");
            e.printStackTrace();
        }
    }
    //endregion


    //region Clients
    public static ObservableList<Client> getTableClients() {
        rs = null;
        ObservableList<Client> clients = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + clientDbName + ";");
            while (rs.next()) {
                clients.add(new Client(rs.getInt(1) + "", rs.getString(2),
                        rs.getString(3), rs.getInt(4) + "", rs.getInt(5) + ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public static void addClient(String nClient, String name, String adresse, String telephone, String fax, TableView<Client> tableView) {
        rs = null;
        try {
            //Checking if the Client doesn't already exist...
            rs = statement.executeQuery("SELECT * FROM " +
                    clientDbName + " WHERE NClient = " + nClient + ";");
            if (!rs.next()) {
                //Inserting new Client into database...
                statement.executeUpdate("INSERT INTO " + clientDbName + " (`NClient`, `NomClient`, `Adresse`, `Telephone`, `Fax`)" +
                        " VALUES (" + nClient + ",'" + name + "','" + adresse + "'," + telephone + "," + fax + ");");
                tableView.setItems(getTableClients());
                Utilities.warningPannel("Félicitation", "Element bien ajoutée!", "", Alert.AlertType.INFORMATION);
            } else { //if article exists already
                Utilities.warningPannel("Erreur!", "Ce client existe déja!", "Veuillez vérifier le code introduit...", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            System.out.println("INSERT INTO `" + clientDbName + "(`NClient`, `NomClient`, `Adresse`, `Telephone`, `Fax`)" +
                    " VALUES (" + nClient + ",'" + name + "','" + adresse + "'," + telephone + "," + fax + ");");
            e.printStackTrace();
        }
    }

    public static void deleteClient(String nClient, TableView<Client> tableView) {
        try {
            statement.executeUpdate("DELETE FROM client WHERE NClient = " + nClient);
            tableView.setItems(getTableClients());
            Utilities.warningPannel("Félicitation", "Element bien supprimé!", "", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateClient(String nClient, String columnName, String newValue, TableView tableView) {
        try {
            statement.executeUpdate("UPDATE " + clientDbName + " SET " + columnName + " = " + newValue + " Where NClient = " + nClient + ";");
            tableView.setItems(getTableClients());
        } catch (SQLException e) {
            System.out.println("UPDATE " + articleDbName + " SET " + columnName + " = " + newValue + " Where NArticle = " + nClient + ";");
            e.printStackTrace();
        }
    }

    //endregion
}