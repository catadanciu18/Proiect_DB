package dao;

import model.Pacient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacientiDAO {

    public int addPacient(Pacient pacient) {
        String sql = "INSERT INTO Pacienti (nume, prenume, data_nasterii, nr_telefon, adresa) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pacient.getNume());
            stmt.setString(2, pacient.getPrenume());
            stmt.setString(3, pacient.getDataNasterii());
            stmt.setString(4, pacient.getNrTelefon());
            stmt.setString(5, pacient.getAdresa());

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

    public List<Pacient> getPacienti(String search) {
        List<Pacient> pacienti = new ArrayList<>();
        String sql = "SELECT * FROM Pacienti " +
                "WHERE nume LIKE ? " +
                "OR prenume LIKE ? " +
                "OR CONCAT(nume, ' ', prenume) LIKE ? " +
                "OR nr_telefon LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + search + "%";
            stmt.setString(1, searchPattern); // Căutare după nume
            stmt.setString(2, searchPattern); // Căutare după prenume
            stmt.setString(3, searchPattern); // Căutare după combinație nume + prenume
            stmt.setString(4, searchPattern); // Căutare după nr_telefon

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pacient pacient = new Pacient(
                        rs.getInt("id_pacient"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("data_nasterii"),
                        rs.getString("nr_telefon"),
                        rs.getString("adresa")
                );
                pacienti.add(pacient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacienti;
    }


    public boolean updatePacient(Pacient pacient) {
        String sql = "UPDATE Pacienti SET nume = ?, prenume = ?, data_nasterii = ?, nr_telefon = ?, adresa = ? WHERE id_pacient = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pacient.getNume());
            stmt.setString(2, pacient.getPrenume());
            stmt.setString(3, pacient.getDataNasterii());
            stmt.setString(4, pacient.getNrTelefon());
            stmt.setString(5, pacient.getAdresa());
            stmt.setInt(6, pacient.getIdPacient());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deletePacient(int idPacient) {
        String sql = "DELETE FROM Pacienti WHERE id_pacient = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPacient);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
