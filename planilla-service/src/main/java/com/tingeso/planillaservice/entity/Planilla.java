package com.tingeso.planillaservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Planilla {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
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
