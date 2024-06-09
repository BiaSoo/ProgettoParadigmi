package Classi;

import java.util.HashSet;
import java.util.Set;

public class GestoreCategorie {
    private static Set<String> categorie = new HashSet<>();

    public void aggiungiCategoria(String categoria) {
        if (categorie.add(categoria)) {
            System.out.println("Categoria '" + categoria + "' aggiunta.");
        } else {
            System.out.println("Categoria '" + categoria + "' già presente.");
        }
    }

    public static String cercaCategoria(String categoria) {
        return categorie.contains(categoria) ? categoria : null;
    }

    public void cancellaCategoria(String categoria) throws GestoreException {
        if (!categorie.remove(categoria)) {
            throw new GestoreException("La categoria '" + categoria + "' non esiste!");
        } else {
            System.out.println("Categoria '" + categoria + "' cancellata.");
            GestoreListe.sostituisciCategoria(categoria, "non categorizzato");
        }
    }


    public static void modificaCategoria(String vecchiaCategoria, String nuovaCategoria) throws ListaException {
        if (categorie.remove(vecchiaCategoria)) {
            categorie.add(nuovaCategoria);
        } else {
            throw new ListaException("Categoria '" + vecchiaCategoria + "' non trovata.");
        }
    }

    public static Set<String> getCategorie() {
        return new HashSet<>(categorie);  // Restituisce una copia per evitare modifiche esterne
    }

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
