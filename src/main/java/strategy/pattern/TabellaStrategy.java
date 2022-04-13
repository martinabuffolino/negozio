package strategy.pattern;

import javafx.collections.ObservableList;

/*
 * Interfaccia che rappresenta la "TabellaStrategy" del Pattern Strategy
 * @param <E> Può essere "Accessori" o "Marca"
 * @see Categoria
 * @see Marca
 */

public interface TabellaStrategy<E> {

    public ObservableList<E> crea();
}
