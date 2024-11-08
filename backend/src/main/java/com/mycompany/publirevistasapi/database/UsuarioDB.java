/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.publirevistasapi.database;

import com.mycompany.publirevistasapi.modelos.usuarios.Rol;
import com.mycompany.publirevistasapi.modelos.usuarios.Seguridad;
import com.mycompany.publirevistasapi.modelos.usuarios.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 3198935960914 - Eleazar Colop
 */
public class UsuarioDB {

    private Connection connection;

    public UsuarioDB() {
        try {
            this.connection = DataSourceDBSingleton.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
        }
    }

    // Método para registrar un usuario
    public boolean registrarUsuario(Usuario usuario) {
        if (existeUsuario(usuario.getNombreUsuario())) {
            return false; // El usuario ya existe en la base de datos
        }

        String consulta = "INSERT INTO usuarios (nombre_usuario, contraseña, perfil, rol) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, usuario.getNombreUsuario());
            statement.setString(2, usuario.getContrasena());
            statement.setString(3, usuario.getDescripcion());  // Asegúrate de que 'descripcion' es lo que se debe guardar
            statement.setString(4, usuario.getRol()); // Aquí se guarda el rol como String

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    // Método para verificar si el usuario ya existe en la base de datos
    private boolean existeUsuario(String nombreUsuario) {
        String consulta = "SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, nombreUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de usuario", e);
        }
        return false;
    }

    // Método para autenticar usuario
    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        Seguridad seguridad = new Seguridad();
        Usuario usuarioObtenido = obtenerUsuario(nombreUsuario);

        if (usuarioObtenido == null) {
            return null;
        }

        // Verifica la contraseña usando un sistema de seguridad
        if (seguridad.verificarContrasena(contrasena, usuarioObtenido.getContrasena())) {
            return usuarioObtenido;
        }

        return null;
    }

    // Método para obtener un usuario por nombre de usuario
    public Usuario obtenerUsuario(String nombreUsuario) {
        String consulta = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, nombreUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre_usuario");
                    String password = resultSet.getString("contraseña");
                    String perfil = resultSet.getString("perfil");
                    String fotoPerfil = resultSet.getString("foto_perfil");
                    String rol = resultSet.getString("rol");
                    String hobbies = resultSet.getString("hobbies");
                    String temasInteres = resultSet.getString("temas_interes");
                    String descripcion = resultSet.getString("descripcion");
                    String gustos = resultSet.getString("gustos");
                    Date fechaCreacion = resultSet.getDate("fecha_creacion");
                    String estado = resultSet.getString("estado");

                    // Aquí se construye el usuario con el constructor proporcionado
                    Usuario usuario = new Usuario(
                            nombre, 
                            password, 
                            rol, 
                            fotoPerfil, 
                            hobbies, 
                            temasInteres, 
                            descripcion, 
                            gustos, 
                            fechaCreacion, 
                            estado
                    );
                    return usuario;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el usuario", e);
        }
        return null;
    }

    // Método para actualizar el perfil de un usuario
    public void actualizarUsuario(String nombreUsuario, String texto, String fotoPerfilPath) {
        String consulta = "UPDATE usuarios SET perfil = ?, foto_perfil = ? WHERE nombre_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(consulta)) {
            stmt.setString(1, texto);
            stmt.setString(2, fotoPerfilPath);
            stmt.setString(3, nombreUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

