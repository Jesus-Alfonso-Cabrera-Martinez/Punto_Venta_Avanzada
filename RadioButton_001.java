package Programacion_Avanzada;
import java.awt.BorderLayout;

import javax.swing.*;
public class RadioButton_001 {
	public static void main (String[] args) {
		Marco_radio_sintaxis mimarco = new Marco_radio_sintaxis();
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
class Marco_radio_sintaxis extends JFrame{
	public Marco_radio_sintaxis() {
		setVisible(true);
		setBounds(550,300,500,300);
		Lamina_radio_sintaxis milamina = new Lamina_radio_sintaxis();
		add(milamina);
	}
}
class Lamina_radio_sintaxis extends JPanel{
	public Lamina_radio_sintaxis() {
		setLayout(new BorderLayout());
		ButtonGroup migrupo1 = new ButtonGroup();
		JRadioButton boton1 = new JRadioButton("Azul",false);
		JRadioButton boton2 = new JRadioButton("Rojo",true);
		JRadioButton boton3 = new JRadioButton("Verde",false);
		JRadioButton boton4 = new JRadioButton("Amarillo",false);
		migrupo1.add(boton1);
		migrupo1.add(boton2);
		migrupo1.add(boton3);
		migrupo1.add(boton4);
		JPanel radio_lamina1 = new JPanel();
		radio_lamina1.add(boton1);
		radio_lamina1.add(boton2);
		radio_lamina1.add(boton3);
		radio_lamina1.add(boton4);
		add(radio_lamina1,BorderLayout.NORTH);
		ButtonGroup migrupo2 = new ButtonGroup();
		JRadioButton boton5 = new JRadioButton("Masculino",false);
		JRadioButton boton6 = new JRadioButton("Femenino",true);
		migrupo2.add(boton5);
		migrupo2.add(boton6);
		JPanel radio_lamina2 = new JPanel();
		radio_lamina2.add(boton5);
		radio_lamina2.add(boton6);
		add(radio_lamina2,BorderLayout.SOUTH);
	}
}