package Classi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import jbook.util.Input;
import Classi.GestoreListe;
import Classi.ListaSpesa;
import Classi.Articolo;

public class InterfacciaUtente
{
    public static void main(String[] args) throws GestoreException {
        int scelta;
        String x,nome;
        do {
            System.out.println("""
    		        MENU OPERAZIONI:
    		        \n1 Creazione nuova lista della spesa
    		        \n2 Cancellazione lista della spesa
    		        \n3 Creazione nuova lista da file
                    \n4 Scrittura lista su file
                  	\n5 Visualizzazione liste della spesa
                    \n6 Modifica lista della spesa
                  	\n7 Uscita dal programma""");
            System.out.println("Effettua una scelta: ");

            switch(Integer.parseInt(Input.readString())){

                case 1:
                    System.out.println("CREAZIONE LISTA DELLA SPESA\n");
                    System.out.println("Inserisci il nome della lista che vuoi creare: ");
                    try{
                        GestoreListe.aggiungiLista(Input.readString());
                        System.out.println("Lista creata correttamente!");
                    }
                    catch (GestoreException e)
                    {
                        System.out.println("ERRORE: "+e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("CANCELLAZIONE LISTA DELLA SPESA\n");
                    System.out.print("Inserisci il nome della lista che si intende cancellare: ");
                    try
                    {
                        GestoreListe.rimuoviLista(Input.readString());
                        System.out.println("Lista cancellata correttamente!");
                    } catch (GestoreException e)
                    {
                        System.out.println("ERRORE: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("CREAZIONE LISTA DELLA SPESA DA FILE\n");
                    System.out.print("Inserisci il percorso del file da cui cercare la lista: ");
                    String path =Input.readString();
                    System.out.print("Inserisci il nome della nuova lista: ");
                    String nuovo = Input.readString();
                    try
                    {
                        GestoreListe.leggiDaFile(path,nuovo);
                        System.out.println("Lista creata da file correttamente!");
                    }
                    catch (IOException | GestoreException e) {
                        System.out.println("ERRORE: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("SCRITTURA LISTA DELLA SPESA SU FILE\n");
                    break;

                case 5:
                    System.out.println("VISUALIZZAZIONE LISTE DELLA SPESA\n");
                    int numeroListe=GestoreListe.numeroListe();
                    if(numeroListe<=0)
                        System.out.println("Nessuna lista presente!");
                    else {
                        for(ListaSpesa listaSpesa: GestoreListe.listeSpesa)
                            System.out.println(listaSpesa.toString());
                    }
                    break;

                case 6:
                    System.out.println("MODIFICA LISTA DELLA SPESA\n");
                    try {
                        controlloListe();
                    } catch (GestoreException e)
                    {
                        System.out.println(e.getMessage());
                        break;
                    }

                    ListaSpesa listaSpesa;
                    System.out.println("In quale lista si vuole effettuare delle modifiche?");
                    listaSpesa=GestoreListe.cercaLista(nome=Input.readString());

            }
        }while(true);


    }

    private static ListaSpesa ricercaLista(String nome) throws GestoreException{
        for(ListaSpesa listaSpesa: GestoreListe.listeSpesa){
            if(listaSpesa.getNome().equals(nome))
                return listaSpesa;
        }
        throw new GestoreException(("Lista della spesa non trovata!"));
    }

    private static void controlloListe()throws GestoreException {
        if(GestoreListe.listeSpesa.isEmpty())
            throw new GestoreException("Non risulta presenta alcuna lista della spesa, creane prima una!");
    }
}