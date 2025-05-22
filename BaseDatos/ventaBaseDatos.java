package BaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ventaBaseDatos {
    private final BaseDatos base;
    public ventaBaseDatos() {
        this.base = new BaseDatos();
    }
    public boolean insertarVenta(int idVenta, String fechaVenta) {
        String tabla = "ventas";
        String campos = "id_venta, fecha_venta";
        String[] valores = {String.valueOf(idVenta), fechaVenta};
        return base.insertar(tabla, campos, valores);
    }
    public boolean insertarDetalleVenta(int idDetalle, int idVenta, long idProducto, int cantidad, double precio) {
        String tabla = "detalle_venta";
        String campos = "id_detalle, id_venta, id_producto, cantidad_producto, precio_producto";
        String[] valores = {
                String.valueOf(idDetalle),
                String.valueOf(idVenta),
                String.valueOf(idProducto),
                String.valueOf(cantidad),
                String.valueOf(precio),
        };
        return base.insertar(tabla, campos, valores);
    }
    public int obtenerUltimoIdDetalle() {
        int idMaximo = 0;
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBaseDatos.obtenerConexion();
            String sql = "SELECT MAX(id_detalle) FROM detalle_venta";
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                idMaximo = resultado.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (resultado != null) resultado.close(); } catch (SQLException e) { }
            try { if (sentencia != null) sentencia.close(); } catch (SQLException e) { }
        }
        return idMaximo;
    }
    public ArrayList<String[]> obtenerDetallePorVenta(int idVenta) {
        String condicion = "id_venta = " + idVenta;
        return base.consultar("detalle_venta", null, condicion);
    }
    public ArrayList<String[]> obtenerVenta(int idVenta) {
        String condicion = "id_venta = " + idVenta;
        return base.consultar("ventas", null, condicion);
    }
    public void eliminarVenta(int idVenta) {
        base.eliminar("detalle_venta", "id_venta", String.valueOf(idVenta));
        base.eliminar("ventas", "id_venta", String.valueOf(idVenta));
    }
    public boolean existeVenta(int idVenta) {
        return base.existe("ventas", "id_venta = " + idVenta);
    }
    public void cerrar() {
        base.cerrarConexion();
    }
}