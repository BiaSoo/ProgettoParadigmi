package Classi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe per la gestione degli articoli presenti nella lista e degli elementi che rappresentano la lista stessa
 * @author Gabriele Magenta Biasina Matricola: 20044231
 */
public class ListaSpesa implements Iterable<Articolo>
{
    private String nome;
    private ArrayList<Articolo>articoli= new ArrayList<>();
    public ListaSpesa(String nome){
        this.nome=nome;
    }

    /**
     * Getter Nome lista
     * @return nome della lista
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter Nome lista
     * @param nome della lista
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo per l'aggiunta di un nuovo articolo nella lista
     * @param articolo
     */
    public void aggiungiArticolo(Articolo articolo){
        articoli.add(articolo);
    }

    /**
     * Metodo per la ricerca di un articolo dato il suo nome
     * @param nome
     * @return articolo
     */
    public Articolo cercaArticoloPerNome(String nome) {
        for (Articolo articolo : articoli) {
            if (articolo.getNome().equals(nome)) {
                return articolo;
            }
        }
        return null;
    }

    /**
     * Metodo per la rimozione di un articolo dato il suo nome
     * @param nome
     * @throws ListaException
     */
    public void rimuoviArticolo(String nome) throws ListaException {
        Articolo articoloDaRimuovere = cercaArticoloPerNome(nome);
        if (articoloDaRimuovere != null) {
            articoli.remove(articoloDaRimuovere);
            System.out.println("Articolo eliminato con successo! ");
        } else {
            throw new ListaException("Nessun articolo trovato!");
        }
    }

    /**
     * Metodo per il calcolo del costo totale
     * @return costo totale
     */
    public double costoTotale(){
        double costoTot=0;
        for(Articolo articolo: articoli)
            costoTot+= articolo.getQuantita()* articolo.getCosto();
        return costoTot;
    }

    /**
     * Metodo per la ricerca di articoli per categoria
     * @param categoria
     * @return categoria
     */
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

    /**
     * Metodo per scrivere una lista su file dato il nome
     * @param nomeFile
     * @throws ListaException
     */
    public void scriviSuFile(String nomeFile) throws ListaException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            for (Articolo articolo : articoli) {
                writer.write(articolo.getNome() + ", " + articolo.getQuantita() + ", " + articolo.getCosto() + ", " + articolo.getCategoria());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ListaException("Impossibile scrivere su file: " + e.getMessage());
        }
    }


    /**
     * Getter Articoli della lista
     * @return articoli
     */
    public ArrayList<Articolo> getArticoli() {
        return articoli;
    }

    /**
     * Setter articoli della lista
     * @param articoli
     */
    public void setArticoli(ArrayList<Articolo>articoli){
        this.articoli=articoli;
    }

    /**
     * Metodo per aggiornare un articolo
     * @param a
     * @param nome
     * @param quantita
     * @param categoria
     * @param costo
     * @throws ListaException
     * @throws ArticoloException
     */
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

    /**
     * Metodo che ritorna il numero di articoli
     * @return totale articoli
     */
    public int numeroArticoli() {
        int totaleArticoli = 0;
        for (Articolo articolo : articoli) {
            totaleArticoli += articolo.getQuantita();
        }
        return totaleArticoli;
    }
}
