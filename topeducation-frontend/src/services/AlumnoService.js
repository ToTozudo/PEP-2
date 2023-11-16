import axios from 'axios';

const ALUMNOS_API_URL = "http://alumnos:8080/alumnos";

class ProveedorService {

    getAlumnos(){
        return axios.get(ALUMNOS_API_URL);
    }
    getAlumno(rut){
        return axios.get('${ALUMNOS_API_URL}/rut');
    }

    registrarAlumno(alumno){
        return axios.post(ALUMNOS_API_URL, alumno);
    }
}

export default new ProveedorService()