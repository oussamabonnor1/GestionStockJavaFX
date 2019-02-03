package ToolBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConnection {
    public static Connection connection;
    public static Statement statement;


    public static void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/TP1-DB";
            connection = DriverManager.getConnection(dbURL, "user", "password");
            statement = connection.createStatement();

        } catch (Exception e) {
            System.out.println(" Echec !");
            e.printStackTrace();
        }
    }
}