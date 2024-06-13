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

    public Articolo cercaArticoloPerNome(String nome) {
        for (Articolo articolo : articoli) {
            if (articolo.getNome().equals(nome)) {
                return articolo;
            }
        }
        return null; // Se non viene trovato alcun articolo con il nome specificato
    }


    public void rimuoviArticolo(String nome) throws ListaException {
        Articolo articoloDaRimuovere = cercaArticoloPerNome(nome);
        if (articoloDaRimuovere != null) {
            articoli.remove(articoloDaRimuovere);
            System.out.println("Articolo eliminato con successo! ");
        } else {
            throw new ListaException("Nessun articolo trovato!");
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

    public void scriviSuFile(String nomeFile) throws ListaException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            for (Articolo articolo : articoli) {
                // Scrivi i dettagli dell'articolo nel file con lo stesso pattern utilizzato in leggiDaFile
                writer.write(articolo.getNome() + ", " + articolo.getQuantita() + ", " + articolo.getCosto() + ", " + articolo.getCategoria());
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
        int indice = articoli.indexOf(a);
        Articolo articoloDaAggiornare = articoli.get(indice);

        if (nome != null && !nome.isEmpty()) {
            articoloDaAggiornare.setNome(nome);
        }
        if (quantita >= 0) {
            articoloDaAggiornare.setQuantita(quantita);
        }
        if (categoria != null && !categoria.isEmpty()) {
            articoloDaAggiornare.setCategoria(categoria);
        }
        if (costo >= 0) {
            articoloDaAggiornare.setCosto(costo);
        }
    }

    public int numeroArticoli() {
        int totaleArticoli = 0;
        for (Articolo articolo : articoli) {
            totaleArticoli += articolo.getQuantita();
        }
        return totaleArticoli;
    }
}
