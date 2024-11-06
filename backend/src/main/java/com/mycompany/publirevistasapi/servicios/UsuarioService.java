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
                usuario = new Suscriptor(usuario.getNombreUsuario(), usuario.getContrasena());
                break;
            case "EDITOR":
                usuario = new Editor(usuario.getNombreUsuario(), usuario.getContrasena());
                agregarCartera = true;
                
                break;
            case "ANUNCIANTE":
                usuario = new Anunciante(usuario.getNombreUsuario(), usuario.getContrasena());
                agregarCartera = true;
              
                break;
            case "ADMINISTRADOR":
                usuario = new Administrador(usuario.getNombreUsuario(), usuario.getContrasena());
                break;
            default:
                //Error
                break;
        }

        if (usuarioDB.registrarUsuario(usuario)) {
            if (agregarCartera) {
                carteraDB.crearCartera(usuario);
            }
        }
    }
     
 
    public Usuario autenticarUsuario(String nombreUsuario, String contrasena) {
       return usuarioDB.iniciarSesion(nombreUsuario, contrasena);
    }
}
