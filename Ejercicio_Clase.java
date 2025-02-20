package Programacion_Avanzada;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Ejercicio_Clase extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio_Clase frame = new Ejercicio_Clase();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ejercicio_Clase() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("Universidad Autonoma  de Tamaulipas");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 5;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Profesores Disponibles");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Salones Disponibles");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 1;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Materias Disponiibles");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 1;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Alumnos Disponibles");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 3;
		gbc_lblNewLabel_4.gridy = 1;
		contentPane.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1_1.gridx = 4;
		gbc_lblNewLabel_1_1.gridy = 1;
		contentPane.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		contentPane.add(comboBox, gbc_comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 2;
		contentPane.add(comboBox_1, gbc_comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 2;
		gbc_comboBox_2.gridy = 2;
		contentPane.add(comboBox_2, gbc_comboBox_2);
		
		JComboBox comboBox_2_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_2_1 = new GridBagConstraints();
		gbc_comboBox_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2_1.gridx = 3;
		gbc_comboBox_2_1.gridy = 2;
		contentPane.add(comboBox_2_1, gbc_comboBox_2_1);
		
		JButton btnNewButton = new JButton("Agregar Profesores");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Agregar Salones");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 3;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnGruposAsignados = new JButton("Agregar Materias");
		btnGruposAsignados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnGruposAsignados = new GridBagConstraints();
		gbc_btnGruposAsignados.insets = new Insets(0, 0, 5, 5);
		gbc_btnGruposAsignados.gridx = 2;
		gbc_btnGruposAsignados.gridy = 3;
		contentPane.add(btnGruposAsignados, gbc_btnGruposAsignados);
		
		JButton btnNewButton_2 = new JButton("Agregar Alumnos");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 3;
		gbc_btnNewButton_2.gridy = 3;
		contentPane.add(btnNewButton_2, gbc_btnNewButton_2);
		
		JButton btnModificarProfesores = new JButton("Modificar Profesores");
		btnModificarProfesores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnModificarProfesores = new GridBagConstraints();
		gbc_btnModificarProfesores.insets = new Insets(0, 0, 5, 5);
		gbc_btnModificarProfesores.gridx = 0;
		gbc_btnModificarProfesores.gridy = 4;
		contentPane.add(btnModificarProfesores, gbc_btnModificarProfesores);
		
		JButton btnModificarSalones = new JButton("Modificar Salones");
		GridBagConstraints gbc_btnModificarSalones = new GridBagConstraints();
		gbc_btnModificarSalones.insets = new Insets(0, 0, 5, 5);
		gbc_btnModificarSalones.gridx = 1;
		gbc_btnModificarSalones.gridy = 4;
		contentPane.add(btnModificarSalones, gbc_btnModificarSalones);
		
		JButton btnModificarMaterias = new JButton("Modificar Materias");
		GridBagConstraints gbc_btnModificarMaterias = new GridBagConstraints();
		gbc_btnModificarMaterias.insets = new Insets(0, 0, 5, 5);
		gbc_btnModificarMaterias.gridx = 2;
		gbc_btnModificarMaterias.gridy = 4;
		contentPane.add(btnModificarMaterias, gbc_btnModificarMaterias);
		
		JButton btnModificarAlumnos = new JButton("Modificar Alumnos");
		GridBagConstraints gbc_btnModificarAlumnos = new GridBagConstraints();
		gbc_btnModificarAlumnos.insets = new Insets(0, 0, 5, 5);
		gbc_btnModificarAlumnos.gridx = 3;
		gbc_btnModificarAlumnos.gridy = 4;
		contentPane.add(btnModificarAlumnos, gbc_btnModificarAlumnos);
		
		JButton btnEliminarProfesoresProfesores = new JButton("Eliminar Profesores");
		GridBagConstraints gbc_btnEliminarProfesoresProfesores = new GridBagConstraints();
		gbc_btnEliminarProfesoresProfesores.insets = new Insets(0, 0, 5, 5);
		gbc_btnEliminarProfesoresProfesores.gridx = 0;
		gbc_btnEliminarProfesoresProfesores.gridy = 5;
		contentPane.add(btnEliminarProfesoresProfesores, gbc_btnEliminarProfesoresProfesores);
		
		JButton btnNewButton_1_1 = new JButton("Eliminar Salones");
		GridBagConstraints gbc_btnNewButton_1_1 = new GridBagConstraints();
		gbc_btnNewButton_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1_1.gridx = 1;
		gbc_btnNewButton_1_1.gridy = 5;
		contentPane.add(btnNewButton_1_1, gbc_btnNewButton_1_1);
		
		JButton btnAgregarEliminar = new JButton("Eliminar Materias");
		GridBagConstraints gbc_btnAgregarEliminar = new GridBagConstraints();
		gbc_btnAgregarEliminar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAgregarEliminar.gridx = 2;
		gbc_btnAgregarEliminar.gridy = 5;
		contentPane.add(btnAgregarEliminar, gbc_btnAgregarEliminar);
		
		JButton btnNewButton_2_1 = new JButton("Eliminar Alumnos");
		GridBagConstraints gbc_btnNewButton_2_1 = new GridBagConstraints();
		gbc_btnNewButton_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2_1.gridx = 3;
		gbc_btnNewButton_2_1.gridy = 5;
		contentPane.add(btnNewButton_2_1, gbc_btnNewButton_2_1);
	}

}