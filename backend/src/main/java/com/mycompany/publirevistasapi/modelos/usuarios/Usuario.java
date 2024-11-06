/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.publirevistasapi.modelos.usuarios;

/**
 *
 * @author 3198935960914 - Eleazar Colop
 */
public class Usuario {
    


   private String nombreUsuario;
   private String contrasena;
   private String texto;
   private String fotoPerfil;
   private Rol rol;
   private String fechaCreacion;
   
   


   //Constructor para registrar a un usuario
    public Usuario(String nombreUsuario, String contrasena, Rol rol) {
         this.nombreUsuario = nombreUsuario;
         this.contrasena = guardarContrasena(contrasena);
         this.texto = null;
         this.fotoPerfil = null;
         this.rol = rol;
    }

    public Usuario() {
    }
    
    
  
    //Constructor para recuperar a un usuario de la base de datos
    public Usuario(String nombreUsuario, String contrasena, String texto, String fotoPerfil, Rol rol, String fechaCreacion) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.texto = texto;
        this.fotoPerfil = fotoPerfil;
        this.rol = rol;
        this.fechaCreacion = fechaCreacion;
    }

    private String guardarContrasena(String contrasena){
        Seguridad seguridad = new Seguridad();
        return seguridad.encriptarContrasena(contrasena);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    
}
