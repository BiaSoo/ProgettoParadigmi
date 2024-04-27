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

    public void scriviSuFile(String nomeFile){

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
