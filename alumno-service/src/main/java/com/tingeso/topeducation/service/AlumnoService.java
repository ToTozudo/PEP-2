package com.tingeso.topeducation.service;

import com.tingeso.topeducation.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlumnoService {
    @Autowired
    AlumnoRepository alumnoRepository;
}
