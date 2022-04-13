package state.pattern;

public class StatoInCorso implements Stato {

    private static final String statoAttuale = "In Corso";

    @Override
    public String showStatus(){
        return statoAttuale;
    }
}