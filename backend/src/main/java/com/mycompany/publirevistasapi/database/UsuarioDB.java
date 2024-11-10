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

        String consulta = "INSERT INTO usuarios (nombre, contrasena, tipo_usuario) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, usuario.getNombreUsuario());
            statement.setString(2, usuario.getContrasena());
            statement.setString(3, usuario.getRol()); // Tipo de usuario

            int filasAfectadas = statement.executeUpdate();

            // Si el usuario es de tipo 'especial', crear registro en carteras_digitales
            if (usuario.getRol().equals("especial") || usuario.getRol().equals("editor")) {
                registrarCarteraDigital(usuario.getNombreUsuario());
                System.out.println("se creo una cartera digital");
            }

            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    // Método para registrar cartera digital si el usuario es de tipo 'especial'
    private void registrarCarteraDigital(String nombreUsuario) {
        String consultaCartera = "INSERT INTO carteras_digitales (nombre_usuario, saldo, fecha_creacion) VALUES (?, 0.00, CURRENT_DATE)";
        try (PreparedStatement statementCartera = connection.prepareStatement(consultaCartera)) {
            statementCartera.setString(1, nombreUsuario);
            statementCartera.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar cartera digital: " + e.getMessage());
        }
    }

    // Método para verificar si el usuario ya existe en la base de datos
    private boolean existeUsuario(String nombreUsuario) {
        String consulta = "SELECT COUNT(*) FROM usuarios WHERE nombre = ?";
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
        String consulta = "SELECT * FROM usuarios WHERE nombre = ?";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, nombreUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String password = resultSet.getString("contrasena");
                    String rol = resultSet.getString("tipo_usuario");
                    String fotoPerfil = resultSet.getString("foto_perfil");
                    String hobbies = resultSet.getString("hobbies");
                    String temasInteres = resultSet.getString("temas_interes");
                    String descripcion = resultSet.getString("descripcion");
                    String gustos = resultSet.getString("gustos");
                    Date fechaCreacion = resultSet.getDate("fecha_creacion");
                    String estado = resultSet.getString("estado");

                    // Construcción del usuario
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
    public void actualizarUsuario(String nombreUsuario, String descripcion, String fotoPerfilPath) {
        String consulta = "UPDATE usuarios SET descripcion = ?, foto_perfil = ? WHERE nombre = ?";

        try (PreparedStatement stmt = connection.prepareStatement(consulta)) {
            stmt.setString(1, descripcion);
            stmt.setString(2, fotoPerfilPath);
            stmt.setString(3, nombreUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}