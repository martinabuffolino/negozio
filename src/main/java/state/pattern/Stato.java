package state.pattern;

/* Pattern State: gestisce il cambiamento di stato della riparazione
 * L'interfaccia implementa un metodo showSatus che, a seconda del tipo di classe richiamante,
 * eseguirà  un operazione speficica.
 */

public interface Stato {

    public String showStatus();

}
