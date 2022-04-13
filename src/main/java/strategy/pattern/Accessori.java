package strategy.pattern;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/* Classe Accessori: contiene i prodotti di categoria iphone
 * La classe per ogni attributo ha i metodi get e set
 */

public class Accessori {

    private SimpleIntegerProperty codice;
    private SimpleStringProperty nome;
    private SimpleStringProperty modello;
    private SimpleStringProperty marca;
    private SimpleDoubleProperty prezzo;
    private SimpleIntegerProperty quantita;
    private SimpleIntegerProperty venduti;

    // Costruttore vuoto
    public Accessori()
    {
        codice=new SimpleIntegerProperty(0);
        nome=new SimpleStringProperty("");
        modello=new SimpleStringProperty("");
        marca=new SimpleStringProperty("");
        prezzo=new SimpleDoubleProperty(0);
        quantita=new SimpleIntegerProperty(0);
        venduti = new SimpleIntegerProperty(0);
    }

    /**
     * Costruttore che inizializza ogni attributo di classe
     * @param cod Codice
     * @param nom Nome
     * @param mod modello
     * @param mar marca
     * @param prez prezzo
     * @param quant quantita
     * @param vend venduti
     *
     */

    public Accessori(int cod, String nom, String mod, String mar, double prez, int quant, int vend)
    {
        codice=new SimpleIntegerProperty(cod);
        nome=new SimpleStringProperty(nom);
        modello=new SimpleStringProperty(mod);
        marca=new SimpleStringProperty(mar);
        prezzo=new SimpleDoubleProperty(prez);
        quantita=new SimpleIntegerProperty(quant);
        venduti=new SimpleIntegerProperty(vend);
    }

    public int getCodice() {
        return codice.get();
    }

    public void setCodice(int cod) {
        codice.set(cod);
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nom) {
        nome.set(nom);
    }

    public String getModello() {
        return modello.get();
    }

    public void setModello(String mode) {
        modello.set(mode);
    }

    public String getMarca() {
        return marca.get();
    }

    public void setMarca(String marc) {
        modello.set(marc);
    }

    public double getPrezzo() {
        return prezzo.get();
    }

    public void setPrezzo(double prez) {
        prezzo.set(prez);
    }

    public int getQuantita() {
        return quantita.get();
    }

    public void setQuantita(int quant) {
        quantita.set(quant);
    }

    public int getVenduti() {
        return venduti.get();
    }

    public void setVenduti(int vend) {
        venduti.set(vend);
    }

}
