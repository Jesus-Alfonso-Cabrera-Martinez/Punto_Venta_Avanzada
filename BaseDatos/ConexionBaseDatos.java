package BaseDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionBaseDatos {
    private static final String URL = "jdbc:sqlserver://localhost\\DESKTOP-LRBE0GK:1433;databaseName=PuntoVenta;instance=SQLDEVELOPER;encrypt=false;trustServerCertificate=true;";
    private static final String USUARIO = "Jesus_Alfonso";
    private static final String CONTRASENA = "Papanatas882";
    public static Connection conexion = null;
    public static Connection obtenerConexion() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el controlador JDBC de SQL Server: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        obtenerConexion();
        cerrarConexion();
    }
}