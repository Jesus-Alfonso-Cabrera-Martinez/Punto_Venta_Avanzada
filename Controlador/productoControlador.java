package Controlador;
import Modelo.inicioSesionModelo;
import Modelo.productoModelo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
public class productoControlador {
    public static inicioSesionModelo empleadoActual;
    private static final String ARCHIVO_JSON = "productos.json";
    private static List<productoModelo> listaProductos;
    static {
        listaProductos = leerProductosJSON(ARCHIVO_JSON);
    }
    public static void registrarProducto(inicioSesionModelo empleado) {
        if (empleadoActual == null || !empleadoActual.esAdmin()) {
            String adminUser = JOptionPane.showInputDialog("Usuario administrador:");
            String adminPass = JOptionPane.showInputDialog("Contraseña:");
            inicioSesionModelo admin = inicioSesionControlador.validarUsuario(adminUser, adminPass);
            if (admin == null || !admin.esAdmin()) {
                JOptionPane.showMessageDialog(null, "Acceso denegado. Solo admins pueden agregar productos.");
                return;
            }
        }
        try {
            String nombre = solicitarTexto("Ingrese el nombre del producto:");
            if (nombre == null) return;
            String idStr = solicitarTexto("Ingrese el ID del producto:");
            if (idStr == null) return;
            BigInteger id = new BigInteger(idStr);
            String precioStr = solicitarTexto("Ingrese el precio del producto por unidad:");
            if (precioStr == null) return;
            double precio = Double.parseDouble(precioStr);
            String[] opciones = {"GR", "ML"};
            String tamaño = (String) JOptionPane.showInputDialog(null, "Seleccione la unidad de medida:", "Unidad", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (tamaño == null) return;
            int cantidad = Integer.parseInt(solicitarTexto("Ingrese la cantidad por unidad (ej: 100 gramos):"));
            int stock = Integer.parseInt(solicitarTexto("Ingrese el stock del producto:"));
            String proveedor = solicitarTexto("Ingrese el proveedor del producto:");
            if (proveedor == null) return;
            productoModelo producto = new productoModelo(nombre, id, precio, tamaño, cantidad, stock, proveedor);
            ConexionBaseDatosControladorProducto dbControlador = new ConexionBaseDatosControladorProducto();
            boolean exitoDb = dbControlador.insertarProducto(producto);
            if (exitoDb) {
                listaProductos = leerProductosJSON(ARCHIVO_JSON);
                listaProductos.add(producto);
                guardarProductosJSON(listaProductos, ARCHIVO_JSON);
                mostrarMensaje("Producto agregado exitosamente en base de datos y JSON");
            } else {
                mostrarError("Error al agregar producto en la base de datos.");
            }
        } catch (NumberFormatException e) {
            mostrarError("Ingrese valores numéricos válidos.");
        }
    }
    public static void eliminarProducto(inicioSesionModelo empleado, BigInteger id) {
        if (empleadoActual == null || !empleadoActual.esAdmin()) {
            String adminUser = JOptionPane.showInputDialog("Usuario administrador:");
            String adminPass = JOptionPane.showInputDialog("Contraseña:");
            inicioSesionModelo admin = inicioSesionControlador.validarUsuario(adminUser, adminPass);
            if (admin == null || !admin.esAdmin()) {
                JOptionPane.showMessageDialog(null, "Acceso denegado. Solo admins pueden eliminar productos.");
                return;
            }
        }
        ConexionBaseDatosControladorProducto dbControlador = new ConexionBaseDatosControladorProducto();
        boolean exitoDb = dbControlador.eliminarProducto(id);
        if (exitoDb) {
            listaProductos = leerProductosJSON(ARCHIVO_JSON);
            productoModelo producto = buscarProductoPorId(id);
            if (producto != null) {
                listaProductos.remove(producto);
                guardarProductosJSON(listaProductos, ARCHIVO_JSON);
                mostrarMensaje("Producto eliminado exitosamente en base de datos y JSON");
            } else {
                mostrarError("Producto no encontrado en JSON.");
            }
        } else {
            mostrarError("Error al eliminar producto en la base de datos.");
        }
    }
    public static void actualizarProducto(inicioSesionModelo empleado, BigInteger id) {
        if (empleadoActual == null || !empleadoActual.esAdmin()) {
            String adminUser = JOptionPane.showInputDialog("Usuario administrador:");
            String adminPass = JOptionPane.showInputDialog("Contraseña:");
            inicioSesionModelo admin = inicioSesionControlador.validarUsuario(adminUser, adminPass);
            if (admin == null || !admin.esAdmin()) {
                JOptionPane.showMessageDialog(null, "Acceso denegado. Solo admins pueden actualizar productos.");
                return;
            }
        }
        ConexionBaseDatosControladorProducto dbControlador = new ConexionBaseDatosControladorProducto();
        productoModelo producto = dbControlador.obtenerProductos().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
        if (producto != null) {
            try {
                String nombre = solicitarTexto("Nuevo nombre del producto:", producto.getNombre());
                if (nombre == null) return;
                double precio = Double.parseDouble(solicitarTexto("Nuevo precio:", String.valueOf(producto.getPrecio())));
                String[] opciones = {"GR", "ML"};
                String tamaño = (String) JOptionPane.showInputDialog(null, "Nueva unidad:", "Unidad", JOptionPane.QUESTION_MESSAGE, null, opciones, producto.getTamaño());
                if (tamaño == null) return;
                int cantidad = Integer.parseInt(solicitarTexto("Nueva cantidad:", String.valueOf(producto.getCantidad())));
                int stock = Integer.parseInt(solicitarTexto("Nuevo stock:", String.valueOf(producto.getStock())));
                String proveedor = solicitarTexto("Nuevo proveedor:", producto.getProveedor());
                if (proveedor == null) return;
                producto.setNombre(nombre);
                producto.setPrecio(precio);
                producto.setTamaño(tamaño);
                producto.setCantidad(cantidad);
                producto.setStock(stock);
                producto.setProveedor(proveedor);
                boolean exitoDb = dbControlador.actualizarProducto(producto);
                if (exitoDb) {
                    listaProductos = leerProductosJSON(ARCHIVO_JSON);
                    for (int i = 0; i < listaProductos.size(); i++) {
                        if (listaProductos.get(i).getId().equals(id)) {
                            listaProductos.set(i, producto);
                            break;
                        }
                    }
                    guardarProductosJSON(listaProductos, ARCHIVO_JSON);
                    mostrarMensaje("Producto actualizado exitosamente en base de datos y JSON");
                } else {
                    mostrarError("Error al actualizar producto en la base de datos.");
                }
            } catch (NumberFormatException e) {
                mostrarError("Ingrese valores numéricos válidos.");
            }
        } else {
            mostrarError("Producto no encontrado en la base de datos.");
        }
    }
    public static productoModelo buscarProductoPorId(BigInteger id) {
        for (productoModelo producto : listaProductos) {
            if (producto.getId().equals(id)) {
                return producto;
            }
        }
        return null;
    }
    public static void guardarProductosJSON(List<productoModelo> productos, String archivo) {
        try (FileWriter writer = new FileWriter(archivo)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(productos, writer);
        } catch (IOException e) {
            mostrarError("Error al guardar los productos: " + e.getMessage());
        }
    }
    public static List<productoModelo> leerProductosJSON(String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            Gson gson = new Gson();
            List<productoModelo> productos = gson.fromJson(reader, new TypeToken<List<productoModelo>>() {}.getType());
            return (productos != null) ? productos : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    public static List<productoModelo> getListaProductos() {
        return listaProductos;
    }
    private static String solicitarTexto(String mensaje) {
        return JOptionPane.showInputDialog(mensaje);
    }
    private static String solicitarTexto(String mensaje, String valorPorDefecto) {
        return JOptionPane.showInputDialog(null, mensaje, valorPorDefecto);
    }
    private static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
    private static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}