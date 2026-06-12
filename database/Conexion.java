package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:sqlite:reconocimiento_facial.db";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
