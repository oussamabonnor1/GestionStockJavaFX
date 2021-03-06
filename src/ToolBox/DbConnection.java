package ToolBox;

import Controllers.ControllerSelling;
import Launcher.Launcher;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
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

    public static ObservableList<Article> searchArticles(String bon, String date) {
        rs = null;
        ObservableList<Article> articles = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("SELECT * FROM " + articleDbName + ", " + detailAppDbName
                    + ", " + approvisiontDbName + " " +
                    " WHERE detail_app.NBonA= " + bon + " AND approvisiont.Date = '" + date + "'" +
                    " AND approvisiont.NBonA = " + bon + " AND article.NArticle = detail_app.NArticle;");
            while (rs.next()) {
                articles.add(new Article(rs.getInt(1) + "", rs.getString(2),
                        rs.getFloat(3) + "", rs.getInt(4) + ""));
            }
        } catch (SQLException e) {
            System.out.println("SELECT NArticle, Label, Price, MinStock FROM " + articleDbName + ", " + detailAppDbName
                    + "," + approvisiontDbName + " " +
                    " WHERE detail_app.NBonA=" + bon + " AND approvisiont.Date = '" + date + "'" +
                    " AND approvisiont.NBonA = " + bon + " AND article.NArticle = detail_app.NArticle;");
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

    public static ObservableList<Client> searchClients(String address) {
        rs = null;
        ObservableList<Client> clients = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + clientDbName + " where adresse = '" + address + "';");
            while (rs.next()) {
                clients.add(new Client(rs.getInt(1) + "", rs.getString(2),
                        rs.getString(3), rs.getInt(4) + "", rs.getInt(5) + ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
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

    public static String getIdFournisseur(String Name) {
        rs = null;
        try {
            rs = statement.executeQuery("Select * FROM " + fournisseurDbName + " WHERE NomFournisseur = '" + Name + "';");
            rs.next();
            return rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
                else {
                    statement.executeUpdate("UPDATE " + stockDbName + " SET Date = '" + date + "', QntL = " + qntL +
                            ", Stock = Stock -" + qntL
                            + " Where NArticle= " + nArticle + ";");
                    rs = statement.executeQuery("Select Stock from " + stockDbName + " where NArticle = " + nArticle + ";");
                    if (rs.next()) {
                        int stockLeft = rs.getInt(1);
                        rs = statement.executeQuery("Select MinStock from " + articleDbName + " where NArticle = " + nArticle + ";");
                        rs.next();
                        int minStock = rs.getInt(1);
                        if (stockLeft <= minStock) {
                            if (Utilities.confirmationPanel("Attention!", "Votre stock du article:" + nArticle + " est presque épuisé!",
                                    "Voulez vous le recharger maintenant?")) {
                                try {
                                    Parent root = FXMLLoader.load(ControllerSelling.class.getResource("/Views/viewReceipt.fxml"));
                                    Scene scene = new Scene(root, Launcher.stage.getScene().getWidth(), Launcher.stage.getScene().getHeight());
                                    Launcher.stage.setScene(scene);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
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

    public static ObservableList<Stock> shortStock() {
        rs = null;
        ObservableList<Stock> stock = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("SELECT * FROM " + stockDbName + "," + articleDbName + "" +
                    " WHERE stock.NArticle = article.NArticle AND stock.Stock <= article.MinStock");
            while (rs.next()) {
                stock.add(new Stock(rs.getInt(1) + "", rs.getDate(2) + "",
                        rs.getInt(4) + "", rs.getInt(3) + "", rs.getInt(5) + ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

    public static ObservableList<Stock> stockSearchDate(String start, String end) {
        rs = null;
        ObservableList<Stock> stock = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("SELECT * FROM " + stockDbName + " where Date BETWEEN '" + start + "' and '" + end + "';");
            while (rs.next()) {
                stock.add(new Stock(rs.getInt(1) + "", rs.getDate(2) + "",
                        rs.getInt(4) + "", rs.getInt(3) + "", rs.getInt(5) + ""));
            }
        } catch (SQLException e) {
            System.out.println("SELECT * FROM " + stockDbName + "where Date BETWEEN '" + start + "' and '" + end + "';");
            e.printStackTrace();
        }
        return stock;
    }
    //endregion

    //region Approvisiont
    public static ObservableList<ReceiptApprovision> getTableApprovisiont() {
        rs = null;
        ObservableList<ReceiptApprovision> approvisionts = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + approvisiontDbName + ";");
            while (rs.next()) {
                approvisionts.add(new ReceiptApprovision(rs.getInt(1) + "", rs.getDate(2) + "",
                        rs.getInt(3) + "", "", "", "", ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateComboBoxesApprovision();
        return approvisionts;
    }

    public static ObservableList<ReceiptApprovision> getArticlesList(String nBon) {
        rs = null;
        ObservableList<ReceiptApprovision> approvisionts = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + approvisiontDbName + "," + detailAppDbName +
                    " WHERE " + detailAppDbName + ".NBonA = " + nBon + " AND " + approvisiontDbName + ".NBonA = " + detailAppDbName + ".NBonA ;");

            while (rs.next()) {
                approvisionts.add(new ReceiptApprovision(rs.getInt(1) + "", rs.getDate(2) + "",
                        rs.getInt(3) + "", rs.getInt(5) + "", rs.getInt(6) + "", "", ""));
            }
            for (int i = 0; i < approvisionts.size(); i++) {
                Article temp = getArticle(approvisionts.get(i).getnArticle());
                approvisionts.get(i).setLabel(temp.getLabel());
                approvisionts.get(i).setPrix(temp.getPrice());
            }
        } catch (SQLException e) {
            System.out.println("Select * FROM " + approvisiontDbName + "," + detailAppDbName +
                    " WHERE " + detailAppDbName + ".NBonA = " + nBon + " AND " + approvisiontDbName + ".NBonA = " + detailAppDbName + ".NBonA ;");
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
            rs = statement.executeQuery("Select Label from " + articleDbName + ";");
            while (rs.next()) {
                articlesList.add(rs.getString(1));
            }
            rs = statement.executeQuery("Select NomFournisseur from " + fournisseurDbName + ";");
            while (rs.next()) {
                fournisseurList.add(rs.getString(1));
            }
            articlesApp.setItems(articlesList);
            fournisseurs.setItems(fournisseurList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addApprovisiont(String date, String nFournisseur, TableView<ReceiptApprovision> tableView) {
        rs = null;
        try {
            //Inserting new app into database...
            //part 1 (approvisionnement)
            statement.executeUpdate("INSERT INTO " + approvisiontDbName + " (`Date`, `NFournisseur`)" +
                    " VALUES ('" + date + "'," + nFournisseur + ");");
            //populating the table
            tableView.setItems(getTableApprovisiont());
            Utilities.warningPannel("Félicitation", "Element bien ajoutée!", "", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            System.out.println("INSERT INTO " + approvisiontDbName + " (`Date`, `NFournisseur`)" +
                    " VALUES ('" + date + "'," + nFournisseur + ");");
            e.printStackTrace();
        }
    }

    public static void addApprovisiontProduit(String nBon, String nArticle, String qntA, String date, TableView<ReceiptApprovision> tableView) {
        try {
            //part 2 (detail app)
            statement.executeUpdate("INSERT INTO " + detailAppDbName + " (`NBonA`,`NArticle`, `QntA`)" +
                    " VALUES (" + nBon + "," + nArticle + "," + qntA + ");");
            //populating the table
            tableView.setItems(getArticlesList(nBon));
            //creating/updating the stock
            addStock(nArticle, date, qntA, "0", qntA, new TableView<>());
        } catch (SQLException e) {
            System.out.println("INSERT INTO " + detailAppDbName + " (`NBonA`,`NArticle`, `QntA`)" +
                    " VALUES (" + nBon + "," + nArticle + "," + qntA + ");");
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

    public static void deleteApprovisiontProduit(String nBonA, String nArticle, TableView<ReceiptApprovision> tableView) {
        try {
            if (Utilities.confirmationPanel("Attention", "Cet élément sera supprimé", "etes vous sure ?")) {
                statement.executeUpdate("DELETE FROM " + detailAppDbName + " WHERE NBonA = " + nBonA + " AND NArticle = " + nArticle);
                tableView.setItems(getArticlesList(nBonA));
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
            rs = statement.executeQuery("Select * FROM " + livraisonDbName + ";");
            while (rs.next()) {
                livraisons.add(new ReceiptLivraison(rs.getInt(1) + "", rs.getDate(2) + "",
                        rs.getInt(3) + "", "", "", "", ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateComboBoxesLivraison();
        return livraisons;
    }

    public static ObservableList<ReceiptLivraison> getArticlesListL(String nBon) {
        rs = null;
        ObservableList<ReceiptLivraison> livraisons = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery("Select * FROM " + livraisonDbName + "," + detailLivDbName +
                    " WHERE " + detailLivDbName + ".NBonL = " + nBon + " AND " + livraisonDbName + ".NBonL = " + detailLivDbName + ".NBonL ;");

            while (rs.next()) {
                livraisons.add(new ReceiptLivraison(rs.getInt(1) + "", rs.getDate(2) + "",
                        rs.getInt(3) + "", rs.getInt(5) + "", rs.getInt(6) + "", "", ""));
            }
            for (int i = 0; i < livraisons.size(); i++) {
                Article temp = getArticle(livraisons.get(i).getnArticle());
                livraisons.get(i).setLabel(temp.getLabel());
                livraisons.get(i).setPrix(temp.getPrice());
            }
        } catch (SQLException e) {
            System.out.println("Select * FROM " + livraisonDbName + "," + detailLivDbName +
                    " WHERE " + detailLivDbName + ".NBonA = " + nBon + " AND " + livraisonDbName + ".NBonA = " + detailLivDbName + ".NBonA ;");
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
            rs = statement.executeQuery("Select Label from " + articleDbName + ";");
            while (rs.next()) {
                articlesList.add(rs.getString(1));
            }
            rs = statement.executeQuery("Select NomClient from " + clientDbName + ";");
            while (rs.next()) {
                clientList.add(rs.getString(1));
            }
            articlesApp.setItems(articlesList);
            clients.setItems(clientList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addLivraison(String date, String nClient, TableView<ReceiptLivraison> tableView) {
        rs = null;
        try {
            //Inserting new app into database...
            //part 1 (livraison)
            statement.executeUpdate("INSERT INTO " + livraisonDbName + " (`Date`, `NClient`)" +
                    " VALUES ('" + date + "'," + nClient + ");");
            //populating the table
            tableView.setItems(getTableLivraison());
            Utilities.warningPannel("Félicitation", "Element bien ajoutée!", "", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            System.out.println("INSERT INTO " + livraisonDbName + " (`Date`, `NClient`)" +
                    " VALUES ('" + date + "'," + nClient + ");");
            e.printStackTrace();
        }
    }

    public static void addLivraisonProduit(String nBon, String nArticle, String qntL, String date, TableView<ReceiptLivraison> tableView) {
        try {
            //part 2 (detail app)
            statement.executeUpdate("INSERT INTO " + detailLivDbName + " (`NBonL`,`NArticle`, `QntL`)" +
                    " VALUES (" + nBon + "," + nArticle + "," + qntL + ");");
            //populating the table
            tableView.setItems(getArticlesListL(nBon));
            //creating/updating the stock
            addStock(nArticle, date, "0", qntL, "0", new TableView<>());
        } catch (SQLException e) {
            System.out.println("INSERT INTO " + detailLivDbName + " (`NBonL`,`NArticle`, `QntL`)" +
                    " VALUES (" + nBon + "," + nArticle + "," + qntL + ");");
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

    public static void deleteLivraisonProduit(String nBonL, String nArticle, TableView<ReceiptLivraison> tableView) {
        try {
            if (Utilities.confirmationPanel("Attention", "Cet élément sera supprimé", "etes vous sure ?")) {
                statement.executeUpdate("DELETE FROM " + detailLivDbName + " WHERE NBonL = " + nBonL + " AND NArticle = " + nArticle);
                tableView.setItems(getArticlesListL(nBonL));
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
            }
            tableView.setItems(getTableLivraison());
        } catch (SQLException e) {
            System.out.println("UPDATE " + livraisonDbName + " SET " + columnName + " = " + newValue + " Where NBonL= " + nBonL + ";");
            System.out.println("UPDATE " + detailLivDbName + " SET " + columnName + " = " + newValue + " Where NBonL= " + nBonL + ";");
            e.printStackTrace();
        }
    }

    //endregion

}