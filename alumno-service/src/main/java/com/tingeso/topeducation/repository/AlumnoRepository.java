package com.tingeso.topeducation.repository;

import com.tingeso.topeducation.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, String>{
    Alumno findByRut(String rut);
}
