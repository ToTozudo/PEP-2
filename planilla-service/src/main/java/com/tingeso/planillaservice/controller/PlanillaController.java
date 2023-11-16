package com.tingeso.planillaservice.controller;

import com.tingeso.planillaservice.entity.Planilla;
import com.tingeso.planillaservice.service.PlanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planilla")
public class PlanillaController {

    @Autowired
    PlanillaService planillaService;

    @GetMapping
    public ResponseEntity<List<Planilla>> getAll() {
        List<Planilla> planillas = planillaService.getAll();
        if(planillas.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(planillas);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Boolean> save(@RequestBody Planilla planilla) {
        planillaService.guardarPlanilla(planilla);
        return ResponseEntity.ok(true);
    }
    @PostMapping("/actualizar/{rut}")
    public ResponseEntity<Boolean> update(@RequestParam("rut") String rut, @RequestBody Planilla planilla) {
        planillaService.updatePlanilla(rut, planilla);
        return ResponseEntity.ok(true);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Planilla> getById(@PathVariable("rut") String rut) {
        Planilla planilla = planillaService.getPlanillaByRut(rut);
        if(planilla == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(planilla);
    }


}
