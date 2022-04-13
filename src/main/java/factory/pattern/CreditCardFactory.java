package factory.pattern;

public class CreditCardFactory implements Factory{

    private MetodoPagamentoFactory cardNumber;

    public CreditCardFactory(String nom,String cogn,String card,String c)
    {
        cardNumber = new CreditCard(nom,cogn,card,c);
        getMetodo();
    }

    /*
     * Metodo "factory" del Pattern
     */
    public MetodoPagamentoFactory getMetodo()
    {
        return cardNumber;
    }
}
