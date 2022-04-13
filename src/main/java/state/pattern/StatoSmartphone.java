package state.pattern;

public class StatoSmartphone {

    private Stato stato;

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public String currentStatus(){
        return stato.showStatus();
    }
}
