package state.pattern;

public class StatoInUscita implements Stato {

    private static final String statoAttuale = "In Uscita";

    @Override
    public String showStatus(){
        return statoAttuale;
    }
}