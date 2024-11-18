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

        String consulta = "INSERT INTO usuarios (nombre, contrasena, tipo_usuario, foto_perfil, hobbies, temas_interes, descripcion, gustos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, usuario.getNombreUsuario());
            statement.setString(2, usuario.getContrasena());
            statement.setString(3, usuario.getRol().toString()); // Tipo de usuario
            statement.setString(4, usuario.getFotoPerfil()); // Foto perfil in Base64 format
            statement.setString(5, usuario.getHobbies());
            statement.setString(6, usuario.getTemasInteres());
            statement.setString(7, usuario.getDescripcion());
            statement.setString(8, usuario.getGustos());

            int filasAfectadas = statement.executeUpdate();

            // If the user is 'especial' or 'editor', create a digital wallet entry
            if (usuario.getRol().equals("especial") || usuario.getRol().equals("editor")) {
                registrarCarteraDigital(usuario.getNombreUsuario());
                System.out.println("Se creó una cartera digital");
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
        // Si la contraseña coincide con el hash almacenado, devuelve un objeto Usuario
        if (seguridad.verificarContrasena(contrasena, usuarioObtenido.getContrasena())) {
            System.out.println("Contraseña correcta");
            return usuarioObtenido;
        }

        // Si no se encontró un usuario con las credenciales dadas, devuelve null
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

                    // Construcción del usuario
                    Usuario usuario = new Usuario(
                            nombre, 
                            password, 
                            Rol.valueOf(rol), 
                            fotoPerfil, 
                            hobbies, 
                            temasInteres, 
                            descripcion, 
                            gustos, 
                            fechaCreacion
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
