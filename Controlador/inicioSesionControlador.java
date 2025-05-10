package Controlador;
import java.util.HashMap;
import java.util.Map;
import Modelo.inicioSesionModelo;
public class inicioSesionControlador {
    private static final Map<String, inicioSesionModelo> empleados = new HashMap<>();
    static {
        empleados.put("Alfonso", new inicioSesionModelo(1, "Alfonso", "123", true));
        empleados.put("Cabrera", new inicioSesionModelo(2, "Cabrera", "123", false));
    }
    public static inicioSesionModelo validarUsuario(String usuario, String contraseña) {
        inicioSesionModelo empleado = empleados.get(usuario);
        if (empleado != null && empleado.validarContraseña(contraseña)) {
            return empleado;
        }
        return null;
    }
}