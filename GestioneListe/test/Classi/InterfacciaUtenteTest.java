package Classi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterfacciaUtenteTest {

    private ListaSpesa listaSpesa;

    // Variabile per simulare l'input durante i test
    private String[] simulatedInput;
    private int simulatedInputIndex;

    @BeforeEach
    void setUp() {
        listaSpesa = new ListaSpesa("Lista di test");

        // Inizializza l'input simulato
        simulatedInput = new String[]{"Pomodori", "", "2", "2.5"}; // Quantità "2"
        simulatedInputIndex = 0;
    }

    // Metodo per leggere l'input simulato
    private String readString(String prompt) {
        // Simula la lettura dell'input dall'utente
        System.out.println(prompt); // Puoi rimuovere questa riga se non vuoi output nel test
        return simulatedInput[simulatedInputIndex++];
    }

    @Test
    void testAggiungiArticolo() {
        // Simuliamo l'input per il test
        String nomeArticolo = readString("Inserisci il nome dell'articolo: ").trim();
        String inputCategoria = readString("Inserisci la categoria dell'articolo: ").trim();
        String inputQuantita = readString("Inserisci la quantità dell'articolo: ").trim();
        String costoArticolo = readString("Inserisci il costo dell'articolo: ").trim();

        // Aggiungi categoria se è stata fornita
        String categoriaArticolo = inputCategoria.isEmpty() ? "non categorizzato" : inputCategoria;

        // Conversione della quantità in intero
        int quantitaArticolo;
        try {
            quantitaArticolo = Integer.parseInt(inputQuantita);
        } catch (NumberFormatException e) {
            quantitaArticolo = 1; // Imposta la quantità predefinita se non è stato fornito un numero valido
        }

        // Aggiungi l'articolo alla lista
        Articolo nuovoArticolo = new Articolo(nomeArticolo, quantitaArticolo, categoriaArticolo, Float.parseFloat(costoArticolo));
        listaSpesa.aggiungiArticolo(nuovoArticolo);

        // Verifica l'aggiunta dell'articolo nella lista
        assertEquals(1, listaSpesa.getArticoli().size());
        Articolo articoloAggiunto = listaSpesa.getArticoli().get(0);
        assertEquals("Pomodori", articoloAggiunto.getNome());
        assertEquals("non categorizzato", articoloAggiunto.getCategoria()); // Categoria predefinita
        assertEquals(2, articoloAggiunto.getQuantita()); // Quantità aggiornata
        assertEquals(2.5f, articoloAggiunto.getCosto());
    }

    @Test
    void testCercaArticoliPerPrefisso() {
        // Aggiungi articoli alla lista della spesa

        listaSpesa.aggiungiArticolo(new Articolo("Pane", 1, "Alimenti", 2.0f));
        listaSpesa.aggiungiArticolo(new Articolo("Pasta", 3, "Alimenti", 1.0f));
        listaSpesa.aggiungiArticolo(new Articolo("Patate", 2, "Verdura", 0.8f));
        listaSpesa.aggiungiArticolo(new Articolo("Uova", 6, "Uova", 3.0f));

        // Esegui la ricerca per prefisso
        ArrayList<Articolo> risultato = InterfacciaUtente.cercaArticoliPerPrefisso(listaSpesa, "Pa");

        // Verifica il numero di articoli trovati per il prefisso "Pa"
        assertEquals(3, risultato.size());

        // Verifica che gli articoli trovati siano quelli attesi
        assertEquals("Pane", risultato.get(0).getNome());
        assertEquals("Pasta", risultato.get(1).getNome());
    }
}
