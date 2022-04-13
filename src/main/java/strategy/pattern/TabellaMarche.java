package strategy.pattern;

import java.sql.ResultSet;
import java.util.ArrayList;
import database.DataBaseNegozioConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 * Classe che rappresenta una "ConcreteStrategy" del Pattern Strategy,
 * con il compito di inizializzare una tabella che contenga tutte le marche del DB
 */

public class TabellaMarche implements TabellaStrategy<Marca>{

    @FXML
    private ObservableList<Marca> data;
    @FXML
    private ObservableList<Accessori> selected;
    private ArrayList<Marca> marche;

    /**
     * Costruttore di classe che inizializza gli attributi
     * @param x Lista di Accessori selezionati dall'utente
     */
    public TabellaMarche(ObservableList<Accessori> x)
    {
        marche = new ArrayList<Marca>();
        data = FXCollections.observableArrayList();
        selected = x;
    }

    /**
     * Metodo che ritorna la lista dei fornitori presenti nel DB
     */
    public ObservableList<Marca> crea()
    {
        try
        {
            //Inizializzo lista fornitori
            String sqlQuery="SELECT DISTINCT MARCA FROM ACCESSORI";
            ResultSet rs=DataBaseNegozioConnect.dbExecute(sqlQuery);
            while(rs.next())
                marche.add(new Marca(rs.getString(1)));

            //Calcolo il costo per ogni marca
            for(Accessori x : selected)
            {
                sqlQuery = "SELECT CODICE,NOME,MODELLO,MARCA,QUANTITA,VENDUTI,PREZZO FROM ACCESSORI WHERE MARCA = '" + x.getMarca() + "' ";
                rs = DataBaseNegozioConnect.dbExecute(sqlQuery);
                while(rs.next())
                    for(Marca y : marche)
                        if(y.getMarca().equals(rs.getString(2)))
                        {
                            Accessori temp = new Accessori();
                            temp.setCodice(rs.getInt(1));
                            temp.setNome(x.getNome());
                            temp.setModello(x.getModello());
                            temp.setQuantita(rs.getInt(6)-rs.getInt(7));
                            temp.setPrezzo( x.getQuantita()*temp.getPrezzo());
                            temp.setQuantita( temp.getQuantita()-x.getQuantita() );
                            y.addAcc(temp);
                        }
            }
            for(Marca x : marche)
                data.add(x);
        }
        catch(Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

}
