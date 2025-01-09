package dao;

import model.Medicament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamenteDAO {

    public List<Medicament> getRaportMedicamente() {
        List<Medicament> raportMedicamente = new ArrayList<>();
        String sql = "SELECT denumire, data_expirare FROM Medicamente";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medicament medicament = new Medicament(
                        0, // ID-ul nu este necesar aici
                        rs.getString("denumire"),
                        null, // Concentratia nu este relevantă
                        null, // Producătorul nu este necesar
                        rs.getString("data_expirare")
                );
                raportMedicamente.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return raportMedicamente;
    }
    public int addMedicament(Medicament medicament) {
        String sql = "INSERT INTO Medicamente (denumire, concentratie, producator, data_expirare) VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, medicament.getDenumire());
            stmt.setString(2, medicament.getConcentratie());
            stmt.setString(3, medicament.getProducator());
            stmt.setString(4, medicament.getDataExpirare());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public List<Medicament> getMedicamente(String search) {
        List<Medicament> medicamente = new ArrayList<>();
        String sql = "SELECT * FROM Medicamente WHERE denumire LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + search + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Medicament medicament = new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("denumire"),
                        rs.getString("concentratie"),
                        rs.getString("producator"),
                        rs.getString("data_expirare")
                );
                medicamente.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamente;
    }

    public boolean deleteMedicament(int idMedicament) {
        String sql = "DELETE FROM Medicamente WHERE id_medicament = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedicament);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

}
