package application;

/* Classe Tecnico: contiene i dati del tecnico
 * La classe per ogni attributo ha i metodi get e set
 */

public class Tecnico {

    private int codiceTecnico;
    private String nomeTecnico;
    private String cognomeTecnico;


    public int getCodice() {
        return codiceTecnico;
    }

    public void setCodice(int codT) {
        this.codiceTecnico = codT;
    }

    public String getNome() {
        return nomeTecnico;
    }

    public void setNome(String nomeT) {
        this.nomeTecnico = nomeT;
    }

    public String getCognome() {
        return cognomeTecnico;
    }

    public void setCognome(String cognomeT) {
        this.cognomeTecnico = cognomeT;
    }
}