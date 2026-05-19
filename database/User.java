package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Crear la tabla si no existe
    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL," +
                "correo TEXT UNIQUE NOT NULL" +
                ")";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.execute();
            System.out.println("Tabla usuarios lista.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Agregar un usuario
    public void agregarUsuario(String nombre, String apellido, String correo) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correo) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.executeUpdate();
            System.out.println("Usuario agregado: " + nombre);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Consultar todos los usuarios
    public void listarUsuarios() {
        String sql = "SELECT * FROM usuarios";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("nombre") + " " +
                        rs.getString("apellido") + " | " +
                        rs.getString("correo"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}