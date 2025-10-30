package com.duxsoftware.pruebatecnica.futbol_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private Jwt jwt;
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {

        // 1. BUSCAR EL HEADER "Authorization"
        String header = request.getHeader("Authorization");

        // 2. ¿EXISTE Y EMPIEZA CON "Bearer "?
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Quita "Bearer "

            // 3. ¿EL TOKEN ES VÁLIDO?
            if (jwt.validateToken(token)) {
                String username = jwt.extractUsername(token);

                // 4. AUTENTICAR AL USUARIO EN SPRING SECURITY
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // 5. CONTINUAR (SIEMPRE)
        chain.doFilter(request, response);
    }
}