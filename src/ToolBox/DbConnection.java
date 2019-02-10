package ToolBox;

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

    public static void getTableArticle() {
        rs = null;
        try {
            rs = statement.executeQuery("Select * FROM " + articleDbName + ";");
            while (rs.next()) {
                System.out.println("Item: " + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addArticle(String nArticle, String label, String price, String minStock) {
        rs = null;
        try {
            //Checking if the Article doesn't already exist...
            rs = statement.executeQuery("SELECT * FROM " +
                    articleDbName + " WHERE " + articleDbName + ".NArticle = " + nArticle + ";");
            if (!rs.next()) {
                //Inserting new article into database...
                statement.executeUpdate("INSERT INTO `" + articleDbName + "`(`NArticle`, `Label`, `Price`, `MinStock`)" +
                        " VALUES (" + nArticle + ",'" + label + "'," + price + "," + minStock + ")");
                System.out.println("Item added!");
                //TODO: Popup info 10/02/2019
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