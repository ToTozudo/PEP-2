package com.tingeso.planillaservice.repository;

import com.tingeso.planillaservice.entity.Planilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanillaRepository extends JpaRepository <Planilla, Long> {
    Planilla findByAlumnoRut(String rut);
}
