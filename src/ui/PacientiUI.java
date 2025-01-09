package ui;

import dao.PacientiDAO;
import model.Pacient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PacientiUI {
    private JFrame frame;
    private JTable pacientiTable;
    private JTextField searchField;
    private PacientiDAO pacientiDAO;

    private final Color primaryColor = new Color(70, 130, 180);
    private final Color buttonTextColor = Color.WHITE;

    public PacientiUI() {
        pacientiDAO = new PacientiDAO();
        initialize();
        loadPacientiData("");
    }

    private void initialize() {
        frame = new JFrame("Gestionare Pacienți");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        Font fontGeneral = new Font("Arial", Font.PLAIN, 14);
        UIManager.put("Label.font", fontGeneral);
        UIManager.put("Button.font", fontGeneral);
        UIManager.put("Table.font", fontGeneral);
        UIManager.put("TableHeader.font", new Font("Arial", Font.BOLD, 14));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Caută");
        styleButton(searchButton, primaryColor, buttonTextColor);
        searchButton.setPreferredSize(new Dimension(150, 50));
        searchButton.addActionListener(e -> loadPacientiData(searchField.getText()));
        searchPanel.add(new JLabel("Caută:", JLabel.RIGHT));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        pacientiTable = new JTable();
        pacientiTable.setRowHeight(25);
        pacientiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(pacientiTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista Pacienților"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Centrare și spațiere
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Adaugă Pacient");
        styleButton(addButton, primaryColor, buttonTextColor);
        addButton.setPreferredSize(new Dimension(200, 60));
        addButton.addActionListener(e -> openAddPacientDialog());

        JButton deleteButton = new JButton("Șterge Pacient");
        styleButton(deleteButton, primaryColor, buttonTextColor);
        deleteButton.setPreferredSize(new Dimension(200, 60));
        deleteButton.addActionListener(e -> deleteSelectedPacient());

        JButton backButton = new JButton("Înapoi");
        styleButton(backButton, primaryColor, buttonTextColor);
        backButton.setPreferredSize(new Dimension(200, 60));
        backButton.addActionListener(e -> {
            frame.dispose();
            new MainUI();
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    private void loadPacientiData(String search) {
        List<Pacient> pacienti = pacientiDAO.getPacienti(search);
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nume", "Prenume", "Data Nașterii", "Nr. Telefon", "Adresă"}, 0);

        for (Pacient pacient : pacienti) {
            model.addRow(new Object[]{
                    pacient.getIdPacient(),
                    pacient.getNume(),
                    pacient.getPrenume(),
                    pacient.getDataNasterii(),
                    pacient.getNrTelefon(),
                    pacient.getAdresa()
            });
        }

        pacientiTable.setModel(model);
    }

    private void openAddPacientDialog() {
        JDialog dialog = new JDialog(frame, "Adaugă Pacient", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField numeField = new JTextField(20);
        JTextField prenumeField = new JTextField(20);
        JTextField dataNasteriiField = new JTextField(20);
        JTextField telefonField = new JTextField(20);
        JTextField adresaField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Nume:"), gbc);
        gbc.gridx = 1;
        dialog.add(numeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Prenume:"), gbc);
        gbc.gridx = 1;
        dialog.add(prenumeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("Data Nașterii (An/Lună/Zi):"), gbc);
        gbc.gridx = 1;
        dialog.add(dataNasteriiField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(new JLabel("Nr. Telefon:"), gbc);
        gbc.gridx = 1;
        dialog.add(telefonField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        dialog.add(new JLabel("Adresă:"), gbc);
        gbc.gridx = 1;
        dialog.add(adresaField, gbc);

        JButton saveButton = new JButton("Salvează");
        styleButton(saveButton, new Color(34, 139, 34), buttonTextColor);
        saveButton.setPreferredSize(new Dimension(200, 60));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        dialog.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            Pacient pacient = new Pacient(
                    0,
                    numeField.getText(),
                    prenumeField.getText(),
                    dataNasteriiField.getText(),
                    telefonField.getText(),
                    adresaField.getText()
            );
            int idGenerat = pacientiDAO.addPacient(pacient);
            if (idGenerat != -1) {
                JOptionPane.showMessageDialog(dialog, "Pacient adăugat cu succes! ID: " + idGenerat);
                loadPacientiData("");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Eroare la adăugare.");
            }
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void deleteSelectedPacient() {
        int selectedRow = pacientiTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Selectați un pacient pentru a-l șterge.");
            return;
        }

        int idPacient = (int) pacientiTable.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Sigur doriți să ștergeți acest pacient?",
                "Confirmare Ștergere",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = pacientiDAO.deletePacient(idPacient);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Pacient șters cu succes!");
                loadPacientiData("");
            } else {
                JOptionPane.showMessageDialog(frame, "Eroare la ștergere.");
            }
        }
    }
}
