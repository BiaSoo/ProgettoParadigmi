package Classi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GestoreListeTest {

    @BeforeEach
    public void setup() {
        GestoreListe.getListeSpesa().clear();
    }

    @Test
    public void testAggiungiLista() throws GestoreException {
        GestoreListe.aggiungiLista("Lista1");
        assertEquals(1, GestoreListe.numeroListe(), "La lista dovrebbe essere aggiunta.");
    }

    @Test
    public void testAggiungiListaDuplicata() {
        try {
            GestoreListe.aggiungiLista("Lista1");
            GestoreListe.aggiungiLista("Lista1");
            fail("Dovrebbe lanciare un'eccezione se la lista esiste già.");
        } catch (GestoreException e) {
            assertEquals("Esiste già una lista con questo nome", e.getMessage());
        }
    }

    @Test
    public void testRimuoviLista() throws GestoreException {
        GestoreListe.aggiungiLista("Lista1");
        GestoreListe.rimuoviLista("Lista1");
        assertEquals(0, GestoreListe.numeroListe(), "La lista dovrebbe essere rimossa.");
    }

    @Test
    void testRimuoviListaNonEsistente() {
        String nomeListaInesistente = "ListaInesistente";

        GestoreException exception = assertThrows(GestoreException.class, () -> {
            GestoreListe.rimuoviLista(nomeListaInesistente);
        });

        assertTrue(exception.getMessage().contains("Lista della spesa non trovata!"),
                "Il messaggio di eccezione non corrisponde a quello atteso.");
    }


    @Test
    public void testRicercaLista() throws GestoreException {
        GestoreListe.aggiungiLista("Lista1");
        ListaSpesa lista = GestoreListe.ricercaLista("Lista1");
        assertNotNull(lista, "La lista dovrebbe essere trovata.");
    }

    @Test
    public void testRicercaListaNonEsistente() {
        try {
            GestoreListe.ricercaLista("ListaInesistente");
            fail("Dovrebbe lanciare un'eccezione se la lista non esiste.");
        } catch (GestoreException e) {
            assertEquals("Lista della spesa non trovata!", e.getMessage());
        }
    }

    @Test
    public void testLeggiDaFile() throws IOException, GestoreException {
        File tempFile = File.createTempFile("testFile", ".txt");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("Mela, 2, 0.5, Frutta\n");
        writer.write("Pane, 1, 1.0, Panetteria\n");
        writer.close();

        GestoreListe.leggiDaFile(tempFile.getAbsolutePath(), "Lista1");

        ListaSpesa lista = GestoreListe.ricercaLista("Lista1");
        assertNotNull(lista, "La lista dovrebbe essere stata aggiunta.");
        assertEquals(2, lista.getArticoli().size(), "La lista dovrebbe contenere due articoli.");

        tempFile.delete();
    }

    @Test
    public void testModificaCategoria() throws GestoreException, ListaException {
        GestoreListe.aggiungiLista("Lista1");
        ListaSpesa lista = GestoreListe.ricercaLista("Lista1");
        lista.aggiungiArticolo(new Articolo("Mela", 2, "Frutta", 0.5f));

        GestoreListe.aggiungiCategoria("Frutta");

        GestoreListe.modificaCategoria("Frutta", "Frutta fresca");
        assertEquals("Frutta fresca", lista.getArticoli().get(0).getCategoria(), "La categoria dovrebbe essere aggiornata.");
    }

    @Test
    public void testAggiungiCategoria() {
        GestoreListe.aggiungiCategoria("Frutta");
        assertNotNull(GestoreListe.cercaCategoria("Frutta"), "La categoria dovrebbe essere aggiunta.");
    }

    @Test
    public void testCancellaCategoria() {
        GestoreListe.aggiungiCategoria("Frutta");
        try {
            GestoreListe.cancellaCategoria("Frutta");
            assertNull(GestoreListe.cercaCategoria("Frutta"), "La categoria dovrebbe essere cancellata.");
        } catch (GestoreException e) {
            fail("Non dovrebbe lanciare eccezione durante la cancellazione di una categoria esistente.");
        }
    }

    @Test
    public void testSostituisciCategoria() throws GestoreException {
        GestoreListe.aggiungiLista("Lista1");
        ListaSpesa lista = GestoreListe.ricercaLista("Lista1");
        lista.aggiungiArticolo(new Articolo("Mela", 2, "Frutta", 0.5f));
        GestoreListe.sostituisciCategoria("Frutta", "Frutta fresca");
        assertEquals("Frutta fresca", lista.getArticoli().get(0).getCategoria(), "La categoria dovrebbe essere sostituita.");
    }
}
