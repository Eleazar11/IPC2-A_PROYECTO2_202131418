/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.publirevistasapi.modelos.usuarios;

import java.sql.Date;

/**
 *
 * @author 3198935960914 - Eleazar Colop
 */
public class Editor extends Usuario {

    public Editor(String nombreUsuario, String contrasena, String fotoPerfil, String hobbies, String temasInteres, String descripcion, String gustos) {
        super(nombreUsuario, contrasena, Rol.EDITOR, fotoPerfil, hobbies, temasInteres, descripcion, gustos);
    }
}
