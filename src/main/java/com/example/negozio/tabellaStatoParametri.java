package com.example.negozio;

    /* Classe tabellaStatoParametri: contiene la lista di riparazioni
       con il rispettivo tipo di riparazione, lo stato  e il seriale dello smartphone
       Questa tabella viene visualizzata dal tecnico per cambiare lo stato della riparazione una volta terminata
       La classe per ogni attributo ha i metodi get e set
     */

public class tabellaStatoParametri {
    String codRiparazione;

    String tipoRiparazione;
    String statoRiparazione;
    String serialeSmartphone;

    public tabellaStatoParametri(String codRiparazione, String tipoRiparazione, String statoRiparazione, String serialeSmartphone) {
        this.codRiparazione = codRiparazione;
        this.tipoRiparazione = tipoRiparazione;
        this.statoRiparazione = statoRiparazione;
        this.serialeSmartphone = serialeSmartphone;
    }

    public void setCodRiparazione(String codRiparazione) {

        this.codRiparazione = codRiparazione;
    }

    public String getCodRiparazione() {

        return codRiparazione;
    }

    public void setTipoRiparazione(String tipoRiparazione) {
        this.tipoRiparazione = tipoRiparazione;
    }

    public String getTipoRiparazione() {
        return tipoRiparazione;
    }

    public void setStatoRiparazione(String statoRiparazione) {

        this.statoRiparazione = statoRiparazione;
    }

    public String getStatoRiparazione() {

        return statoRiparazione;
    }

    public void setSerialeSmartphone(String serialeSmartphone) {

        this.serialeSmartphone = serialeSmartphone;
    }

    public String getSerialeSmartphone() {

        return serialeSmartphone;
    }
}
