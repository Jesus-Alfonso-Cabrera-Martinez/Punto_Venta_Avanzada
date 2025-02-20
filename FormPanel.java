package Programacion_Avanzada;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
public class FormPanel extends JPanel{
	
	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okBtn;
	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		nameLabel=new JLabel("Nombre:");
		occupationLabel = new JLabel("Ocupacion:");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		okBtn = new JButton("OK");
		Border innerBorder = BorderFactory.createTitledBorder("Añadir persona");
		Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(innerBorder,outerBorder));
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		//PRIMERA FILA//
		gc.weightx = 0;
		gc.weighty = 0.1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(nameLabel,gc);
		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets= new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField,gc);
		//SEGUNDA FILA//
		gc.weightx = 0;
		gc.weighty = 0.1;
		gc.gridy = 1;
		gc.gridx = 0;
		gc.insets = new Insets(0,0,0,5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(occupationLabel,gc);
		gc.gridy = 1;
		gc.gridx = 1;
		gc.insets= new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField,gc);
		//TERCERA FILA//
		gc.weightx = 0;
		gc.weighty = 2.0;
		gc.gridy = 2;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets= new Insets(0,0,0,0);
		add(okBtn,gc);
		
	}
}
