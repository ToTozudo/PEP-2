package com.tingeso.topeducation.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {
    private String rut;
    private String apellidos;
    private String nombres;
    private String colegio;
    private int clasificacion;
    private LocalDate fechaNacimiento;
    private int egreso;
    private int promPuntaje;
    private int pruebasRendidas;
}
