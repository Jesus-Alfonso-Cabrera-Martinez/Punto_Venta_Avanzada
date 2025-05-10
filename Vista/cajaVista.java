package Vista;
import Controlador.cajaControlador;
import Modelo.inicioSesionModelo;
import javax.swing.*;
import java.awt.*;
class PanelCaja extends JPanel {
    private inicioSesionModelo empleadoActual;
    public PanelCaja(inicioSesionModelo empleadoActual) {
        this.empleadoActual = empleadoActual;
        setLayout(new GridLayout(3, 1, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        setBackground(new Color(245, 245, 245));
        add(crearBoton("Iniciar Caja", e -> cajaControlador.inicioCaja()));
        add(crearBoton("Cerrar Caja", e -> cajaControlador.cerrarCaja()));
        add(crearBoton("Rembolso Caja", e -> {cajaControlador.rembolsoCaja(empleadoActual);}));
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