package Programacion_Avanzada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class LoanAssistant extends JFrame {
    private JTextField balanceTextField, interestTextField, monthsTextField, paymentTextField;
    private JTextArea analysisTextArea;
    private JButton computeButton, newLoanButton, monthsButton, paymentButton, exitButton;
    private boolean computePayment;
    private Color lightYellow = new Color(255, 255, 128);

    public LoanAssistant() {
        setTitle("Asistente de Préstamos");
        setResizable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        Font myFont = new Font("Arial", Font.PLAIN, 16);
        
        JLabel balanceLabel = new JLabel("Saldo del Préstamo");
        balanceLabel.setFont(myFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(balanceLabel, gbc);

        balanceTextField = new JTextField();
        balanceTextField.setPreferredSize(new Dimension(100, 25));
        balanceTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        balanceTextField.setFont(myFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 0;
        add(balanceTextField, gbc);
        
        JLabel interestLabel = new JLabel("Tasa de Interés");
        interestLabel.setFont(myFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        add(interestLabel, gbc);

        interestTextField = new JTextField();
        interestTextField.setPreferredSize(new Dimension(100, 25));
        interestTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        interestTextField.setFont(myFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 1;
        add(interestTextField, gbc);
        
        JLabel monthsLabel = new JLabel("Número de Pagos");
        monthsLabel.setFont(myFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        add(monthsLabel, gbc);

        monthsTextField = new JTextField();
        monthsTextField.setPreferredSize(new Dimension(100, 25));
        monthsTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        monthsTextField.setFont(myFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 2;
        add(monthsTextField, gbc);
        
        JLabel paymentLabel = new JLabel("Pago Mensual");
        paymentLabel.setFont(myFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        add(paymentLabel, gbc);

        paymentTextField = new JTextField();
        paymentTextField.setPreferredSize(new Dimension(100, 25));
        paymentTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        paymentTextField.setFont(myFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 3;
        add(paymentTextField, gbc);
        
        computeButton = new JButton("Calcular Pago Mensual");
        computeButton.addActionListener(e -> calcularPagoMensual());
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(computeButton, gbc);
        
        newLoanButton = new JButton("Nuevo Análisis de Préstamo");
        newLoanButton.setEnabled(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(newLoanButton, gbc);
        
        analysisTextArea = new JTextArea(10, 30);
        analysisTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        analysisTextArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        analysisTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(analysisTextArea);
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 5;
        add(scrollPane, gbc);
        
        exitButton = new JButton("Salir");
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 5;
        add(exitButton, gbc);
        
        exitButton.addActionListener(e -> System.exit(0));
        
        pack();
        setLocationRelativeTo(null);
    }

    private void calcularPagoMensual() {
        try {
            double balance = Double.parseDouble(balanceTextField.getText());
            double interestRate = Double.parseDouble(interestTextField.getText());
            int months = Integer.parseInt(monthsTextField.getText());
            
            double monthlyInterest = interestRate / 1200;
            double multiplier = Math.pow(1 + monthlyInterest, months);
            double payment = balance * monthlyInterest * multiplier / (multiplier - 1);
            
            paymentTextField.setText(new DecimalFormat("0.00").format(payment));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoanAssistant().setVisible(true));
    }
}
