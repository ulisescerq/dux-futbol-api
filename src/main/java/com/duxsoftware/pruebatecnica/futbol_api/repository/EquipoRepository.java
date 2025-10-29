package com.duxsoftware.pruebatecnica.futbol_api.repository;

import com.duxsoftware.pruebatecnica.futbol_api.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    // Búsqueda de equipos por nombre (case insensitive, búsqueda parcial)
    @Query("SELECT e FROM Equipo e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Equipo> findByNombreContainingIgnoreCase(String nombre);
}
