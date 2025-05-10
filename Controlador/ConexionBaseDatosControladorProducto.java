package Controlador;
import Modelo.ConexionBaseDatosModelo;
import Modelo.productoModelo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ConexionBaseDatosControladorProducto {
    public List<productoModelo> obtenerProductos() {
        List<productoModelo> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (Connection conexion = ConexionBaseDatosModelo.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                String nombre = rs.getString("nombre_producto");
                BigInteger id = rs.getBigDecimal("id_producto").toBigInteger();
                double precio = rs.getDouble("precio_producto");
                String tamaño = rs.getString("tamaño_producto");
                int cantidad = rs.getInt("cantidad_producto");
                int stock = rs.getInt("stock_producto");
                String proveedor = rs.getString("proveedor_producto");

                productoModelo producto = new productoModelo(nombre, id, precio, tamaño, cantidad, stock, proveedor);
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los datos del producto: " + e.getMessage());
        }
        return productos;
    }
    public boolean insertarProducto(productoModelo producto) {
        String sql = "INSERT INTO Producto (id_producto, nombre_producto, precio_producto, tamaño_producto, cantidad_producto, stock_producto, proveedor_producto) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBaseDatosModelo.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, producto.getId().longValue());
            stmt.setString(2, producto.getNombre());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setString(4, producto.getTamaño());
            stmt.setInt(5, producto.getCantidad());
            stmt.setInt(6, producto.getStock());
            stmt.setString(7, producto.getProveedor());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }
    public boolean eliminarProducto(BigInteger id) {
        String sql = "DELETE FROM Producto WHERE id_producto = ?";
        try (Connection conexion = ConexionBaseDatosModelo.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setBigDecimal(1, new BigDecimal(id));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    public boolean actualizarProducto(productoModelo producto) {
        String sql = "UPDATE Producto SET nombre_producto = ?, precio_producto = ?, tamaño_producto = ?, cantidad_producto = ?, stock_producto = ?, proveedor_producto = ? WHERE id_producto = ?";
        try (Connection conexion = ConexionBaseDatosModelo.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setString(3, producto.getTamaño());
            stmt.setInt(4, producto.getCantidad());
            stmt.setInt(5, producto.getStock());
            stmt.setString(6, producto.getProveedor());
            stmt.setBigDecimal(7, new BigDecimal(producto.getId()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }
}