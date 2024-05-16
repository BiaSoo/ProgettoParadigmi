package Classi;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import Classi.GestoreListe;
import Classi.Articolo;

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

    public void rimuoviArticolo(String nome) throws ListaException {
        Articolo eliminaArticolo=null;
        for(Articolo articolo: articoli){
            if(articolo.getNome().equals(nome)){
                eliminaArticolo=articolo;
                break;
            }
        }
        if(eliminaArticolo!=null){
            articoli.remove(eliminaArticolo);
            System.out.println("Articolo eliminato con successo! ");
        }
        else
            throw new ListaException("Nessun articolo trovato!");

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

    public void scriviSuFile(String nomeFile) throws ListaException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            for (Articolo articolo : articoli) {
                // Scrivi i dettagli dell'articolo nel file, ad esempio: nome, categoria, quantità, costo
                writer.write(articolo.getNome() + "," + articolo.getCategoria() + "," + articolo.getQuantita() + "," + articolo.getCosto());
                writer.newLine(); // Vai a capo dopo ogni articolo
            }
        } catch (IOException e) {
            throw new ListaException("Impossibile scrivere su file: " + e.getMessage());
        }
    }


    public ArrayList<Articolo> getArticoli() {
        return articoli;
    }

    public void setArticoli(ArrayList<Articolo>articoli){
        this.articoli=articoli;
    }

    public void aggiornaArticolo( Articolo a, String nome, int quantita, String categoria, float costo) throws ListaException, ArticoloException
    {
        if (!articoli.contains(a))
        {
            throw new ListaException("L'articolo indicato non risulta nella lista della spesa!");
        }
    }

    public int numeroArticoli(){
        return articoli.size();
    }
}
