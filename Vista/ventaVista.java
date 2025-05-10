package Vista;
import javax.swing.*;
import java.awt.*;
import Controlador.ventaControlador;
class ventaVista extends JPanel {
    public ventaVista() {
        setLayout(new GridLayout(4, 1, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        setBackground(new Color(245, 245, 245));
        add(crearBoton("Seleccionar Producto", e -> ventaControlador.seleccionarProducto()));
        add(crearBoton("Calcular Total", e -> ventaControlador.calcularTotal()));
        add(crearBoton("Pago Venta", e -> ventaControlador.pagarVenta()));
        add(crearBoton("️Rembolso Venta", e -> ventaControlador.rembolsoVenta()));
    }
    private JButton crearBoton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBackground(new Color(90, 90, 90));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (action != null) btn.addActionListener(action);
        return btn;
    }
}