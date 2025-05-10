package Vista;
import Modelo.inicioSesionModelo;
import javax.swing.*;
import java.awt.*;
public class marcoVista extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel mainPanel = new JPanel(layout);
    public marcoVista(inicioSesionModelo empleado) {
        setTitle("Punto de Venta");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(35, 35, 35));
        sidePanel.setPreferredSize(new Dimension(180, 0));
        JLabel titulo = new JLabel("Hola, " + empleado.getUsuario());
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidePanel.add(titulo);
        sidePanel.add(botonNavegacion("Productos", e -> cambiarVista("productos")));
        sidePanel.add(Box.createVerticalStrut(15));
        sidePanel.add(botonNavegacion("Ventas", e -> cambiarVista("ventas")));
        sidePanel.add(Box.createVerticalStrut(15));
        sidePanel.add(botonNavegacion("Caja", e -> cambiarVista("caja")));
        sidePanel.add(Box.createVerticalStrut(15));
        sidePanel.add(botonNavegacion("Reportes", e -> cambiarVista("reportes")));
        registrarVista("productos", new Vista.PanelProductos(empleado));
        registrarVista("ventas", new Vista.ventaVista());
        registrarVista("caja", new Vista.PanelCaja(empleado));
        registrarVista("reportes", new Vista.reportesVista());
        mainPanel.setBackground(new Color(9, 9, 9));
        add(sidePanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        cambiarVista("productos");
        setVisible(true);
    }
    private JButton botonNavegacion(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setBackground(new Color(55, 55, 55));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(action);
        return btn;
    }
    private void registrarVista(String nombre, JPanel panel) {
        mainPanel.add(panel, nombre);
    }
    private void cambiarVista(String nombre) {
        layout.show(mainPanel, nombre);
    }
}