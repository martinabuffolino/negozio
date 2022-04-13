package command.pattern;

/* Pattern Command: gestisce le notifiche da inviare al cliente
 * Questo pattern sarà usato per gestire le notifiche da inviare al cliente.
 * L'interfaccia implementa un metodo void generico esegui che a seconda del tipo di classe richiamante
 * eseguirà  un operazione speficica.
 */
public interface Command {
    public void execute();

}
