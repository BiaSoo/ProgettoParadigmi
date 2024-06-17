package Classi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterfacciaUtenteTest {

    private ListaSpesa listaSpesa;
    private String[] simulatedInput;
    private int simulatedInputIndex;

    @BeforeEach
    void setUp() {
        listaSpesa = new ListaSpesa("Lista di test");

        simulatedInput = new String[]{"Pomodori", "", "2", "2.5"};
        simulatedInputIndex = 0;
    }

    private String readString(String prompt) {
        System.out.println(prompt);
        return simulatedInput[simulatedInputIndex++];
    }

    @Test
    void testAggiungiArticolo() {
        String nomeArticolo = readString("Inserisci il nome dell'articolo: ").trim();
        String inputCategoria = readString("Inserisci la categoria dell'articolo: ").trim();
        String inputQuantita = readString("Inserisci la quantità dell'articolo: ").trim();
        String costoArticolo = readString("Inserisci il costo dell'articolo: ").trim();
        String categoriaArticolo = inputCategoria.isEmpty() ? "non categorizzato" : inputCategoria;

        int quantitaArticolo;
        try {
            quantitaArticolo = Integer.parseInt(inputQuantita);
        } catch (NumberFormatException e) {
            quantitaArticolo = 1;
        }

        Articolo nuovoArticolo = new Articolo(nomeArticolo, quantitaArticolo, categoriaArticolo, Float.parseFloat(costoArticolo));
        listaSpesa.aggiungiArticolo(nuovoArticolo);

        assertEquals(1, listaSpesa.getArticoli().size());
        Articolo articoloAggiunto = listaSpesa.getArticoli().get(0);
        assertEquals("Pomodori", articoloAggiunto.getNome());
        assertEquals("non categorizzato", articoloAggiunto.getCategoria());
        assertEquals(2, articoloAggiunto.getQuantita());
        assertEquals(2.5f, articoloAggiunto.getCosto());
    }

    @Test
    void testCercaArticoliPerPrefisso() {
        listaSpesa.aggiungiArticolo(new Articolo("Pane", 1, "Alimenti", 2.0f));
        listaSpesa.aggiungiArticolo(new Articolo("Pasta", 3, "Alimenti", 1.0f));
        listaSpesa.aggiungiArticolo(new Articolo("Patate", 2, "Verdura", 0.8f));
        listaSpesa.aggiungiArticolo(new Articolo("Uova", 6, "Uova", 3.0f));

        ArrayList<Articolo> risultato = InterfacciaUtente.cercaArticoliPerPrefisso(listaSpesa, "Pa");

        assertEquals(3, risultato.size());

        assertEquals("Pane", risultato.get(0).getNome());
        assertEquals("Pasta", risultato.get(1).getNome());
    }
}
