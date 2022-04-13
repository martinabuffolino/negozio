package factory.pattern;

public class PayPalFactory implements Factory{

    private MetodoPagamentoFactory cardPayPal;

    public PayPalFactory(String nom,String cogn,String email,String password)
    {
        cardPayPal = new PayPal(nom,cogn,email,password);
        getMetodo();
    }

    /*
     * Metodo "factory" del Pattern
     */
    public MetodoPagamentoFactory getMetodo()
    {
        return cardPayPal;
    }
}
