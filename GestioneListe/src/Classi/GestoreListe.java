package Classi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe per la gestione delle liste
 * @author Gabriele Magenta Biasina Matricola: 20044231
 */
public class GestoreListe {

    private static GestoreCategorie categoriaGestore = new GestoreCategorie();
    static final List<ListaSpesa> listeSpesa = new ArrayList<>();

    /**
     * Metodo per il controllo dell'esistenza di una lista dato il nome
     * @param nome
     * @throws GestoreException
     */
    public static void controlloEsistenza(String nome) throws GestoreException {
        for (ListaSpesa lista : listeSpesa) {
            if (lista.getNome().equalsIgnoreCase(nome)) {
                throw new GestoreException("Esiste già una lista con questo nome");
            }
        }
    }

    /**
     * Metodo per l'aggiunta di una lista
     * @param nome
     * @throws GestoreException
     */
    public static void aggiungiLista(String nome) throws GestoreException {
        controlloEsistenza(nome);
        listeSpesa.add(new ListaSpesa(nome));
        aggiornaCategorie();
    }

    /**
     * Metodo per la rimozione di una lista
     * @param nome
     * @throws GestoreException
     */
    public static void rimuoviLista(String nome) throws GestoreException {
        ListaSpesa lista = ricercaLista(nome);
        if (lista != null) {
            listeSpesa.remove(lista);
            aggiornaCategorie();
        } else {
            throw new GestoreException("Rimozione lista non avvenuta, il nome della lista inserito non esiste!");
        }
    }

    /**
     * Metodo per la ricerca di una lista dato il suo nome
     * @param nome
     * @return lista spesa
     * @throws GestoreException
     */
    public static ListaSpesa ricercaLista(String nome) throws GestoreException {
        for (ListaSpesa listaSpesa : GestoreListe.listeSpesa) {
            if (listaSpesa.getNome().equalsIgnoreCase(nome)) {
                return listaSpesa;
            }
        }
        throw new GestoreException("Lista della spesa non trovata!");
    }

    /**
     * Metodo per la creazione di una lista da file
     * @param path
     * @param nomeLista
     */
    public static void leggiDaFile(String path, String nomeLista) {
        BufferedReader reader = null;
        try {
            aggiungiLista(nomeLista);

            ListaSpesa nuovaLista = null;
            for (ListaSpesa lista : listeSpesa) {
                if (lista.getNome().equals(nomeLista)) {
                    nuovaLista = lista;
                    break;
                }
            }

            if (nuovaLista == null) {
                throw new GestoreException("Errore nell'aggiunta della lista.");
            }

            reader = new BufferedReader(new FileReader(path));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                if (parts.length == 4) {
                    String nomeArticolo = parts[0].trim();
                    int quantita = Integer.parseInt(parts[1].trim());
                    float prezzo = Float.parseFloat(parts[2].trim());
                    String categoria = parts[3].trim();

                    Articolo articolo = new Articolo(nomeArticolo, quantita, categoria, prezzo);
                    nuovaLista.aggiungiArticolo(articolo);
                }
            }
            aggiornaCategorie();

            System.out.println("Lista aggiunta con successo!");
        } catch (IOException | NumberFormatException | GestoreException e) {
            System.out.println("Errore durante la lettura del file o l'aggiunta della lista: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Errore nella chiusura del reader: " + e.getMessage());
                }
            }
        }
    }


    /**
     * Metodo che ritorna il numero di liste inserite all'interno del gestore
     * @return listespesa size
     */
    public static int numeroListe() {
        return listeSpesa.size();
    }

    /**
     * Metodo per aggiornare le categorie
     */
    public static void aggiornaCategorie() {
        Set<String> nuoveCategorie = new HashSet<>();
        for (ListaSpesa lista : listeSpesa) {
            for (Articolo articolo : lista.getArticoli()) {
                nuoveCategorie.add(articolo.getCategoria());
            }
        }
        categoriaGestore.aggiornaCategorie(nuoveCategorie);
    }

    /**
     * Metodo per la modifica di una categoria
     * @param vecchiaCategoria
     * @param nuovaCategoria
     * @throws ListaException
     */
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

    /**
     * Metodo che richiama direttamente aggiungi categoria
     * @param categoria
     */
    public static void aggiungiCategoria(String categoria) {
        categoriaGestore.aggiungiCategoria(categoria);
    }

    /**
     * Metodo che richiama direttamente la ricerca di una categoria
     * @param categoria
     * @return
     */
    public static String cercaCategoria(String categoria) {
        return categoriaGestore.cercaCategoria(categoria);
    }

    /**
     * Metodo che richiama direttamente la cancellazione di una categoria
     * @param categoria
     * @throws GestoreException
     */
    public static void cancellaCategoria(String categoria) throws GestoreException {
        categoriaGestore.cancellaCategoria(categoria);
    }

    /**
     * Metodo per la sostituzione di una categoria
     * @param vecchiaCategoria
     * @param nuovaCategoria
     */
    public static void sostituisciCategoria(String vecchiaCategoria, String nuovaCategoria) {
        for (ListaSpesa lista : listeSpesa) {
            for (Articolo articolo : lista.getArticoli()) {
                if (articolo.getCategoria().equalsIgnoreCase(vecchiaCategoria)) {
                    articolo.setCategoria(nuovaCategoria);
                }
            }
        }
    }

    /**
     * Getter per la lista della spesa
     * @return lista spesa
     */
    public static List<ListaSpesa> getListeSpesa() {
        return listeSpesa;
    }
}

