package Classi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GestoreCategorieTest {

    @BeforeEach
    public void setUp() {
        GestoreCategorie.getCategorie().clear();
    }

    @Test
    public void testAggiungiCategoriaNuova() {
        String categoria = "Frutta";
        GestoreCategorie gestore = new GestoreCategorie();

        gestore.aggiungiCategoria(categoria);

        String categoriaCercata = GestoreCategorie.cercaCategoria(categoria);
        assertEquals(categoria, categoriaCercata);
        assertTrue(GestoreCategorie.getCategorie().contains(categoria));
    }

    @Test
    public void testAggiungiCategoriaDuplicata() {
        String categoria = "Verdura";
        GestoreCategorie gestore = new GestoreCategorie();
        gestore.aggiungiCategoria(categoria);
        gestore.aggiungiCategoria(categoria);
        Set<String> actualCategories = GestoreCategorie.getCategorie();
        assertTrue(actualCategories.contains(categoria));
    }

    @Test
    public void testCancellaCategoriaEsistente() throws GestoreException {
        String categoria = "Carne";
        GestoreCategorie gestore = new GestoreCategorie();

        gestore.aggiungiCategoria(categoria);
        gestore.cancellaCategoria(categoria);

        String categoriaCercata = GestoreCategorie.cercaCategoria(categoria);
        assertNull(categoriaCercata);
        assertFalse(GestoreCategorie.getCategorie().contains(categoria));
    }

    @Test
    public void testCancellaCategoriaInesistente() {
        String categoria = "Latticini";
        GestoreCategorie gestore = new GestoreCategorie();

        assertThrows(GestoreException.class, () -> gestore.cancellaCategoria(categoria));
    }

    @Test
    public void testModificaCategoriaValida() throws ListaException {
        String vecchiaCategoria = "Bevande";
        String nuovaCategoria = "Succhi";
        GestoreCategorie gestore = new GestoreCategorie();

        gestore.aggiungiCategoria(vecchiaCategoria);
        gestore.modificaCategoria(vecchiaCategoria, nuovaCategoria);

        String categoriaCercata = GestoreCategorie.cercaCategoria(nuovaCategoria);
        assertEquals(nuovaCategoria, categoriaCercata);
        assertFalse(GestoreCategorie.getCategorie().contains(vecchiaCategoria));
        assertTrue(GestoreCategorie.getCategorie().contains(nuovaCategoria));
    }

    @Test
    public void testModificaCategoriaInesistente() {
        String vecchiaCategoria = "Dolci";
        String nuovaCategoria = "Gelato";
        GestoreCategorie gestore = new GestoreCategorie();

        assertThrows(ListaException.class, () -> gestore.modificaCategoria(vecchiaCategoria, nuovaCategoria));
    }

    @Test
    public void testGetCategorie() {
        Set<String> expectedCategories = new HashSet<>();
        expectedCategories.add("Pane");
        expectedCategories.add("Pasta");

        GestoreCategorie gestore = new GestoreCategorie();
        gestore.aggiungiCategoria("Pane");
        gestore.aggiungiCategoria("Pasta");

        Set<String> actualCategories = GestoreCategorie.getCategorie();
        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    public void testAggiornaCategorie() {
        Set<String> nuoveCategorie = new HashSet<>();
        nuoveCategorie.add("Cereali");
        nuoveCategorie.add("Legumi");
        nuoveCategorie.add("Pasta");
        nuoveCategorie.add("Pane");
        nuoveCategorie.add("Frutta");

        GestoreCategorie gestore = new GestoreCategorie();
        gestore.aggiungiCategoria("Cereali");
        gestore.aggiornaCategorie(nuoveCategorie);

        Set<String> actualCategories = GestoreCategorie.getCategorie();
        assertEquals(nuoveCategorie, actualCategories);
    }
}
