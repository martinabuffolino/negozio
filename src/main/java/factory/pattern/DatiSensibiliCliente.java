package factory.pattern;


/*
 * Classe Astratta che implementa l'interfaccia utilizzata per il pattern Factory Method,
 * la quale riassume le caratteristiche comuni tra le 2 sottoclassi
 */

public abstract class DatiSensibiliCliente implements MetodoPagamentoFactory{

    private String nome,cognome;

    public DatiSensibiliCliente() {}

    /*
     * Costruttore utilizzato dalle sottoclassi per inizializzare i due parametri
     * @param a nome
     * @param b cognome
     */

    public DatiSensibiliCliente(String a,String b) {
        nome=a;
        cognome=b;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    /*
     * Metodo da sovrascrivere nelle sottoclassi
     */
    public abstract String paga();
}
