package com.tingeso.topeducation.controller;

import com.tingeso.topeducation.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    @Autowired
    AlumnoService alumnoService;
}
