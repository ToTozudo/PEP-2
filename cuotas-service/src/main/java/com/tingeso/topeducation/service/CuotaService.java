package com.tingeso.topeducation.service;

import com.tingeso.topeducation.entity.Cuota;
import com.tingeso.topeducation.models.Alumno;
import com.tingeso.topeducation.models.Planilla;
import com.tingeso.topeducation.repository.CuotaRepository;
import org.springframework.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class CuotaService {
    @Autowired
    CuotaRepository cuotaRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<Cuota> getCuotasByAlumnoRut(String rut){return cuotaRepository.findByRut(rut);}

    public List<Cuota> getCuotasByEstado(String rut, int estado){return cuotaRepository.findByRutAndEstado(rut, estado);}

    public void guardarCuota(Cuota cuota){cuotaRepository.save(cuota);}
    public void crearCuotas(String rut, Integer numeroCuotas){
        Planilla newPlanilla = new Planilla();
        newPlanilla.setArancelTotal(1500000);
        newPlanilla.setDctoClasificacion(0);
        newPlanilla.setDctoEgreso(0);
        Alumno alumnoMatriculado = restTemplate.getForObject("http://alumno-service/alumnos/"+rut, Alumno.class);
        newPlanilla.setRut(alumnoMatriculado.getRut());
        newPlanilla.setNombres(alumnoMatriculado.getNombres());
        newPlanilla.setApellidos(alumnoMatriculado.getApellidos());

        //Si el colegio del estudiante es:
        //Municipal     => 20% dscto.
        //Subvencionado => 10% dscto.
        //Privado       =>  0% dscto.
        if (alumnoMatriculado.getClasificacion() == 0){
            newPlanilla.setDctoClasificacion(newPlanilla.getArancelTotal() * 0.2);
        } else if (alumnoMatriculado.getClasificacion() == 1){
            newPlanilla.setDctoClasificacion(newPlanilla.getArancelTotal() * 0.1);
        }
        //System.out.println(newPlanilla.getDctoClasificacion());
        //Si el estudiante egreso:
        //<1 año  => 15% dscto.
        // 1 - 2  =>  8% dscto.
        // 3 - 4  =>  4% dscto.
        //>4 años =>  0% dscto.
        if(LocalDate.now().getYear() - alumnoMatriculado.getEgreso() < 1){
            newPlanilla.setDctoEgreso(newPlanilla.getArancelTotal() * 0.15);
            //System.out.println("Menos de 1 año");
        } else if(LocalDate.now().getYear() - alumnoMatriculado.getEgreso() <3){
            newPlanilla.setDctoEgreso(newPlanilla.getArancelTotal() * 0.08);
            //System.out.println("Entree 1 y 2");
        } else if(LocalDate.now().getYear() - alumnoMatriculado.getEgreso() <5){
            newPlanilla.setDctoEgreso(newPlanilla.getArancelTotal() * 0.04);
        }
        newPlanilla.setArancelFinal((newPlanilla.getArancelTotal()- newPlanilla.getDctoClasificacion() - newPlanilla.getDctoEgreso()));

        for(int i=0; i< numeroCuotas; i++){
            Cuota newCuota = new Cuota();
            newCuota.setMonto(Math.ceil(newPlanilla.getArancelFinal()/numeroCuotas));
            newCuota.setRut(rut);
            newCuota.setEstado(0);
            newCuota.setPlazo(LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()).plusDays(4).plusMonths(i));
            guardarCuota(newCuota);
        }
        newPlanilla.setDctoPuntaje(0);
        newPlanilla.setIntereses(0);
        HttpEntity request = new HttpEntity<Planilla>(newPlanilla);
        restTemplate.postForObject("http://planilla-service/planilla/guardar/"+rut,request,Planilla.class);
    }

    public void descuentoPuntaje(String rut, int puntaje, LocalDate fechaRendicion){
        Alumno alumno = restTemplate.getForObject("http://alumno-service/alumnos/"+rut, Alumno.class);
        List<Cuota> cuotasPendientes = getCuotasByEstado(rut, 0);
        Planilla planilla = restTemplate.getForObject("http://planilla-service/planilla/"+rut,Planilla.class);
        double descuento = 0;
        if(getCuotasByAlumnoRut(rut).size() == 1){
            puntaje = alumno.getPromPuntaje();
        }
        if (puntaje > 949){
            descuento = 0.1;
        } else if (puntaje > 899) {
            descuento = 0.05;
        } else if (puntaje >= 850) {
            descuento = 0.02;
        }
        for (Cuota cuota : cuotasPendientes) {
            planilla.setDctoPuntaje(planilla.getDctoPuntaje()+Math.floor(cuota.getMonto()*(descuento)));
            cuota.setMonto(Math.floor(cuota.getMonto()*(1-descuento)));
            cuotaRepository.save(cuota);
        }
        HttpEntity request = new HttpEntity<Planilla>(planilla);
        restTemplate.postForObject("http://planilla-service/planilla/actualizar/"+rut,request,Planilla.class);
    }
    public void calcularIntereses(String rut){
        Cuota primeraCuotaNoPaga = cuotaRepository.findFirstByRutAndEstadoOrderByPlazoAsc( rut, 0);
        Planilla planillaAlumno = restTemplate.getForObject("http://planilla-service/planilla/"+rut,Planilla.class);
        if (primeraCuotaNoPaga != null) {
            LocalDate fechaActual = LocalDate.now();
            LocalDate plazoCuota = primeraCuotaNoPaga.getPlazo();
            long mesesDeAtraso = ChronoUnit.MONTHS.between(plazoCuota, fechaActual);

            if (mesesDeAtraso > 0) {
                double interes = 0.03; // 3% de interés por defecto

                if (mesesDeAtraso >= 2) {
                    interes = 0.06; // 6% de interés si se deben al menos 2 meses
                }

                if (mesesDeAtraso >= 3) {
                    interes = 0.09; // 9% de interés si se deben al menos 3 meses
                }

                if (mesesDeAtraso > 3) {
                    interes = 0.15; // 15% de interés si se deben más de 3 meses
                }

                // Aplicar el interés a todas las cuotas pendientes
                List<Cuota> cuotasPendientes = cuotaRepository.findAllByRutAndEstado(primeraCuotaNoPaga.getRut(), 0);

                for (Cuota cuota : cuotasPendientes) {
                    planillaAlumno.setIntereses(planillaAlumno.getIntereses()+cuota.getMonto()*interes);
                    cuota.setMonto(cuota.getMonto() * (1 + interes));
                    cuotaRepository.save(cuota);
                }
            }
        }
        HttpEntity request = new HttpEntity<Planilla>(planillaAlumno);
        restTemplate.postForObject("http://planilla-service/planilla/actualizar/"+rut,request,Planilla.class);
    }
    public void pagarCuota(Long id){
        Cuota cuotaPagada=cuotaRepository.findById(id).get();
        cuotaPagada.setEstado(1);
        cuotaRepository.save(cuotaPagada);
    }
}
