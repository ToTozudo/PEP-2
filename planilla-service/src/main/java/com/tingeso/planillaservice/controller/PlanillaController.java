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

    @PostMapping()
    public ResponseEntity<Planilla> save(@RequestBody Planilla planilla) {
        Planilla nuevaPlanilla = planillaService.save(planilla);
        return ResponseEntity.ok(nuevaPlanilla);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Planilla> getById(@PathVariable("rut") String rut) {
        Planilla planilla = planillaService.getPlanillaByRut(rut);
        if(planilla == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(planilla);
    }


    @GetMapping("/bystudent/{studentId}")
    public ResponseEntity<List<Book>> getByStudentId(@PathVariable("studentId") int studentId) {
        List<Book> books = bookService.byStudentId(studentId);
        return ResponseEntity.ok(books);
    }

}
