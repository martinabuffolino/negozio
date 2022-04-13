package database;

import java.sql.SQLException;

/*  Classe Cliente: permette la manipolazione sul database per oggetti di tipo Cliente.
 *  Ci sono metodi per l'inserimento e la cancellazione
 */

public class ClienteDB {

    //metodo per l'inserimento del cliente sul database
    public static void insertCliente(String cf, String nome, String cognome, String email, String password, String cellulare) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO CLIENTE (CODFISCALE,NOME,COGNOME,EMAIL,PASSWORD,CELLULARE) VALUES('"+cf+"','"+nome+"','"+cognome+"','"+email+"','"+password+"','"+cellulare+"');";

        try {
            DataBaseNegozioConnect.dbExectuteQuery(sql);
            System.out.println("Cliente inserito con successo");

        } catch(SQLException e) {
            System.out.println("Errore nell'inserimento del: " + e);
            e.printStackTrace();
            throw e;
        }
    }

    //metodo per la cancellazione del cliente dal database
    public static void deleteCliente(String cf) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM CLIENTE WHERE CODFISCALE  = '"+cf+"'";

        try {
            DataBaseNegozioConnect.dbExectuteQuery(sql);
            System.out.println("Cliente cancellato con successo");
        } catch (SQLException e) {
            System.out.println("Errore nell'eliminazione dal database" + e);
            e.printStackTrace();
            throw e;
        }
    }
}
