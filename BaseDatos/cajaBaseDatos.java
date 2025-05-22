package BaseDatos;
import Modelo.cajaModelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
public class cajaBaseDatos {
    public static boolean insertarCajaInicio(cajaModelo caja) {
        String sql = "INSERT INTO caja (id_venta, abierto_caja, montoinicial_caja) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBaseDatos.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 0);
            ps.setString(2, caja.getFechaHoraInicio());
            ps.setDouble(3, caja.getMontoInicial());
            int filas = ps.executeUpdate();
            System.out.println("✅ Caja abierta registrada en base de datos.");
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar caja abierta: " + e.getMessage());
            return false;
        }
    }
    public static boolean insertarCajaCierre(cajaModelo caja, String fechaInicio) {
        String sql = "UPDATE caja SET cerrado_caja = ?, montofinal_caja = ? WHERE abierto_caja = ?";
        try (Connection conn = ConexionBaseDatos.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, LocalDateTime.now().toString());
            ps.setDouble(2, caja.getMontoActual());
            ps.setString(3, fechaInicio);
            int filas = ps.executeUpdate();
            System.out.println("✅ Caja cerrada actualizada en base de datos.");
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al cerrar caja en BD: " + e.getMessage());
            return false;
        }
    }
    public static void actualizarMontoActualCaja(String fechaHoraInicio, double nuevoMontoActual) {
        String sql = "UPDATE caja SET montofinal_caja = ? WHERE abierto_caja = ?";
        try (Connection conn = ConexionBaseDatos.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, nuevoMontoActual);
            stmt.setString(2, fechaHoraInicio);
            stmt.executeUpdate();
            System.out.println("✅ Monto actualizado correctamente en la base de datos.");
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar el monto final de la caja: " + e.getMessage());
        }
    }
}