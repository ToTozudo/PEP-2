package com.tingeso.topeducation.service;

import com.tingeso.topeducation.entity.Alumno;
import com.tingeso.topeducation.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlumnoService {
    @Autowired
    AlumnoRepository alumnoRepository;
    public List<Alumno> getAll(){return alumnoRepository.findAll();}

    public Alumno registrarAlumno(String rut, String apellidos, String nombres, LocalDate fecha, String tipoColegio, String colegio, Integer egreso){
        Alumno nuevoAlumno = new Alumno();
        nuevoAlumno.setRut(rut);
        nuevoAlumno.setApellidos(apellidos);
        nuevoAlumno.setNombres(nombres);
        nuevoAlumno.setFechaNacimiento(fecha);
        nuevoAlumno.setEgreso(egreso);
        nuevoAlumno.setColegio(colegio);
        switch (tipoColegio){
            case "Municipal" -> nuevoAlumno.setClasificacion(0);
            case "Subvencionado" -> nuevoAlumno.setClasificacion(1);
            case "Privado" -> nuevoAlumno.setClasificacion(2);
            default -> System.out.println("Tipo de colegio no registrado.");
        }
        return alumnoRepository.save(nuevoAlumno);
    }
    public Alumno getByRut(String rut){return alumnoRepository.findByRut(rut);}
}
