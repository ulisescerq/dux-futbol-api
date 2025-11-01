package com.duxsoftware.pruebatecnica.futbol_api.service;

import com.duxsoftware.pruebatecnica.futbol_api.exception.RecursoNoEncontradoException;
import com.duxsoftware.pruebatecnica.futbol_api.model.Equipo;
import com.duxsoftware.pruebatecnica.futbol_api.repository.EquipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipoServiceTest {

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoService equipoService;

    private Equipo equipo1;
    private Equipo equipo2;

    @BeforeEach
    void setUp() {
        equipo1 = new Equipo(1L, "River Plate", "Primera División", "Argentina");
        equipo2 = new Equipo(2L, "Boca Juniors", "Primera División", "Argentina");
    }

    @Test
    void deberiaTraerTodosLosEquipos() {
        when(equipoRepository.findAll()).thenReturn(Arrays.asList(equipo1, equipo2));
        List<Equipo> result = equipoService.getAllEquipos();
        assertEquals(2, result.size());
        verify(equipoRepository).findAll();
    }

    @Test
    void deberiaTraerUnEquipoPorId() {
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipo1));
        Equipo result = equipoService.getEquipoById(1L);
        assertEquals("River Plate", result.getNombre());
        verify(equipoRepository).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoIdNoExiste() {
        when(equipoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class, () -> equipoService.getEquipoById(99L));
        verify(equipoRepository).findById(99L);
    }

    @Test
    void deberiaRetornarEquiposBuscadosPorNombre() {
        when(equipoRepository.findByNombreContainingIgnoreCase("river"))
                .thenReturn(Arrays.asList(equipo1));
        List<Equipo> result = equipoService.searchByNombre("river");
        assertEquals(1, result.size());
        assertEquals("River Plate", result.get(0).getNombre());
    }

    @Test
    void deberiaLanzarExcepcionCuandoElNombreNoExiste() {
        when(equipoRepository.findByNombreContainingIgnoreCase("xyz"))
                .thenReturn(Arrays.asList());
        assertThrows(RecursoNoEncontradoException.class,
                () -> equipoService.searchByNombre("xyz"));
    }

    @Test
    void deberiaCrearNuevoEquipo() {
        // Preparo datos de prueba
        Equipo nuevoEquipo = new Equipo(null, "Independiente", "Primera División", "Argentina");
        Equipo equipoGuardado = new Equipo(3L, "Independiente", "Primera División", "Argentina");

        // Simulo que el repositorio devuelve esto
        when(equipoRepository.save(nuevoEquipo)).thenReturn(equipoGuardado);

        //Llamo al SERVICIO (no al repositorio)
        Equipo result = equipoService.createEquipo(nuevoEquipo);

        // 4. THEN: Verifico que funciona
        assertNotNull(result.getId());           // ID se generó
        assertEquals("Independiente", result.getNombre());  // Datos correctos
        verify(equipoRepository).save(nuevoEquipo); // ← Servicio llamó al repositorio
    }

    @Test
    void deberiaActualizarEquipoCuandoExiste() {
        Equipo equipoExistente = new Equipo(1L, "River Plate", "Primera División", "Argentina");
        Equipo equipoActualizado = new Equipo(1L, "River Plate", "Copa Libertadores", "Argentina");

        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipoExistente));
        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoActualizado);

        Equipo result = equipoService.updateEquipo(1L, equipoActualizado);

        assertEquals("Copa Libertadores", result.getLiga());
        verify(equipoRepository).findById(1L);
        verify(equipoRepository).save(any(Equipo.class));
    }

    @Test
    void deberiaLanzarExcepcion_cuandoNoExisteEquipoParaActualizar() {
    
        Equipo equipoActualizado = new Equipo(99L, "Equipo Falso", "Liga Falsa", "País Falso");
        when(equipoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> equipoService.updateEquipo(99L, equipoActualizado));
        //confirma que se llama a la funcion para buscar el id 
        verify(equipoRepository).findById(99L);
        //confirma que no se llamo nunca al save 
        verify(equipoRepository, never()).save(any());
    }

    @Test
    void deberiaEliminarEquipoPorIdCuandoExiste() {
        when(equipoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(equipoRepository).deleteById(1L);

        equipoService.deleteEquipo(1L);

        verify(equipoRepository).existsById(1L);
        verify(equipoRepository).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcion_cuandoNoExisteEquipoParaBorrar() {

        when(equipoRepository.existsById(99L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class,
                () -> equipoService.deleteEquipo(99L));
        verify(equipoRepository).existsById(99L);
        verify(equipoRepository, never()).deleteById(any());
    }
}