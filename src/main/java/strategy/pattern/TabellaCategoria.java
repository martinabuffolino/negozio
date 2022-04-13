package strategy.pattern;

import java.sql.ResultSet;

import database.DataBaseNegozioConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/*
 * Classe che rappresenta "TabellaCategoria" del Pattern Strategy,
 * con il compito di inizializzare una tabella che contenga tutti gli accessori del DB
 * filtrati in base alla categoria scelta dall'utente
 */

public class TabellaCategoria implements TabellaStrategy<Accessori>{

    @FXML
    private ObservableList<Accessori> data;
    private String cat;

    /**
     * Costruttore che inizializza gli attributi di classe
     * @param x Categoria scelta dall'utente da ricercare nel DB
     */
    public TabellaCategoria(String x)
    {
        data = FXCollections.observableArrayList();
        cat = x;
    }

    /*
     * Metodo che ritorna la lista di categorie presenti nel DB
     */
    @Override
    public ObservableList<Accessori> crea()
    {
        try
        {
            String sqlQuery = "SELECT ACCESS FROM ACCESS_CATEG WHERE CATEG = '" + cat + "'";
            ResultSet rs=DataBaseNegozioConnect.dbExecute(sqlQuery);
            while(rs.next())
            {
                String sql1 = "SELECT * FROM ACCESSORI WHERE codice = " + rs.getInt(1);
                ResultSet rs1 = DataBaseNegozioConnect.dbExecute(sql1);
                while(rs1.next())
                {
                    double prez=rs1.getDouble(5);
                    Accessori acc=new Accessori(rs1.getInt(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),prez,rs1.getInt(6),rs1.getInt(7));
                    data.add(acc);
                }
            }
        }
        catch(Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return data;
    }
}
