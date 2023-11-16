import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import styles from '../style.module.css';


class verAlumnosComponent extends Component {
    constructor(props){
        super(props);
        this.state = {
            alumnos: [],
        }
    }
    
    componentDidMount()
    {
        fetch("http://localhost:8080/alumnos/")
        .then((response) => response.json())
        .then((data) => this.setState({ alumnos: data }));
    }
    generarPlanilla = async event => {
        event.preventDefault();
        const MySwal = withReactContent(Swal);

        const flag = await fetch("http://localhost:8080/planilla/generar/" + this.state.codigo)
        .then((response) => response.json())

        console.log("flag: " + flag);

        if(flag == true)    
        {
            this.setState({redirect: true})
            console.log("Redirect: " + this.state.redirect);
            window.location.href = "/verPlanilla";
        }
        else
        {
            MySwal.fire({
                title: <strong>Error</strong>,
                html: <i>No se han encontrado datos suficientes para generar una planilla</i>,
                icon: 'error'
            });
        }
    }
    
    render() {
        return (
            <div>
                <header>
                    <h1>Alumnos Matriculados</h1>
                </header>
                <nav>
                    <ul>
                        <li><a href="/">Volver al menú principal</a></li>
                        <li><a href="/registrar">Matricular nuevo alumno</a></li>
                    </ul>
                </nav>
                <div>
                    <h1>Lista de proveedores</h1>
                    <table className={styles.contentTable}>
                        <thead>
                            <tr>
                                <th>Rut</th>
                                <th>Apellidos</th>
                                <th>Nombres</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.alumnos.map((alumno) =>(
                                <tr key={alumno.rut}>
                                    <td> {alumno.rut} </td>
                                    <td> {alumno.apellidos} </td>
                                    <td> {alumno.nombre} </td>
                                    <td> <Button variant="outlined" color="primary" href="/cuotas">Ver Cuotas</Button> </td>
                                    ) : (
                                        <td> No </td>
                                    )}
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
                
            </div>
        );
    }
}

export default verProveedoresComponent;