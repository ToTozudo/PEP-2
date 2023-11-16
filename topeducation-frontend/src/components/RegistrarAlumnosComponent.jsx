import React, {Component} from 'react';
import '../App.css';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

class RegistrarAlumnoComponent extends Component {

    
    constructor(props) {
    }

    changeRutAlumno = event => {
        this.setState({
            rut: event.target.value
        });
        console.log(this.state.rut)
    };

    validarRUT = (rut) => {
        const rutPattern = /^(\d{1,2}\.\d{3}\.\d{3}-[\dkK])$/;
        return rutPattern.test(rut);
    };

    changeNombresAlumno = event => {
        this.setState({
            nombres: event.target.value
        });
        console.log(this.state.nombres)
    };

    changeApellidoAlumno = event => {
        this.setState({
            apellidos: event.target.value
        });
        console.log(this.state.apellidos)
    };

    changeFechaNacimientoAlumno = event => {
        this.setState({
            fechaNacimiento: event.target.value
        });
        console.log(this.state.fechaNacimiento)
    };

    changeColegioAlumno = event => {
        this.setState({
            colegio: event.target.value
        });
        console.log(this.state.colegio)
    };

    changeClasificacionAlumno = event => {
        this.setState({
            clasificacion: event.target.value
        });
        console.log(this.state.clasificacion)
    };

    changeEgresoAlumno = event => {
        this.setState({
            egreso: event.target.value
        });
        console.log(this.state.egreso)
    };

    changeCuotasAlumno = event => {
        this.setState({
            cuotas: event.target.value
        });
        console.log(this.state.cuotas)
    };

    

    guardarAlumno = event => {
        event.preventDefault();
        const MySwal = withReactContent(Swal);
        if(this.state.rut == '' || !this.validarRUT(this.state.rut))
        {
            MySwal.fire({
                title: <strong>Error en el rut</strong>,
                html: <i>Porfavor escriba un rut valido</i>,
                icon: 'error'
            });
        }
        else if(this.state.nombres == '')
        {
            MySwal.fire({
                title: <strong>Error en nombres</strong>,
                html: <i>Porfavor escriba un nombre valido</i>,
                icon: 'error'
            });
        }
        else if(this.state.categoria == '')
        {
            MySwal.fire({
                title: <strong>Error en categoria</strong>,
                html: <i>Porfavor escriba una categoria valido</i>,
                icon: 'error'
            });
        }
        else if(this.state.retencion == null)
        {
            MySwal.fire({
                title: <strong>Error en retencion</strong>,
                html: <i>Porfavor escoga una opción</i>,
                icon: 'error'
            });
        }
        else
        {
            fetch(`http://localhost:8080/alumno/registrar?rut=${this.state.rut}&nombres=${this.state.nombres}&categoria=${this.state.categoria}&retencion=${this.state.retencion}`,
            {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ title: '' }) 
            })
            .catch(err => console.error(err));

            MySwal.fire({
                title: <strong>Exito!</strong>,
                html: <i>Se a añadido el proveedor correctamente</i>,
                icon: 'success'
            });

            this.setState({codigo: ''});
            this.setState({nombre: ''});
            this.setState({categoria: ''});
            this.setState({retencion: null});

            event.target.reset();
        }
        
    }

    render() {
        return (
            <div>
                <head>
                    <title>Proveedores</title>
                </head>
                <header>
                    <h1>Proveedores</h1>    
                </header>
                <nav>
                    <ul>
                        <li><a href="/">Volver al menú principal</a></li>
                        <li><a href="/verProveedores">Ver proveedores</a></li>
                    </ul>
                </nav>
                <div>
                    <h1>Ingrese los datos del nuevo proveedor</h1>
                    <body>
                        <form onSubmit={this.guardarProveedor}>
                            <ul class="registration-form">
                                <li>
                                    <label>Rut</label>
                                    <input type="text" placeholder={this.state.textoRut} id="rut"
                                        name="rut" onChange={this.changeRutAlumno}/>
                                </li>
                                <li>
                                    <label>Nombre</label>
                                    <input type="text" placeholder="Ingrese el nombre del proveedor" id="nombre"
                                        name="nombre" onChange={this.changeNombresAlumno}/>
                                </li>
                                <li>
                                    <label>Categoria</label>
                                    <select id="categoria" name="categoria" onChange={this.changeCategoriaProveedor}>
                                        <option value="" selected>Seleccione una categoria</option>
                                        <option value="A">A</option>
                                        <option value="B">B</option>
                                        <option value="C">C</option>
                                    </select>
                                </li>
                                <li>
                                <label>Retencion</label>
                                    <input type="radio" name="retencion" value="true" onChange={this.changeRetencionProveedor}/> Si
                                    {'\u00A0'}{'\u00A0'}{'\u00A0'}{'\u00A0'}{'\u00A0'}
                                    <input type="radio" name="retencion" value="false" onChange={this.changeRetencionProveedor}/> No
                                </li>
                                <li class="btn-secondary-container">
                                    <input class="btn-secondary" type="submit" value="Ingresar proveedor"/>
                                </li>
                            </ul> 
                        </form>
                        <footer>
                            <p>Derechos reservados MilkStgo</p>
                        </footer>
                    </body>
                </div>
            </div>
        );
    }
}

export default RegistrarAlumnoComponent;