package application;

/* Classe Ricevuta: verrï¿½ rilasciata al cliente
 * La classe per ogni attributo ha i metodi get e set
 */

public class Ricevuta {

    private int codRicevuta;
    private Smartphone smartphoneControllato;
    private Tecnico tecnicoSostituzione;
    private Riparazione sostituzioneEffettuata;
    private double costoRiparazione;
    private String emailCliente;


    public int getCodRicevuta() {
        return codRicevuta;
    }

    public void setCodRicevuta(int codRic) {
        this.codRicevuta = codRic;
    }

    public Smartphone getSmartphoneControllato() {
        return smartphoneControllato;
    }

    public void setSmartphoneControllato(Smartphone smartphoneControllato) {
        this.smartphoneControllato = smartphoneControllato;
    }

    public Tecnico getTecnicoSostituzione() {
        return tecnicoSostituzione;
    }

    public void setTecnicoSostituzione(Tecnico tecnicoSostituzione) {
        this.tecnicoSostituzione = tecnicoSostituzione;
    }

    public Riparazione getSostituzioneEffettuata() {
        return sostituzioneEffettuata;
    }

    public void setSostituzioneEffettuata(Riparazione sostituzioneEffettuata) {
        this.sostituzioneEffettuata = sostituzioneEffettuata;
    }

    public double getCostoRiparazione() {
        return costoRiparazione;
    }

    public void setCostoRiparazione(double costoRiparazione) {
        this.costoRiparazione = costoRiparazione;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }
}