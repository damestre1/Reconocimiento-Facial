package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {

    // Crear la tabla si no existe (ahora con datos médicos)
    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL," +
                "correo TEXT UNIQUE NOT NULL," +
                "tipo_sangre TEXT," +
                "alergias TEXT," +
                "cirugias_previas TEXT," +
                "medicamentos TEXT," +
                "contacto_emergencia TEXT" +
                ")";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.execute();
            System.out.println("Tabla usuarios lista.");
        } catch (SQLException e) {
            System.out.println("Error al crear tabla: " + e.getMessage());
        }
    }

    // Agregar un usuario con sus datos médicos. Devuelve el id generado (o -1 si falla)
    public int agregarUsuario(String nombre, String apellido, String correo,
                              String tipoSangre, String alergias,
                              String cirugiasPrevias, String medicamentos,
                              String contactoEmergencia) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correo, tipo_sangre, " +
                "alergias, cirugias_previas, medicamentos, contacto_emergencia) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, tipoSangre);
            ps.setString(5, alergias);
            ps.setString(6, cirugiasPrevias);
            ps.setString(7, medicamentos);
            ps.setString(8, contactoEmergencia);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    System.out.println("Usuario agregado: " + nombre + " (id " + id + ")");
                    return id;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar usuario: " + e.getMessage());
        }
        return -1;
    }

    // Mostrar la ficha médica de un usuario por id
    // (esto es lo que mostraría la app después de reconocer la cara)
    public void mostrarFichaMedica(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("=== FICHA MEDICA ===");
                    System.out.println("Paciente: " + rs.getString("nombre") + " " + rs.getString("apellido"));
                    System.out.println("Correo: " + rs.getString("correo"));
                    System.out.println("Tipo de sangre: " + rs.getString("tipo_sangre"));
                    System.out.println("Alergias: " + rs.getString("alergias"));
                    System.out.println("Cirugias previas: " + rs.getString("cirugias_previas"));
                    System.out.println("Medicamentos: " + rs.getString("medicamentos"));
                    System.out.println("Contacto de emergencia: " + rs.getString("contacto_emergencia"));
                } else {
                    System.out.println("No se encontro usuario con id " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }
    }

    // Consultar todos los usuarios
    public void listarUsuarios() {
        String sql = "SELECT id, nombre, apellido, correo, tipo_sangre FROM usuarios";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("nombre") + " " +
                        rs.getString("apellido") + " | " +
                        rs.getString("correo") + " | Sangre: " +
                        rs.getString("tipo_sangre"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
    }
}
