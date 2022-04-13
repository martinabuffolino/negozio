package strategy.pattern;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import database.DataBaseNegozioConnect;


/*
 * Classe che rappresenta "tabellaMarca" del Pattern Strategy,
 * con il compito di inizializzare una tabella che contenga tutti gli accessori del DB
 * filtrati in base alla categoria scelta dall'utente
 */

public class TabellaMarca implements TabellaStrategy<Accessori>{

    @FXML
    private ObservableList<Accessori> data;
    private String marc;

    public TabellaMarca(String x)
    {
        data = FXCollections.observableArrayList();
        marc = x;
    }

    /*
     * Metodo che ritorna la lista delle marche presenti nel DB
     */
    @Override
    public ObservableList<Accessori> crea()
    {
        try
        {
            String sqlQuery = "SELECT * FROM ACCESSORI WHERE MARCA = '" + marc + "'";
            ResultSet rs=DataBaseNegozioConnect.dbExecute(sqlQuery);
            while(rs.next())
            {
                int quant=rs.getInt(6);
                int vend=rs.getInt(7);
                double prez=rs.getDouble(5);
                Accessori acc=new Accessori(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),prez,quant,vend);
                acc.setQuantita(quant-vend);
                data.add(acc);
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
