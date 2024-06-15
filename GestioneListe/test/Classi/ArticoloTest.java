package Classi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticoloTest {

    private Articolo articolo;

    @BeforeEach
    void setUp() {
        articolo = new Articolo("Pomodori", 2, "Verdura", 1.5f);
    }

    @Test
    void testGetNome() {
        assertEquals("Pomodori", articolo.getNome());
    }

    @Test
    void testSetNome() {
        articolo.setNome("Cipolle");
        assertEquals("Cipolle", articolo.getNome());
    }

    @Test
    void testGetQuantita() {
        assertEquals(2, articolo.getQuantita());
    }

    @Test
    void testSetQuantita() {
        articolo.setQuantita(3);
        assertEquals(3, articolo.getQuantita());
    }

    @Test
    void testGetCategoria() {
        assertEquals("Verdura", articolo.getCategoria());
    }

    @Test
    void testSetCategoria() {
        articolo.setCategoria("Frutta");
        assertEquals("Frutta", articolo.getCategoria());
    }

    @Test
    void testGetCosto() {
        assertEquals(1.5f, articolo.getCosto(), 0.001);
    }

    @Test
    void testSetCosto() {
        articolo.setCosto(2.0f);
        assertEquals(2.0f, articolo.getCosto(), 0.001);
    }

    @Test
    void testToString() {
        String expected = "Nome: Pomodori, Quantità: 2, Categoria: Verdura, Costo: 1.5";
        assertEquals(expected, articolo.toString());
    }
}
