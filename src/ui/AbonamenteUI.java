package ui;

import dao.AbonamentDAO;
import model.Abonament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AbonamenteUI {
    private JFrame frame;
    private JTable abonamenteTable;
    private JTextField searchField;
    private AbonamentDAO abonamentDAO;

    public AbonamenteUI() {
        abonamentDAO = new AbonamentDAO();
        initialize();
        loadAbonamenteData("");
    }

    private void initialize() {
        frame = new JFrame("Abonamente");
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
        JButton searchButton = createStyledButton("Caută");
        searchButton.addActionListener(e -> loadAbonamenteData(searchField.getText()));
        searchPanel.add(new JLabel("Caută:", JLabel.RIGHT));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        abonamenteTable = new JTable();
        abonamenteTable.setRowHeight(25);
        abonamenteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(abonamenteTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista Abonamentelor"));

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton addButton = createLargeStyledButton("Adaugă Abonament");
        addButton.addActionListener(e -> openAddAbonamentByTelefonDialog());
        buttonPanel.add(addButton, gbc);

        gbc.gridy = 1;
        JButton backButton = createLargeStyledButton("Înapoi");
        backButton.addActionListener(e -> {
            frame.dispose();
            new MainUI();
        });
        buttonPanel.add(backButton, gbc);

        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    private JButton createLargeStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 50));
        return button;
    }

    private void loadAbonamenteData(String search) {
        List<Abonament> abonamente = abonamentDAO.getAbonamente(search);
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID Abonament", "ID Pacient", "Tip Abonament", "Data Începere", "Data Expirare"},
                0
        );

        for (Abonament abonament : abonamente) {
            model.addRow(new Object[]{
                    abonament.getIdAbonament(),
                    abonament.getIdPacient(),
                    abonament.getTipAbonament(),
                    abonament.getDataIncepere(),
                    abonament.getDataExpirare()
            });
        }

        abonamenteTable.setModel(model);
    }

    private void openAddAbonamentByTelefonDialog() {
        JDialog dialog = new JDialog(frame, "Adaugă Abonament", true);
        dialog.setSize(600, 400);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font textFieldFont = new Font("Arial", Font.PLAIN, 16);
        Dimension textFieldDimension = new Dimension(300, 35);

        JTextField telefonField = new JTextField();
        telefonField.setFont(textFieldFont);
        telefonField.setPreferredSize(textFieldDimension);

        JTextField tipAbonamentField = new JTextField();
        tipAbonamentField.setFont(textFieldFont);
        tipAbonamentField.setPreferredSize(textFieldDimension);

        JTextField dataIncepereField = new JTextField();
        dataIncepereField.setFont(textFieldFont);
        dataIncepereField.setPreferredSize(textFieldDimension);

        JTextField dataExpirareField = new JTextField();
        dataExpirareField.setFont(textFieldFont);
        dataExpirareField.setEditable(false);
        dataExpirareField.setPreferredSize(textFieldDimension);

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Număr Telefon:"), gbc);
        gbc.gridx = 1;
        dialog.add(telefonField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Tip Abonament:"), gbc);
        gbc.gridx = 1;
        dialog.add(tipAbonamentField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("Data Începere (Zi/Lună/An):"), gbc);
        gbc.gridx = 1;
        dialog.add(dataIncepereField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(new JLabel("Data Expirare (Zi/Lună/An):"), gbc);
        gbc.gridx = 1;
        dialog.add(dataExpirareField, gbc);

        JButton saveButton = createLargeStyledButton("Salvează");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String nrTelefon = telefonField.getText();
            String tipAbonament = tipAbonamentField.getText();
            String dataIncepere = dataIncepereField.getText();

            if (dataIncepere.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Introduceți data de început în formatul corect (Zi/Lună/An).", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String dataIncepereDB = convertToDatabaseDateFormat(dataIncepere);
            if (dataIncepereDB == null) {
                JOptionPane.showMessageDialog(dialog, "Data de început nu este în formatul corect (Zi/Lună/An).", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String dataExpirare = calculateExpiryDate(dataIncepere);
            if (dataExpirare != null) {
                dataExpirareField.setText(dataExpirare);
            } else {
                JOptionPane.showMessageDialog(dialog, "Eroare la calcularea datei de expirare.", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idGenerat = abonamentDAO.addAbonamentByNrTelefon(nrTelefon, tipAbonament, dataIncepereDB, convertToDatabaseDateFormat(dataExpirare));
            if (idGenerat != -1) {
                JOptionPane.showMessageDialog(dialog, "Abonament adăugat cu succes! ID: " + idGenerat);
                loadAbonamenteData("");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Eroare: Pacientul nu există.", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private String calculateExpiryDate(String startDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inputFormat.parse(startDate));
            calendar.add(Calendar.MONTH, 6); // Adăugăm 6 luni
            return inputFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String convertToDatabaseDateFormat(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.format(inputFormat.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
