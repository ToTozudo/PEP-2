package com.tingeso.topeducation.service;

import com.tingeso.topeducation.entity.Cuota;
import com.tingeso.topeducation.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.util.List;

@Service
public class CuotaService {
    @Autowired
    CuotaRepository cuotaRepository;
    @Autowired
    RestTemplate restTemplate;
    public List<Cuota> getCuotasByAlumnoRut(String rut){return cuotaRepository.findByRut(rut);}

    public List<Cuota> getCuotasByEstado(String rut, String estado){return cuotaRepository.findByRutAndEstado(rut, estado)}

    public Cuota registrarAlumno(String rut, String apellidos, String nombres, LocalDate fecha, String tipoColegio, String colegio, Integer egreso){
        PlanillaEntity newPlanilla = new PlanillaEntity();
        newPlanilla.setArancelTotal(1500000);
        newPlanilla.setDescuentoColegio(0);
        newPlanilla.setDescuentoEgreso(0);
        AlumnoEntity alumnoMatriculado = alumnoRepository.findByRut(rut);
        newPlanilla.setAlumno(alumnoMatriculado);

        //Si el colegio del estudiante es:
        //Municipal     => 20% dscto.
        //Subvencionado => 10% dscto.
        //Privado       =>  0% dscto.
        if (alumnoMatriculado.getColegio().getTipoColegio().getId_tipo_colegio() == 0){
            newPlanilla.setDescuentoColegio(newPlanilla.getArancelTotal() * 0.2);
        } else if (alumnoMatriculado.getColegio().getTipoColegio().getId_tipo_colegio() == 1){
            newPlanilla.setDescuentoColegio(newPlanilla.getArancelTotal() * 0.1);
        }
        System.out.println(newPlanilla.getDescuentoColegio());
        //Si el estudiante egreso:
        //<1 año  => 15% dscto.
        // 1 - 2  =>  8% dscto.
        // 3 - 4  =>  4% dscto.
        //>4 años =>  0% dscto.
        if(LocalDate.now().getYear() - alumnoMatriculado.getEgreso() < 1){
            newPlanilla.setDescuentoEgreso(newPlanilla.getArancelTotal() * 0.15);
            System.out.println("Menos de 1 año");
        } else if(LocalDate.now().getYear() - alumnoMatriculado.getEgreso() <3){
            newPlanilla.setDescuentoEgreso(newPlanilla.getArancelTotal() * 0.08);
            System.out.println("Entree 1 y 2");
        } else if(LocalDate.now().getYear() - alumnoMatriculado.getEgreso() <5){
            newPlanilla.setDescuentoEgreso(newPlanilla.getArancelTotal() * 0.04);
        }
        System.out.println(newPlanilla.getDescuentoEgreso());
        newPlanilla.setArancelFinal((newPlanilla.getArancelTotal()- newPlanilla.getDescuentoColegio() - newPlanilla.getDescuentoEgreso()));

        for(int i=0; i< numeroCuotas; i++){
            CuotaEntity newCuota = new CuotaEntity();
            newCuota.setMonto(Math.ceil(newPlanilla.getArancelFinal()/numeroCuotas));
            newCuota.setAlumno(alumnoMatriculado);
            newCuota.setEstadoPago("No pago");
            newCuota.setPlazo(LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()).plusDays(4).plusMonths(i));
            cuotaRepository.save(newCuota);
        }
        newPlanilla.setN_Cuotas(numeroCuotas);
        newPlanilla.setDescuentoPuntaje(0);
        newPlanilla.setIntereses(0);
        planillaRepository.save(newPlanilla);
    }
}
