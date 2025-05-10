package Vista;
import javax.swing.*;
import java.awt.*;
import Controlador.reporteControlador;
class reportesVista extends JPanel {
    public reportesVista() {
        setLayout(new GridLayout(3, 1, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        setBackground(new Color(245, 245, 245));
        add(crearBoton("Reporte de Ventas", e -> reporteControlador.generarReporteVentas()));
        add(crearBoton("Reporte de Productos", e -> reporteControlador.generarReporteProductos()));
        add(crearBoton("Reporte de Caja", e -> reporteControlador.generarReporteCaja()));
    }
    private JButton crearBoton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBackground(new Color(90, 90, 90));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        return btn;
    }
}