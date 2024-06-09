package Classi;

import jbook.util.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static Classi.GestoreListe.*;


public class InterfacciaUtente extends JFrame {
    private static boolean isGUI = true; // Flag per tenere traccia dello stato dell'interfaccia

    public static class InterfacciaSwing extends JFrame {

        private static JTextArea textArea;

        public void updateTextArea(String text) {
            if (textArea != null) {
                textArea.append(text + "\n");
            }
        }

        public InterfacciaSwing() {
            // Implementazione dell'interfaccia grafica
            super("Interfaccia Grafica");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 500);

            textArea = new JTextArea();

            // Pannello principale
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
            JLabel labeltitolo = new JLabel("Benvenuto! ");
            titlePanel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
            titlePanel.add(labeltitolo);
            titlePanel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
            mainPanel.add(titlePanel);

            // Passaggio alla CLI
            JPanel buttonPanel = new JPanel();
            JButton switchToCLIButton = new JButton("Passa a CLI");
            switchToCLIButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isGUI = false; // Imposta il flag su false per passare a CLI
                    dispose(); // Chiudi l'interfaccia grafica
                    try {
                        InterfacciaCLI(); // Avvia l'interfaccia da riga di comando
                    } catch (ListaException | GestoreException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            JButton stayOnGUIButton = new JButton("Rimani su GUI");
            stayOnGUIButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isGUI = true; // Imposta il flag su true per restare sulla GUI
                    InterfacciaUtenteGUI interfacciaUtenteGUI = new InterfacciaUtenteGUI();
                    interfacciaUtenteGUI.Interfaccia(); // Interfaccia grafica
                    stayOnGUIButton.setVisible(false);
                    switchToCLIButton.setVisible(false);
                    dispose();
                }
            });

            buttonPanel.add(switchToCLIButton);
            buttonPanel.add(stayOnGUIButton);

            mainPanel.add(buttonPanel, BorderLayout.CENTER);

            add(mainPanel);
        }
    }


    public static void main(String[] args) throws ListaException, GestoreException {
        if (isGUI) {
            // Avvia l'interfaccia grafica
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new InterfacciaSwing().setVisible(true);
                }
            });
        } else {
            // Avvia l'interfaccia da riga di comando
            InterfacciaCLI();
        }
    }

    private static void InterfacciaCLI() throws ListaException, GestoreException {
        int scelta=0;
        String x, nome, nomeArticolo = "", categoriaArticolo = "", quantitaArticolo = "", costoArticolo = "", file = "lista.txt";

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InterfacciaSwing interfaccia = new InterfacciaSwing();
                interfaccia.setVisible(true);
            }
        });
        do {
            System.out.println("""
                    MENU OPERAZIONI:
                    \n1 Creazione nuova lista della spesa
                    \n2 Cancellazione lista della spesa
                    \n3 Creazione nuova lista da file
                    \n4 Scrittura lista su file
                    \n5 Visualizzazione liste della spesa
                    \n6 Modifica lista della spesa
                    \n7 Ricerca rapida articolo
                    \n8 Visualizzazione lista della spesa
                    \n9 Aggiunta categoria
                    \n10 Cancellazione categoria
                    \n11 Modifica categoria
                    \n12 Uscita dal programma""");
            System.out.println("Inserisci l'operazione che si desidera effettuare: ");

            switch (scelta=Input.readInt()) {

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
                    System.out.print("Inserisci il nome della lista che si intende cancellare: ");
                    try {
                        GestoreListe.rimuoviLista(Input.readString());
                        System.out.println("Lista cancellata correttamente!");
                    } catch (GestoreException e) {
                        System.out.println("ERRORE: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("CREAZIONE LISTA DELLA SPESA DA FILE\n");
                    System.out.print("Inserisci il percorso del file da cui cercare la lista: ");
                    String path = Input.readString();
                    System.out.print("Inserisci il nome della nuova lista: ");
                    String nuovo = Input.readString();
                    try {
                        GestoreListe.leggiDaFile(path, nuovo);
                        System.out.println("Lista creata da file correttamente!");
                    } catch (IOException | GestoreException e) {
                        System.out.println("ERRORE: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("SCRITTURA LISTA DELLA SPESA SU FILE\n");
                    System.out.println("Inserisci il nome della lista che si vuole scrivere sul file: ");
                    nome = Input.readString();
                    if (nome != "") {
                        ListaSpesa listaSpesa;
                        try {
                            listaSpesa = ricercaLista(nome);
                            listaSpesa.scriviSuFile(file);
                        } catch (GestoreException e) {
                            System.out.println(e.getMessage());
                        }
                    } else
                        System.out.println("Lista della spesa non trovata!");
                    break;

                case 5:
                    System.out.println("VISUALIZZAZIONE LISTE DELLA SPESA\n");
                    int numeroListe = GestoreListe.numeroListe();
                    if (numeroListe <= 0)
                        System.out.println("Nessuna lista presente!");
                    else {
                        if (numeroListe == 1)
                            System.out.println("Ecco la seguente lista memorizzata:");
                        else
                            System.out.println("Ecco le seguenti liste memorizzate:");

                        for (ListaSpesa listaSpesa : GestoreListe.listeSpesa)
                            System.out.println(listaSpesa.getNome());
                    }

                    break;

                case 6:
                    System.out.println("MODIFICA LISTA DELLA SPESA\n");
                    try {
                        controlloListe();
                    } catch (GestoreException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    ListaSpesa listaSpesa=null;
                    while (listaSpesa == null) {
                        System.out.println("In quale lista si vuole effettuare delle modifiche?");
                        listaSpesa = ricercaLista(nome = Input.readString());
                        if (listaSpesa == null) {
                            System.out.println("Il nome della lista inserita non esiste! Riprova.");
                        }
                    }

                    do {
                        System.out.println("""
                                MENU OPERAZIONI DELLA LISTA: """ + listaSpesa.getNome() +
                                """
                                        \n0 Modifica nome lista della spesa
                                        \n1 Aggiunta articolo
                                        \n2 Elimina articolo	
                                        \n3 Modifica articolo
                                        \n4 Ricerca articoli per categoria	
                                        \n5 Uscita
                                        """);

                        System.out.println("Inserisci l'operazione che si desidera effettuare: ");
                        switch (Integer.parseInt(Input.readString())) {
                            case 0:
                                System.out.println("MODIFICA NOME LISTA DELLA SPESA \n");
                                System.out.println("Attualmente il nome della tua lista è: " + listaSpesa.getNome());
                                String modifica;
                                do {
                                    modifica = Input.readString("Vuoi modificarne il nome? (si/no) ");
                                } while (!isSi(modifica) && !modifica.equalsIgnoreCase("no"));

                                if (isSi(modifica)) {
                                    String nuovonome="";
                                    nuovonome =Input.readString("Inserisci il nuovo nome della lista: ");

                                        if (nuovonome.equals("")) {
                                            try {
                                                throw new ListaException("Il nome non può essere vuoto!");
                                            } catch (ListaException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                        else {
                                            listaSpesa.setNome(nuovonome);
                                            System.out.println("Il nuovo nome della lista è: " +nuovonome);
                                            break;
                                        }
                                }
                                else
                                   break;

                            case 1:
                                System.out.println("AGGIUNTA ARTICOLO \n");
                                aggiungiArticolo(listaSpesa);
                                break;
                            case 2:
                                System.out.println("ELIMINA ARTICOLO \n");
                                System.out.println("Inserisci l'articolo da eliminare");
                                try {
                                    listaSpesa.rimuoviArticolo(Input.readString());

                                } catch (ListaException e) {
                                    System.out.println("Articolo non rimosso!");
                                    System.out.println("ERRORE: " + e.getMessage());
                                }
                                break;

                            case 3:
                                System.out.println("MODIFICA ARTICOLO \n");

                                Articolo articolo;
                                System.out.println("A quale articolo vorresti effettuare delle modifiche? ");
                                articolo = listaSpesa.cercaArticoloPerNome(Input.readString());
                                if (articolo != null) {
                                    // Procedi con le modifiche all'articolo
                                    System.out.println("Articolo trovato: " + articolo.getNome());
                                    // Aggiungi qui il codice per modificare l'articolo
                                } else {
                                    System.out.println("Articolo inserito non trovato!");
                                }

                                System.out.println("""
                                        MENU OPERAZIONI del seguente articolo :""" + articolo.getNome() + """
                                        \n1 Modifica nome articolo 
                                        \n2 Modifica quantita' articolo 
                                        \n3 Modifica categoria articolo
                                        \n4 Modifica costo articolo
                                        \n5 Uscita 
                                        """);

                                ArrayList<Articolo> articoli = listaSpesa.getArticoli();
                                if (!articoli.isEmpty()) {
                                    Articolo ar = articoli.get(0);
                                    try {
                                        switch (Integer.parseInt(Input.readString())) {
                                            case 1:
                                                System.out.println("MODIFICA NOME ARTICOLO \n");
                                                try {
                                                    listaSpesa.aggiornaArticolo(ar, Input.readString("Inserisci il nuovo nome dell'articolo: "), ar.getQuantita(), ar.getCategoria(), ar.getCosto());
                                                } catch (ListaException e) {
                                                    e.printStackTrace();
                                                }
                                                break;

                                            case 2:
                                                System.out.println("MODIFICA QUANTITA' ARTICOLO \n");
                                                try {
                                                    listaSpesa.aggiornaArticolo(ar, ar.getNome(), Integer.parseInt(Input.readString("Inserisci la nuova quantita' dell'articolo: ")), ar.getCategoria(), ar.getCosto());
                                                } catch (ListaException e) {
                                                    e.printStackTrace();
                                                }
                                                break;

                                            case 3:
                                                System.out.println("MODIFICA CATEGORIA ARTICOLO \n");
                                                try {
                                                    listaSpesa.aggiornaArticolo(ar, ar.getNome(), ar.getQuantita(), Input.readString("Inserisci la nuova categoria dell'articolo: "), ar.getCosto());
                                                } catch (ListaException e) {
                                                    e.printStackTrace();
                                                }
                                                break;

                                            case 4:
                                                System.out.println("MODIFICA PREZZO ARTICOLO \n");
                                                try {
                                                    listaSpesa.aggiornaArticolo(ar, ar.getNome(), ar.getQuantita(), ar.getCategoria(), Integer.parseInt(Input.readString("Inserisci  il nuovo prezzo dell'articolo: ")));
                                                } catch (ListaException e) {
                                                    e.printStackTrace();
                                                }
                                                break;

                                            case 5:
                                                System.out.println("USCITA\n");
                                                return;
                                        }
                                    } catch (ArticoloException e) {
                                        System.out.println("Alcuni dei parametri inseriti non rispettano i requisiti!");
                                    }
                                } else {
                                    System.out.println("Non risulta nessuna lista della spesa!");
                                }
                                break;

                            case 4:
                                System.out.println("RICERCA ARTICOLI PER CATEGORIA \n");
                                try {
                                    String categoriaDaCercare = Input.readString("Inserisci la categoria da cercare: ");
                                    boolean articoliTrovati = false;

                                    // Itera attraverso tutte le liste della spesa
                                    for (ListaSpesa lista : GestoreListe.getListeSpesa()) {
                                        // Itera attraverso gli articoli della lista corrente
                                        for (Articolo articolo4 : lista.getArticoli()) {
                                            if (articolo4.getCategoria().equalsIgnoreCase(categoriaDaCercare)) {
                                                System.out.println("Lista: " + lista.getNome() + " - Articolo: " + articolo4.getNome() + ", Quantità: " + articolo4.getQuantita() + ", Costo: " + articolo4.getCosto());
                                                articoliTrovati = true;
                                            }
                                        }
                                    }

                                    if (!articoliTrovati) {
                                        System.out.println("Nessun articolo trovato per la categoria '" + categoriaDaCercare + "'.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Errore durante la ricerca degli articoli: " + e.getMessage());
                                }
                                break;

                            case 5:
                                System.out.println("USCITA\n");
                                return;

                            default:
                                System.out.println("La scelta inserita non è valida");
                                break;
                        }
                    } while (true);

                case 7:
                    System.out.println("RICERCA RAPIDA ARTICOLO\n");
                    String prefisso=Input.readString("Inserisci il prefisso da cercare:");
                    String nomeLista=Input.readString("Inserisci il nome della lista da cercare:");
                    listaSpesa = ricercaLista(nomeLista);

                    ArrayList<Articolo> articoliTrovati=new ArrayList();
                    if (listaSpesa != null) {
                        articoliTrovati= cercaArticoliPerPrefisso(listaSpesa, prefisso);
                        System.out.println("Articoli trovati con prefisso \"" + prefisso + "\":");
                        for (Articolo articolo : articoliTrovati) {
                            System.out.println(articolo.getNome()+ " "+articolo.getQuantita()+ " "+articolo.getCategoria()+" "+articolo.getCosto());
                        }
                    } else
                        System.out.println("Lista della spesa non trovata.");
                    break;

                case 8:
                    System.out.println("VISUALIZZAZIONE LISTA DELLA SPESA\n");
                    nomeLista = Input.readString("Inserisci il nome della lista da cercare:");
                    listaSpesa = ricercaLista(nomeLista);

                    if (listaSpesa != null) {
                        System.out.println("Lista della spesa: " + listaSpesa.getNome());
                        System.out.println("La lista ha il seguente numero di articoli: " + listaSpesa.numeroArticoli());
                        if (listaSpesa.numeroArticoli() == 0) {
                            System.out.println("Non risultano articoli presenti in questa lista!");
                            break;
                        }
                        System.out.println("Articoli nella lista:");

                        for (Articolo articolo : listaSpesa) {
                            System.out.println("Nome: " + articolo.getNome() +
                                    ", Quantità: " + articolo.getQuantita() +
                                    ", Categoria: " + articolo.getCategoria() +
                                    ", Costo: " + articolo.getCosto());
                        }

                        double totale = listaSpesa.costoTotale();
                        System.out.printf("Totale: %.2f\n", totale);
                    } else {
                        System.out.println("Lista della spesa non trovata.");
                    }

                    break;
                case 9:
                    System.out.println("AGGIUNGI CATEGORIA\n");
                    String nuovaCategoria = Input.readString("Inserisci la nuova categoria:");
                    GestoreListe.aggiungiCategoria(nuovaCategoria);
                    break;
                case 10:
                    System.out.println("CANCELLAZIONE CATEGORIA\n");
                    String categoriaDaCancellare = Input.readString("Inserisci la categoria da cancellare:");
                    GestoreListe.cancellaCategoria(categoriaDaCancellare);
                    break;

                case 11:
                    System.out.println("MODIFICA CATEGORIA\n");
                    String vecchiaCategoria = Input.readString("Inserisci la categoria da modificare:");
                    String nuovaCategoriaModificata = Input.readString("Inserisci la nuova categoria:");
                    if(cercaCategoria(vecchiaCategoria)!=null) {
                        //la categoria esiste
                        try {
                            GestoreListe.modificaCategoria(vecchiaCategoria, nuovaCategoriaModificata);
                        } catch (ListaException e) {
                            System.out.println("Impossibile modificare la categoria");
                        }
                    }
                    else
                        throw new GestoreException("La categoria inserita non esiste!");
                    break;

                case 12:
                    System.out.println("USCITA DAL PROGRAMMA\n");
                    System.out.println("Premi invio per uscire dal programma: ");
                    x = Input.readString();
                    break;

                default:
                    System.out.println("ERRORE La scelta inserita non è valida!");
                    break;
            }
        } while (scelta!=12);

    }

    private static void controlloListe() throws GestoreException {
        if (GestoreListe.listeSpesa.isEmpty())
            throw new GestoreException("Non risulta presenta alcuna lista della spesa, creane prima una!");
    }

    private static void aggiungiArticolo(ListaSpesa listaSpesa) throws ListaException {
        String nomeArticolo, categoriaArticolo = "non categorizzato", quantitaArticolo = "1", costoArticolo;
        nomeArticolo = Input.readString("Inserisci il nome dell'articolo: ").trim();

        String inputCategoria = Input.readString("Inserisci la categoria dell'articolo: ").trim();
        if (!inputCategoria.isEmpty()) {
            categoriaArticolo = inputCategoria;
            GestoreListe.aggiungiCategoria(categoriaArticolo);  // Usa il metodo per aggiungere la categoria
        }

        String inputQuantita = Input.readString("Inserisci la quantità dell'articolo: ").trim();
        if (!inputQuantita.isEmpty()) {
            try {
                int quantita = Integer.parseInt(inputQuantita);
                if (quantita > 0) {
                    quantitaArticolo = inputQuantita;
                } else {
                    System.out.println("Quantità non valida. Utilizzando la quantità predefinita di 1.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Quantità non valida. Utilizzando la quantità predefinita di 1.");
            }
        }

        do {
            costoArticolo = Input.readString("Inserisci il costo dell'articolo: ").trim();
        } while (!costoArticolo.matches("^\\d+(\\.\\d{1,2})?$"));

        float costoArticoloFloat = Float.parseFloat(costoArticolo);

        Articolo articoloEsistente = null;
        for (Articolo articolo : listaSpesa.getArticoli()) {
            if (articolo.getNome().equalsIgnoreCase(nomeArticolo) && articolo.getCosto() == costoArticoloFloat) {
                articoloEsistente = articolo;
                break;
            }
        }

        if (articoloEsistente != null) {
            System.out.println("Esiste già un articolo con il nome '" + nomeArticolo + "'.");
            System.out.println("La quantità attuale è: " + articoloEsistente.getQuantita());

            String scelta;
            do {
                scelta = Input.readString("Vuoi modificarne la quantità? (si/no) ").trim();
            } while (!isSi(scelta) && !scelta.equalsIgnoreCase("no"));

            if (isSi(scelta)) {
                int nuovaQuantita = 1;
                try {
                    nuovaQuantita = Integer.parseInt(quantitaArticolo);
                    if (nuovaQuantita <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Quantità non valida. Utilizzando la quantità predefinita di 1.");
                    nuovaQuantita = 1;
                }

                articoloEsistente.setQuantita(articoloEsistente.getQuantita() + nuovaQuantita);
                System.out.println("La nuova quantità dell'articolo '" + nomeArticolo + "' è: " + articoloEsistente.getQuantita());
                return;
            }
        } else {
            // Aggiungi il nuovo articolo
            Articolo nuovoArticolo = new Articolo(nomeArticolo, Integer.parseInt(quantitaArticolo), categoriaArticolo, costoArticoloFloat);
            try {
                listaSpesa.aggiungiArticolo(nuovoArticolo);
                System.out.println("Articolo aggiunto alla lista " + listaSpesa.getNome() + "!");
                GestoreListe.aggiornaCategorie();  // Aggiorna le categorie dopo l'aggiunta dell'articolo
            } catch (Exception e) {
                System.out.println("Errore durante l'aggiunta dell'articolo: " + e.getMessage());
            }
        }
    }




    private static ArrayList<Articolo> cercaArticoliPerPrefisso(ListaSpesa listaSpesa, String prefisso) {
        ArrayList<Articolo> articoliTrovati = new ArrayList<>();
        for (Articolo articolo : listaSpesa.getArticoli()) {
            if (articolo.getNome().startsWith(prefisso)) {
                articoliTrovati.add(articolo);
            }
        }
        return articoliTrovati;
    }

    private static boolean isSi(String input) {
        return input.equalsIgnoreCase("si")
                || input.equalsIgnoreCase("s")
                || input.equalsIgnoreCase("yes")
                || input.equalsIgnoreCase("y");
    }
}
