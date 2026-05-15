package library.uz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    String url ="jdbc:postgresql://localhost:5432/ecommerce";
    String username = "postgres";
    String password = "zse123";
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                url,
                username,
                password
        );
    }
}
