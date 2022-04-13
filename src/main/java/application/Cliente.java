package application;

/* Classe Cliente: contiene i dati del cliente
 * La classe per ogni attributo ha i metodi get e set
 */

public class Cliente {

    private String codiceFiscale;
    private String nomeCliente;
    private String cognomeCliente;
    private String email;
    private String password;
    private String cellulare;


    public String getNome() {
        return this.nomeCliente;
    }

    public void setNome(String nomeC) {
        this.nomeCliente = nomeC;
    }

    public String getCognome() {
        return this.cognomeCliente;
    }

    public void setCognome(String cognomeC) {
        this.cognomeCliente = cognomeC;
    }

    public String getCF() {
        return this.codiceFiscale;
    }

    public void setCF(String cf) {
        this.codiceFiscale = cf;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }
}