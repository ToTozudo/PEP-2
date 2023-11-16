package com.tingeso.topeducation.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuota {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long ID;
        private String rut;
        private Double monto;
        private int estado;
        private LocalDate plazo;

}
