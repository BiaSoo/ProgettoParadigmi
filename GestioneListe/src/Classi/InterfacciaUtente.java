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
    public static void main(String[] args) {
        int scelta;
        String x;
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
            }
        }while(true);
        

    }
}