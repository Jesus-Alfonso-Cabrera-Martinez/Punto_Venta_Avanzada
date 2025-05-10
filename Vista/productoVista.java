package Vista;
import Modelo.inicioSesionModelo;
import Controlador.productoControlador;
import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
class PanelProductos extends JPanel {
    private final inicioSesionModelo empleadoActual;
    public PanelProductos(inicioSesionModelo empleado) {
        this.empleadoActual = empleado;
        setLayout(new GridLayout(3, 1, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        setBackground(new Color(245, 245, 245));
        add(crearBoton("Agregar Producto", e -> productoControlador.registrarProducto(empleadoActual)));
        add(crearBoton("Eliminar Producto", e -> {
            BigInteger id = solicitarId("ID del producto a eliminar:");
            if (id != null) {
                productoControlador.eliminarProducto(empleadoActual, id);
            }
        }));
        add(crearBoton("Actualizar Producto", e -> {
            BigInteger id = solicitarId("ID del producto a actualizar:");
            if (id != null) {
                productoControlador.actualizarProducto(empleadoActual, id);
            }
        }));
    }
    private JButton crearBoton(String texto, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setBackground(new Color(60, 63, 65));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(200, 40));
        boton.addActionListener(accion);
        return boton;
    }
    private BigInteger solicitarId(String mensaje) {
        String idStr = JOptionPane.showInputDialog(this, mensaje);
        if (idStr == null) return null;
        try {
            return new BigInteger(idStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}