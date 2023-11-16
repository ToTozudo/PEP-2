package com.tingeso.topeducation.service;

import com.tingeso.topeducation.entity.Alumno;
import com.tingeso.topeducation.repository.AlumnoRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

@Service
public class AlumnoService {
    @Autowired
    AlumnoRepository alumnoRepository;
    RestTemplate restTemplate;
    public List<Alumno> getAll(){return alumnoRepository.findAll();}

    public Alumno registrarAlumno(String rut, String apellidos, String nombres, LocalDate fecha, String tipoColegio, String colegio, Integer egreso, Integer nCuotas){
        Alumno nuevoAlumno = new Alumno();
        nuevoAlumno.setRut(rut);
        nuevoAlumno.setApellidos(apellidos);
        nuevoAlumno.setNombres(nombres);
        nuevoAlumno.setFechaNacimiento(fecha);
        nuevoAlumno.setEgreso(egreso);
        nuevoAlumno.setColegio(colegio);
        nuevoAlumno.setPruebasRendidas(0);
        switch (tipoColegio){
            case "Municipal" -> nuevoAlumno.setClasificacion(0);
            case "Subvencionado" -> nuevoAlumno.setClasificacion(1);
            case "Privado" -> nuevoAlumno.setClasificacion(2);
            default -> System.out.println("Tipo de colegio no registrado.");
        }
        List<String> cuotas = new ArrayList<>();
        cuotas.add(rut);
        cuotas.add(nCuotas.toString());
        HttpEntity request = new HttpEntity<List>(cuotas);
        restTemplate.postForObject("http://cuota-service/cuotas/crear", request, List.class);
        return alumnoRepository.save(nuevoAlumno);

    }
    public Alumno getByRut(String rut){return alumnoRepository.findByRut(rut);}

    private final Logger logg = (Logger) LoggerFactory.getLogger(AlumnoService.class);
    public boolean guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean leerCsvExamen(String direccion){
        String texto = "";
        BufferedReader bf = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD-MM-YYYY");


        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarNotas( bfRead.split(";")[0], LocalDate.parse(bfRead.split(";")[1], formatter), Integer.parseInt(bfRead.split(";")[2]));
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
            return true;
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
        return false;
    }

    private void guardarNotas(String rut, LocalDate fecha, Integer puntaje) {
        Alumno alumno = getByRut(rut);
        alumno.setPruebasRendidas(alumno.getPruebasRendidas()+1);
        if(alumno.getPromPuntaje() <= 0){
            alumno.setPromPuntaje(puntaje);
        }else alumno.setPromPuntaje((int) (((alumno.getPruebasRendidas()-1)*alumno.getPromPuntaje()) + puntaje) / alumno.getPruebasRendidas());
        List<String> datos = new ArrayList<>();
        datos.add(rut);
        datos.add(puntaje.toString());
        datos.add(fecha.toString());
        HttpEntity request = new HttpEntity<List>(datos);
        restTemplate.postForObject("http://cuota-service/descuento-puntaje", request, List.class);
    }
}
