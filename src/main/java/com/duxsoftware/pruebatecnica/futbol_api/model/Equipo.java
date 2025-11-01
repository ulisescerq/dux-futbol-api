package com.duxsoftware.pruebatecnica.futbol_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity // Marca la clase como una tabla de base de datos
@Table(name = "equipo")
@Schema(description = "Representa un equipo de fútbol registrado en el sistema")
@Data // Lombok: genera getters, setters, toString, equals, hashCode
@NoArgsConstructor 
@AllArgsConstructor 
@Builder // Lombok: Patrón Builder para crear objetos fácilmente
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Schema(description = "ID único del equipo. Generado automáticamente por la base de datos.",
        accessMode = Schema.AccessMode.READ_ONLY
        )
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre del equipo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(nullable = false) 
    @Schema(description = "Liga o torneo en el que participa",requiredMode = Schema.RequiredMode.REQUIRED)
    private String liga;

    @Column(nullable = false)
    @Schema(description = "País de origen del equipo",requiredMode = Schema.RequiredMode.REQUIRED)
    private String pais;
}