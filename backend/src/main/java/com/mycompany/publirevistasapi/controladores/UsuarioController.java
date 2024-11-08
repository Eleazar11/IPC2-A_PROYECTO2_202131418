/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.publirevistasapi.controladores;

import com.mycompany.publirevistasapi.modelos.usuarios.Usuario;
import com.mycompany.publirevistasapi.servicios.UsuarioService;
import com.mycompany.publirevistasapi.util.GeneradorToken;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 3198935960914 - Eleazar Colop
 */
@Path("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;
    private static final String SECRET_KEY = "clave_usr_revistas_online"; // Cambia esto a algo seguro

    public UsuarioController() {
        this.usuarioService = new UsuarioService(); // Instanciación manual
    }
    
    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(Usuario usuario) {
        // Validación de datos
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()
                || usuario.getContrasena() == null || usuario.getContrasena().isEmpty()
                || usuario.getRol() == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Todos los campos son obligatorios");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        try {
            usuarioService.registrarUsuario(usuario);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario registrado exitosamente");
            return Response.ok(response).build();
        } catch (SQLException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al registrar el usuario");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuario(Usuario usuario) {
        Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuario.getNombreUsuario(), usuario.getContrasena());

        if (usuarioAutenticado != null) {
            GeneradorToken generadorToken = new GeneradorToken();
            String token = generadorToken.crearTokenJWT(usuarioAutenticado);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return Response.ok()
                    .header("Authorization", "Bearer " + token)
                    .entity(response)
                    .build();
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Credenciales incorrectas");
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }
    }
}