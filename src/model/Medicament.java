package model;

public class Medicament {
    private int idMedicament;
    private String denumire;
    private String concentratie;
    private String producator;
    private String dataExpirare;

    public Medicament(int idMedicament, String denumire, String concentratie, String producator, String dataExpirare) {
        this.idMedicament = idMedicament;
        this.denumire = denumire;
        this.concentratie = concentratie;
        this.producator = producator;
        this.dataExpirare = dataExpirare;
    }

    public int getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getConcentratie() {
        return concentratie;
    }

    public void setConcentratie(String concentratie) {
        this.concentratie = concentratie;
    }

    public String getProducator() {
        return producator;
    }

    public void setProducator(String producator) {
        this.producator = producator;
    }

    public String getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(String dataExpirare) {
        this.dataExpirare = dataExpirare;
    }
}
