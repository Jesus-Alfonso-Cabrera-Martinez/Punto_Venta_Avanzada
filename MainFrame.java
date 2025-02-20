package Programacion_Avanzada;
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
public class MainFrame extends JFrame{
	private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	public MainFrame() {
		super("Hola mundo");
		setLayout(new BorderLayout());
		toolbar = new Toolbar();
		textPanel= new TextPanel();
		formPanel = new FormPanel();
		toolbar.setStringListener(new StringListener(){
			public void textEmitted(String text) {
				textPanel.appendText(text);
			}
		});
		add(formPanel,BorderLayout.WEST);
		add(toolbar,BorderLayout.NORTH);
		add(textPanel,BorderLayout.CENTER);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
}