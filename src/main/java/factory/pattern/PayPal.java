package factory.pattern;

/*
 * Classe che estende "DatiSensibiliCliente" e
 * rappresenta il metodo di pagamento "PayPal"
 */

public class PayPal extends DatiSensibiliCliente{

    private String email;
    private String password;

    /*
     * Costruttore della classe
     * @param a nome, contenuto nella superclassse
     * @param b cognome, contenuto nella superclasse
     * @param e email
     * @param p password
     */

    public PayPal(String a, String b, String e, String p) {
        super(a,b);
        email = e;
        password = p;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String paga() {
        return "Il conto e' stato pagato con PayPal usato l'email:  "+email;
    }
}