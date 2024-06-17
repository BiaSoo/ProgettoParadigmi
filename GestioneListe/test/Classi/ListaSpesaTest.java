package Classi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ListaSpesaTest {

    private ListaSpesa listaSpesa;
    private Articolo articolo1;
    private Articolo articolo2;

    @BeforeEach
    public void setUp() {
        listaSpesa = new ListaSpesa("Spesa settimanale");
        articolo1 = new Articolo("Mele", 2, "Frutta", 1.5f);
        articolo2 = new Articolo("Latte", 1, "Bevande", 2.0f);
        listaSpesa.aggiungiArticolo(articolo1);
        listaSpesa.aggiungiArticolo(articolo2);
    }

    @Test
    public void testAggiungiArticolo() {
        Articolo articolo3 = new Articolo("Pane", 1, "Panetteria", 0.8f);
        listaSpesa.aggiungiArticolo(articolo3);

        int numeroArticoli = listaSpesa.getArticoli().size();

        assertEquals(3, numeroArticoli);
        assertTrue(listaSpesa.getArticoli().contains(articolo3));
    }

    @Test
    public void testCercaArticoloPerNome() {
        Articolo articoloTrovato = listaSpesa.cercaArticoloPerNome("Mele");

        assertNotNull(articoloTrovato);
        assertEquals("Mele", articoloTrovato.getNome());

        Articolo articoloNonTrovato = listaSpesa.cercaArticoloPerNome("Uova");

        assertNull(articoloNonTrovato);
    }

    @Test
    public void testRimuoviArticolo() throws ListaException {
        listaSpesa.rimuoviArticolo("Latte");

        int numeroArticoli = listaSpesa.getArticoli().size();
        assertFalse(listaSpesa.getArticoli().contains(articolo2));

        assertEquals(1, numeroArticoli);
    }

    @Test
    public void testRimuoviArticoloNonEsistente() throws ListaException {
        assertThrows(ListaException.class, () -> listaSpesa.rimuoviArticolo("Uova"));
    }

    @Test
    public void testCostoTotale() {
        double costoTot = listaSpesa.costoTotale();

        assertEquals(5.0f, costoTot);
    }

    @Test
    public void testArticoliPerCategoria() {
        ArrayList<Articolo> articoliFrutta = listaSpesa.ArticoliPerCategoria("Frutta");

        assertEquals(1, articoliFrutta.size());
        assertTrue(articoliFrutta.contains(articolo1));

        ArrayList<Articolo> articoliBevande = listaSpesa.ArticoliPerCategoria("Bevande");

        assertEquals(1, articoliBevande.size());
        assertTrue(articoliBevande.contains(articolo2));
    }

    @Test
    public void testScriviSuFile() throws ListaException {
        String nomeFile = "lista_spesa.txt";
        listaSpesa.scriviSuFile(nomeFile);
    }

    @Test
    public void testAggiornaArticoloValido() throws ListaException, ArticoloException {
        Articolo articoloDaAggiornare = listaSpesa.cercaArticoloPerNome("Mele");
        assertNotNull(articoloDaAggiornare, "Articolo con nome 'Mele' non trovato!");

        String nuovoNome = "Mele Golden";
        int nuovaQuantita = 3;
        String nuovaCategoria = "Frutta e verdura";
        float nuovoCosto = 1.8f;

        listaSpesa.aggiornaArticolo(articoloDaAggiornare, nuovoNome, nuovaQuantita, nuovaCategoria, nuovoCosto);
        Articolo articoloAggiornato = listaSpesa.cercaArticoloPerNome(nuovoNome);
        assertNotNull(articoloAggiornato);
        assertEquals(nuovoNome, articoloAggiornato.getNome());
        assertEquals(nuovaQuantita, articoloAggiornato.getQuantita());
        assertEquals(nuovaCategoria, articoloAggiornato.getCategoria());
        assertEquals(nuovoCosto, articoloAggiornato.getCosto());
    }


    @Test
    public void testAggiornaArticoloNonEsistente() throws ListaException {
        Articolo articoloNonEsistente = new Articolo("Uova", 1, "Alimentari",2.0f);
        assertThrows(ListaException.class, () -> listaSpesa.aggiornaArticolo(articoloNonEsistente, "Mele Golden", 3, "Frutta e verdura", 1.8f));
    }
}