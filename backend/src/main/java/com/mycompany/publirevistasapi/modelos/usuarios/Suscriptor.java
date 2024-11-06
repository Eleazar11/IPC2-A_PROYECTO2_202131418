/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.publirevistasapi.modelos.usuarios;

/**
 *
 * @author 3198935960914 - Eleazar Colop
 */
public class Suscriptor extends Usuario {

    public Suscriptor(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena, Rol.SUSCRIPTOR);
    }
}

