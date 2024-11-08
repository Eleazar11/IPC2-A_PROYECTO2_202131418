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

    // Constructor del controlador
    public UsuarioController() {
        this.usuarioService = new UsuarioService(); // Instanciación manual del servicio
    }

    /**
     * Método para registrar un nuevo usuario.
     * @param usuario El objeto Usuario con los datos del usuario a registrar.
     * @return Response con el resultado de la operación.
     */
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
            // Intentamos registrar el usuario en la base de datos
            usuarioService.registrarUsuario(usuario);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario registrado exitosamente");
            return Response.ok(response).build();
        } catch (SQLException e) {
            // En caso de error en la base de datos, retornamos un error
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al registrar el usuario");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    /**
     * Método para autenticar a un usuario mediante sus credenciales.
     * @param usuario El objeto Usuario con el nombre de usuario y la contraseña a autenticar.
     * @return Response con el resultado de la operación.
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuario(Usuario usuario) {
        // Intentamos autenticar al usuario con las credenciales proporcionadas
        Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuario.getNombreUsuario(), usuario.getContrasena());

        if (usuarioAutenticado != null) {
            // Si las credenciales son correctas, generamos un token JWT
            GeneradorToken generadorToken = new GeneradorToken();
            String token = generadorToken.crearTokenJWT(usuarioAutenticado);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            // Retornamos el token JWT en la respuesta
            return Response.ok()
                    .header("Authorization", "Bearer " + token)
                    .entity(response)
                    .build();
        } else {
            // Si las credenciales son incorrectas, retornamos un error
            Map<String, String> response = new HashMap<>();
            response.put("error", "Credenciales incorrectas");
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }
    }
}