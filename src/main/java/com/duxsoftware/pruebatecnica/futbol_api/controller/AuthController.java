package com.duxsoftware.pruebatecnica.futbol_api.controller;

import com.duxsoftware.pruebatecnica.futbol_api.dto.ErrorResponse;
import com.duxsoftware.pruebatecnica.futbol_api.dto.LoginRequest;
import com.duxsoftware.pruebatecnica.futbol_api.security.Jwt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
// Controlador para la autenticación
@RestController
// Ruta base para autenticación
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Login con JWT")
public class AuthController {

    @Autowired
    private Jwt jwt;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Devuelve un token JWT")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    //ResponseEntity<?> indica que puede devolver cualquier tipo de respuesta
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Validar credenciales
        if ("test".equals(loginRequest.getUsername()) && 
            "12345".equals(loginRequest.getPassword())) {
            
            String token = jwt.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            ErrorResponse error = new ErrorResponse("Autenticación fallida", 401);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
}