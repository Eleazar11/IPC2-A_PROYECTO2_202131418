/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.publirevistasapi.modelos.usuarios;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author 3198935960914 - Eleazar Colop
 */
public class Seguridad {

    // Instancia de PasswordEncoder (BCrypt)
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Este metodo encripta la contrasena con BCrypt y la retorna
     * @param contrasena
     * @return
     */
    public String encriptarContrasena(String contrasena) {
        return passwordEncoder.encode(contrasena); // Encripta la contraseña con BCrypt
    }
    
    /**
     * Verifica si la contrasena ingresada coincide con el hash almacenado
     * @param contrasena Contraseña original
     * @param hash Hash almacenado
     * @return true si coinciden, false si no
     */
    public boolean verificarContrasena(String contrasena, String hash) {
        return passwordEncoder.matches(contrasena, hash); // Verifica si la contraseña coincide
    }
}
