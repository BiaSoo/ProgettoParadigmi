package Classi;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import jbook.util.Input;
import Classi.ListaSpesa;
import Classi.InterfacciaUtente;


public class InterfacciaUtenteGUI extends JFrame{
    private static JTextArea textArea;

    private boolean nomeListaGiaInUso(String nomeLista) {
        for (ListaSpesa lista : GestoreListe.listeSpesa) {
            if (lista.getNome().equals(nomeLista)) {
                return true; // Il nome della lista è già in uso
            }
        }
        return false; // Il nome della lista non è in uso
    }

    public void updateTextArea(String text) {
        if (textArea != null) {
            textArea.append(text + "\n");
        }
    }
    public void aggiornaInterfaccia() {
        // Aggiorna l'interfaccia grafica
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setVisible(true);

        // Pannello menu principale
        JPanel menuPanel = new JPanel(new FlowLayout());
        JLabel labeltitolo = new JLabel("MENU OPERAZIONI: ");
        String[] voci = {"1. Creazione lista della spesa", "2. Eliminazione lista della spesa", "3. Creazione lista da file", "4. Scrittura lista su file", "5. Visualizzazione liste della spesa", "6. Modifica lista della spesa"};

        JComboBox<String> listaMenu1 = new JComboBox<>(voci);
        listaMenu1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JComboBox comboBox = (JComboBox) e.getSource();

                String scelta = (String) comboBox.getSelectedItem();
                labeltitolo.setVisible(false);


                switch (scelta) {

                    case "1. Creazione lista della spesa":

                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
                        listaMenu1.setVisible(false);
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
                        JTextField textFieldNomeLista = new JTextField(20);
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
                                textFieldNomeLista.setText("");
                            }
                        });

                        optionsPanel.add(btnCreaLista);
                        JButton btnMenu1 = new JButton("Torna al Menu");
                        btnMenu1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Rimuovi i componenti aggiunti durante la creazione della lista
                                menuPanel.removeAll();
                                // Riaggiungi il menu principale
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); // Rivalida il layout del pannello
                                menuPanel.repaint(); // Ridisegna il pannello
                            }
                        });


                        // Aggiungi il pannello delle opzioni al centro del menuPanel
                        menuPanel.add(optionsPanel);

                        menuPanel.add(btnMenu1);
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
                                textFieldNomeLista.setText("");
                            }
                        });

                        options2Panel.add(btnEliminaLista);
                        JButton btnMenu2 = new JButton("Torna al Menu");
                        btnMenu2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Rimuovi i componenti aggiunti durante la creazione della lista
                                menuPanel.removeAll();
                                // Riaggiungi il menu principale
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); // Rivalida il layout del pannello
                                menuPanel.repaint(); // Ridisegna il pannello
                            }
                        });


                        // Aggiungi il pannello delle opzioni al centro del menuPanel
                        menuPanel.add(options2Panel);

                        menuPanel.add(btnMenu2);
                        break;

                    case "3.Creazione lista da file":

                        listaMenu1.setVisible(false);

                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                        // Pannello per il titolo
                        JPanel title3Panel = new JPanel();
                        title3Panel.setLayout(new BoxLayout(title3Panel, BoxLayout.X_AXIS));
                        JLabel title3Label = new JLabel("CREAZIONE LISTA DELLA SPESA DA FILE:");
                        title3Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
                        title3Panel.add(title3Label);
                        title3Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
                        menuPanel.add(title3Panel);

                        // Pannello per le opzioni
                        JPanel options3Panel = new JPanel(new FlowLayout());

                        // Aggiungi le opzioni al pannello delle opzioni
                        options3Panel.add(new JLabel("Nome file:"));
                        JTextField textFieldNomeFile = new JTextField(20);
                        options3Panel.add(textFieldNomeFile);
                        options3Panel.add(new JLabel("Path:"));
                        JTextField textFieldPathFile = new JTextField(20);
                        options3Panel.add(textFieldPathFile);
                        JButton btnCreaListaDaFile = new JButton("Crea Lista da File");

                        btnCreaListaDaFile.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nomeFile = textFieldNomeFile.getText().trim(); // Rimuovi eventuali spazi vuoti all'inizio e alla fine
                                if (nomeFile.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Inserisci un nome per il file!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return; // Esci dal metodo senza fare altro
                                }
                                String path=textFieldPathFile.getText();
                                if (path.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Inserisci un path per il file!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return; // Esci dal metodo senza fare altro
                                }
                                try {
                                    GestoreListe.leggiDaFile(path,nomeFile); // Leggi la lista da file
                                    JOptionPane.showMessageDialog(null, "Lista creata da file: " + nomeFile, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                    updateTextArea("Lista creata da file: " + nomeFile);
                                } catch (GestoreException | IOException ex) {
                                    JOptionPane.showMessageDialog(null, "Errore durante la creazione della lista da file: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                                    updateTextArea("Errore: " + ex.getMessage());
                                }
                                textFieldNomeFile.setText("");
                            }
                        });
                        JButton btnMenu3 = new JButton("Torna al Menu");
                        btnMenu3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Rimuovi i componenti aggiunti durante la creazione della lista
                                menuPanel.removeAll();
                                // Riaggiungi il menu principale
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); // Rivalida il layout del pannello
                                menuPanel.repaint(); // Ridisegna il pannello
                            }
                        });


                        // Aggiungi il pannello delle opzioni al centro del menuPanel
                        menuPanel.add(options3Panel);

                        menuPanel.add(btnMenu3);
                        break;


                    case "4. Scrittura lista su file":
                        listaMenu1.setVisible(false);

                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                        // Pannello per il titolo
                        JPanel title4Panel = new JPanel();
                        title4Panel.setLayout(new BoxLayout(title4Panel, BoxLayout.X_AXIS));
                        JLabel title4Label = new JLabel("SCRITTURA LISTA DELLA SPESA SU FILE:");
                        title4Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
                        title4Panel.add(title4Label);
                        title4Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
                        menuPanel.add(title4Panel);

                        // Pannello per le opzioni
                        JPanel options4Panel = new JPanel(new FlowLayout());

                        // Aggiungi le opzioni al pannello delle opzioni
                        options4Panel.add(new JLabel("Nome lista da scrivere su file:"));
                        JTextField textFieldNomeLista4 = new JTextField(20);
                        options4Panel.add(textFieldNomeLista4);
                        JButton btnScritturaListaSuFile = new JButton("Scrittura Lista su File");

                        btnScritturaListaSuFile.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nomeLista4 = textFieldNomeLista4.getText().trim(); // Rimuovi eventuali spazi vuoti all'inizio e alla fine
                                if (nomeLista4.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Inserisci un nome per la lista!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return; // Esci dal metodo senza fare altro
                                }

                                // Crea un'istanza della lista di spesa
                                ListaSpesa lista = new ListaSpesa(nomeLista4);

                                try {
                                    lista.scriviSuFile(nomeLista4); // Scrivi la lista su file
                                    JOptionPane.showMessageDialog(null, "Lista scritta su file: " + nomeLista4, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                    updateTextArea("Lista scritta su file: " + nomeLista4);
                                } catch (ListaException ex) {
                                    JOptionPane.showMessageDialog(null, "Errore durante la scrittura della lista su file: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                                    updateTextArea("Errore: " + ex.getMessage());
                                }
                            }
                        });


                        options4Panel.add(btnScritturaListaSuFile);
                        JButton btnMenu4 = new JButton("Torna al Menu");
                        btnMenu4.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Rimuovi i componenti aggiunti durante la scrittura della lista su file
                                menuPanel.removeAll();
                                // Riaggiungi il menu principale
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); // Rivalida il layout del pannello
                                menuPanel.repaint(); // Ridisegna il pannello
                            }
                        });

                        // Aggiungi il pannello delle opzioni al centro del menuPanel
                        menuPanel.add(options4Panel);

                        menuPanel.add(btnMenu4);
                        break;

                    case "5. Visualizzazione liste della spesa":
                        listaMenu1.setVisible(false);

                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                        // Pannello per il titolo
                        JPanel title5Panel = new JPanel();
                        title5Panel.setLayout(new BoxLayout(title5Panel, BoxLayout.X_AXIS));
                        JLabel title5Label = new JLabel("NOMI LISTE DELLA SPESA:");
                        title5Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
                        title5Panel.add(title5Label);
                        title5Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
                        menuPanel.add(title5Panel);

                        // Pannello per la visualizzazione dei nomi delle liste
                        JPanel nomiListePanel = new JPanel(new FlowLayout());

                        // Otteniamo la stringa dei nomi delle liste della spesa
                        StringBuilder nomiListeStringBuilder = new StringBuilder();
                        for (ListaSpesa listaSpesa : GestoreListe.listeSpesa) {
                            nomiListeStringBuilder.append(listaSpesa.getNome()).append(", ");
                        }
                        // Rimuoviamo l'ultima virgola e lo spazio in eccesso
                        String nomiListeStringa = nomiListeStringBuilder.toString();
                        if (nomiListeStringa.length() > 2) {
                            nomiListeStringa = nomiListeStringa.substring(0, nomiListeStringa.length() - 2);
                        }


                        // Verifichiamo se la stringa non è vuota
                        if (nomiListeStringa != null && !nomiListeStringa.isEmpty()) {
                            // Dividiamo la stringa dei nomi in un array di stringhe usando come delimitatore la virgola
                            String[] nomiListeArray = nomiListeStringa.split(",");
                            for (String nomeLista : nomiListeArray) {
                                JLabel nomeListaLabel = new JLabel(nomeLista.trim()); // Rimuoviamo eventuali spazi vuoti all'inizio e alla fine
                                nomiListePanel.add(nomeListaLabel);
                            }
                        } else {
                            // Nel caso la stringa sia vuota o nulla, aggiungiamo un messaggio di avviso alla GUI
                            JLabel avvisoLabel = new JLabel("Nessuna lista presente");
                            nomiListePanel.add(avvisoLabel);
                        }


                        // Aggiungi il pannello dei nomi delle liste al menuPanel
                        menuPanel.add(nomiListePanel);

                        JButton btnMenu = new JButton("Torna al Menu");
                        btnMenu.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Rimuovi i componenti aggiunti durante la visualizzazione dei nomi delle liste
                                menuPanel.removeAll();
                                // Riaggiungi il menu principale
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); // Rivalida il layout del pannello
                                menuPanel.repaint(); // Ridisegna il pannello
                            }
                        });

                        // Aggiungi il pulsante "Torna al Menu"
                        menuPanel.add(btnMenu);

                        menuPanel.revalidate(); // Rivalida il layout del menuPanel
                        menuPanel.repaint(); // Ridisegna il menuPanel
                        break;


                        case "6. Modifica lista della spesa":
                        listaMenu1.setVisible(false);
                        menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.Y_AXIS));
                    
                        // Pannello per il titolo
                        JPanel title6Panel = new JPanel();
                        title6Panel.setLayout(new BoxLayout(title6Panel,BoxLayout.X_AXIS ));
                        JLabel title6Label = new JLabel("MODIFICA LISTA DELLA SPESA:");
                        title6Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
                        title6Panel.add(title6Label);
                        title6Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
                        menuPanel.add(title6Panel, "Title");
                        title6Panel.setVisible(true);
                    
                        // Pannello per le opzioni
                        JPanel options6Panel = new JPanel(new FlowLayout());
                    
                        // Aggiungi le opzioni al pannello delle opzioni
                        options6Panel.add(new JLabel("Nome lista da modificare:"));
                        JTextField textFieldNomeLista6 = new JTextField(20);
                        options6Panel.add(textFieldNomeLista6);
                        JButton btnModificaLista = new JButton("Modifica lista");
                        options6Panel.add(btnModificaLista);
                        menuPanel.add(options6Panel, "Options");
                    

                        btnModificaLista.addActionListener(new ActionListener() {
                           @Override
                           public void actionPerformed(ActionEvent e) {
                               String nomeLista = textFieldNomeLista6.getText().trim();
                               if (nomeLista.isEmpty()) {
                                   JOptionPane.showMessageDialog(null, "Inserisci un nome per la lista!", "Errore", JOptionPane.ERROR_MESSAGE);
                                   return; // Esci dal metodo senza fare altro
                               }

                               // Verifica se il nome della lista esiste
                               if (!nomeListaGiaInUso(nomeLista)) {
                                   JOptionPane.showMessageDialog(null, "Il nome della lista non esiste!", "Errore", JOptionPane.ERROR_MESSAGE);
                                   return; // Esci dal metodo senza fare altro
                               }

                               String[] opzioniModifica = {"Aggiungi articolo", "Rimuovi articolo", "Modifica articolo", "Visualizza lista della spesa"};
                               String sceltaModifica = (String) JOptionPane.showInputDialog(null, "Scegli un'operazione di modifica", "Modifica Lista della Spesa", JOptionPane.QUESTION_MESSAGE, null, opzioniModifica, opzioniModifica[0]);

                               if (sceltaModifica != null) {
                                   // Rimuovi i componenti precedenti e aggiungi il nuovo pannello
                                   menuPanel.removeAll();

                                   // Gestisci la scelta dell'utente
                                   switch (sceltaModifica) {
                                       case "Aggiungi articolo":
                                               // Pannello per il titolo
                                               JPanel title0Panel = new JPanel();
                                               title0Panel.setLayout(new BoxLayout(title0Panel, BoxLayout.X_AXIS));
                                               JLabel title0Label = new JLabel("AGGIUNTA ARTICOLO ALLA LISTA DELLA SPESA:");
                                               title0Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
                                               title0Panel.add(title0Label);
                                               title0Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
                                               menuPanel.add(title0Panel);



                                               // Opzioni del pannello
                                           JPanel options0Panel = new JPanel(new GridBagLayout());
                                           GridBagConstraints gbc = new GridBagConstraints();
                                           gbc.insets = new Insets(5, 5, 5, 5);
                                           gbc.anchor = GridBagConstraints.WEST;

                                           // Aggiungi le opzioni al pannello delle opzioni
                                           gbc.gridx = 0;
                                           gbc.gridy = 0;
                                           options0Panel.add(new JLabel("Nome dell'articolo:"), gbc);
                                           gbc.gridx = 1;
                                           JTextField textFieldNomeArticolo = new JTextField(20);
                                           options0Panel.add(textFieldNomeArticolo, gbc);

                                           gbc.gridx = 0;
                                           gbc.gridy = 1;
                                           options0Panel.add(new JLabel("Quantità:"), gbc);
                                           gbc.gridx = 1;
                                           JTextField textFieldQuantita = new JTextField(5);
                                           options0Panel.add(textFieldQuantita, gbc);

                                           gbc.gridx = 0;
                                           gbc.gridy = 2;
                                           options0Panel.add(new JLabel("Prezzo:"), gbc);
                                           gbc.gridx = 1;
                                           JTextField textFieldPrezzo = new JTextField(20);
                                           options0Panel.add(textFieldPrezzo, gbc);

                                           gbc.gridx = 0;
                                           gbc.gridy = 3;
                                           options0Panel.add(new JLabel("Categoria:"), gbc);
                                           gbc.gridx = 1;
                                           JTextField textFieldCategoria = new JTextField(20);
                                           options0Panel.add(textFieldCategoria, gbc);

                                           gbc.gridx = 0;
                                           gbc.gridy = 4;
                                           gbc.gridwidth = 2;
                                           gbc.anchor = GridBagConstraints.CENTER;
                                           JButton btnAggiungiArticolo = new JButton("Aggiungi articolo");


                                           btnAggiungiArticolo.addActionListener(new ActionListener() {
                                                   @Override
                                                   public void actionPerformed(ActionEvent e) {
                                                       String nomeArticolo = textFieldNomeArticolo.getText().trim();
                                                       String quantitaString = textFieldQuantita.getText().trim();
                                                       String prezzoString = textFieldPrezzo.getText().trim();
                                                       String categoria = textFieldCategoria.getText().trim();
                                                       if (nomeLista.isEmpty() || nomeArticolo.isEmpty() || quantitaString.isEmpty()) {
                                                           JOptionPane.showMessageDialog(null, "Inserisci nome lista, nome articolo e quantità!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                           return;
                                                       }


                                                       int quantita;
                                                       try {
                                                           quantita = Integer.parseInt(quantitaString);
                                                       } catch (NumberFormatException ex) {
                                                           JOptionPane.showMessageDialog(null, "Quantità non valida!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                           return;
                                                       }

                                                       if (quantita <= 0) {
                                                           JOptionPane.showMessageDialog(null, "La quantità deve essere maggiore di zero!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                           return;
                                                       }

                                                       // Verifica se l'articolo esiste già nella lista
                                                       for (ListaSpesa lista : GestoreListe.listeSpesa) {
                                                           if (lista.getNome().equals(nomeLista)) {
                                                               for (Articolo articolo : lista.getArticoli()) {
                                                                   if (articolo.getNome().equalsIgnoreCase(nomeArticolo)) {
                                                                       // Se l'articolo è già presente nella lista, chiedi se l'utente vuole aumentarne la quantità
                                                                       int scelta = JOptionPane.showConfirmDialog(null, "L'articolo '" + nomeArticolo + "' è già presente nella lista.\nVuoi aumentarne la quantità?", "Articolo già presente", JOptionPane.YES_NO_OPTION);
                                                                       if (scelta == JOptionPane.YES_OPTION) {
                                                                           articolo.setQuantita(articolo.getQuantita() + quantita);
                                                                           JOptionPane.showMessageDialog(null, "Quantità aggiornata!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                                                                           updateTextArea("Quantità aggiornata per l'articolo '" + nomeArticolo + "' nella lista: " + nomeLista);
                                                                           return;
                                                                       } else {
                                                                           // Se l'utente ha scelto di non aumentare la quantità, esci dal metodo
                                                                           return;
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                       }

                                                       // Se l'articolo non esiste già, aggiungilo
                                                       for (ListaSpesa lista : GestoreListe.listeSpesa) {
                                                           if (lista.getNome().equals(nomeLista)) {
                                                               Articolo nuovoArticolo = new Articolo(nomeArticolo, quantita, categoria,Float.parseFloat(prezzoString));
                                                               lista.aggiungiArticolo(nuovoArticolo);
                                                               JOptionPane.showMessageDialog(null, "Articolo aggiunto alla lista: " + nomeLista, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                                               updateTextArea("Articolo aggiunto alla lista: " + nomeLista);
                                                               break;
                                                           }
                                                       }
                                                   }
                                               });

                                               options0Panel.add(btnAggiungiArticolo,gbc);


                                               // Bottone per tornare al menu principale
                                               gbc.gridx=0;
                                               gbc.gridy=5;
                                               gbc.gridwidth=2;
                                               JButton btnMenu0 = new JButton("Torna al Menu");
                                               btnMenu0.addActionListener(new ActionListener() {
                                                   @Override
                                                   public void actionPerformed(ActionEvent e) {
                                                       // Rimuovi i componenti aggiunti durante l'aggiunta dell'articolo
                                                       menuPanel.removeAll();
                                                       // Riaggiungi il menu principale
                                                       menuPanel.add(labeltitolo);
                                                       menuPanel.add(listaMenu1);
                                                       listaMenu1.setVisible(true);
                                                       menuPanel.revalidate(); // Rivalida il layout del pannello
                                                       menuPanel.repaint(); // Ridisegna il pannello
                                                   }
                                               });
                                               options0Panel.add(btnMenu0);
                                               menuPanel.add(options0Panel);

                                               menuPanel.revalidate();
                                               menuPanel.repaint();
                                               break;


                                           case "Rimuovi articolo":
                                               JOptionPane.showMessageDialog(null, "Rimuovi articolo dalla lista: " + nomeLista, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                               updateTextArea("Rimuovi articolo dalla lista: " + nomeLista);

                                               // Pannello per il titolo
                                               JPanel title1Panel = new JPanel();
                                               title1Panel.setLayout(new BoxLayout(title1Panel, BoxLayout.X_AXIS));
                                               JLabel title1Label = new JLabel("RIMOZIONE ARTICOLO DALLA LISTA DELLA SPESA:");
                                               title1Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
                                               title1Panel.add(title1Label);
                                               title1Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
                                               menuPanel.add(title1Panel);

                                               // Pannello per le opzioni
                                               JPanel options1Panel = new JPanel(new FlowLayout());

                                               // Aggiungi le opzioni al pannello delle opzioni
                                               options1Panel.add(new JLabel("Nome dell'articolo da rimuovere:"));
                                               JTextField textFieldNomeArticolo1 = new JTextField(20);
                                               options1Panel.add(textFieldNomeArticolo1);

                                               JButton btnRimuoviArticolo = new JButton("Rimuovi articolo");

                                               btnRimuoviArticolo.addActionListener(new ActionListener() {
                                                   @Override
                                                   public void actionPerformed(ActionEvent e) {
                                                       String nomeArticolo = textFieldNomeArticolo1.getText().trim();
                                                       if (nomeLista.isEmpty() || nomeArticolo.isEmpty()) {
                                                           JOptionPane.showMessageDialog(null, "Inserisci nome lista e nome articolo!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                           return;
                                                       }

                                                       boolean articoloRimosso = false;

                                                       for (ListaSpesa lista : GestoreListe.listeSpesa) {
                                                           if (lista.getNome().equals(nomeLista)) {
                                                               try {
                                                                   lista.rimuoviArticolo(nomeArticolo);
                                                               } catch (ListaException ex) {
                                                                   throw new RuntimeException(ex);
                                                               }
                                                               break;
                                                           }
                                                       }

                                                       if (articoloRimosso) {
                                                           JOptionPane.showMessageDialog(null, "Articolo rimosso dalla lista: " + nomeLista, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                                           updateTextArea("Articolo rimosso dalla lista: " + nomeLista);
                                                       } else {
                                                           JOptionPane.showMessageDialog(null, "Articolo non trovato nella lista: " + nomeLista, "Errore", JOptionPane.ERROR_MESSAGE);
                                                       }
                                                   }
                                               });

                                               options1Panel.add(btnRimuoviArticolo);
                                               menuPanel.add(options1Panel);

                                               // Bottone per tornare al menu principale
                                               JButton btnMenu1 = new JButton("Torna al Menu");
                                               btnMenu1.addActionListener(new ActionListener() {
                                                   @Override
                                                   public void actionPerformed(ActionEvent e) {
                                                       // Rimuovi i componenti aggiunti durante la rimozione dell'articolo
                                                       menuPanel.removeAll();
                                                       // Riaggiungi il menu principale
                                                       menuPanel.add(labeltitolo);
                                                       menuPanel.add(listaMenu1);
                                                       listaMenu1.setVisible(true);
                                                       menuPanel.revalidate(); // Rivalida il layout del pannello
                                                       menuPanel.repaint(); // Ridisegna il pannello
                                                   }
                                               });
                                               options1Panel.add(btnMenu1);
                                               menuPanel.add(options1Panel);

                                               menuPanel.revalidate();
                                               menuPanel.repaint();
                                               break;

                                       case "Modifica articolo":
                                           JOptionPane.showMessageDialog(null, "Modifica articolo della lista: " + nomeLista, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                           updateTextArea("Modifica articolo della lista: " + nomeLista);

                                           // Pannello per il titolo
                                           JPanel title2Panel = new JPanel();
                                           title2Panel.setLayout(new BoxLayout(title2Panel, BoxLayout.X_AXIS));
                                           JLabel title2Label = new JLabel("MODIFICA ARTICOLO DELLA LISTA DELLA SPESA:");
                                           title2Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
                                           title2Panel.add(title2Label);
                                           title2Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
                                           menuPanel.add(title2Panel);

                                           // Pannello per le opzioni
                                           JPanel options2Panel = new JPanel(new FlowLayout());

                                           // Aggiungi le opzioni al pannello delle opzioni
                                           options2Panel.add(new JLabel("Nome dell'articolo da modificare:"));
                                           JTextField textFieldNomeArticolo2 = new JTextField(20);
                                           options2Panel.add(textFieldNomeArticolo2);

                                           options2Panel.add(new JLabel("Nuovo nome dell'articolo:"));
                                           JTextField textFieldNuovoNomeArticolo = new JTextField(20);
                                           options2Panel.add(textFieldNuovoNomeArticolo);

                                           options2Panel.add(new JLabel("Nuova quantità:"));
                                           JTextField textFieldNuovaQuantita = new JTextField(5);
                                           options2Panel.add(textFieldNuovaQuantita);

                                           options2Panel.add(new JLabel("Nuovo prezzo:"));
                                           JTextField textFieldNuovoPrezzo = new JTextField(20);
                                           options2Panel.add(textFieldNuovoPrezzo);

                                           options2Panel.add(new JLabel("Nuova categoria:"));
                                           JTextField textFieldNuovaCategoria = new JTextField(20);
                                           options2Panel.add(textFieldNuovaCategoria);

                                           JButton btnModificaArticolo = new JButton("Modifica articolo");

                                           btnModificaArticolo.addActionListener(new ActionListener() {
                                               @Override
                                               public void actionPerformed(ActionEvent e) {
                                                   String nomeArticolo = textFieldNomeArticolo2.getText().trim();
                                                   String nuovoNomeArticolo = textFieldNuovoNomeArticolo.getText().trim();
                                                   String nuovaQuantitaString = textFieldNuovaQuantita.getText().trim();
                                                   String nuovoPrezzoString = textFieldNuovoPrezzo.getText().trim();
                                                   String nuovaCategoria = textFieldNuovaCategoria.getText().trim();
                                                   if (nomeLista.isEmpty() || nomeArticolo.isEmpty()) {
                                                       JOptionPane.showMessageDialog(null, "Inserisci nome lista e nome articolo!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                       return;
                                                   }

                                                   int nuovaQuantita;
                                                   try {
                                                       nuovaQuantita = Integer.parseInt(nuovaQuantitaString);
                                                   } catch (NumberFormatException ex) {
                                                       JOptionPane.showMessageDialog(null, "Quantità non valida!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                       return;
                                                   }

                                                   float nuovoCosto;
                                                   try {
                                                       nuovoCosto = Float.parseFloat(nuovoPrezzoString);
                                                   } catch (NumberFormatException ex) {
                                                       JOptionPane.showMessageDialog(null, "Prezzo non valido!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                       return;
                                                   }

                                                   boolean articoloModificato = false;

                                                   for (ListaSpesa lista : GestoreListe.listeSpesa) {
                                                       if (lista.getNome().equals(nomeLista)) {
                                                           for (Articolo articolo : lista.getArticoli()) {
                                                               if (articolo.getNome().equalsIgnoreCase(nomeArticolo)) {
                                                                   articolo.setNome(nuovoNomeArticolo);
                                                                   articolo.setQuantita(nuovaQuantita);
                                                                   articolo.setCosto(nuovoCosto);
                                                                   articolo.setCategoria(nuovaCategoria);
                                                                   articoloModificato = true;
                                                                   break;
                                                               }
                                                           }
                                                           break;
                                                       }
                                                   }

                                                   if (articoloModificato) {
                                                       JOptionPane.showMessageDialog(null, "Articolo modificato nella lista: " + nomeLista, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                                       updateTextArea("Articolo modificato nella lista: " + nomeLista);
                                                   } else {
                                                       JOptionPane.showMessageDialog(null, "Articolo non trovato nella lista: " + nomeLista, "Errore", JOptionPane.ERROR_MESSAGE);
                                                   }
                                               }
                                           });

                                           options2Panel.add(btnModificaArticolo);
                                           menuPanel.add(options2Panel);

                                           // Bottone per tornare al menu principale
                                           JButton btnMenu2 = new JButton("Torna al Menu");
                                           btnMenu2.addActionListener(new ActionListener() {
                                               @Override
                                               public void actionPerformed(ActionEvent e) {
                                                   // Rimuovi i componenti aggiunti durante la modifica dell'articolo
                                                   menuPanel.removeAll();
                                                   // Riaggiungi il menu principale
                                                   menuPanel.add(labeltitolo);
                                                   menuPanel.add(listaMenu1);
                                                   listaMenu1.setVisible(true);
                                                   menuPanel.revalidate(); // Rivalida il layout del pannello
                                                   menuPanel.repaint(); // Ridisegna il pannello
                                               }
                                           });
                                           options2Panel.add(btnMenu2);
                                           menuPanel.add(options2Panel);

                                           menuPanel.revalidate();
                                           menuPanel.repaint();
                                       break;

                                       case "Visualizza lista della spesa":
                                           // Pannello per il titolo
                                           JPanel title3Panel = new JPanel();
                                           title3Panel.setLayout(new BoxLayout(title3Panel, BoxLayout.X_AXIS));
                                           JLabel title3Label = new JLabel("VISUALIZZA LISTA DELLA SPESA:");
                                           title3Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a sinistra
                                           title3Panel.add(title3Label);
                                           title3Panel.add(Box.createHorizontalGlue()); // Aggiungi spazio vuoto a destra
                                           menuPanel.add(title3Panel);

                                           // Pannello per la visualizzazione della lista
                                           JPanel options3Panel = new JPanel(new FlowLayout());
                                           JTextArea textAreaLista = new JTextArea(10, 30);
                                           textAreaLista.setEditable(false);

                                           for (ListaSpesa lista : GestoreListe.listeSpesa) {
                                               if (lista.getNome().equals(nomeLista)) {
                                                   for (Articolo articolo : lista.getArticoli()) {
                                                       textAreaLista.append("Articolo: " + articolo.getNome() + ", Quantità: " + articolo.getQuantita() + ", Prezzo: " + articolo.getCosto() + ", Categoria: " + articolo.getCategoria() + "\n");
                                                   }
                                                   break;
                                               }
                                           }

                                           options3Panel.add(new JScrollPane(textAreaLista));
                                           menuPanel.add(options3Panel);

                                           // Bottone per tornare al menu principale
                                           JButton btnMenu3 = new JButton("Torna al Menu");
                                           btnMenu3.addActionListener(new ActionListener() {
                                               @Override
                                               public void actionPerformed(ActionEvent e) {
                                                   // Rimuovi i componenti aggiunti durante la visualizzazione della lista
                                                   menuPanel.removeAll();
                                                   // Riaggiungi il menu principale
                                                   menuPanel.add(labeltitolo);
                                                   menuPanel.add(listaMenu1);
                                                   listaMenu1.setVisible(true);
                                                   menuPanel.revalidate(); // Rivalida il layout del pannello
                                                   menuPanel.repaint(); // Ridisegna il pannello
                                               }
                                           });
                                           options3Panel.add(btnMenu3);
                                           menuPanel.add(options3Panel);

                                           menuPanel.revalidate();
                                           menuPanel.repaint();
                                           break;


                                   }
                               }


                           }

                       });


                        options6Panel.add(btnModificaLista);
                        JButton btnMenu6 = new JButton("Torna al Menu");
                        btnMenu6.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Rimuovi i componenti aggiunti durante la modifica della lista
                                menuPanel.removeAll();
                                // Riaggiungi il menu principale
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); // Rivalida il layout del pannello
                                menuPanel.repaint(); // Ridisegna il pannello
                            }
                        });

                        // Aggiungi il pannello delle opzioni al centro del menuPanel
                        menuPanel.add(options6Panel);

                        menuPanel.add(btnMenu6);
                        break;


                    default:
                        throw new IllegalStateException("Unexpected value: " + scelta);
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
}

