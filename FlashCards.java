package Programacion_Avanzada;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
public class FlashCards {
    private JFrame frame;
    private JPanel panel;
    private JButton showButton, nextButton, loadButton, saveButton;
    private JTextArea textArea;
    private ArrayList<String> flashcards;
    private int currentIndex;
    public FlashCards() {
        frame = new JFrame("Flashcard App");
        panel = new JPanel();
        showButton = new JButton("Mostrar respuesta");
        nextButton = new JButton("Siguiente pregunta");
        loadButton = new JButton("Recargar flashcards");
        saveButton = new JButton("Guardar flashcards");
        textArea = new JTextArea(10, 20);
        flashcards = new ArrayList<>();
        currentIndex = 0;
        flashcards.add("¿Cual es la capital de Francia?\nParis");
        flashcards.add("¿Cuanto es 2+2?\n4");
        flashcards.add("¿Quien escribio Don Quijote De La Mancha?\nMiguel de Cervantes");
        textArea.setText(flashcards.get(currentIndex).split("\\n")[0]);
        showButton.addActionListener(e -> {
            String[] card = flashcards.get(currentIndex).split("\\n");
            if (textArea.getText().equals(card[0])) {
                textArea.setText(card[1]);
            }
        });
        nextButton.addActionListener(e -> {
            currentIndex = (currentIndex + 1) % flashcards.size();
            textArea.setText(flashcards.get(currentIndex).split("\\n")[0]);
        });
        loadButton.addActionListener(e -> loadFlashcards());
        saveButton.addActionListener(e -> saveFlashcards());
        panel.add(textArea);
        panel.add(showButton);
        panel.add(nextButton);
        panel.add(loadButton);
        panel.add(saveButton);
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private void loadFlashcards() {
        try (BufferedReader reader = new BufferedReader(new FileReader("flashcards.txt"))) {
            flashcards.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                flashcards.add(line);
            }
            currentIndex = 0;
            textArea.setText(flashcards.get(currentIndex).split("\\n")[0]);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error loading flashcards.");
        }
    }
    private void saveFlashcards() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("flashcards.txt"))) {
            for (String card : flashcards) {
                writer.write(card + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving flashcards.");
        }
    }
    public static void main(String[] args) {
        new FlashCards();
    }
}
