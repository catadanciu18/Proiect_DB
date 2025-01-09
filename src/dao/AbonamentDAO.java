package dao;

import model.Abonament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonamentDAO {

    public List<Abonament> getAbonamente(String search) {
        List<Abonament> abonamente = new ArrayList<>();
        String sql = "SELECT * FROM abonament WHERE id_pacient IN (SELECT id_pacient FROM pacienti WHERE nr_telefon LIKE ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + search + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Abonament abonament = new Abonament(
                        rs.getInt("id_abonament"),
                        rs.getInt("id_pacient"),
                        rs.getString("tip_abonament"),
                        rs.getString("data_incepere"),
                        rs.getString("data_expirare")
                );
                abonamente.add(abonament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return abonamente;
    }

    public int addAbonamentByNrTelefon(String nrTelefon, String tipAbonament, String dataIncepere, String dataExpirare) {
        String sqlFindPacient = "SELECT id_pacient FROM pacienti WHERE nr_telefon = ?";
        String sqlInsert = "INSERT INTO abonament (id_pacient, tip_abonament, data_incepere, data_expirare) VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement findStmt = conn.prepareStatement(sqlFindPacient);
             PreparedStatement insertStmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

            findStmt.setString(1, nrTelefon);
            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {
                int idPacient = rs.getInt("id_pacient");
                insertStmt.setInt(1, idPacient);
                insertStmt.setString(2, tipAbonament);
                insertStmt.setString(3, dataIncepere);
                insertStmt.setString(4, dataExpirare);

                insertStmt.executeUpdate();
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }
}
