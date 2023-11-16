import axios from 'axios';

const PROVEEDOR_API_URL = "http://proveedor:8080/proveedores";

class ProveedorService {

    getProveedores(){
        return axios.get(PROVEEDOR_API_URL);
    }

    crearProveedor(proveedor){
        return axios.post(PROVEEDOR_API_URL, proveedor);
    }
}

export default new ProveedorService()