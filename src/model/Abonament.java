package model;

public class Abonament {
    private int idAbonament;
    private int idPacient;
    private String tipAbonament;
    private String dataIncepere;
    private String dataExpirare;

    public Abonament(int idAbonament, int idPacient, String tipAbonament, String dataIncepere, String dataExpirare) {
        this.idAbonament = idAbonament;
        this.idPacient = idPacient;
        this.tipAbonament = tipAbonament;
        this.dataIncepere = dataIncepere;
        this.dataExpirare = dataExpirare;
    }

    public int getIdAbonament() {
        return idAbonament;
    }

    public int getIdPacient() {
        return idPacient;
    }

    public String getTipAbonament() {
        return tipAbonament;
    }

    public String getDataIncepere() {
        return dataIncepere;
    }

    public String getDataExpirare() {
        return dataExpirare;
    }
}
