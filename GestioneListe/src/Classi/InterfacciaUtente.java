package Classi;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import jbook.util.Input;


public class InterfacciaUtente extends JFrame
{
    private static boolean isGUI = true; // Flag per tenere traccia dello stato dell'interfaccia
    public static class InterfacciaSwing extends JFrame {

        private static JTextArea textArea;
        private static JTextField textFieldNomeLista;
        private JTextField textFieldNomeArticolo;
        private JTextField textFieldQuantita;
        private JTextField textFieldCategoria;
        private JTextField textFieldCosto;

        public InterfacciaSwing() {
            // Implementazione dell'interfaccia grafica
            super("Interfaccia Grafica");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 300);
            textArea = new JTextArea();

            // Pannello principale
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

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
                    } catch (ListaException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            JButton stayOnGUIButton = new JButton("Rimani su GUI");
            stayOnGUIButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isGUI = true; // Imposta il flag su true per restare sulla GUI
                    aggiornaInterfaccia(); // Aggiorna l'interfaccia grafica
                    stayOnGUIButton.setVisible(false);
                    switchToCLIButton.setVisible(false);

                }
            });

            buttonPanel.add(switchToCLIButton);
            buttonPanel.add(stayOnGUIButton);

            mainPanel.add(buttonPanel, BorderLayout.CENTER);

            add(mainPanel);
        }

        public void aggiornaInterfaccia() {
            // Aggiorna l'interfaccia grafica
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            setVisible(true);

            // Pannello menu principale
            JPanel menuPanel = new JPanel(new FlowLayout());
            JLabel labeltitolo = new JLabel("MENU OPERAZIONI: ");
            String[] voci = {"1. Creazione lista della spesa", "2. Eliminazione lista della spesa", "3. Creazione lista da file", "4. Scrittura lista su file", "5. Visualizzazione liste della spesa", "6. Modifica lista della spesa", "7. Uscita"};

            JComboBox<String> listaMenu1 = new JComboBox<>(voci);
            listaMenu1.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    JComboBox comboBox = (JComboBox) e.getSource();

                    String scelta = (String) comboBox.getSelectedItem();
                    labeltitolo.setVisible(false);


                    switch (scelta) {

                        case "1. Creazione lista della spesa":
                            listaMenu1.setVisible(false);
                            menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                            // Pannello per il titolo
                            JPanel titlePanel = new JPanel();
                            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
                            JLabel titleLabel = new JLabel("CREAZIONE LISTA DELLA SPESA:");
                            titlePanel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
                            titlePanel.add(titleLabel);
                            titlePanel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
                            menuPanel.add(titlePanel);

                            // Pannello per le opzioni
                            JPanel optionsPanel = new JPanel(new FlowLayout());

                            // Aggiungi le opzioni al pannello delle opzioni
                            optionsPanel.add(new JLabel("Nome lista:"));
                            textFieldNomeLista = new JTextField(20);
                            optionsPanel.add(textFieldNomeLista);
                            JButton btnCreaLista = new JButton("Crea Lista");

                            btnCreaLista.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String nomeLista = textFieldNomeLista.getText().trim(); // Rimuovi eventuali spazi vuoti all'inizio e alla fine
                                    if (nomeLista.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Inserisci un nome per la lista!", "Errore", JOptionPane.ERROR_MESSAGE);
                                        return; // Esci dal metodo senza fare altro
                                    }

                                    // Verifica se il nome della lista è già in uso
                                    if (nomeListaGiaInUso(nomeLista)) {
                                        JOptionPane.showMessageDialog(null, "Il nome della lista è già in uso, inseriscine un altro!", "Errore", JOptionPane.ERROR_MESSAGE);
                                        return; // Esci dal metodo senza fare altro
                                    }

                                    try {
                                        GestoreListe.aggiungiLista(nomeLista);
                                        JOptionPane.showMessageDialog(null, "Lista creata: " + nomeLista, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                        updateTextArea("Lista creata: " + nomeLista);
                                    } catch (GestoreException ex) {
                                        JOptionPane.showMessageDialog(null, "Errore durante la creazione della lista: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                                        updateTextArea("Errore: " + ex.getMessage());
                                    }
                                }
                            });

                            optionsPanel.add(btnCreaLista);

                            // Aggiungi il pannello delle opzioni al centro del menuPanel
                            menuPanel.add(optionsPanel);

                            break;

                        case "2. Eliminazione lista della spesa":
                            listaMenu1.setVisible(false);
                            menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                            // Pannello per il titolo
                            JPanel title2Panel = new JPanel();
                            title2Panel.setLayout(new BoxLayout(title2Panel, BoxLayout.X_AXIS));
                            JLabel title2Label = new JLabel("ELIMINAZIONE LISTA DELLA SPESA:");
                            title2Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
                            title2Panel.add(title2Label);
                            title2Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
                            menuPanel.add(title2Panel);

                            // Pannello per le opzioni
                            JPanel options2Panel = new JPanel(new FlowLayout());

                            // Aggiungi le opzioni al pannello delle opzioni
                            options2Panel.add(new JLabel("Nome lista da eliminare:"));
                            textFieldNomeLista = new JTextField(20);
                            options2Panel.add(textFieldNomeLista);
                            JButton btnEliminaLista = new JButton("Elimina lista");

                            btnEliminaLista.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String nomeLista = textFieldNomeLista.getText().trim(); // Rimuovi eventuali spazi vuoti all'inizio e alla fine
                                    if (nomeLista.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Inserisci un nome per la lista!", "Errore", JOptionPane.ERROR_MESSAGE);
                                        return; // Esci dal metodo senza fare altro
                                    }

                                    // Verifica se il nome della lista è già in uso
                                    if (!nomeListaGiaInUso(nomeLista)) {
                                        JOptionPane.showMessageDialog(null, "Il nome della lista non esiste!", "Errore", JOptionPane.ERROR_MESSAGE);
                                        return; // Esci dal metodo senza fare altro
                                    }

                                    try {
                                        GestoreListe.rimuoviLista(nomeLista); // Rimuovi la lista
                                        JOptionPane.showMessageDialog(null, "Lista eliminata: " + nomeLista, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                        updateTextArea("Lista eliminata: " + nomeLista);
                                    } catch (GestoreException ex) {
                                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione della lista: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                                        updateTextArea("Errore: " + ex.getMessage());
                                    }
                                }
                            });

                            options2Panel.add(btnEliminaLista);

                            // Aggiungi il pannello delle opzioni al centro del menuPanel
                            menuPanel.add(options2Panel);

                            break;


                    }
                    JLabel selezione=new JLabel("Hai selezionato: " + scelta);

                    menuPanel.add(selezione,BorderLayout.SOUTH);
                    mainPanel.add(menuPanel);
                    pack();
                }
            });

            menuPanel.add(labeltitolo);
            menuPanel.add(listaMenu1);
            mainPanel.add(menuPanel, BorderLayout.CENTER);

            add(mainPanel);
            pack();
        }
            /*topPanel.add(new JLabel("Nome Lista:"));
            textFieldNomeLista = new JTextField(20);
            topPanel.add(textFieldNomeLista);
            JButton btnCreaLista = new JButton("Crea Lista");
            btnCreaLista.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nomeLista = textFieldNomeLista.getText();
                    try {
                        GestoreListe.aggiungiLista(nomeLista);
                        updateTextArea("Lista creata: " + nomeLista);
                    } catch (GestoreException ex) {
                        updateTextArea("Errore: " + ex.getMessage());
                    }
                }
            });
            topPanel.add(btnCreaLista);
            mainPanel.add(topPanel, BorderLayout.NORTH);

            // TextArea per visualizzare le operazioni
            textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            // Pannello inferiore per l'input degli articoli
            JPanel bottomPanel = new JPanel(new GridLayout(3, 2));
            bottomPanel.add(new JLabel("Nome Articolo:"));
            textFieldNomeArticolo = new JTextField(20);
            bottomPanel.add(textFieldNomeArticolo);
            bottomPanel.add(new JLabel("Quantità:"));
            textFieldQuantita = new JTextField(10);
            bottomPanel.add(textFieldQuantita);
            bottomPanel.add(new JLabel("Categoria:"));
            textFieldCategoria = new JTextField(10);
            bottomPanel.add(textFieldCategoria);
            bottomPanel.add(new JLabel("Costo:"));
            textFieldCosto = new JTextField(10);
            bottomPanel.add(textFieldCosto);
            JButton btnAggiungiArticolo = new JButton("Aggiungi Articolo");
            btnAggiungiArticolo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a) {
                    String nomeLista = textFieldNomeLista.getText();
                    String nomeArticolo = textFieldNomeArticolo.getText();
                    int quantita = Integer.parseInt(textFieldQuantita.getText());
                    String categoria = textFieldCategoria.getText();
                    float costo = Float.parseFloat(textFieldCosto.getText());
                    ListaSpesa lista = GestoreListe.cercaLista(nomeLista);
                    Articolo articolo = new Articolo(nomeArticolo, quantita, categoria, costo);
                    lista.aggiungiArticolo(articolo);
                    updateTextArea("Articolo aggiunto alla lista " + nomeLista + ": " + nomeArticolo);
                }
            });
            bottomPanel.add(btnAggiungiArticolo);
            mainPanel.add(bottomPanel, BorderLayout.SOUTH);
*/



        private boolean nomeListaGiaInUso(String nomeLista) {
            for (ListaSpesa lista : GestoreListe.listeSpesa) {
                if (lista.getNome().equals(nomeLista)) {
                    return true; // Il nome della lista è già in uso
                }
            }
            return false; // Il nome della lista non è in uso
        }

        private static void updateTextArea(String text) {
            textArea.append(text + "\n");

        }
    }


    public static void main(String[] args) throws ListaException {
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
        }}
          private static void InterfacciaCLI() throws ListaException {

        int scelta;
        String x,nome,nomeArticolo="",categoriaArticolo="",quantitaArticolo = "", costoArticolo="",file="lista.txt";

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
                  	\n7 Uscita dal programma""");
            System.out.println("Inserisci l'operazione che si desidera effettuare: ");

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
                    System.out.println("Inserisci il nome della lista che si vuole scrivere sul file: ");
                    nome=Input.readString();
                    if(nome!=""){
                        ListaSpesa listaSpesa;
                        try{
                            listaSpesa=ricercaLista(nome);
                            listaSpesa.scriviSuFile(file);
                        }
                        catch (GestoreException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    else
                        System.out.println("Lista della spesa non trovata!");
                    break;

                case 5:
                    System.out.println("VISUALIZZAZIONE LISTE DELLA SPESA\n");
                    int numeroListe=GestoreListe.numeroListe();
                    if(numeroListe<=0)
                        System.out.println("Nessuna lista presente!");
                    else {
                        if(numeroListe==1)
                            System.out.println("Ecco la seguente lista memorizzata:");
                        else
                            System.out.println("Ecco le seguenti liste memorizzate:");
                        for(ListaSpesa lista: GestoreListe.listeSpesa)
                            System.out.println(lista.getNome());
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
                    do{
                        System.out.println("""
	            				MENU OPERAZIONI DELLA LISTA: """+ listaSpesa.getNome()+
                                """
	            				\n1 Aggiunta articolo
	            				\n2 Elimina articolo	
	            				\n3 Modifica articolo
	            				\n4 Visualizzazione lista della spesa	
	            				\n5 Uscita
	            				""");

                            System.out.println("Inserisci l'operazione che si desidera effettuare: ");
                            switch(Integer.parseInt(Input.readString())) {
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
                                    System.out.println("""
                                            MENU OPERAZIONI : 
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
                                                        listaSpesa.aggiornaArticolo(ar, Input.readString("Inserisci il nuovo nome dell'articolo"), ar.getQuantita(), ar.getCategoria(), ar.getCosto());
                                                    } catch (ListaException e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;

                                                case 2:
                                                    System.out.println("MODIFICA QUANTITA' ARTICOLO \n");
                                                    try {
                                                        listaSpesa.aggiornaArticolo(ar,ar.getNome(), Integer.parseInt(Input.readString("Inserisci la nuova quantita' dell'articolo")), ar.getCategoria(), ar.getCosto());
                                                    } catch (ListaException e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;

                                                case 3:
                                                    System.out.println("MODIFICA CATEGORIA ARTICOLO \n");
                                                    try {
                                                        listaSpesa.aggiornaArticolo(ar, ar.getNome(), ar.getQuantita(), Input.readString("Inserisci la nuova categoria dell'articolo"), ar.getCosto());
                                                    } catch (ListaException e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;

                                                case 4:
                                                    System.out.println("MODIFICA PREZZO ARTICOLO \n");
                                                    try {
                                                        listaSpesa.aggiornaArticolo(ar, ar.getNome(), ar.getQuantita(), ar.getCategoria(), Integer.parseInt(Input.readString("Inserisci  il nuovo prezzo dell'articolo")));
                                                    } catch (ListaException e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;

                                                case 5:
                                                    System.out.println("USCITA\n");
                                                    return;
                                            }
                                        } catch(ArticoloException e){
                                                System.out.println("Alcuni dei parametri inseriti non rispettano i requisiti!");
                                            }
                                    } else {
                                        System.out.println("Non risulta nessuna lista della spesa!");
                                    }
                                    break;

                                case 4:
                                    System.out.println("VISUALIZZAZIONE LISTA DELLA SPESA\n");
                                    System.out.println("La lista ha il seguente numero di articoli: " + listaSpesa.numeroArticoli());

                                    if (listaSpesa.numeroArticoli() == 0)
                                    {
                                        System.out.println("Non risultano articoli presenti in questa lista!");
                                        break;
                                    }

                                    for(Articolo articolo : listaSpesa)
                                    {
                                        System.out.println(articolo.toString());
                                    }
                                    break;

                                case 5:
                                    System.out.println("USCITA\n");
                                    return;

                                default:
                                    System.out.println("La scelta inserita non è valida");
                                    break;
                            }
                    }while(true);
                case 7:
                    System.out.println("USCITA DAL PROGRAMMA\n");
                    System.out.println("Premi invio per uscire dal programma: ");
                    x = Input.readString();
                    break;

                default:
                    System.out.println("ERRORE La scelta inserita non è valida!");
                    break;
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

    private static void aggiungiArticolo(ListaSpesa listaSpesa) throws ListaException {
        String nomeArticolo, categoriaArticolo, quantitaArticolo, costoArticolo;
        nomeArticolo = Input.readString("Inserisci il nome dell'articolo: ");
        do {
            quantitaArticolo = Input.readString("Inserisci la quantità dell'articolo: ");
        } while(!quantitaArticolo.matches("^\\d+$"));
        categoriaArticolo = Input.readString("Inserisci la categoria dell'articolo: ");
        do {
            costoArticolo = Input.readString("Inserisci il costo dell'articolo: ");
        } while(!costoArticolo.matches("^\\d+(\\.\\d{1,2})?$"));


        Articolo articolo = new Articolo(nomeArticolo, Integer.parseInt(quantitaArticolo), categoriaArticolo, Float.parseFloat(costoArticolo));
        try {
            listaSpesa.aggiungiArticolo(articolo);
            System.out.println("Articolo aggiunto alla lista " + listaSpesa.getNome() + " !");
        }catch(Exception e){
            System.out.println("Errore durante l'aggiunta dell'articolo: " + e.getMessage());
        }
    }
}
