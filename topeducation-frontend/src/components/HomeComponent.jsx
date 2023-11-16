// HomeComponent.js
import React from 'react';
import { Link } from 'react-router-dom';

const HomeComponent = () => {
    return (
        <div>
        <header>
            <h1>TopEducation 2024</h1>
        </header>
        <nav>
            <ul>
            <li>
                <Link to="/alumnos">Ver alumnos matriculados</Link>
            </li>
            <li>
                <Link to="/subir">Importar notas examenes</Link>
            </li>
            </ul>
        </nav>
        <main>
            <div>
            <table>
                <tbody>
                <tr>
                    <th>
                    <h2>Página Administrativa TopEducation</h2>
                    <p></p>
                    </th>
                    <th></th>
                </tr>
                </tbody>
            </table>
            </div>
        </main>
        <footer>
            <p></p>
        </footer>
        </div>
    );
};

export default HomeComponent;
