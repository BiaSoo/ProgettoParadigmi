package Classi;

/**
 * Classe per la creazione di un articolo
 * @author Gabriele Magenta Biasina Matricola: 20044231
 */
public class Articolo
{
    private String nome;
    private int quantita;
    private String categoria;
    private float costo;

    /**
     * Costruttore
     * @param nome
     * @param quantita
     * @param categoria
     * @param costo
     */
    public Articolo(String nome,int quantita, String categoria,float costo){
        this.nome=nome;
        this.quantita=quantita;
        this.categoria=categoria;
        this.costo=costo;

    }

    /**
     * Getter e Setter
     */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    /**
     * Metodo per ritornare in stringa tutti gli elementi di articolo
     * @return
     */
    public String toString() {
        return "Nome: " + nome + ", Quantità: " + quantita + ", Categoria: " + categoria + ", Costo: " + costo;
    }

}
