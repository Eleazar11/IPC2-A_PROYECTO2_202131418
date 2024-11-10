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
public class Anunciante extends Usuario {

    public Anunciante(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena, Rol.ANUNCIANTE);
    }
}
