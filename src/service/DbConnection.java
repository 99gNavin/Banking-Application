package service;

import java.sql.Connection;
import java.sql.DriverManager;

//Singleton class

public class DbConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db", "nabin", "pass123@D");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
