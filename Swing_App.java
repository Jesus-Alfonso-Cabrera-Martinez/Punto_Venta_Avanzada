package Programacion_Avanzada;
import javax.swing.*;

public class Swing_App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new MainFrame();
            }
        });
    }
}
