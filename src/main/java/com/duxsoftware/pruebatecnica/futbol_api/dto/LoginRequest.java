package com.duxsoftware.pruebatecnica.futbol_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Representa los datos necesarios para realizar el login")
public class LoginRequest {
    @Schema(description = "Nombre de usuario")
    private String username;
    @Schema(description = "Contraseña")
    private String password;
}