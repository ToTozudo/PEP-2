// VerAlumnosComponent.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'react-bootstrap'; // Importa los componentes de Bootstrap según sea necesario
import axios from 'axios';
import '../App.css';
import styles from '../style.module.css';

const VerAlumnosComponent = () => {
    const [alumnos, setAlumnos] = useState([]);

    useEffect(() => {
        // Realizar la solicitud HTTP para obtener los datos de los alumnos
        axios.get('/alumnos')
        .then(response => {
            setAlumnos(response.data);
        })
        .catch(error => {
            console.error('Error al obtener los datos de los alumnos:', error);
        });
    }, []);

    return (
        <div>
        <header>
            <h1>Alumnos Matriculados</h1>
        </header>
        <nav>
            <ul>
            <li>
                <Link to="/">Inicio</Link>
            </li>
            <li>
                <Link to="/subir">Importar notas examenes</Link>
            </li>
            </ul>
        </nav>
        <div class="container-sm">
    	    <table className={styles.contentTable}>
            <thead className="thead-dark">
                <tr>
                <th>RUT</th>
                <th>Apellidos</th>
                <th>Nombre</th>
                <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                {alumnos.map((alumno) => (
                <tr key={alumno.rut}>
                    <td>{alumno.rut}</td>
                    <td>{alumno.apellidos}</td>
                    <td>{alumno.nombres}</td>
                    <td>
                    <Button variant="primary" as={Link} to={`/cuotas/${alumno.rut}`}>
                        Ver Cuotas
                    </Button>
                    <Button variant="primary" as={Link} to={`/planillas/${alumno.rut}`}>
                        Generar Planilla
                    </Button>
                    </td>
                </tr>
                ))}
            </tbody>
            </table>
            <Button variant="primary" as={Link} to="/alumnos/registrar">
            Añadir
            </Button>
        </div>
        </div>
    );
};

export default VerAlumnosComponent;
