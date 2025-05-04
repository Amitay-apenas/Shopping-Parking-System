package mySQLConecction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String url = "jdbc:mysql://localhost:3306/shoppinparking";
    private static final String user = "root";
    private static final String password = "ameeami09";

    private static Connection conn;

    public static Connection getConnector() {

        try {
            if (conn == null) {
                conn = DriverManager.getConnection(url, user, password);
                return conn;
            }else{
                return conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

