package ui;

import dao.MedicamenteDAO;
import model.Medicament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RapoarteUI {
    public RapoarteUI() {
        JFrame frame = new JFrame("Raport Medicamente");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        MedicamenteDAO medicamenteDAO = new MedicamenteDAO();
        List<Medicament> raportMedicamente = medicamenteDAO.getRaportMedicamente();

        String[] columnNames = {"Denumire", "Data Expirare"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        for (Medicament medicament : raportMedicamente) {
            tableModel.addRow(new Object[]{medicament.getDenumire(), medicament.getDataExpirare()});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
