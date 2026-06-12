import database.RegistroDAO;
import database.UsuarioDAO;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        UsuarioDAO usuarios = new UsuarioDAO();
        RegistroDAO registros = new RegistroDAO();

        // 1. Crear las tablas (solo crea si no existen)
        usuarios.crearTabla();
        registros.crearTabla();

        // 2. Agregar un paciente de prueba con sus datos médicos
        int id = usuarios.agregarUsuario(
                "Diego", "Mestre", "diego@ejemplo.com",
                "O+",
                "Penicilina, mani",
                "Apendicectomia (2021)",
                "Ninguno",
                "Maria Gomez - 300 123 4567"
        );

        // 3. Simular que el reconocimiento facial identifico al usuario con ese id
        if (id != -1) {
            registros.agregarRegistro(id, LocalDateTime.now().toString(), "RECONOCIDO");
            usuarios.mostrarFichaMedica(id);
        }

        // 4. Listar todo
        System.out.println("\n--- Usuarios registrados ---");
        usuarios.listarUsuarios();
        System.out.println("\n--- Historial de accesos ---");
        registros.listarRegistros();
    }
}
