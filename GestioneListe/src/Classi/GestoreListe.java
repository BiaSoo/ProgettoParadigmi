package Classi;

import java.util.*;


public class GestoreListe {
    private ArrayList<ListaSpesa> listeSpesa;
    private Set<String> categorie;

    public GestoreListe(){
        this.listeSpesa=new ArrayList<>();
        this.categorie=new HashSet<>();
    }

    public void aggiungiLista(ListaSpesa lista){
        listeSpesa.add(lista);
    }

    public void rimuoviLista(ListaSpesa lista){
        listeSpesa.remove(lista);
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
}
