package com.duxsoftware.pruebatecnica.futbol_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity // Marca la clase como una tabla de base de datos
@Table(name = "equipo")
@Data // Lombok: genera getters, setters, toString, equals, hashCode
@NoArgsConstructor 
@AllArgsConstructor 
@Builder // Lombok: Patrón Builder para crear objetos fácilmente
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false) 
    private String liga;

    @Column(nullable = false)
    private String pais;
}