/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.publirevistasapi.servicios;

import com.mycompany.publirevistasapi.database.CarteraDB;
import com.mycompany.publirevistasapi.database.UsuarioDB;
import com.mycompany.publirevistasapi.modelos.usuarios.Administrador;
import com.mycompany.publirevistasapi.modelos.usuarios.Anunciante;
import com.mycompany.publirevistasapi.modelos.usuarios.Editor;
import com.mycompany.publirevistasapi.modelos.usuarios.Suscriptor;
import com.mycompany.publirevistasapi.modelos.usuarios.Usuario;
import java.sql.SQLException;

/**
 *
 * @author 3198935960914 - Eleazar Colop
 */
public class UsuarioService {

    private UsuarioDB usuarioDB = new UsuarioDB();
    private CarteraDB carteraDB = new CarteraDB();

    public void registrarUsuario(Usuario usuario) throws SQLException {

        boolean agregarCartera = false;

        switch (usuario.getRol().toString()) {
            case "SUSCRIPTOR":
                // Asegúrate de tener todos los valores necesarios para crear un Suscriptor
                usuario = new Suscriptor(
                        usuario.getNombreUsuario(),
                        usuario.getContrasena(), 
                        usuario.getFotoPerfil(),
                        usuario.getHobbies(),
                        usuario.getTemasInteres(),
                        usuario.getDescripcion(),
                        usuario.getGustos()
                );
                break;

            case "EDITOR":
                // Los editores necesitan una cartera
                usuario = new Editor(
                        usuario.getNombreUsuario(),
                        usuario.getContrasena(), usuario.getFotoPerfil(),
                        usuario.getHobbies(),
                        usuario.getTemasInteres(),
                        usuario.getDescripcion(),
                        usuario.getGustos()
                );
                agregarCartera = true;  // Agregar cartera para los editores
                break;

            case "ANUNCIANTE":
                // Los anunciantes también necesitan una cartera
                usuario = new Anunciante(
                        usuario.getNombreUsuario(),
                        usuario.getContrasena(), usuario.getFotoPerfil(),
                        usuario.getHobbies(),
                        usuario.getTemasInteres(),
                        usuario.getDescripcion(),
                        usuario.getGustos()
                );
                agregarCartera = true;  // Agregar cartera para los anunciantes
                break;

            case "ADMINISTRADOR":
                // Para los administradores, no se necesita agregar cartera
                usuario = new Administrador(
                        usuario.getNombreUsuario(),
                        usuario.getContrasena(), usuario.getFotoPerfil(),
                        usuario.getHobbies(),
                        usuario.getTemasInteres(),
                        usuario.getDescripcion(),
                        usuario.getGustos()
                );
                break;

            default:
                // Si no coincide con ningún rol, puedes lanzar un error o manejarlo de otra forma
                throw new IllegalArgumentException("Rol desconocido: " + usuario.getRol().toString());
        }

        // Registro del usuario en la base de datos
        if (usuarioDB.registrarUsuario(usuario)) {
            if (agregarCartera) {
                carteraDB.crearCartera(usuario);  // Si el rol es Editor o Anunciante, agregar cartera
            }
        }
    }

    public Usuario autenticarUsuario(String nombreUsuario, String contrasena) {
        return usuarioDB.iniciarSesion(nombreUsuario, contrasena);
    }
}
