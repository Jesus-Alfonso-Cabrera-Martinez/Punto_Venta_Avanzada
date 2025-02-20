package Programacion_Avanzada;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Layouts_001 {
    public static void main(String[] args) {
        Marco_Loyout marco = new Marco_Loyout();
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setVisible(true);
    }
}
class Marco_Loyout extends JFrame {
    public Marco_Loyout() {
        setTitle("Prueba de acciones");
        setBounds(600, 350, 600, 300);
        Panel_Layout lamina = new Panel_Layout();
        Panel_Layout2 lamina2 = new Panel_Layout2();
        add(lamina,BorderLayout.NORTH);
        add(lamina2,BorderLayout.SOUTH);
    }
}
class Panel_Layout extends JPanel {
    public Panel_Layout() {
    	setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new JButton("Amarillo"));
        add(new JButton("Rojo"));
    }
}
class Panel_Layout2 extends JPanel{
	public Panel_Layout2() {
		setLayout(new BorderLayout());
        add(new JButton("Azul"),BorderLayout.WEST);
        add(new JButton("Verde"),BorderLayout.EAST);
        add(new JButton("Negro"),BorderLayout.CENTER);
	}
}
