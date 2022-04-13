package database;

import java.sql.SQLException;

/*  Classe Ricevuta: permette la modifica o aggiunta sul database per oggetti di tipo Ricevuta.
 *  Ci sono metodi per l'inserimento e la cancellazione
 */

public class RicevutaDB {
    //metodo per l'inserimento della ricevuta
    public static void insertRicevuta(int costoRiparazione, int codRicevuta, String Seriale, int codiceTecnico, String emailCliente) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO RICEVUTA (COSTORIPARAZIONE,CODRICEVUTA,SERIALE,CODICETECNICO,EMAILCLIENTE) " +
                "VALUES('"+costoRiparazione+"','"+codRicevuta+"','"+Seriale+"','"+codiceTecnico+"','"+emailCliente+"');";

        try {
            DataBaseNegozioConnect.dbExectuteQuery(sql);
            System.out.println("Ricevuta Inserita");

        } catch(SQLException e) {
            System.out.println("Errore nell'inserimento del dato " + e);
            e.printStackTrace();
            throw e;
        }
    }

    //metodo per la selezione della ricevuta attraverso il seriale dello smartphone
    public static void selectCodRicevuta(String smartphone) throws SQLException {

        String sql = "SELECT * FROM RICEVUTA WHERE SERIALE = '"+smartphone+"';";

        try {
            DataBaseNegozioConnect.dbExecute(sql);
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //metodo per la selezione della ricevuta tramite numero e email cliente
    public static void selectRicevuta(String codRicevuta, String email) throws SQLException {

        String sql = "SELECT EXISTS(SELECT CODRICEVUTA FROM RICEVUTA WHERE CODRICEVUTA = '"+codRicevuta+"' AND EMAIL = '"+email+"');";

        try {
            DataBaseNegozioConnect.dbExecute(sql);
            if(DataBaseNegozioConnect.risultatoQuery != 0) {
                System.out.println("RICEVUTA SELEZIONATA!");
            } else {
                System.out.println("RICEVUTA NON ESISTE!");
            }

        } catch (SQLException | ClassNotFoundException e){
            System.out.println("RICEVUTA NON TROVATA");
            e.printStackTrace();
        }

    }

    //metodo per la cancellazione della ricevuta
    public static void deleteRicevuta(int codRicevuta) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM RICEVUTA WHERE CODRICEVUTA  =" + codRicevuta;

        try {
            DataBaseNegozioConnect.dbExectuteQuery(sql);
            System.out.println("Ricevuta cancellata!");
        } catch (SQLException e) {
            System.out.println("Errore nell'eliminazione della ricevuta" + e);
            e.printStackTrace();
            throw e;
        }
    }
}
