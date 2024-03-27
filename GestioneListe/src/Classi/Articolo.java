package Classi;

public class Articolo
{
    private String nome;
    private int quantita;
    private String categoria;
    private float costo;

    public Articolo(String nome,int quantita, String categoria,float costo){
        this.nome=nome;
        this.quantita=quantita;
        this.categoria=categoria;
        this.costo=costo;
    }

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
}
