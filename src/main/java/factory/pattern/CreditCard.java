package factory.pattern;

/**
 * Classe che estende "DatiSensibili" e
 * rappresenta il metodo di pagamento "Carta di Credito"
 */

public class CreditCard extends DatiSensibiliCliente{

    private String cardNumber;
    private String cvv;

    /*
     * Costruttore della classe
     * @param a nome, contenuto nella superclassse
     * @param b cognome, contenuto nella superclasse
     * @param numcart cardNumber
     * @param cv cvv
     */

    public CreditCard(String a, String b, String numcart, String cv) {
        super(a,b);
        cardNumber = numcart;
        cvv = cv;
    }

    public String getCart() {
        return cardNumber;
    }

    @Override
    public String paga() {
        return("Il conto e' stato con la Carta di Credito n." + cardNumber);
    }
}