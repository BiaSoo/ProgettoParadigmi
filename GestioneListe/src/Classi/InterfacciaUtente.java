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
        String x,nomeLista="",nomeArticolo="";
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

            switch (Integer.parseInt(Input.readString())) {

                case 1:
                    System.out.println("CREAZIONE LISTA DELLA SPESA\n");
                    System.out.println("Inserisci il nome della lista che vuoi creare: ");
                    try {
                        GestoreListe.aggiungiLista(Input.readString());
                        System.out.println("Lista creata correttamente!");
                    } catch (GestoreException e) {
                        System.out.println("ERRORE: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("CANCELLAZIONE LISTA DELLA SPESA\n");
                    System.out.print("Inserisci il nome della lista che si desidera cancellare: ");
                    try {
                        GestoreListe.rimuoviLista(Input.readString());
                        System.out.println("Lista cancellata correttamente!");
                    } catch (GestoreException e) {
                        System.out.println("ERRORE: " + e.getMessage());
                    }
                    break;

                case 3: /*
                    System.out.println("CREAZIONE LISTA DA FILE\n");
                    System.out.print("Inserisci il percorso del file: ");
                    String filePath =Input.readString();
                    System.out.print("Inserisci il nome della lista da inserire: ");
                    String nuovo = Input.readString();
                    try
                    {
                        GestoreListe.creaDaFile(filePath, nuovo);
                        System.out.println("Lista creata da file con successo.");
                    }
                    catch ( e) {
                        System.out.println("ERRORE: " + e.getMessage());
                    }*/
                    break;

                case 4:

                    break;

                case 5:
                    System.out.println("VISUALIZZAZIONE LISTE\n");
                    int numeroListe = GestoreListe.numeroListe();
                    if (numeroListe <= 0) {
                        System.out.println("Nessuna lista trovata!");
                    } else {
                        for (ListaSpesa listaSpesa : GestoreListe.listeSpesa) {
                            System.out.println(listaSpesa.toString());
                        }
                    }
                    break;

                case 6:
                    System.out.println("MODIFICA LISTA DELLA SPESA\n");
                    try {
                        controlloLista();
                    } catch (GestoreException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    ListaSpesa listaSpesa;
                    try {
                        System.out.println("Inserire in quale lista della spesa si desidera apportare delle modifiche: ");
                        listaSpesa=cercaLista(nomeLista=Input.readString());
                    }catch (GestoreException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    do{
                        System.out.println("""
	            				MENU OPERAZIONI:
	            				\n1 Aggiunta di un articolo
	            				\n2 Eliminazione di un articolo	
	            				\n3 Modifica di un articolo
	            				\n4 Visualizzazione intera lista della spesa	
	            				\n5 Menù precedente
	            				""");
                        System.out.println("Effettua una scelta: ");

                        switch(Integer.parseInt(Input.readString())){

                            case 1:
                                System.out.println("AGGIUNTA DI UN ARTICOLO\n");
                                /* inserisco il nome
                                    controllo se esiste già
                                    se non esiste
                                            inserisco
                                    altrimenti
                                        chiedo se si vuole aggiungere una nuova quantità
                                        se si
                                            inserisco la nuova quantità e visualizzo il totale dei pezzi di quel articolo
                                        se no non inserisco
                                 */
                                break;

                            case 2:
                                System.out.println("ELIMINAZIONE DI UN ARTICOLO\n");
                                System.out.println("Inserisci l'articoo che si desidera eliminare");
                                try{
                                    listaSpesa.rimuoviArticolo(Input.readString());
                                }catch (GestoreException e){
                                    System.out.println("Non è stato possibile rimuovere l'articolo indicato!");
                                    System.out.println("ERRORE: "+e.getMessage());
                                }
                                break;
                        }
                    }
                    while(true);


                case 7:
                    System.out.println("USCITA DAL PROGRAMMA\n");
                    System.out.println("Premi invio per uscire dal programma: ");
                    x=Input.readString();
                    break;

                default:
                    System.out.println("ERRORE La scelta inserita non è valida!");
                    break;
            }

        } while (true);
    }
        private static void controlloLista() throws GestoreException{
            if(GestoreListe.listeSpesa.isEmpty()){
                throw new GestoreException("Nessuna lista trovata, aggiungine una!");
            }
        }

        private static ListaSpesa cercaLista(String nome) throws GestoreException{
            for(ListaSpesa listaSpesa: GestoreListe.listeSpesa){
                if(listaSpesa.getNome().equals(nome)){
                    return listaSpesa;
                }
            }
            throw new GestoreException("Lista della spesa non trovata!");
        }
}




