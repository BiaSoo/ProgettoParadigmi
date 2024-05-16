package Classi;

import java.io.BufferedReader;
import java.io.*;
import java.util.*;
import Classi.ListaSpesa;


public class GestoreListe {
    static final ArrayList<ListaSpesa> listeSpesa =new ArrayList<>();
    private static Set<String> categorie;

    public GestoreListe(){
        this.categorie=new HashSet<>();
    }


    public static void controlloEsistenza(String nome) throws GestoreException {
        for (ListaSpesa lista : listeSpesa) {
            if (lista.getNome().equals(nome)) {
                throw new GestoreException("Esiste già una lista con questo nome");
            }
        }
    }

    public static void aggiungiLista(String nome) throws GestoreException {
        controlloEsistenza(nome);
        listeSpesa.add(new ListaSpesa(nome));
        aggiornaCategorie(new ListaSpesa(nome)); // Aggiorna le categorie dopo l'aggiunta della lista
    }

    public static void rimuoviLista(String nome) throws GestoreException {
        ListaSpesa lista = cercaLista(nome);
        if (lista != null) {
            listeSpesa.remove(lista);
            aggiornaCategorie(); // Aggiorna le categorie dopo la rimozione della lista
        } else {
            throw new GestoreException("Rimozione lista non avvenuta, il nome della lista inserito non esiste!");
        }
    }

    public static ListaSpesa cercaLista(String nome) {
        for (ListaSpesa lista : listeSpesa) {
            if (lista.getNome().equals(nome))
                return lista;
        }
        return null;
    }

    public static void leggiDaFile(String path, String nomeFile) throws GestoreException, IOException {
        if (!new File(path).exists())
            throw new GestoreException("Il path inserito non corrisponde ad alcun file!");
        controlloEsistenza(nomeFile);
        ListaSpesa listaSpesa = new ListaSpesa(nomeFile);
        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            String file = buffer.readLine();
        }
    }

    public static int numeroListe() {
        return listeSpesa.size();
    }

    private static void aggiornaCategorie(ListaSpesa listaSpesa) {
        for (Articolo articolo : listaSpesa.getArticoli()) {
            String categoria = articolo.getCategoria();
            categorie.add(categoria);
        }
    }

    private static void aggiornaCategorie() {
        categorie.clear(); // Svuota le categorie attuali
        for (ListaSpesa lista : listeSpesa) {
            for (Articolo articolo : lista.getArticoli()) {
                String categoria = articolo.getCategoria();
                categorie.add(categoria);
            }
        }
    }

    public static void rimuoviCategoriaProdotto(String categoria) {
        categorie.remove(categoria);
    }

    public static void aggiornaListe(String categoria) {
        for (ListaSpesa lista : listeSpesa) {
            for (Articolo articolo : lista) {
                if (articolo.getCategoria().equals(categoria))
                    articolo.setCategoria("Non categorizzato");
            }
        }
    }
}





