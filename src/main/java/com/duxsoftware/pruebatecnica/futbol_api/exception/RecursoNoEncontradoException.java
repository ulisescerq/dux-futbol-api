package com.duxsoftware.pruebatecnica.futbol_api.exception;

import org.springframework.http.HttpStatus;//importa el estatus HTTP
import org.springframework.web.bind.annotation.ResponseStatus;//importa la anotación para definir el estatus de respuesta

@ResponseStatus(HttpStatus.NOT_FOUND)

public class RecursoNoEncontradoException extends RuntimeException {
    //Constructor que recibe un mensaje de error
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
}
