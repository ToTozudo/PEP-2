package com.tingeso.topeducation.controller;

import com.tingeso.topeducation.entity.Cuota;
import com.tingeso.topeducation.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuotas")
public class CuotaController {
    @Autowired
    CuotaService cuotaService;

    @GetMapping("/{rut}")
    public ResponseEntity<List<Cuota>> obtenerCuotas(@PathVariable("rut") String rut){
        List<Cuota> cuotas = cuotaService.getCuotasByAlumnoRut(rut);
        if(cuotas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuotas);
    }
    @PostMapping("/crear")
    public ResponseEntity<Boolean> crear(List<String> datos){
        String rut = datos.get(0);
        int nCuotas = Integer.parseInt(datos.get(1));
        cuotaService.crearCuotas(rut, nCuotas);
        return ResponseEntity.ok(true);
    }
    @GetMapping("/pagar/{id}")
    public ResponseEntity<Boolean> pagar(@PathVariable("id") Long id){
        cuotaService.pagarCuota(id);
        return ResponseEntity.ok(true);
    }
}
