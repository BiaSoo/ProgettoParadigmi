package Classi;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe per la gestione delle categorie
 * @author Gabriele Magenta Biasina Matricola: 20044231
 */
public class GestoreCategorie {
    private static Set<String> categorie = new HashSet<>();

    /**
     * Metodo per aggiungere una categoria
     * @param categoria
     */
    public void aggiungiCategoria(String categoria) {
        if (categorie.add(categoria)) {
            System.out.println("Categoria '" + categoria + "' aggiunta.");
        } else {
            System.out.println("Categoria '" + categoria + "' già presente.");
        }
    }

    /**
     * Metodo per la ricerca di una categoria
     * @param categoria
     * @return categoria trovata o non trovata
     */
    public static String cercaCategoria(String categoria) {
        return categorie.contains(categoria) ? categoria : null;
    }

    /**
     * Metodo per la cancellazione di una categoria
     * @param categoria
     * @throws GestoreException
     */
    public void cancellaCategoria(String categoria) throws GestoreException {
        if (!categorie.remove(categoria)) {
            throw new GestoreException("La categoria '" + categoria + "' non esiste!");
        } else {
            System.out.println("Categoria '" + categoria + "' cancellata.");
            GestoreListe.sostituisciCategoria(categoria, "non categorizzato");
        }
    }

    /**
     * Metodo per modificare il nome di una categoria
     * @param vecchiaCategoria
     * @param nuovaCategoria
     * @throws ListaException
     */
    public static void modificaCategoria(String vecchiaCategoria, String nuovaCategoria) throws ListaException {
        if (categorie.remove(vecchiaCategoria)) {
            categorie.add(nuovaCategoria);
        } else {
            throw new ListaException("Categoria '" + vecchiaCategoria + "' non trovata.");
        }
    }

    /**
     * Metodo per restituire la struttura set che contiene le categorie
     * @return categorie
     */
    public static Set<String> getCategorie() {
        return new HashSet<>(categorie);
    }

    /**
     * Metodo per aggiornare le categorie
     * @param nuoveCategorie
     */
    public static void aggiornaCategorie(Set<String> nuoveCategorie) {
        boolean aggiornata = false;
        for (String categoria : nuoveCategorie) {
            if (categorie.add(categoria)) {
                aggiornata = true;
            }
        }
        if (aggiornata) {
            System.out.println("Categorie aggiornate: " + categorie);
        }
    }


}
