package strategy.pattern;

import java.sql.ResultSet;

import database.DataBaseNegozioConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;


public class TabellaAccessori implements TabellaStrategy<Accessori>{

    @FXML
    private ObservableList<Accessori> data;

    public TabellaAccessori()
    {
        data = FXCollections.observableArrayList();
    }

    /*
     * Metodo che ritorna tutti gli accessori presenti nel DB
     */
    @Override
    public ObservableList<Accessori> crea()
    {
        try
        {
            String sqlQuery="Select * from ACCESSORI";
            ResultSet rs=DataBaseNegozioConnect.dbExecute(sqlQuery);
            while(rs.next())
            {
                Accessori acc=new Accessori(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getInt(6),rs.getInt(7));
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
