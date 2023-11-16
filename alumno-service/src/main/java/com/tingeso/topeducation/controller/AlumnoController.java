package com.tingeso.topeducation.controller;

import com.tingeso.topeducation.entity.Alumno;
import com.tingeso.topeducation.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    @Autowired
    AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<Alumno>> obtenerAlumnos(){
        List<Alumno> alumnos = alumnoService.getAll();
        if(alumnos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alumnos);
    }
    @GetMapping("/{rut}")
    public ResponseEntity<Alumno> obtenerByRut(@RequestParam("rut") String rut){
        return ResponseEntity.ok(alumnoService.getByRut(rut));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Boolean> registrar(@RequestParam ("rut") String rut,
                                             @RequestParam ("apellidos") String apellidos,
                                             @RequestParam ("nombres") String nombres,
                                             @RequestParam ("colegio") String colegio,
                                             @RequestParam ("clasificacion") String clasificacion,
                                             @RequestParam ("fechaNacimiento")LocalDate fechaNacimiento,
                                             @RequestParam ("egreso") int egreso,
                                             @RequestParam ("nCuotas") int nCuotas){
        alumnoService.registrarAlumno(rut,apellidos,nombres,fechaNacimiento,clasificacion,colegio,egreso, nCuotas);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/subir")
    public ResponseEntity<Boolean> subir(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        alumnoService.guardar(file);
        boolean correcto = alumnoService.leerCsvExamen(file.getOriginalFilename());
        if (correcto)
        {
            redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        }
        else
        {
            redirectAttributes.addFlashAttribute("mensaje", "Verifique el archivo que esta subiendo");
        }

        return ResponseEntity.ok(correcto);
    }
}
