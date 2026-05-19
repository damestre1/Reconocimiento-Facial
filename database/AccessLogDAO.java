package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistroDAO {

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS access_logs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "datetime TEXT NOT NULL," +
                "status TEXT NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES usuarios(id)" +
                ")";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.execute();
            System.out.println("Tabla de registros lista.");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }

    public void agregarRegistro(int userId, String datetime, String status) {
        String sql = "INSERT INTO access_logs (user_id, datetime, status) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, datetime);
            ps.setString(3, status);
            ps.executeUpdate();
            System.out.println("Registro agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar registro: " + e.getMessage());
        }
    }

    public void listarRegistros() {
        String sql = "SELECT * FROM access_logs";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        "Usuario: " + rs.getInt("user_id") + " | " +
                        rs.getString("datetime") + " | " +
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar registros: " + e.getMessage());
        }
    }
}