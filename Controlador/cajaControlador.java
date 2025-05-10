package Controlador;
import Modelo.cajaModelo;
import Modelo.inicioSesionModelo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class cajaControlador {
    private static final String ARCHIVO_CAJA = "caja.json";
    public static inicioSesionModelo empleadoActual;
    public static void inicioCaja() {
        if (hayCajaActiva()) {
            JOptionPane.showMessageDialog(null, "¡Ya hay una caja abierta!");
            return;
        }
        if (empleadoActual == null || !empleadoActual.esAdmin()) {
            String adminUser = JOptionPane.showInputDialog("Usuario administrador:");
            String adminPass = JOptionPane.showInputDialog("Contraseña:");
            inicioSesionModelo admin = inicioSesionControlador.validarUsuario(adminUser, adminPass);
            if (admin == null || !admin.esAdmin()) {
                JOptionPane.showMessageDialog(null, "Acceso denegado. Solo admins pueden iniciar la caja.");
                return;
            }
        }
        String montoStr = JOptionPane.showInputDialog("Ingrese el monto inicial:");
        if (montoStr == null || montoStr.trim().isEmpty()) return;
        try {
            double montoInicial = Double.parseDouble(montoStr);
            if (montoInicial < 0) {
                JOptionPane.showMessageDialog(null, "El monto no puede ser negativo.");
                return;
            }
            cajaModelo nuevaCaja = new cajaModelo(montoInicial, LocalDateTime.now().toString(), false);
            nuevaCaja.montoActual = montoInicial;
            guardarCaja(nuevaCaja);
            JOptionPane.showMessageDialog(null, "Caja iniciada con: $" + montoInicial);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "¡Monto inválido!");
        }
    }
    public static void cerrarCaja() {
        if (!hayCajaActiva()) {
            JOptionPane.showMessageDialog(null, "No hay caja activa para cerrar.");
            return;
        }
        if (empleadoActual == null || !empleadoActual.esAdmin()) {
            String adminUser = JOptionPane.showInputDialog("Usuario administrador:");
            String adminPass = JOptionPane.showInputDialog("Contraseña:");
            inicioSesionModelo admin = inicioSesionControlador.validarUsuario(adminUser, adminPass);
            if (admin == null || !admin.esAdmin()) {
                JOptionPane.showMessageDialog(null, "Acceso denegado. Solo admins pueden cerrar la caja.");
                return;
            }
        }
        List<cajaModelo> historial = leerHistorialCaja();
        cajaModelo cajaActiva = historial.get(historial.size() - 1);
        String resumen = """
            RESUMEN DE CIERRE:
            Monto inicial: $%.2f
            Monto final: $%.2f
            Fecha inicio: %s
            Fecha cierre: %s
            """.formatted(
                cajaActiva.montoInicial,
                cajaActiva.montoActual,
                cajaActiva.fechaHoraInicio,
                LocalDateTime.now()
        );
        JOptionPane.showMessageDialog(null, resumen);
        cajaModelo cierre = new cajaModelo(cajaActiva.montoInicial, LocalDateTime.now().toString(), true);
        cierre.montoActual = cajaActiva.montoActual;
        guardarCaja(cierre);
    }
    public static void rembolsoCaja(inicioSesionModelo empleadoActual) {
        if (!hayCajaActiva()) {
            JOptionPane.showMessageDialog(null, "No hay caja activa.");
            return;
        }
        String montoStr = JOptionPane.showInputDialog(null, "Ingrese el monto a reembolsar:");
        if (montoStr == null || montoStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se ingresó un monto.");
            return;
        }
        double montoRembolso;
        try {
            montoRembolso = Double.parseDouble(montoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "¡Monto inválido! Por favor ingrese un número válido.");
            return;
        }
        List<cajaModelo> historial = leerHistorialCaja();
        cajaModelo cajaActiva = historial.get(historial.size() - 1);
        if (montoRembolso <= 0) {
            JOptionPane.showMessageDialog(null, "El monto de reembolso debe ser positivo.");
            return;
        }
        if (montoRembolso > cajaActiva.montoActual) {
            JOptionPane.showMessageDialog(null, "No existe feria, perdón.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (empleadoActual == null || !empleadoActual.esAdmin()) {
            String adminUser = JOptionPane.showInputDialog("Usuario administrador:");
            String adminPass = JOptionPane.showInputDialog("Contraseña:");
            inicioSesionModelo admin = inicioSesionControlador.validarUsuario(adminUser, adminPass);
            if (admin == null || !admin.esAdmin()) {
                JOptionPane.showMessageDialog(null, "Acceso denegado. Solo admins pueden autorizar reembolsos.");
                return;
            }
        }
        cajaActiva.montoActual -= montoRembolso;
        historial.set(historial.size() - 1, cajaActiva);
        guardarHistorial(historial);
        JOptionPane.showMessageDialog(null, "Reembolso exitoso: $" + montoRembolso);
    }
    public static void actualizarCaja(double montoVenta) {
        if (!hayCajaActiva()) {
            JOptionPane.showMessageDialog(null, "No hay caja activa para registrar ventas.");
            return;
        }
        List<cajaModelo> historial = leerHistorialCaja();
        cajaModelo cajaActiva = historial.get(historial.size() - 1);
        cajaActiva.montoActual += montoVenta;
        historial.set(historial.size() - 1, cajaActiva);
        guardarHistorial(historial);
    }
    public static boolean hayCajaActiva() {
        List<cajaModelo> historial = leerHistorialCaja();
        return !historial.isEmpty() && !historial.get(historial.size() - 1).esCierre;
    }
    public static double obtenerMontoCaja() {
        List<cajaModelo> historial = leerHistorialCaja();
        if (historial.isEmpty()) {
            return 0;
        }
        cajaModelo ultimaCaja = historial.get(historial.size() - 1);
        return ultimaCaja.montoActual;
    }
    private static void guardarCaja(cajaModelo caja) {
        List<cajaModelo> historial = leerHistorialCaja();
        historial.add(caja);
        guardarHistorial(historial);
    }
    private static void guardarHistorial(List<cajaModelo> historial) {
        try (FileWriter writer = new FileWriter(ARCHIVO_CAJA)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(historial, writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
        }
    }
    public static List<cajaModelo> leerHistorialCaja() {
        File archivo = new File(ARCHIVO_CAJA);
        if (!archivo.exists()) return new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<List<cajaModelo>>() {}.getType());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo.");
            return new ArrayList<>();
        }
    }
}