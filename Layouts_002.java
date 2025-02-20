package Programacion_Avanzada;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Layouts_002 {
    public static void main(String[] args) {
    	MarcoCalculadora marco = new MarcoCalculadora();
    	marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	marco.setVisible(true);
    }
}
class MarcoCalculadora extends JFrame{
	public MarcoCalculadora() {
		setTitle("Calculadora");
		setBounds(500,300,450,300);
		LaminaCalculadora lamina = new LaminaCalculadora();
		add (lamina);
	}
}
class LaminaCalculadora extends JPanel{
	private JPanel lamina2;
	private JButton pantalla;
	private boolean principio;
	private double resultado;
	private String UltimaOperacion;
	public LaminaCalculadora() {
		principio=true;
		setLayout (new BorderLayout());
		pantalla = new JButton("0");
		pantalla.setEnabled(false);
		add(pantalla,BorderLayout.NORTH);
		lamina2 = new JPanel();
		lamina2.setLayout(new GridLayout(4,4));
		ActionListener insertar= new InsertarNumero();
		ActionListener orden=new AccionOrden();
		PonerBoton("7",insertar);
		PonerBoton("8",insertar);
		PonerBoton("9",insertar);
		PonerBoton("/",orden);
		PonerBoton("4",insertar);
		PonerBoton("5",insertar);
		PonerBoton("6",insertar);
		PonerBoton("*",orden);
		PonerBoton("1",insertar);
		PonerBoton("2",insertar);
		PonerBoton("3",insertar);
		PonerBoton("-",orden);
		PonerBoton("0",insertar);
		PonerBoton(".",insertar);
		PonerBoton("+",orden);
		PonerBoton("=",orden);
		add(lamina2,BorderLayout.CENTER);
		UltimaOperacion="=";
	}
	private void PonerBoton(String rotulo, ActionListener oyente) {
		JButton boton = new JButton(rotulo);
		boton.addActionListener(oyente);
		lamina2.add(boton);
	}
	private class InsertarNumero implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String entrada=e.getActionCommand();
			if(principio) {
				pantalla.setText("");
				principio=false;
			}
			pantalla.setText(pantalla.getText()+entrada);
		}
		
	}
	private class AccionOrden implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String operacion=e.getActionCommand();
			calcular(Double.parseDouble(pantalla.getText()));
			UltimaOperacion=operacion;
			principio=true;
		}
		public void calcular (double x) {
			if(UltimaOperacion.equals("+")) {
				resultado+=x;
			}
			else if (UltimaOperacion.equals("=")) {
				resultado=x;
			}
			if(UltimaOperacion.equals("-")) {
				resultado-=x;
			}
			else if (UltimaOperacion.equals("=")) {
				resultado=x;
			}
			if(UltimaOperacion.equals("*")) {
				resultado*=x;
			}
			else if (UltimaOperacion.equals("=")) {
				resultado=x;
			}
			if(UltimaOperacion.equals("/")) {
				resultado/=x;
			}
			else if (UltimaOperacion.equals("=")) {
				resultado=x;
			}
			pantalla.setText("" + resultado);
		}
	}
}