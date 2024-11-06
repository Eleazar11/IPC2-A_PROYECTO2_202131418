/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.publirevistasapi.database;

import com.mycompany.publirevistasapi.modelos.usuarios.Rol;
import com.mycompany.publirevistasapi.modelos.usuarios.Seguridad;
import com.mycompany.publirevistasapi.modelos.usuarios.Usuario;
import java.sql.Connection;
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
            throw new RuntimeException(e);
        }
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (existeUsuario(usuario.getNombreUsuario())) {
            return false;
            // Significa que el usuario ya existe en la base de datos
        }

        String consulta = "INSERT INTO usuarios (nombre_usuario, contraseña, perfil, rol) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, usuario.getNombreUsuario());
            statement.setString(2, usuario.getContrasena());
            statement.setString(3, usuario.getTexto()); // Se usa String aquí
            statement.setString(4, usuario.getRol().toString());

            // Si la inserción fue exitosa, executeUpdate devuelve el número de filas afectadas
            int filasAfectadas = statement.executeUpdate();

            // Si se afectó al menos una fila, devuelve true, significa que la inserción fue exitosa
            return filasAfectadas > 0;
        } catch (SQLException e) {
            // Si ocurre una excepción, puedes manejarla y devolver false
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    private boolean existeUsuario(String nombreUsuario) {
        String consulta = "SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, nombreUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1); // Obtiene el conteo de usuarios
                    return count > 0; // Devuelve true si hay uno o más usuarios
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
        // Devuelve false si no hay usuarios o se produjo un error
    }

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

    public Usuario obtenerUsuario(String nombreUsuario) {
        String consulta = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, nombreUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre_usuario");
                    String password = resultSet.getString("contraseña");
                    String texto = resultSet.getString("perfil");
                    String fotoPerfil = resultSet.getString("foto_perfil");
                    String rol = resultSet.getString("rol");
                    //fechaCreacion es un campo de tipo DATE en la base de datos
                    String fechaCreacion = resultSet.getString("fecha_creacion");
                    return new Usuario(nombre, password, texto, fotoPerfil, Rol.valueOf(rol), fechaCreacion);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

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

