package Classi;

import java.util.*;

public class ListaSpesa implements Iterable<Articolo>
{
    private String nome;
    private ArrayList<Articolo>articoli= new ArrayList<>();
    public ListaSpesa(String nome){
        this.nome=nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void aggiungiArticolo(Articolo articolo){
        articoli.add(articolo);
    }

    public void rimuoviArticolo(Articolo articolo){
        articoli.add(articolo);
    }

    public double costoTotale(){
        double costoTot=0;
        for(Articolo articolo: articoli)
            costoTot+= articolo.getQuantita()* articolo.getCosto();
        return costoTot;
    }

    public ArrayList<Articolo> ArticoliPerCategoria(String categoria){
        ArrayList<Articolo> CategoriaArticoli= new ArrayList<>();
        for(Articolo articolo: articoli){
            if(articolo.getCategoria().equals(categoria))
                CategoriaArticoli.add(articolo);
        }
        return CategoriaArticoli;
    }

    @Override
    public Iterator<Articolo> iterator() {
        return articoli.iterator();
    }

    public void scriviSuFile(String nomeFile){

    }

    public void leggiDaFile(String nomeFile){

    }
}
