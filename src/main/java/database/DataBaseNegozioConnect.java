package database;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

/*  Classe DataBaseNegozioConnect: permette al progetto di connettersi con il database mysql chiamato "negozio".
 *  Ci sono dei metodi che permettono di eseguire interrogazioni e operazioni sul database.
 */

public class DataBaseNegozioConnect {

    private static final String  JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //Driver JDBC: componente software che consente a un'applicazione Java di interagire con un database

    private static Connection connection = null;

    private static final String connStr = "jdbc:mysql://localhost/negozio?user=root&password=";

    public static int risultatoQuery;

    public static void dbConnect() throws SQLException, ClassNotFoundException {

        try {

            Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException e) {
            System.out.println("Where is your JDBC Driver?");
            e.printStackTrace();//error
            throw e;
        }

        System.out.println("JDBC Driver ha been regestered!");

        try {

            connection = DriverManager.getConnection(connStr);
            System.out.println("Connesione stabilita!");

        } catch (SQLException e) {
            System.out.println("Connesione fallita!");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CONNESSIONE NON RIUSCITA");
            alert.setHeaderText("Connessione al database non riuscita");
            alert.setContentText("Controllare la connessione, assicurarsi che la connessione al server sia funzionante");
            alert.showAndWait();
            throw e;
        }
    }

    //questo metodo chiude la connessione con il DB
    public static void dbDisconnect() throws SQLException {

        try {

            if (connection != null && connection.isClosed()) {
                connection.close();
            }

        } catch (SQLException e) {
            throw e;
        }
    }

    //metodo per inserire,modificare ed eliminare nel DB
    public static void dbExectuteQuery(String sqlStmt) throws SQLException, ClassNotFoundException {

        Statement stmt = null;

        try {
            dbConnect();

            stmt = connection.createStatement();
            stmt.executeUpdate(sqlStmt);
        }

        catch (SQLException e) {
            System.out.println("Problem occured at dbExecuteQuery operation!" + e);
            throw e;
        }

        finally {

            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }

    //questo metodo permette di riavere il risultato della query dal database
    @SuppressWarnings("finally")
    public static ResultSet dbExecute(String sqlQuery) throws SQLException, ClassNotFoundException {

        Statement stmt = null;
        ResultSet rs = null;
        CachedRowSet crs = null;

        try {
            dbConnect();

            stmt = connection.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            crs.populate(rs);
        }

        catch (SQLException e) {

            System.out.println("Error occured in dbExecute operation" + e);
            throw e;
        }

        finally {

            if (rs.next()) {
                System.out.println("Auto Generated Primary Key " + rs.getInt(1));
                risultatoQuery = rs.getInt(1);
            }

            if (rs!=null) {
                rs.close();
            }

            if (stmt!=null) {
                stmt.close();
            }

            dbDisconnect();

            return crs;
        }
    }
}