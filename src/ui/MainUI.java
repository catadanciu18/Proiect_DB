package ui;

import javax.swing.*;
import java.awt.*;

public class MainUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Farmacie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));


        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Color buttonColor = new Color(70, 130, 180);
        Color textColor = Color.WHITE;


        JButton pacientiButton = new JButton("Pacienți");
        pacientiButton.setFont(buttonFont);
        pacientiButton.setBackground(buttonColor);
        pacientiButton.setForeground(textColor);
        pacientiButton.setFocusPainted(false);
        pacientiButton.setPreferredSize(new Dimension(200, 60));
        pacientiButton.addActionListener(e -> new PacientiUI());

        JButton medicamenteButton = new JButton("Medicamente");
        medicamenteButton.setFont(buttonFont);
        medicamenteButton.setBackground(buttonColor);
        medicamenteButton.setForeground(textColor);
        medicamenteButton.setFocusPainted(false);
        medicamenteButton.setPreferredSize(new Dimension(200, 60));
        medicamenteButton.addActionListener(e -> new MedicamenteUI());

        JButton abonamenteButton = new JButton("Abonamente");
        abonamenteButton.setFont(buttonFont);
        abonamenteButton.setBackground(buttonColor);
        abonamenteButton.setForeground(textColor);
        abonamenteButton.setFocusPainted(false);
        abonamenteButton.setPreferredSize(new Dimension(200, 60));
        abonamenteButton.addActionListener(e -> new AbonamenteUI());



        buttonPanel.add(pacientiButton);
        buttonPanel.add(medicamenteButton);
        buttonPanel.add(abonamenteButton);

        frame.add(buttonPanel);
        frame.setVisible(true);
    }
}
