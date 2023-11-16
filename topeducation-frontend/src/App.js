import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeComponent from './components/HomeComponent';
import VerAlumnosComponent from './components/VerAlumnosComponent';
import RegistrarAlumnosComponent from './components/RegistrarAlumnosComponent';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes >
          <Route path = "/" element = {<HomeComponent />}> </Route>
          <Route path = "/alumnos" element  = {<VerAlumnosComponent />}> </Route>
          <Route path = "/alumnos/regitrar" element  = {<RegistrarAlumnosComponent />}> </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
