import React, {Component} from 'react';
import '../App.css';

class HomeComponent extends Component {
    
    render() {
        return (
            <div>
                
                    <header>
                        <h1>TopEducation 2024</h1>
                    </header>
                    <nav>
                        <ul>
                            <li><a href="/alumnos">Ver alumnos matriculados</a></li>
                            <li><a href="/subir">Importar notas examenes</a></li>
                        </ul>
                    </nav>
                    <main>
                        <div>
                            <table>
                                <tr>
                                    <th>
                                        <h2>Página Administrativa TopEducation</h2>
                                        <p></p>
                                    </th>
                                    <th>

                                    </th>
                                </tr>
                            </table>
                        </div>
                    </main>
                    <footer>
                        <p></p>
                    </footer>
                    
            </div>
        );
    }
}

export default HomeComponent;