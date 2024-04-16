package Classi;

import java.io.BufferedReader;
import java.io.*;
import java.util.*;


public class GestoreListe {
    static final ArrayList<ListaSpesa> listeSpesa =new ArrayList<>();
    private Set<String> categorie;

    public GestoreListe(){
        this.categorie=new HashSet<>();
        inizializzaCategorie();
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

    public static void rimuoviLista(String nome)throws GestoreException{
        controlloEsistenza(nome);
        ListaSpesa listaSpesa= cercaLista(nome);
        listeSpesa.remove(listaSpesa);
    }

    public static ListaSpesa cercaLista(String nome) {
        for (ListaSpesa lista : listeSpesa) {
            if (lista.getNome().equals(nome))
                return lista;
        }
        return null;
    }

    public static void leggiDaFile(String path,String nomeFile)throws GestoreException, IOException {
        if(!new File(path).exists())
            throw new GestoreException("Il path inserito non corrisponde ad alcun file!");
        controlloEsistenza(nomeFile);
        ListaSpesa listaSpesa= new ListaSpesa(nomeFile);
        try (BufferedReader buffer=new BufferedReader(new FileReader(path))){
            String file= buffer.readLine();

        }
    }

    public static int numeroListe(){ return listeSpesa.size();}

    private void inizializzaCategorie() {
        for (ListaSpesa lista : listeSpesa) {
            for (Articolo articolo : lista.getArticoli()) {
                String categoria = articolo.getCategoria();
                categorie.add(categoria);
            }
        }
    }
    public void rimuoviCategoriaProdotto(String categoria) {
        categorie.remove(categoria);}


    public static void aggiornaListe(String categoria) {
        for (ListaSpesa lista : listeSpesa) {
            for (Articolo articolo : lista) {
                if (articolo.getCategoria().equals(categoria))
                    articolo.setCategoria("Non categorizzato");
            }
        }
    }
}





