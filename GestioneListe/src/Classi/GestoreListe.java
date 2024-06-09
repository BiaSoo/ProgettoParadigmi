package Classi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class GestoreListe {

    private static GestoreCategorie categoriaGestore = new GestoreCategorie();
    static final List<ListaSpesa> listeSpesa = new ArrayList<>();

    public GestoreListe() {
    }

    public static void controlloEsistenza(String nome) throws GestoreException {
        for (ListaSpesa lista : listeSpesa) {
            if (lista.getNome().equalsIgnoreCase(nome)) {
                throw new GestoreException("Esiste già una lista con questo nome");
            }
        }
    }

    public static void aggiungiLista(String nome) throws GestoreException {
        controlloEsistenza(nome);
        listeSpesa.add(new ListaSpesa(nome));
        aggiornaCategorie(); // Aggiorna le categorie dopo l'aggiunta della lista
    }

    public static void rimuoviLista(String nome) throws GestoreException {
        ListaSpesa lista = ricercaLista(nome);
        if (lista != null) {
            listeSpesa.remove(lista);
            aggiornaCategorie(); // Aggiorna le categorie dopo la rimozione della lista
        } else {
            throw new GestoreException("Rimozione lista non avvenuta, il nome della lista inserito non esiste!");
        }
    }

    public static ListaSpesa ricercaLista(String nome) throws GestoreException {
        for (ListaSpesa listaSpesa : GestoreListe.listeSpesa) {
            if (listaSpesa.getNome().equalsIgnoreCase(nome)) {
                return listaSpesa;
            }
        }
        throw new GestoreException("Lista della spesa non trovata!");
    }

    public static void leggiDaFile(String path, String nomeFile) throws GestoreException, IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new GestoreException("Il path inserito non corrisponde ad alcun file!");
        }
        controlloEsistenza(nomeFile);
        ListaSpesa listaSpesa = new ListaSpesa(nomeFile);
        try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                // Logica per leggere e aggiungere articoli alla listaSpesa
            }
        }
        listeSpesa.add(listaSpesa);
        aggiornaCategorie(); // Aggiorna le categorie dopo l'aggiunta della lista dal file
    }

    public static int numeroListe() {
        return listeSpesa.size();
    }

    public static void aggiornaCategorie() {
        Set<String> nuoveCategorie = new HashSet<>();
        for (ListaSpesa lista : listeSpesa) {
            for (Articolo articolo : lista.getArticoli()) {
                nuoveCategorie.add(articolo.getCategoria());
            }
        }
        categoriaGestore.aggiornaCategorie(nuoveCategorie);
    }

    public static void modificaCategoria(String vecchiaCategoria, String nuovaCategoria) throws ListaException {
        categoriaGestore.modificaCategoria(vecchiaCategoria, nuovaCategoria);

        for (ListaSpesa lista : listeSpesa) {
            for (Articolo articolo : lista.getArticoli()) {
                if (articolo.getCategoria().equalsIgnoreCase(vecchiaCategoria)) {
                    articolo.setCategoria(nuovaCategoria);
                }
            }
        }
        System.out.println("Categoria '" + vecchiaCategoria + "' modificata in '" + nuovaCategoria + "' e articoli aggiornati.");
    }

    public static void aggiungiCategoria(String categoria) {
        categoriaGestore.aggiungiCategoria(categoria);
    }

    public static String cercaCategoria(String categoria) {
        return categoriaGestore.cercaCategoria(categoria);
    }

    public static void cancellaCategoria(String categoria) throws GestoreException {
        categoriaGestore.cancellaCategoria(categoria);
    }

    public static void sostituisciCategoria(String vecchiaCategoria, String nuovaCategoria) {
        for (ListaSpesa lista : listeSpesa) {
            for (Articolo articolo : lista.getArticoli()) {
                if (articolo.getCategoria().equalsIgnoreCase(vecchiaCategoria)) {
                    articolo.setCategoria(nuovaCategoria);
                }
            }
        }
    }

    public static List<ListaSpesa> getListeSpesa() {
        return listeSpesa;
    }
}

