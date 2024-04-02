package Classi;

import java.util.*;
import  Classi.GestoreListe;

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

    public void rimuoviArticolo(String nome) throws GestoreException{
        Articolo articoloDaRimuovere=null;
        for(Articolo articolo: articoli){
            if(articolo.getNome().equals(nome)) {
                articoli.remove(articoloDaRimuovere);
                System.out.println("Articolo "+articoloDaRimuovere+"cancellato correttamente!");
            }
            else
                throw new GestoreException("Nessun articolo trovato con quel nome!");
        }
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
