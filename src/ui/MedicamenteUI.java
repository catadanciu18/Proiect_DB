package ui;

import dao.MedicamenteDAO;
import model.Medicament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MedicamenteUI {
    private JFrame frame;

    public MedicamenteUI() {
        frame = new JFrame("Medicamente");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"ID", "Denumire", "Concentrație", "Producător", "Data Expirare"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        MedicamenteDAO medicamenteDAO = new MedicamenteDAO();
        List<Medicament> medicamente = medicamenteDAO.getMedicamente("");
        for (Medicament medicament : medicamente) {
            tableModel.addRow(new Object[]{
                    medicament.getIdMedicament(),
                    medicament.getDenumire(),
                    medicament.getConcentratie(),
                    medicament.getProducator(),
                    medicament.getDataExpirare()
            });
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20)); // 2x2 grilă cu spații
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100)); // Margini generoase pentru centrare

        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Color buttonColor = new Color(70, 130, 180);
        Color textColor = Color.WHITE;

        JButton addButton = new JButton("Adaugă Medicament");
        formatButton(addButton, buttonFont, buttonColor, textColor);
        addButton.addActionListener(e -> openAddMedicamentDialog(medicamenteDAO, tableModel));

        JButton deleteButton = new JButton("Șterge Medicament");
        formatButton(deleteButton, buttonFont, buttonColor, textColor);
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                boolean success = medicamenteDAO.deleteMedicament(id);
                if (success) {
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(frame, "Medicament șters cu succes!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Eroare la ștergerea medicamentului.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Selectați un medicament pentru a-l șterge.");
            }
        });


        JButton reportButton = new JButton("Rapoarte");
        formatButton(reportButton, buttonFont, buttonColor, textColor);
        reportButton.addActionListener(e -> new RapoarteUI());


        JButton backButton = new JButton("Înapoi");
        formatButton(backButton, buttonFont, buttonColor, textColor);
        backButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(backButton);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void openAddMedicamentDialog(MedicamenteDAO medicamenteDAO, DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(frame, "Adaugă Medicament", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        Font inputFont = new Font("Arial", Font.PLAIN, 16);

        JTextField denumireField = new JTextField(20);
        denumireField.setMinimumSize(new Dimension(350, 40));
        denumireField.setPreferredSize(new Dimension(350, 40));
        denumireField.setFont(inputFont);

        JTextField concentratieField = new JTextField(20);
        concentratieField.setMinimumSize(new Dimension(350, 40));
        concentratieField.setPreferredSize(new Dimension(350, 40));
        concentratieField.setFont(inputFont);

        JTextField producatorField = new JTextField(20);
        producatorField.setMinimumSize(new Dimension(350, 40));
        producatorField.setPreferredSize(new Dimension(350, 40));
        producatorField.setFont(inputFont);

        JTextField dataExpirareField = new JTextField(20);
        dataExpirareField.setMinimumSize(new Dimension(350, 40));
        dataExpirareField.setPreferredSize(new Dimension(350, 40));
        dataExpirareField.setFont(inputFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Denumire:"), gbc);
        gbc.gridx = 1;
        dialog.add(denumireField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Concentrație:"), gbc);
        gbc.gridx = 1;
        dialog.add(concentratieField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("Producător:"), gbc);
        gbc.gridx = 1;
        dialog.add(producatorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(new JLabel("Data Expirare (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        dialog.add(dataExpirareField, gbc);

        JButton saveButton = new JButton("Salvează");
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.setBackground(new Color(34, 139, 34)); // Verde
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(200, 50));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String denumire = denumireField.getText();
            String concentratie = concentratieField.getText();
            String producator = producatorField.getText();
            String dataExpirare = dataExpirareField.getText();

            Medicament medicament = new Medicament(0, denumire, concentratie, producator, dataExpirare);
            int id = medicamenteDAO.addMedicament(medicament);

            if (id > 0) {
                tableModel.addRow(new Object[]{id, denumire, concentratie, producator, dataExpirare});
                JOptionPane.showMessageDialog(dialog, "Medicament adăugat cu succes!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Eroare la adăugarea medicamentului.");
            }
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void formatButton(JButton button, Font font, Color backgroundColor, Color textColor) {
        button.setFont(font);
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 60));
    }
}
