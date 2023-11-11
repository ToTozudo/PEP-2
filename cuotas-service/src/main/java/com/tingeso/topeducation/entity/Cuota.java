package com.tingeso.topeducation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
        private String estado;
        private LocalDate plazo;

}
