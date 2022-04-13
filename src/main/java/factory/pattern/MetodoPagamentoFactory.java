package factory.pattern;

/* Pattern Factory: gestisce il metodo di pagamento
 * L'interfaccia implementa un metodo paga che, a seconda del tipo di classe richiamante,
 * eseguirà un operazione speficica.
 */

public interface MetodoPagamentoFactory {

    public String paga();
}
