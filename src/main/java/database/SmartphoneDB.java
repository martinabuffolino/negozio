package database;

import java.sql.SQLException;

/*  Classe SmartphoneDB: permette la modifica o aggiunta sul database per oggetti di tipo Smartphone.
 *  Ci sono metodi per l'inserimento e la cancellazione
 */

public class SmartphoneDB {

    //metodo per l'inserimento dello smartphone nel database
    public static void insertSmartphone(String seriale, String modello,String marca, String colore, String cfCliente) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO SMARTPHONE (SERIALE,MARCA,MODELLO,COLORE,CODFISCALE) VALUES('"+seriale+"','\"+marca+\"','"+modello+"','"+colore+"','"+cfCliente+"');";

        try {
            DataBaseNegozioConnect.dbExectuteQuery(sql);
            System.out.println("Smartphone inserito con successo");
        } catch(SQLException e) {
            System.out.println("Errore nell'inserimento " + e);
            e.printStackTrace();
            throw e;
        }
    }

    //metodo per la cancellazione dello smartphone nel database
    public static void deleteSmartphone(String seriale) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM SMARTPHONE WHERE SERIALESMARTPHONE  = '"+seriale+"'";

        try {
            DataBaseNegozioConnect.dbExectuteQuery(sql);
            System.out.println("Smartphone cancellato con successo");
        } catch (SQLException e) {
            System.out.println("Errore nella cancellazione" + e);
            e.printStackTrace();
            throw e;
        }
    }
}