package Classi;

import java.util.*;
import java.io.*;

public class ListaSpesa implements Iterable<Articolo>
{
    private String nome;
    private ArrayList<Articolo>articoli;
    public ListaSpesa(String nome, ArrayList<Articolo>articoli){
        this.nome=nome;
        this.articoli=articoli;
    }

    @Override
    public Iterator<Articolo> iterator() {
        return null;
    }
}
