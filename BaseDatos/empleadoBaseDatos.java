package BaseDatos;
import Modelo.inicioSesionModelo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class empleadoBaseDatos {
    public List<inicioSesionModelo> obtenerEmpleados() {
        List<inicioSesionModelo> empleados = new ArrayList<>();
        String sql = "SELECT * FROM inicioSesion";
        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_inicioSesion");
                String usuario = rs.getString("usuario_inicioSesion");
                String contraseña = rs.getString("contraseña_inicioSesion");
                boolean esAdmin = rs.getBoolean("admin_inicioSesion");
                inicioSesionModelo empleado = new inicioSesionModelo(id, usuario, contraseña, esAdmin);
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los datos de los empleados: " + e.getMessage());
        }
        return empleados;
    }
    public boolean insertarEmpleado(inicioSesionModelo empleado) {
        String sql = "INSERT INTO inicioSesion (id_inicioSesion, usuario_inicioSesion, contraseña_inicioSesion, admin_inicioSesion) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, empleado.getId());
            stmt.setString(2, empleado.getUsuario());
            stmt.setString(3, empleado.getContraseña());
            stmt.setBoolean(4, empleado.esAdmin());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar empleado: " + e.getMessage());
            return false;
        }
    }
    public boolean eliminarEmpleado(int id) {
        String sql = "DELETE FROM inicioSesion WHERE id_inicioSesion = ?";
        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }
    public boolean actualizarEmpleado(inicioSesionModelo empleado) {
        String sql = "UPDATE inicioSesion SET usuario_inicioSesion = ?, contraseña_inicioSesion = ?, admin_inicioSesion = ? WHERE id_inicioSesion = ?";
        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, empleado.getUsuario());
            stmt.setString(2, empleado.getContraseña());
            stmt.setBoolean(3, empleado.esAdmin());
            stmt.setInt(4, empleado.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }
}