package application;

/* Classe Riparazione: contiene le informazioni della riparazione richiesta dal cliente
 * La classe per ogni attributo ha i metodi get e set
 */

import state.pattern.Stato;
import state.pattern.StatoInCorso;
import state.pattern.StatoSmartphone;

public class Riparazione {

    private int codRiparazione;
    private String tipoRiparazione;
    private StatoSmartphone statoRiparazione = new StatoSmartphone(); //creazione oggetto
    private String serialeSmartphone;
    private int codTecnico;

    public StatoSmartphone getStatoRiparazione() {
        return this.statoRiparazione;
    }

    public void setStatoRiparazione(StatoSmartphone statoRip) {
        this.statoRiparazione = statoRip;
    }

    public int getCodRiparazione() {
        return codRiparazione;
    }

    public void setCodRiparazione(int codRip) {
        this.codRiparazione = codRip;
    }

    public String getTipoRiparazione() {
        return tipoRiparazione;
    }

    public void setTipoRiparazione(String tipoRip) {
        this.tipoRiparazione = tipoRip;
    }


    public String getSerialeSmartphone() {
        return serialeSmartphone;
    }

    public void setSerialeSmartphone(String serialeSmartphone) {
        this.serialeSmartphone = serialeSmartphone;
    }

    public int getCodTecnico() {
        return codTecnico;
    }

    public void setCodTecnico(int codTecnico) {
        this.codTecnico = codTecnico;
    }

}