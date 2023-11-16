package com.tingeso.topeducation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Planilla {
    private long id;
    private String rut;
    private String apellidos;
    private String nombres;
    private double arancelTotal;
    private double dctoClasificacion;
    private double dctoEgreso;
    private double dctoPuntaje;
    private double intereses;
    private double arancelFinal;
}
