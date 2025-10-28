package com.duxsoftware.pruebatecnica.futbol_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
// Clase para representar una respuesta de error
@Data
//sirve para crear constructores, getters, setters, toString, equals y hashCode
@AllArgsConstructor
//
public class ErrorResponse {
    private String mensaje;
    private int codigo;
    
}
