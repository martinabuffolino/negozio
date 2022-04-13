package application;

/* Classe Smartphone: dati dello smartphone registrati dal cliente
 *   La classe per ogni attributo ha i metodi get e set
 *   Ci sono metodi aggiuntivi per ottenere singoli attributi:
 *   - getCFCliente: stampa un messaggio con i dati del cliente
 *   - setSmartphone: setta i parametri necessari dello smartphone in un solo metodo
 *   - getSmartphone: prende i paramentri dello smartphone
 */

public class Smartphone {

    private Cliente cliente = new Cliente();
    private String seriale;
    private String marca;
    private String modello;
    private String colore;

    public String getCliente() {
        System.out.println("Info Cliente");
        return "Codice Fiscale: " + this.cliente.getCF() + "\nNome: " + this.cliente.getNome() + "\nCognome: " + this.cliente.getCognome();
    }

    public void setCliente(String codiceFiscale, String nome, String cognome) {
        this.cliente.setNome(nome);
        this.cliente.setCognome(cognome);
        this.cliente.setCF(codiceFiscale);
    }

    public String getCFCliente(){
        System.out.println("CodiceFiscale: " + cliente.getCF());
        return this.cliente.getCF();
    }

    public String getSeriale() {
        return this.seriale;
    }

    private void setSeriale(String seriale) {
        this.seriale = seriale;
    }

    private void setMarca(String m) {
        this.marca = m;
    }

    public String getMarca() {
        return this.marca;
    }

    public String getModello() {
        return this.modello;
    }

    private void setModello(String modello) {
        this.modello = modello;
    }

    public String getColore() {
        return this.colore;
    }

    private void setColore(String colore) {
        this.colore = colore;
    }

    public void setSmartphone(String ser, String marca, String mod, String col) {
        setSeriale(ser);
        setMarca(marca);
        setModello(mod);
        setColore(col);
    }

    public void getSmartphone() {
        System.out.println("Info Smartphone:");
        System.out.println("Seriale: " + getSeriale());
        System.out.println("Marca: " + getMarca());
        System.out.println("Modello: "+ getModello());
        System.out.println("Colore: " + getColore());
    }
}