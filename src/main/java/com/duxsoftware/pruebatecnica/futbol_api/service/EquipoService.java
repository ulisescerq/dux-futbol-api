package com.duxsoftware.pruebatecnica.futbol_api.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duxsoftware.pruebatecnica.futbol_api.exception.RecursoNoEncontradoException;
import com.duxsoftware.pruebatecnica.futbol_api.exception.SolicitudInvalidaException;
import com.duxsoftware.pruebatecnica.futbol_api.model.Equipo;
import com.duxsoftware.pruebatecnica.futbol_api.repository.EquipoRepository;

import java.util.List;

@Service
public class EquipoService {

    // Inyección del repositorio de Equipo
    @Autowired
    private EquipoRepository equipoRepository;

    //el List sirve para devolver una lista de equipos
    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    //El public Equipo devuelve un solo equipo
    public Equipo getEquipoById(Long id) {
        return equipoRepository.findById(id)
        //el .orElseThrow lanza una excepcion si no encuentra el equipo
        // el RecursoNoEncontradoException es una excepcion personalizada
        .orElseThrow(() -> new RecursoNoEncontradoException("Equipo no encontrado"));
    }

    public List<Equipo> searchByNombre(String nombre) {
        List<Equipo> equipos = equipoRepository.findByNombreContainingIgnoreCase(nombre);
        if (equipos.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontro ningun equipo con ese nombre.");
        }
        return equipos;
    }

    //el metodo recibe un objeto equipo y lo guarda en la base de datos
    public Equipo createEquipo(Equipo equipo) {
        //validar que los campos no esten vacios
        validarEquipo(equipo);
        return equipoRepository.save(equipo);
    }
    //el equipo equipoDetails contiene los nuevos datos para actualizar, sale del cuerpo de la solicitud
    public Equipo updateEquipo(Long id, Equipo equipoDetails) {
        Equipo equipo = getEquipoById(id);
        validarEquipo(equipoDetails);
        equipo.setNombre(equipoDetails.getNombre());
        equipo.setLiga(equipoDetails.getLiga());
        equipo.setPais(equipoDetails.getPais());
        return equipoRepository.save(equipo);
    }

    public void deleteEquipo(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Equipo no encontrado");
        }
        equipoRepository.deleteById(id);
    }

    //Este metodo valida que los campos no esten vacios
    //Es private porque solo se usa dentro de esta clase
    private void validarEquipo(Equipo equipo) {
        if (equipo.getNombre() == null || equipo.getNombre().trim().isEmpty() ||
            equipo.getLiga() == null || equipo.getLiga().trim().isEmpty() ||
            equipo.getPais() == null || equipo.getPais().trim().isEmpty()) {
            throw new SolicitudInvalidaException("La solicitud es invalida");
        }
    }
}