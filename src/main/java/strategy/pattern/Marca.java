package strategy.pattern;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;

/**
 * Classe Marca: con tutti gli accessori che contiene
 * @see Accessori
 */

public class Marca {

    private ArrayList<Accessori> accessori;
    private SimpleStringProperty marc;

    public Marca(String x)
    {
        accessori=new ArrayList<Accessori>();
        marc=new SimpleStringProperty(x);
    }

    public void addAcc(Accessori x)
    {
        accessori.add(x);
    }

    public Accessori getAcc(int x)
    {
        return accessori.get(x);
    }

    public int getSize() {
        return accessori.size();
    }

    public String getMarca() {
        return marc.get();
    }

    public void setMarca(String x) {
        marc.set(x);
    }
}
