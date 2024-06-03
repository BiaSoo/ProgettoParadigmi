package Classi;

import java.util.HashSet;
import java.util.Set;

public class Articolo
{
    private String nome;
    private int quantita;
    private String categoria;
    private float costo;
    private static Set<String> categorie=new HashSet<>();

    public Articolo(String nome,int quantita, String categoria,float costo){
        this.nome=nome;
        this.quantita=quantita;
        this.categoria=categoria;
        this.costo=costo;

        aggiungiCategoria(categoria);
    }

    private static void aggiungiCategoria(String categoria) {
        categorie.add(categoria);
    }

    public static Set<String> getCategorie(){
        return categorie;
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

    public String toString() {
        return "Nome: " + nome + ", Quantità: " + quantita + ", Categoria: " + categoria + ", Costo: " + costo;
    }

}
