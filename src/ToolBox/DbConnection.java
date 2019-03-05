package ToolBox;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.sql.*;

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
            if (Utilities.confirmationPanel("Attention", "Cet élément sera supprimé", "etes vous sure ?")) {
                statement.executeUpdate("DELETE FROM article WHERE NArticle = " + nArticle);
                tableView.setItems(getTableArticle());
            }
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
            if (Utilities.confirmationPanel("Attention", "Cet élément sera supprimé", "etes vous sure ?")) {
                statement.executeUpdate("DELETE FROM client WHERE NClient = " + nClient);
                tableView.setItems(getTableClients());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateClient(String nClient, String columnName, String newValue, TableView tableView) {
        try {
            statement.executeUpdate("UPDATE " + clientDbName + " SET " + columnName + " = " + newValue + " Where NClient = " + nClient + ";");
            tableView.setItems(getTableClients());
        } catch (SQLException e) {
            System.out.println("UPDATE " + clientDbName + " SET " + columnName + " = " + newValue + " Where NClient = " + nClient + ";");
            e.printStackTrace();
        }
    }

    //endregion

    //region Fournisseur
    public static ObservableList<Fournisseur> getTableFournisseur() {
        rs = null;
        ObservableList<Fournisseur> fournisseurs = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + fournisseurDbName + ";");
            while (rs.next()) {
                fournisseurs.add(new Fournisseur(rs.getInt(1) + "", rs.getString(2),
                        rs.getString(3), rs.getInt(4) + "", rs.getInt(5) + ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseurs;
    }

    public static void addFournisseur(String nFournisseur, String name, String adresse, String telephone, String fax, TableView<Fournisseur> tableView) {
        rs = null;
        try {
            //Checking if the Client doesn't already exist...
            rs = statement.executeQuery("SELECT * FROM " +
                    fournisseurDbName + " WHERE NFournisseur = " + nFournisseur + ";");
            if (!rs.next()) {
                //Inserting new Client into database...
                statement.executeUpdate("INSERT INTO " + fournisseurDbName + " (`NFournisseur`, `NomFournisseur`, `Adresse`, `Telephone`, `Fax`)" +
                        " VALUES (" + nFournisseur + ",'" + name + "','" + adresse + "'," + telephone + "," + fax + ");");
                tableView.setItems(getTableFournisseur());
                Utilities.warningPannel("Félicitation", "Element bien ajoutée!", "", Alert.AlertType.INFORMATION);
            } else { //if article exists already
                Utilities.warningPannel("Erreur!", "Ce Fournisseur existe déja!", "Veuillez vérifier le code introduit...", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            System.out.println("INSERT INTO " + fournisseurDbName + " (`NFournisseur`, `NomFournisseur`, `Adresse`, `Telephone`, `Fax`)" +
                    " VALUES (" + nFournisseur + ",'" + name + "','" + adresse + "'," + telephone + "," + fax + ");");
            e.printStackTrace();
        }
    }

    public static void deleteFournisseur(String nFournisseur, TableView<Fournisseur> tableView) {
        try {
            if (Utilities.confirmationPanel("Attention", "Cet élément sera supprimé", "etes vous sure ?")) {
                statement.executeUpdate("DELETE FROM " + fournisseurDbName + " WHERE NFournisseur = " + nFournisseur);
                tableView.setItems(getTableFournisseur());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateFournisseur(String nFournisseur, String columnName, String newValue, TableView tableView) {
        try {
            statement.executeUpdate("UPDATE " + fournisseurDbName + " SET " + columnName + " = " + newValue + " Where NFournisseur = " + nFournisseur + ";");
            tableView.setItems(getTableFournisseur());
        } catch (SQLException e) {
            System.out.println("UPDATE " + fournisseurDbName + " SET " + columnName + " = " + newValue + " Where NFournisseur = " + nFournisseur + ";");
            e.printStackTrace();
        }
    }

    //endregion

    //region Stock
    public static ObservableList<Stock> getTableStock() {
        rs = null;
        ObservableList<Stock> stocks = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + stockDbName + ";");
            while (rs.next()) {
                stocks.add(new Stock(rs.getInt(1) + "", rs.getDate(2) + "",
                        rs.getInt(4) + "", rs.getInt(3) + "", rs.getInt(5) + ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public static void addStock(String nArticle, String date, String qntA, String qntL, String stock, TableView<Stock> tableView) {
        rs = null;
        try {
            if (statement.executeQuery("Select * from " + stockDbName + " where NArticle = " + nArticle + ";").next()) {
                if (Integer.valueOf(qntA) > 0)
                    statement.executeUpdate("UPDATE " + stockDbName + " SET Date = '" + date + "', QntA = " + qntA +
                            ", Stock = Stock +" + qntA
                            + " Where NArticle= " + nArticle + ";");
                else
                    statement.executeUpdate("UPDATE " + stockDbName + " SET Date = '" + date + "', QntA = " + qntA +
                            ", Stock = Stock -" + qntL
                            + " Where NArticle= " + nArticle + ";");
            } else {
                //Inserting new Client into database...
                statement.executeUpdate("INSERT INTO " + stockDbName + " (`NArticle`, `Date`, `QntA`, `Qntl`, `Stock`)" +
                        " VALUES (" + nArticle + ",'" + date + "'," + qntA + "," + qntL + "," + stock + ");");
                tableView.setItems(getTableStock());
                Utilities.warningPannel("Félicitation", "Element bien ajoutée!", "", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            System.out.println("INSERT INTO " + stockDbName + " (`NArticle`, `Date`, `QntA`, `Qntl`, `Stock`)" +
                    " VALUES (" + nArticle + ",'" + date + "'," + qntA + "," + qntL + "," + stock + ");");
            e.printStackTrace();
        }
    }

    public static void deleteStock(String nArticle, TableView<Stock> tableView) {
        try {
            if (Utilities.confirmationPanel("Attention", "Cet élément sera supprimé", "etes vous sure ?")) {
                statement.executeUpdate("DELETE FROM " + stockDbName + " WHERE NArticle= " + nArticle);
                tableView.setItems(getTableStock());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStock(String nArticle, String columnName, String newValue, TableView<Stock> tableView) {
        try {
            statement.executeUpdate("UPDATE " + stockDbName + " SET " + columnName + " = " + newValue + " Where NArticle= " + nArticle + ";");
            tableView.setItems(getTableStock());
        } catch (SQLException e) {
            System.out.println("UPDATE " + stockDbName + " SET " + columnName + " = " + newValue + " Where NArticle= " + nArticle + ";");
            e.printStackTrace();
        }
    }

    //endregion

    //region Approvisiont
    public static ObservableList<ReceiptApprovision> getTableApprovisiont() {
        rs = null;
        ObservableList<ReceiptApprovision> approvisionts = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + approvisiontDbName + "," + detailAppDbName +
                    " WHERE " + approvisiontDbName + ".NBonA = " + detailAppDbName + ".NBonA;");
            while (rs.next()) {
                approvisionts.add(new ReceiptApprovision(rs.getInt(1) + "", rs.getDate(2) + "",
                        rs.getInt(3) + "", rs.getInt(5) + "", rs.getInt(6) + ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateComboBoxesApprovision();
        return approvisionts;
    }

    static void updateComboBoxesApprovision() {
        rs = null;
        ObservableList<String> articlesList = FXCollections.observableArrayList();
        ObservableList<String> fournisseurList = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select NArticle from " + articleDbName + ";");
            while (rs.next()) {
                articlesList.add(rs.getString(1));
            }
            rs = statement.executeQuery("Select NFournisseur from " + fournisseurDbName + ";");
            while (rs.next()) {
                fournisseurList.add(rs.getString(1));
            }
            articlesApp.setItems(articlesList);
            fournisseurs.setItems(fournisseurList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addApprovisiont(String nArticle, String date, String qntA, String nFournisseur, TableView<ReceiptApprovision> tableView) {
        rs = null;
        int row = 0;
        try {
            //Inserting new app into database...
            //part 1 (approvisionnement)
            statement.executeUpdate("INSERT INTO " + approvisiontDbName + " (`Date`, `NFournisseur`)" +
                    " VALUES ('" + date + "'," + nFournisseur + ");");
            //part 2 (detail app)
            rs = statement.executeQuery("Select NBonA from " + approvisiontDbName + " where NBonA = (Select MAX(NBonA) from " + approvisiontDbName + ");");
            rs.next();
            row = rs.getInt(1);
            statement.executeUpdate("INSERT INTO " + detailAppDbName + " (`NBonA`,`NArticle`, `QntA`)" +
                    " VALUES (" + row + "," + nArticle + "," + qntA + ");");
            //populating the table
            tableView.setItems(getTableApprovisiont());
            //creating/updating the stock
            addStock(nArticle, date, qntA, "0", qntA, new TableView<>());
            Utilities.warningPannel("Félicitation", "Element bien ajoutée!", "", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            System.out.println("INSERT INTO " + approvisiontDbName + " (`Date`, `NFournisseur`)" +
                    " VALUES ('" + date + "'," + nFournisseur + ");");
            System.out.println("INSERT INTO " + detailAppDbName + " (`NBonA`,`NArticle`, `QntA`)" +
                    " VALUES (" + row + "," + nArticle + "," + qntA + ");");
            e.printStackTrace();
        }
    }

    public static void deleteApprovisiont(String nBonA, TableView<ReceiptApprovision> tableView) {
        try {
            if (Utilities.confirmationPanel("Attention", "Cet élément sera supprimé", "etes vous sure ?")) {
                statement.executeUpdate("DELETE FROM " + approvisiontDbName + " WHERE NBonA = " + nBonA);
                tableView.setItems(getTableApprovisiont());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateApprovisiont(String nBonA, String columnName, String newValue, TableView<ReceiptApprovision> tableView) {
        try {
            if (columnName.matches("NArticle") || columnName.matches("QntA")) {
                statement.executeUpdate("UPDATE " + detailAppDbName + " SET " + columnName + " = " + newValue + " Where NBonA= " + nBonA + ";");
            } else {
                statement.executeUpdate("UPDATE " + approvisiontDbName + " SET " + columnName + " = " + newValue + " Where NBonA= " + nBonA + ";");
                //statement.executeUpdate("UPDATE " + stockDbName + " SET " + columnName + " = " + newValue + " where NArticle = " + nArticle + ";");
            }
            tableView.setItems(getTableApprovisiont());
        } catch (SQLException e) {
            System.out.println("UPDATE " + approvisiontDbName + " SET " + columnName + " = " + newValue + " Where NBonA= " + nBonA + ";");
            System.out.println("UPDATE " + detailAppDbName + " SET " + columnName + " = " + newValue + " Where NBonA= " + nBonA + ";");
            e.printStackTrace();
        }
    }

    //endregion

    //region Livraison
    public static ObservableList<ReceiptLivraison> getTableLivraison() {
        rs = null;
        ObservableList<ReceiptLivraison> livraisons = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + livraisonDbName + "," + detailLivDbName +
                    " WHERE " + livraisonDbName + ".NBonL = " + detailAppDbName + ".NBonL;");
            while (rs.next()) {
                livraisons.add(new ReceiptLivraison(rs.getInt(1) + "", rs.getDate(2) + "",
                        rs.getInt(3) + "", rs.getInt(5) + "", rs.getInt(6) + ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateComboBoxesLivraison();
        return livraisons;
    }

    static void updateComboBoxesLivraison() {
        rs = null;
        ObservableList<String> articlesList = FXCollections.observableArrayList();
        ObservableList<String> clientList = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select NArticle from " + articleDbName + ";");
            while (rs.next()) {
                articlesList.add(rs.getString(1));
            }
            rs = statement.executeQuery("Select NClient from " + clientDbName + ";");
            while (rs.next()) {
                clientList.add(rs.getString(1));
            }
            articlesLiv.setItems(articlesList);
            clients.setItems(clientList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addLivraison(String nArticle, String date, String qntL, String nClient, TableView<ReceiptLivraison> tableView) {
        rs = null;
        int row = 0;
        try {
            //Inserting new liv into database...
            //part 1 (Livraison)
            statement.executeUpdate("INSERT INTO " + livraisonDbName + " (`Date`, `NClient`)" +
                    " VALUES ('" + date + "'," + nClient + ");");
            //part 2 (detail liv)
            rs = statement.executeQuery("Select NBonL from " + livraisonDbName + " where NBonL = (Select MAX(NBonL) from " + livraisonDbName + ");");
            rs.next();
            row = rs.getInt(1);
            statement.executeUpdate("INSERT INTO " + detailLivDbName + " (`NBonL`,`NArticle`, `QntL`)" +
                    " VALUES (" + row + "," + nArticle + "," + qntL + ");");
            //populating the table
            tableView.setItems(getTableLivraison());
            //creating/updating the stock
            //TODO: check this damn thing
            addStock(nArticle, date, "0", qntL, "0", new TableView<>());
            Utilities.warningPannel("Félicitation", "Element bien ajoutée!", "", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            System.out.println("INSERT INTO " + livraisonDbName + " (`Date`, `NClient`)" +
                    " VALUES ('" + date + "'," + nClient + ");");
            System.out.println("INSERT INTO " + detailLivDbName + " (`NBonL`,`NArticle`, `QntL`)" +
                    " VALUES (" + row + "," + nArticle + "," + qntL + ");");
            e.printStackTrace();
        }
    }

    public static void deleteLivraison(String nBonL, TableView<ReceiptLivraison> tableView) {
        try {
            if (Utilities.confirmationPanel("Attention", "Cet élément sera supprimé", "etes vous sure ?")) {
                statement.executeUpdate("DELETE FROM " + livraisonDbName + " WHERE NBonL = " + nBonL);
                tableView.setItems(getTableLivraison());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLivraison(String nBonL, String columnName, String newValue, TableView<ReceiptLivraison> tableView) {
        try {
            if (columnName.matches("NArticle") || columnName.matches("QntL")) {
                statement.executeUpdate("UPDATE " + detailLivDbName + " SET " + columnName + " = " + newValue + " Where NBonL= " + nBonL + ";");
            } else {
                statement.executeUpdate("UPDATE " + livraisonDbName + " SET " + columnName + " = " + newValue + " Where NBonL= " + nBonL + ";");
                //statement.executeUpdate("UPDATE " + stockDbName + " SET " + columnName + " = " + newValue + " where NArticle = " + nArticle + ";");
            }
            tableView.setItems(getTableLivraison());
        } catch (SQLException e) {
            System.out.println("UPDATE " + detailLivDbName + " SET " + columnName + " = " + newValue + " Where NBonL= " + nBonL + ";");
            System.out.println("UPDATE " + livraisonDbName + " SET " + columnName + " = " + newValue + " Where NBonL= " + nBonL + ";");
            e.printStackTrace();
        }
    }

    //endregion

}