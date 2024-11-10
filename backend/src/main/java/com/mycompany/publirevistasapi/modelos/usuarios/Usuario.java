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
public class Usuario {
    private String nombreUsuario;
    private String contrasena;
    private Rol rol; // Refleja el 'tipo_usuario' en la base de datos (puede ser 'editor', 'administrador', etc.)
    private String fotoPerfil; // Ruta de la foto de perfil
    private String hobbies;
    private String temasInteres;
    private String descripcion;
    private String gustos;
    private Date fechaCreacion;
    private String estado; // Refleja el estado (activo, inactivo, suspendido)
    
    
    public Usuario(String nombreUsuario, String contrasena, Rol rol) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = guardarContrasena(contrasena);
        this.fotoPerfil = null;
        this.hobbies = null;
        this.temasInteres = null;
        this.descripcion = null;
        this.gustos = null;
        this.estado = null;
        this.rol = rol;
    }

    public Usuario(String nombreUsuario, String contrasena, Rol rol, String fotoPerfil, String hobbies, String temasInteres, String descripcion, String gustos, Date fechaCreacion) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.fotoPerfil = fotoPerfil;
        this.hobbies = hobbies;
        this.temasInteres = temasInteres;
        this.descripcion = descripcion;
        this.gustos = gustos;
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario() {
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

    

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getTemasInteres() {
        return temasInteres;
    }

    public void setTemasInteres(String temasInteres) {
        this.temasInteres = temasInteres;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGustos() {
        return gustos;
    }

    public void setGustos(String gustos) {
        this.gustos = gustos;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

  
    
}
