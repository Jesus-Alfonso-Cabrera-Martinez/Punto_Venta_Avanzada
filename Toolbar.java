package Programacion_Avanzada;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Toolbar extends JPanel implements ActionListener{
	private JButton helloButton;
	private JButton goodbyeButton;
	private StringListener textListener;
	public Toolbar() {
		setBorder(BorderFactory.createEtchedBorder());
		helloButton = new JButton("Hola");
		goodbyeButton = new JButton("Adios");
		helloButton.addActionListener(this);
		goodbyeButton.addActionListener(this);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(helloButton);
		add(goodbyeButton);
	}
	public void setStringListener(StringListener listener) {
		this.textListener = listener;
	}
	public void actionPerformed(ActionEvent e) {	
		JButton clicked = (JButton)e.getSource();
		if(clicked==helloButton){
			if(textListener!=null) {
				textListener.textEmitted("Hola\n");
			}
		}else if (clicked==goodbyeButton) {
			if(textListener!=null) {
				textListener.textEmitted("Adios\n");
			}
		}
	}
}