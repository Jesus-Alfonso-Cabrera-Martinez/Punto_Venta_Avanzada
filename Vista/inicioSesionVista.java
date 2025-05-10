package Vista;
import Modelo.inicioSesionModelo;
import Controlador.inicioSesionControlador;
import javax.swing.*;
import java.awt.*;
class inicioSesionVista extends JFrame {
    private JTextField usuario;
    private JPasswordField contraseña;
    public inicioSesionVista() {
        setTitle("Login");
        setSize(420, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel titleLabel = new JLabel("Inicio de Sesión");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(etiqueta("Usuario:"), gbc);
        usuario = new JTextField(16);
        gbc.gridx = 1;
        panel.add(usuario, gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(etiqueta("Contraseña:"), gbc);
        contraseña = new JPasswordField(16);
        gbc.gridx = 1;
        panel.add(contraseña, gbc);
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botones.setBackground(new Color(40, 40, 40));
        JButton loginButton = botonInicioSesion("Entrar");
        JButton cancelButton = botonInicioSesion("Cancelar");
        loginButton.addActionListener(e -> verificarCredenciales());
        cancelButton.addActionListener(e -> dispose());
        botones.add(loginButton);
        botones.add(cancelButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(botones, gbc);
        add(panel);
        setVisible(true);
    }
    private JLabel etiqueta(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }
    private void verificarCredenciales() {
        String usuarioCredencial = usuario.getText();
        String contraseñaCredencial = new String(contraseña.getPassword());
        inicioSesionModelo empleado = inicioSesionControlador.validarUsuario(usuarioCredencial, contraseñaCredencial);
        if (empleado != null) {
            JOptionPane.showMessageDialog(this, "Inicio correcto.");
            new marcoVista(empleado);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private JButton botonInicioSesion(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 35));
        return button;
    }
}