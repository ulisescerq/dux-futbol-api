package com.duxsoftware.pruebatecnica.futbol_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duxsoftware.pruebatecnica.futbol_api.model.Equipo;
import com.duxsoftware.pruebatecnica.futbol_api.service.EquipoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestParam;


@RestController //convierte objetos Java en respuestas JSON
@RequestMapping("/equipos")
@Tag(name= "Equipos", description = "Gestión de equipos de fútbol")
public class EquipoController {

    // Inyección del servicio de Equipo
    @Autowired
    private EquipoService equipoService;

    @GetMapping
    @Operation(summary = "Listar todos los equipos", description = "Requiere token JWT")
    @ApiResponse(responseCode = "200", description = "Lista de equipos")
    @ApiResponse(responseCode = "401", description = "Autenticacion requerida")
    public List<Equipo> getAllEquipos() {
        return equipoService.getAllEquipos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener equipo por ID", description = "Busca el equipo por su ID")
    @ApiResponse(responseCode = "200", description = "Equipo encontrado")
    @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    //el PathVariable sirve para capturar el valor de la variable id en la URL
    public Equipo getEquipoById(@PathVariable Long id) {
        return equipoService.getEquipoById(id);
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar equipo por nombre", description = "Búsqueda parcial")
    @ApiResponse(responseCode = "200", description = "equipo encontrado")
    @ApiResponse(responseCode = "404", description = "equipo no encontrado")
    //el requestParam sirve para capturar el valor del parametro nombre en la URL
    public List<Equipo> searchByNombre(@RequestParam String nombre) {
        return equipoService.searchByNombre(nombre);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo equipo")
    @ApiResponse(responseCode = "201", description = "Equipo creado")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    //el ResponseEntity sirve para devolver una respuesta HTTP completa
    //el RequestBody sirve para mapear el cuerpo de la solicitud HTTP a un objeto Java, es decir que convierte el JSON que manda el cliente en un objeto Java
    public ResponseEntity<Equipo> createEquipo(@RequestBody Equipo equipo) {
        //crea una instacia de equipo llamando al metodo createEquipo del servicio
        Equipo nuevoEquipo = equipoService.createEquipo(equipo);
        //devuelve una respuesta con el estatus 201 (CREATED) y el objeto nuevoEquipo en el cuerpo
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar equipo")
    @ApiResponse(responseCode = "200", description = "Equipo actualizado")
    @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    // el equipoDetails sale del cuerpo de la solicitud, osea que lo manda el cliente y es un objeto JSON, es la infomracion nueva para actualizar
    public Equipo updateteEquipo(@PathVariable Long id, @RequestBody Equipo equipoDetails){
        return equipoService.updateEquipo(id, equipoDetails);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar equipo")
    @ApiResponse(responseCode = "204", description = "Equipo eliminado")
    @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    //el noContent sirve para devolver una respuesta HTTP sin cuerpo
    // el .build() construye la respuesta HTTP y devuelve el objeto ResponseEntity
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id){
        equipoService.deleteEquipo(id);
        return ResponseEntity.noContent().build();
    }


    
}
