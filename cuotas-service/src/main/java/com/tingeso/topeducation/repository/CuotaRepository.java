package com.tingeso.topeducation.repository;

import com.tingeso.topeducation.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long>{
    List<Cuota> findByRut(String rut);

    List<Cuota> findByRutAndEstado(String rut, int estado);

    Cuota findFirstByRutAndEstadoOrderByPlazoAsc(String rut, int i);

    List<Cuota> findAllByRutAndEstado(String rut, int i);
}
