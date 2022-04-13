package database;

import java.sql.SQLException;

/*  Classe Riparazione: permette la modifica sul database per oggetti di tipo Riparazione.
 *   Ci sono metodi per l'inserimento e la cancellazione
 */

public class RiparazioneDB {

    //metodo per l'inserimento della riparazione sul database
    public static void insertRiparazione(String tipoRip, String stato, String serialeSmartphone, int codTecnico) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO RIPARAZIONE (TIPORIPARAZIONE,STATO,SERIALE,CODTECNICO) " +
                "VALUES('"+tipoRip+"','"+stato+"','"+serialeSmartphone+"','"+codTecnico+"');";

        try {
            DataBaseNegozioConnect.dbExectuteQuery(sql);
            System.out.println("Riparazione inserita con successo");

        } catch(SQLException e) {
            System.out.println("Errore nell'inserimento " + e);
            e.printStackTrace();
            throw e;
        }
    }

    //metodo per la selezione della riparazione dal database attraverso il seriale dello Smartphone
    public static void selectCodRiparazione(String serialeSmartphone) {

        String sql = "SELECT * FROM RIPARAZIONE WHERE SERIALE = '"+serialeSmartphone+"';";

        try {
            DataBaseNegozioConnect.dbExecute(sql);
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    //metodo per aggiornare lo stato della riparazione nel database
    public static void updateStatoRiparazione(int riparazione, String stato) {
        String sql = "UPDATE RIPARAZIONE SET STATO = '"+stato+"' WHERE CODRIPARAZIONE = '"+riparazione+"';";
        try {
            DataBaseNegozioConnect.dbExectuteQuery(sql);
            System.out.println("Stato aggiornato con successo");
        } catch (SQLException e) {
            System.out.println("Errore aggiornato stato riparazione");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //metodo per la cancellazione della riparazione dal database
    public static void deleteRiparazione(int codRiparazione) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM RIPARAZIONE WHERE CODRIPARAZIONE  =" + codRiparazione;

        try {
            DataBaseNegozioConnect.dbExectuteQuery(sql);
            System.out.println("Riparazione cancellata con successo");
        } catch (SQLException e) {
            System.out.println("Errore nella cancellazione la ripazione dal database" + e);
            e.printStackTrace();
            throw e;
        }
    }
}
