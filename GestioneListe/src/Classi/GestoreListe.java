package Classi;

import java.util.*;


public class GestoreListe {
    static final ArrayList<ListaSpesa> listeSpesa =new ArrayList<>();
    private Set<String> categorie;

    public GestoreListe(){
        this.categorie=new HashSet<>();
    }

    public static void controlloEsistenza(String nome)throws GestoreException {
        for(ListaSpesa listaSpesa: listeSpesa){
            if(listaSpesa.getNome().equals(nome))
                throw new GestoreException(" Il nome inserito è già in uso.\n");
        }
    }
    public static void aggiungiLista(String nome) throws GestoreException{
        controlloEsistenza(nome);
        ListaSpesa listaSpesa= new ListaSpesa(nome);
        listeSpesa.add(listaSpesa);
    }

    public static void rimuoviLista(String nome) throws GestoreException{
        controlloEsistenza(nome);
        ListaSpesa listaSpesa= new ListaSpesa(nome);
        listeSpesa.remove(listaSpesa);
    }

    public ListaSpesa cercaLista(String nome) {
        for (ListaSpesa lista : listeSpesa) {
            if (lista.getNome().equals(nome))
                return lista;
        }
        return null;
    }

    public void aggiungiCategoriaProdotto(String categoria){
        categorie.add(categoria);
    }

    public void rimuoviCategoriaProdotto(String categoria){
        categorie.remove(categoria);
    }

    public void aggiornaListe(String categoria){
        for(ListaSpesa lista: listeSpesa){
            for(Articolo articolo: lista){
                if(articolo.getCategoria().equals(categoria))
                    articolo.setCategoria("Non categorizzato");
            }
        }
    }

    public static int numeroListe(){
        return listeSpesa.size();
    }
}

