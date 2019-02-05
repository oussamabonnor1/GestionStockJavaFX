package ToolBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnection {
    public static Connection connection;
    public static Statement statement;


    public static void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/stockManagement";
            connection = DriverManager.getConnection(dbURL, "root", "");
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select * FROM article;");
            while (rs.next()) {
                System.out.println("Item: " + rs.getString(1));
            }

        } catch (Exception e) {
            System.out.println(" Echec !");
            e.printStackTrace();
        }
    }
}