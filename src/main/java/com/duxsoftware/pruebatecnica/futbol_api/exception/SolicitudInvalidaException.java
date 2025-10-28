package com.duxsoftware.pruebatecnica.futbol_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SolicitudInvalidaException extends RuntimeException {
    //Constructor que recibe un mensaje de error
    public SolicitudInvalidaException(String mensaje) {
        super(mensaje);
    }

    
}
