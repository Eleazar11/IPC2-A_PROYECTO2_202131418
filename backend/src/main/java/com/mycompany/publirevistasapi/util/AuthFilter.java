/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.publirevistasapi.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;

/**
 *
 * @author 3198935960914 - Eleazar Colop
 */
@Provider
public class AuthFilter implements ContainerRequestFilter {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
//        String authorizationHeader = requestContext.getHeaderString("Authorization");
//
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token no proporcionado").build());
//            return;
//        }
//
//        String token = authorizationHeader.substring("Bearer ".length());
//
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(SECRET_KEY)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            String nombreUsuario = claims.getSubject();
//            String rol = claims.get("rol", String.class);
//
//            // Añadir propiedades si es necesario para el contexto
//            requestContext.setProperty("nombreUsuario", nombreUsuario);
//            requestContext.setProperty("rol", rol);
//        } catch (Exception e) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token inválido").build());
//        }
    }
}

