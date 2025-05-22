package BaseDatos;
import java.sql.*;
import java.util.ArrayList;
public class BaseDatos {
    private Connection conexion;
    public BaseDatos(Connection conexion) {
        this.conexion = conexion;
    }
    public BaseDatos() {
        this.conexion = ConexionBaseDatos.obtenerConexion();
    }
    public ArrayList<String[]> consultar(String tabla, String campos, String condicion) {
        ArrayList<String[]> resultados = new ArrayList<>();
        String sql = (campos == null) ? "SELECT * FROM " + tabla : "SELECT " + campos + " FROM " + tabla;
        if (condicion != null && !condicion.isEmpty()) {
            sql += " WHERE " + condicion;
        }
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumnas = rsmd.getColumnCount();
            while (rs.next()) {
                String[] fila = new String[numColumnas];
                for (int i = 1; i <= numColumnas; i++) {
                    fila[i - 1] = rs.getString(i);
                }
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al consultar registros: " + e.getMessage());
            System.err.println("   Consulta SQL: " + sql);
            if (e.getMessage().contains("syntax")) {
                System.err.println("   NOTA: Si estás usando SQL Server, recuerda que la sintaxis LIMIT no es soportada.");
                System.err.println("   Usa TOP en la sección SELECT en lugar de LIMIT al final de la consulta.");
            }
        }
        return resultados;
    }
    public boolean existe(String tabla, String condicion) {
        boolean enc = false;
        String sql = "SELECT TOP 1 1 FROM " + tabla + " WHERE " + condicion;

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            enc = rs.next();
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar existencia: " + e.getMessage());
        }
        return enc;
    }
    public boolean insertar(String tabla, String campos, String[] valores) {
        String sql = "INSERT INTO " + tabla + " (" + campos + ") VALUES (" + "?,".repeat(valores.length - 1) + "?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (int i = 0; i < valores.length; i++) {
                ps.setString(i + 1, valores[i]);
            }
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Registro insertado correctamente en " + tabla);
                return true;
            } else {
                System.out.println("⚠️ No se pudo insertar el registro en " + tabla);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar registro en " + tabla + ": " + e.getMessage());
            return false;
        }
    }
    public void eliminar(String tabla, String campo, String valor) {
        String sql = "DELETE FROM " + tabla + " WHERE " + campo + " = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, valor);
            int resultado = pstmt.executeUpdate();
            System.out.println(resultado > 0 ? "✅ Registros eliminados correctamente." : "⚠️ No se encontraron registros para eliminar.");
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar registros: " + e.getMessage());
        }
    }
    public boolean modificar(String tabla, String campoActualizar, String nuevoValor, String condicion) {
        String sql = "UPDATE " + tabla + " SET " + campoActualizar + " = ? WHERE " + condicion;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevoValor);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ " + filasAfectadas + " registro(s) actualizado(s) correctamente en " + tabla);
                return true;
            } else {
                System.out.println("⚠️ No se encontraron registros para actualizar en " + tabla + " con condición: " + condicion);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar registros en " + tabla + ": " + e.getMessage());
            return false;
        }
    }
    public boolean modificarMultiple(String tabla, String[] campos, String[] valores, String condicion) {
        if (campos.length != valores.length) {
            System.err.println("❌ Error: La cantidad de campos y valores debe ser igual");
            return false;
        }
        StringBuilder setClause = new StringBuilder();
        for (int i = 0; i < campos.length; i++) {
            if (i > 0) setClause.append(", ");
            setClause.append(campos[i]).append(" = ?");
        }
        String sql = "UPDATE " + tabla + " SET " + setClause + " WHERE " + condicion;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (int i = 0; i < valores.length; i++) {
                ps.setString(i + 1, valores[i]);
            }
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ " + filasAfectadas + " registro(s) actualizado(s) con múltiples campos en " + tabla);
                return true;
            } else {
                System.out.println("⚠️ No se encontraron registros para actualizar en " + tabla + " con condición: " + condicion);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar múltiples campos en " + tabla + ": " + e.getMessage());
            return false;
        }
    }
    public void cerrarConexion() {
        ConexionBaseDatos.cerrarConexion();
    }
    public boolean ejecutarSQL(String sql) {
        try (Statement stmt = conexion.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Consulta SQL ejecutada correctamente");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al ejecutar SQL: " + e.getMessage());
            System.err.println("   Consulta SQL: " + sql);
            return false;
        }
    }
}