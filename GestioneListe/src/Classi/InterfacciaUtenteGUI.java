package Classi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import static Classi.GestoreListe.ricercaLista;
import static Classi.InterfacciaUtente.cercaArticoliPerPrefisso;

/*TODO:
 * 5: ricerca rapida articolo: vanno posti dei controlli e una migliore gestione delle eccezioni
 * rimuovere quel fastidioso ritorno alla scelta gui o cli, si ritorna sempre al menù principale
 *
 */

/**
 *  Interfaccia per l'utilizzo dell'ambiente GUI
 *  
 * @author Gabriele Magenta Biasina Matricola: 20044231
 */
public class InterfacciaUtenteGUI extends JFrame{
    private static JTextArea textArea;
    private ListaSpesa listaSpesaCorrente = null;


    ListaSpesa listaSpesa;
    private boolean nomeListaGiaInUso(String nomeLista) {
        for (ListaSpesa lista : GestoreListe.listeSpesa) {
            if (lista.getNome().equals(nomeLista)) {
                return true; 
            }
        }
        return false;
    }

    public void updateTextArea(String text) {
        if (textArea != null) {
            textArea.append(text + "\n");
        }
    }

    /**
     * Metodo dove risiede effettivamente la GUI
     */
    public void Interfaccia() {

        JPanel mainPanel = new JPanel();
        setPreferredSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new BorderLayout());
        setVisible(true);
        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(800, 500));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        JLabel labeltitolo = new JLabel("MENU OPERAZIONI: ");
        titlePanel.add(Box.createHorizontalGlue()); 
        titlePanel.add(labeltitolo);
        titlePanel.add(Box.createHorizontalGlue());
        
        JPanel optionsPanel = new JPanel();
        String[] voci = {"1. Creazione lista della spesa",
                "2. Eliminazione lista della spesa",
                "3. Creazione lista da file",
                "4. Scrittura lista su file",
                "5. Visualizzazione liste della spesa",
                "6. Modifica lista della spesa",
                "7. Ricerca rapida articolo",
                "8. Visualizzazione lista della spesa",
                "9. Aggiunta categoria",
                "10. Cancellazione categoria",
                "11. Modifica categoria"};
        JComboBox<String> listaMenu1 = new JComboBox<>(voci);
        optionsPanel.add(listaMenu1);
        listaMenu1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                String scelta = (String) comboBox.getSelectedItem();
                labeltitolo.setVisible(true);
                /**
                 * Menu principale
                 */
                switch (scelta) {
                    /**
                     * Creazione lista della spesa dato il nome
                     */
                    case "1. Creazione lista della spesa":
                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
                        labeltitolo.setVisible(false);
                        listaMenu1.setVisible(false);
                        
                        JPanel titlePanel = new JPanel();
                        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
                        JLabel titleLabel = new JLabel("CREAZIONE LISTA DELLA SPESA:");
                        titlePanel.add(Box.createHorizontalGlue()); 
                        titlePanel.add(titleLabel);
                        titlePanel.add(Box.createHorizontalGlue()); 
                        menuPanel.add(titlePanel);
                        
                        JPanel optionsPanel = new JPanel(new FlowLayout());
                        optionsPanel.add(new JLabel("Nome lista:"));
                        JTextField textFieldNomeLista = new JTextField(20);
                        optionsPanel.add(textFieldNomeLista);
                        JButton btnCreaLista = new JButton("Crea Lista");

                        btnCreaLista.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nomeLista = textFieldNomeLista.getText().trim(); 
                                if (nomeLista.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Inserisci un nome per la lista!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return; 
                                }

                                if (nomeListaGiaInUso(nomeLista)) {
                                    JOptionPane.showMessageDialog(null, "Il nome della lista è già in uso, inseriscine un altro!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return; 
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
                                menuPanel.removeAll();
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint(); 
                            }
                        });
                        
                        menuPanel.add(optionsPanel);
                        menuPanel.add(btnMenu1);
                        break;
                    /**
                     * Eliminazione lista della spesa dato il nome
                     */
                    case "2. Eliminazione lista della spesa":
                        listaMenu1.setVisible(false);
                        labeltitolo.setVisible(false);
                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
                        
                        JPanel title2Panel = new JPanel();
                        title2Panel.setLayout(new BoxLayout(title2Panel, BoxLayout.X_AXIS));
                        JLabel title2Label = new JLabel("ELIMINAZIONE LISTA DELLA SPESA:");
                        title2Panel.add(Box.createHorizontalGlue()); 
                        title2Panel.add(title2Label);
                        title2Panel.add(Box.createHorizontalGlue()); 
                        menuPanel.add(title2Panel);
                        
                        JPanel options2Panel = new JPanel(new FlowLayout());
                        
                        options2Panel.add(new JLabel("Nome lista da eliminare:"));
                        textFieldNomeLista = new JTextField(20);
                        options2Panel.add(textFieldNomeLista);
                        JButton btnEliminaLista = new JButton("Elimina lista");

                        btnEliminaLista.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nomeLista = textFieldNomeLista.getText().trim();
                                if (nomeLista.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Inserisci un nome per la lista!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }


                                if (!nomeListaGiaInUso(nomeLista)) {
                                    JOptionPane.showMessageDialog(null, "Il nome della lista non esiste!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                try {
                                    GestoreListe.rimuoviLista(nomeLista);
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
                                menuPanel.removeAll();
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint(); 
                            }
                        });


                        
                        menuPanel.add(options2Panel);
                        menuPanel.add(btnMenu2);
                        break;
                    /**
                     * Creazione lista della spesa da file dati nome lista e file path
                     */
                    case "3. Creazione lista da file":

                        listaMenu1.setVisible(false);
                        labeltitolo.setVisible(false);
                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
                        
                        JPanel title3Panel = new JPanel();
                        title3Panel.setLayout(new BoxLayout(title3Panel, BoxLayout.X_AXIS));
                        JLabel title3Label = new JLabel("CREAZIONE LISTA DELLA SPESA DA FILE:");
                        title3Panel.add(Box.createHorizontalGlue()); 
                        title3Panel.add(title3Label);
                        title3Panel.add(Box.createHorizontalGlue()); 
                        menuPanel.add(title3Panel);
                        
                        JPanel options3Panel = new JPanel(new FlowLayout());
                        options3Panel.add(new JLabel("Nome della nuova lista:"));
                        JTextField textFieldNomeFile = new JTextField(20);
                        options3Panel.add(textFieldNomeFile);
                        options3Panel.add(new JLabel("Path:"));
                        JTextField textFieldPathFile = new JTextField(20);
                        options3Panel.add(textFieldPathFile);
                        JButton btnCreaListaDaFile = new JButton("Crea Lista da File");

                        btnCreaListaDaFile.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nomeFile = textFieldNomeFile.getText().trim(); 
                                if (nomeFile.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Inserisci il nome di una lista esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                String path=textFieldPathFile.getText();
                                if (path.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Inserisci un path per il file!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                GestoreListe.leggiDaFile(path,nomeFile); 
                                JOptionPane.showMessageDialog(null, "Lista creata da file: " + nomeFile, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                updateTextArea("Lista creata da file: " + nomeFile);
                                textFieldNomeFile.setText("");
                                textFieldPathFile.setText("");
                            }
                        });
                        options3Panel.add(btnCreaListaDaFile);
                        JButton btnMenu3 = new JButton("Torna al Menu");
                        btnMenu3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuPanel.removeAll();
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint();
                            }
                        });


                        
                        menuPanel.add(options3Panel);
                        menuPanel.add(btnMenu3);
                        break;

                    /**
                     * Scrittura lista su file dato il nome della lista
                     */
                    case "4. Scrittura lista su file":
                        listaMenu1.setVisible(false);
                        labeltitolo.setVisible(false);
                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                        
                        JPanel title4Panel = new JPanel();
                        title4Panel.setLayout(new BoxLayout(title4Panel, BoxLayout.X_AXIS));
                        JLabel title4Label = new JLabel("SCRITTURA LISTA DELLA SPESA SU FILE:");
                        title4Panel.add(Box.createHorizontalGlue()); 
                        title4Panel.add(title4Label);
                        title4Panel.add(Box.createHorizontalGlue()); 
                        menuPanel.add(title4Panel);

                        
                        JPanel options4Panel = new JPanel(new FlowLayout());

                        
                        options4Panel.add(new JLabel("Nome lista da scrivere su file:"));
                        JTextField textFieldNomeLista4 = new JTextField(20);
                        options4Panel.add(textFieldNomeLista4);
                        JButton btnScritturaListaSuFile = new JButton("Scrittura Lista su File");

                        btnScritturaListaSuFile.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nomeLista4 = textFieldNomeLista4.getText().trim();
                                if (nomeLista4.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Inserisci un nome per la lista!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                ListaSpesa lista = null;
                                for (ListaSpesa ls : GestoreListe.listeSpesa) {
                                    if (ls.getNome().equals(nomeLista4)) {
                                        lista = ls;
                                        break;
                                    }
                                }

                                if (lista == null) {
                                    JOptionPane.showMessageDialog(null, "Lista non trovata!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                JFileChooser fileChooser = new JFileChooser();
                                fileChooser.setDialogTitle("Scegli il file di destinazione");
                                int userSelection = fileChooser.showSaveDialog(null);
                                if (userSelection == JFileChooser.APPROVE_OPTION) {
                                    File fileToSave = fileChooser.getSelectedFile();

                                    try {
                                        lista.scriviSuFile(fileToSave.getAbsolutePath());
                                        JOptionPane.showMessageDialog(null, "Lista scritta su file: " + fileToSave.getAbsolutePath(), "Successo", JOptionPane.INFORMATION_MESSAGE);
                                        updateTextArea("Lista scritta su file: " + fileToSave.getAbsolutePath());
                                    } catch (ListaException ex) {
                                        JOptionPane.showMessageDialog(null, "Errore durante la scrittura della lista su file: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                                        updateTextArea("Errore: " + ex.getMessage());
                                    }
                                }
                            }
                        });

                        options4Panel.add(btnScritturaListaSuFile);
                        JButton btnMenu4 = new JButton("Torna al Menu");
                        btnMenu4.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuPanel.removeAll();
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint(); 
                            }
                        });

                        
                        menuPanel.add(options4Panel);
                        menuPanel.add(btnMenu4);
                        break;

                    /**
                     * Visualizzazione di tutti i nomi delle liste presenti nel Gestore Liste
                     */
                    case "5. Visualizzazione liste della spesa":
                        listaMenu1.setVisible(false);
                        labeltitolo.setVisible(false);
                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                        
                        JPanel title5Panel = new JPanel();
                        title5Panel.setLayout(new BoxLayout(title5Panel, BoxLayout.X_AXIS));
                        JLabel title5Label = new JLabel("NOMI LISTE DELLA SPESA:");
                        title5Panel.add(Box.createHorizontalGlue()); 
                        title5Panel.add(title5Label);
                        title5Panel.add(Box.createHorizontalGlue()); 
                        menuPanel.add(title5Panel);

                        JPanel nomiListePanel = new JPanel(new FlowLayout());

                        StringBuilder nomiListeStringBuilder = new StringBuilder();
                        for (ListaSpesa listaSpesa : GestoreListe.listeSpesa) {
                            nomiListeStringBuilder.append(listaSpesa.getNome()).append(", ");
                        }
                        String nomiListeStringa = nomiListeStringBuilder.toString();
                        if (nomiListeStringa.length() > 2) {
                            nomiListeStringa = nomiListeStringa.substring(0, nomiListeStringa.length() - 2);
                        }

                        if (nomiListeStringa != null && !nomiListeStringa.isEmpty()) {
                            String[] nomiListeArray = nomiListeStringa.split(",");
                            for (String nomeLista : nomiListeArray) {
                                JLabel nomeListaLabel = new JLabel(nomeLista.trim());
                                nomiListePanel.add(nomeListaLabel);
                            }
                        } else {
                            JLabel avvisoLabel = new JLabel("Nessuna lista presente");
                            nomiListePanel.add(avvisoLabel);
                        }
                        menuPanel.add(nomiListePanel);

                        JButton btnMenu = new JButton("Torna al Menu");
                        btnMenu.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuPanel.removeAll();
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint(); 
                            }
                        });
                        menuPanel.add(btnMenu);
                        menuPanel.revalidate();
                        menuPanel.repaint();
                        break;

                    /**
                     * Modifica lista della spesa al cui interno trovate un sotto-menu
                     */
                    case "6. Modifica lista della spesa":
                        listaMenu1.setVisible(false);
                        labeltitolo.setVisible(false);
                        menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.Y_AXIS));

                        
                        JPanel title6Panel = new JPanel();
                        title6Panel.setLayout(new BoxLayout(title6Panel,BoxLayout.X_AXIS ));
                        JLabel title6Label = new JLabel("MODIFICA LISTA DELLA SPESA:");
                        title6Panel.add(Box.createHorizontalGlue()); 
                        title6Panel.add(title6Label);
                        title6Panel.add(Box.createHorizontalGlue()); 
                        menuPanel.add(title6Panel, "Title");
                        title6Panel.setVisible(true);

                        
                        JPanel options6Panel = new JPanel(new FlowLayout());

                        
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
                                    return;
                                }

                                if (!nomeListaGiaInUso(nomeLista)) {
                                    JOptionPane.showMessageDialog(null, "Il nome della lista non esiste!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                String[] opzioniModifica = {"Modifica nome lista","Aggiungi articolo", "Rimuovi articolo", "Modifica articolo", "Ricerca articoli per categoria"};
                                String sceltaModifica = (String) JOptionPane.showInputDialog(null, "Scegli un'operazione di modifica", "Modifica Lista della Spesa", JOptionPane.QUESTION_MESSAGE, null, opzioniModifica, opzioniModifica[0]);


                                if (sceltaModifica != null) {
                                    menuPanel.removeAll();
                                    switch (sceltaModifica) {
                                        /**
                                         * Modifica del nome della lista
                                         */
                                        case "Modifica nome lista":
                                            listaMenu1.setVisible(false);
                                            labeltitolo.setVisible(false);
                                            menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                                            for (ListaSpesa lista : GestoreListe.listeSpesa) {
                                                if (lista.getNome().equals(nomeLista)) {
                                                    listaSpesaCorrente = lista;
                                                    break;
                                                }
                                            }

                                            if (listaSpesaCorrente == null) {
                                                JOptionPane.showMessageDialog(null, "La lista della spesa " + nomeLista + " non esiste.", "Errore", JOptionPane.ERROR_MESSAGE);
                                                break;
                                            }

                                            
                                            JPanel title8Panel = new JPanel();
                                            title8Panel.setLayout(new BoxLayout(title8Panel, BoxLayout.X_AXIS));
                                            JLabel title8Label = new JLabel("MODIFICA NOME LISTA: " + nomeLista);
                                            title8Panel.add(Box.createHorizontalGlue()); 
                                            title8Panel.add(title8Label);
                                            title8Panel.add(Box.createHorizontalGlue()); 
                                            menuPanel.add(title8Panel);

                                            
                                            JPanel options8Panel = new JPanel(new FlowLayout());
                                            options8Panel.add(new JLabel("Nuovo nome lista:"));
                                            JTextField textFieldNuovoNomeLista = new JTextField(20);
                                            options8Panel.add(textFieldNuovoNomeLista);
                                            JButton btnModificaNomeLista = new JButton("Modifica");
                                            options8Panel.add(btnModificaNomeLista);
                                            menuPanel.add(options8Panel);

                                            btnModificaNomeLista.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {
                                                    String nuovoNomeLista = textFieldNuovoNomeLista.getText();
                                                    if (nuovoNomeLista != null && !nuovoNomeLista.isEmpty()) {
                                                        boolean nomeEsistente = false;
                                                        for (ListaSpesa lista : GestoreListe.listeSpesa) {
                                                            if (lista.getNome().equals(nuovoNomeLista)) {
                                                                nomeEsistente = true;
                                                                break;
                                                            }
                                                        }

                                                        if (nomeEsistente) {
                                                            JOptionPane.showMessageDialog(null, "Esiste già una lista con il nome " + nuovoNomeLista + ".", "Errore", JOptionPane.ERROR_MESSAGE);
                                                        } else {
                                                            listaSpesaCorrente.setNome(nuovoNomeLista);
                                                            JOptionPane.showMessageDialog(null, "Nome della lista modificato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "Il campo del nuovo nome deve essere compilato.", "Errore", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                            });

                                            JButton btnMenu8 = new JButton("Torna al Menu");
                                            btnMenu8.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    menuPanel.removeAll();
                                                    menuPanel.add(labeltitolo);
                                                    menuPanel.add(listaMenu1);
                                                    listaMenu1.setVisible(true);
                                                    menuPanel.revalidate();
                                                    menuPanel.repaint();
                                                }
                                            });

                                            menuPanel.add(btnMenu8);
                                            menuPanel.revalidate();
                                            menuPanel.repaint();
                                            break;

                                        /**
                                         * Aggiunta di un articolo dati Nome e prezzo, in maniera opzionale è possibile inserire
                                         * la quantità (di default 1) e la categoria (di default "Non categorizzato")
                                         */
                                        case "Aggiungi articolo":
                                            
                                            JPanel title0Panel = new JPanel();
                                            title0Panel.setLayout(new BoxLayout(title0Panel, BoxLayout.X_AXIS));
                                            JLabel title0Label = new JLabel("AGGIUNTA ARTICOLO ALLA LISTA DELLA SPESA:");
                                            title0Panel.add(Box.createHorizontalGlue()); 
                                            title0Panel.add(title0Label);
                                            title0Panel.add(Box.createHorizontalGlue()); 
                                            menuPanel.add(title0Panel);

                                            JPanel options0Panel = new JPanel(new GridBagLayout());
                                            GridBagConstraints gbc = new GridBagConstraints();
                                            gbc.insets = new Insets(5, 5, 5, 5);
                                            gbc.anchor = GridBagConstraints.WEST;

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
                                            options0Panel.add(btnAggiungiArticolo,gbc);

                                            gbc.gridx=0;
                                            gbc.gridy=5;
                                            gbc.gridwidth=2;
                                            JButton btnMenu0 = new JButton("Torna al Menu");
                                            btnMenu0.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    menuPanel.removeAll();
                                                    menuPanel.add(labeltitolo);
                                                    menuPanel.add(listaMenu1);
                                                    listaMenu1.setVisible(true);
                                                    menuPanel.revalidate(); 
                                                    menuPanel.repaint(); 
                                                }
                                            });

                                            options0Panel.add(btnMenu0,gbc);

                                            btnAggiungiArticolo.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    String nomeArticolo = textFieldNomeArticolo.getText().trim();
                                                    String quantitaString = textFieldQuantita.getText().trim();
                                                    String prezzoString = textFieldPrezzo.getText().trim();
                                                    String categoria = textFieldCategoria.getText().trim();
                                                    if (nomeLista.isEmpty() || nomeArticolo.isEmpty()) {
                                                        JOptionPane.showMessageDialog(null, "Inserisci nome articolo e prezzo!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                        return;
                                                    }

                                                    int quantita;
                                                    try {
                                                        if (quantitaString.isEmpty()) {
                                                            quantita = 1;
                                                        } else {
                                                            quantita = Integer.parseInt(quantitaString);
                                                            if (quantita <= 0) {
                                                                throw new NumberFormatException();
                                                            }
                                                        }
                                                    } catch (NumberFormatException ex) {
                                                        JOptionPane.showMessageDialog(null, "La quantità deve essere un numero intero positivo!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                        return;
                                                    }

                                                    float prezzo;
                                                    try {
                                                        prezzo = Float.parseFloat(prezzoString);
                                                    }catch (NumberFormatException ex){
                                                        JOptionPane.showMessageDialog(null,"Il prezzo non può contenere delle stringhe o dei caratteri speciali!","Errore",JOptionPane.ERROR_MESSAGE);
                                                        return;
                                                    }
                                                    if (prezzo <= 0) {
                                                        JOptionPane.showMessageDialog(null, "Il prezzo deve essere maggiore di zero!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                        return;
                                                    }

                                                    String categoriaArticolo;
                                                    if (categoria.isEmpty()) {
                                                        categoriaArticolo = "non categorizzato";
                                                    } else {
                                                        categoriaArticolo = categoria;
                                                    }

                                                    for (ListaSpesa lista : GestoreListe.listeSpesa) {
                                                        if (lista.getNome().equals(nomeLista)) {
                                                            for (Articolo articolo : lista.getArticoli()) {
                                                                if (articolo.getNome().equalsIgnoreCase(nomeArticolo)) {
                                                                    int scelta = JOptionPane.showConfirmDialog(null, "L'articolo '" + nomeArticolo + "' è già presente nella lista.\nVuoi aumentarne la quantità?", "Articolo già presente", JOptionPane.YES_NO_OPTION);
                                                                    if (scelta == JOptionPane.YES_OPTION) {
                                                                        articolo.setQuantita(articolo.getQuantita() + quantita);
                                                                        JOptionPane.showMessageDialog(null, "Quantità aggiornata!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                                                                        updateTextArea("Quantità aggiornata per l'articolo '" + nomeArticolo + "' nella lista: " + nomeLista);
                                                                        return;
                                                                    } else {
                                                                        return;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    for (ListaSpesa lista : GestoreListe.listeSpesa) {
                                                        if (lista.getNome().equals(nomeLista)) {
                                                            Articolo nuovoArticolo = new Articolo(nomeArticolo, quantita, categoria,Float.parseFloat(prezzoString));
                                                            lista.aggiungiArticolo(nuovoArticolo);
                                                            JOptionPane.showMessageDialog(null, "Articolo aggiunto alla lista: " + nomeLista, "Successo", JOptionPane.INFORMATION_MESSAGE);
                                                            updateTextArea("Articolo aggiunto alla lista: " + nomeLista);
                                                            break;
                                                        }
                                                    }
                                                    textFieldNomeArticolo.setText("");
                                                    textFieldQuantita.setText("");
                                                    textFieldPrezzo.setText("");
                                                    textFieldCategoria.setText("");
                                                }
                                            });
                                            menuPanel.add(options0Panel);
                                            menuPanel.revalidate();
                                            menuPanel.repaint();
                                            break;

                                        /**
                                         * Rimozione di un articolo dato il suo nome
                                         */
                                        case "Rimuovi articolo":
                                            listaMenu1.setVisible(false);
                                            labeltitolo.setVisible(false);
                                            menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
                                            updateTextArea("Rimuovi articolo dalla lista: " + nomeLista);

                                            JPanel title1Panel = new JPanel();
                                            title1Panel.setLayout(new BoxLayout(title1Panel, BoxLayout.X_AXIS));
                                            JLabel title1Label = new JLabel("RIMOZIONE ARTICOLO DALLA LISTA DELLA SPESA:");
                                            title1Panel.add(Box.createHorizontalGlue()); 
                                            title1Panel.add(title1Label);
                                            title1Panel.add(Box.createHorizontalGlue()); 
                                            menuPanel.add(title1Panel);

                                            JPanel optionsaPanel = new JPanel();
                                            optionsaPanel.setLayout(new BoxLayout(optionsaPanel, BoxLayout.Y_AXIS));

                                            JPanel nomeArticoloPanel = new JPanel(new FlowLayout());
                                            nomeArticoloPanel.add(new JLabel("Nome dell'articolo da rimuovere:"));
                                            JTextField textFieldNomeArticolo1 = new JTextField(20);
                                            nomeArticoloPanel.add(textFieldNomeArticolo1);

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
                                                                articoloRimosso = true;
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
                                                    textFieldNomeArticolo1.setText("");
                                                }
                                            });
                                            nomeArticoloPanel.add(btnRimuoviArticolo);
                                            optionsaPanel.add(nomeArticoloPanel);

                                            JButton btnMenu1 = new JButton("Torna al Menu");
                                            btnMenu1.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    menuPanel.removeAll();
                                                    menuPanel.add(labeltitolo);
                                                    menuPanel.add(listaMenu1);
                                                    listaMenu1.setVisible(true);
                                                    menuPanel.revalidate(); 
                                                    menuPanel.repaint(); 
                                                }
                                            });

                                            optionsaPanel.add(Box.createVerticalStrut(10));
                                            optionsaPanel.add(btnMenu1);
                                            menuPanel.add(optionsaPanel);
                                            menuPanel.revalidate();
                                            menuPanel.repaint();
                                            break;

                                        /**
                                         * Modifica di tutti gli elementi di un articolo
                                         */
                                        case "Modifica articolo":
                                            
                                            menuPanel.removeAll();
                                            menuPanel.setLayout(new BorderLayout());

                                            JPanel title2Panel = new JPanel();
                                            title2Panel.setLayout(new BoxLayout(title2Panel, BoxLayout.X_AXIS));
                                            JLabel title2Label = new JLabel("MODIFICA ARTICOLO DELLA LISTA DELLA SPESA:");
                                            title2Panel.add(Box.createHorizontalGlue());
                                            title2Panel.add(title2Label);
                                            title2Panel.add(Box.createHorizontalGlue());
                                            menuPanel.add(title2Panel, BorderLayout.NORTH);

                                            JPanel options2Panel = new JPanel(new FlowLayout());
                                            options2Panel.add(new JLabel("Inserisci il nome dell'articolo da modificare: "));
                                            JTextField textFieldNomeArticolo2 = new JTextField(20);
                                            options2Panel.add(textFieldNomeArticolo2);
                                            JButton btnModificaArticolo1 = new JButton("Modifica articolo");
                                            options2Panel.add(btnModificaArticolo1);

                                            menuPanel.add(options2Panel, BorderLayout.CENTER);


                                            JPanel optionsbPanel = new JPanel(new GridBagLayout());
                                            GridBagConstraints gbd = new GridBagConstraints();
                                            gbd.insets = new Insets(5, 5, 5, 5);
                                            gbd.anchor = GridBagConstraints.WEST;

                                            gbd.gridx = 0;
                                            gbd.gridy = 0;
                                            optionsbPanel.add(new JLabel("Nuovo nome articolo:"), gbd);
                                            gbd.gridx = 1;
                                            JTextField textFieldNuovoNomeArticolo = new JTextField(20);
                                            optionsbPanel.add(textFieldNuovoNomeArticolo, gbd);

                                            gbd.gridx = 0;
                                            gbd.gridy = 1;
                                            optionsbPanel.add(new JLabel("Nuova quantità:"), gbd);
                                            gbd.gridx = 1;
                                            JTextField textFieldNuovaQuantita = new JTextField(5);
                                            optionsbPanel.add(textFieldNuovaQuantita, gbd);

                                            gbd.gridx = 0;
                                            gbd.gridy = 2;
                                            optionsbPanel.add(new JLabel("Nuovo prezzo:"), gbd);
                                            gbd.gridx = 1;
                                            JTextField textFieldNuovoPrezzo = new JTextField(20);
                                            optionsbPanel.add(textFieldNuovoPrezzo, gbd);

                                            gbd.gridx = 0;
                                            gbd.gridy = 3;
                                            optionsbPanel.add(new JLabel("Nuova categoria:"), gbd);
                                            gbd.gridx = 1;
                                            JTextField textFieldNuovaCategoria = new JTextField(20);
                                            optionsbPanel.add(textFieldNuovaCategoria, gbd);

                                            gbd.gridx = 0;
                                            gbd.gridy = 4;
                                            gbd.gridwidth = 2;
                                            gbd.anchor = GridBagConstraints.CENTER;
                                            JButton btnModificaArticolo = new JButton("Modifica articolo");
                                            optionsbPanel.add(btnModificaArticolo, gbd);

                                            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                                            JButton btnMenu2 = new JButton("Torna al Menu");
                                            btnMenu2.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    menuPanel.removeAll();
                                                    
                                                    menuPanel.add(labeltitolo, BorderLayout.NORTH);
                                                    menuPanel.add(listaMenu1, BorderLayout.CENTER);
                                                    listaMenu1.setVisible(true);
                                                    menuPanel.revalidate();
                                                    menuPanel.repaint();
                                                }
                                            });

                                            bottomPanel.add(btnMenu2);
                                            menuPanel.add(bottomPanel, BorderLayout.SOUTH);

                                            btnModificaArticolo1.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    String nomeArticolo = textFieldNomeArticolo2.getText().trim();

                                                    if (nomeArticolo.isEmpty()) {
                                                        JOptionPane.showMessageDialog(null, "Inserisci nome articolo!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                        return;
                                                    }

                                                    boolean articoloTrovato = false;

                                                    for (ListaSpesa lista : GestoreListe.listeSpesa) {
                                                        if (lista.getNome().equals(nomeLista)) {
                                                            for (Articolo articolo : lista.getArticoli()) {
                                                                if (articolo.getNome().equalsIgnoreCase(nomeArticolo)) {
                                                                    articoloTrovato = true;
                                                                    break;
                                                                }
                                                            }
                                                            break;
                                                        }
                                                    }

                                                    if (articoloTrovato) {
                                                        menuPanel.remove(options2Panel);
                                                        menuPanel.add(optionsbPanel, BorderLayout.CENTER);
                                                        menuPanel.revalidate();
                                                        menuPanel.repaint();
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "Articolo non trovato nella lista: " + nomeLista, "Errore", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                            });

                                            btnModificaArticolo.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    String nuovoNomeArticolo = textFieldNuovoNomeArticolo.getText().trim();
                                                    String nuovaQuantitaString = textFieldNuovaQuantita.getText().trim();
                                                    String nuovoPrezzoString = textFieldNuovoPrezzo.getText().trim();
                                                    String nuovaCategoria = textFieldNuovaCategoria.getText().trim();

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
                                                                if (articolo.getNome().equalsIgnoreCase(textFieldNomeArticolo2.getText().trim())) {
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

                                            menuPanel.revalidate();
                                            menuPanel.repaint();
                                            break;
                                        /**
                                         * Ricerca di articoli per categoria dati nome lista e categoria
                                         */
                                        case "Ricerca articoli per categoria":
                                            listaMenu1.setVisible(false);
                                            labeltitolo.setVisible(false);
                                            menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                                            JPanel title9Panel = new JPanel();
                                            title9Panel.setLayout(new BoxLayout(title9Panel, BoxLayout.X_AXIS));
                                            JLabel title9Label = new JLabel("RICERCA ARTICOLI PER CATEGORIA:");
                                            title9Panel.add(Box.createHorizontalGlue());
                                            title9Panel.add(title9Label);
                                            title9Panel.add(Box.createHorizontalGlue());
                                            menuPanel.add(title9Panel);

                                            JPanel options9Panel = new JPanel(new FlowLayout());
                                            options9Panel.add(new JLabel("Nome lista da cercare:"));
                                            JTextField textFieldNomeLista9 = new JTextField(20);
                                            options9Panel.add(textFieldNomeLista9);
                                            options9Panel.add(new JLabel("Categoria articolo:"));
                                            JTextField textFieldCercaCategoria = new JTextField(20);
                                            options9Panel.add(textFieldCercaCategoria);
                                            JButton btnCercaCategoria = new JButton("Cerca");
                                            options9Panel.add(btnCercaCategoria);
                                            menuPanel.add(options9Panel);

                                            JTextArea textAreaCategoria = new JTextArea(10, 30);
                                            textAreaCategoria.setEditable(false);
                                            JScrollPane scrollPaneCategoria = new JScrollPane(textAreaCategoria);
                                            menuPanel.add(scrollPaneCategoria);

                                            btnCercaCategoria.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {
                                                    String nomeListaRicerca = textFieldNomeLista9.getText().trim();
                                                    String categoria = textFieldCercaCategoria.getText().trim();
                                                    if (nomeListaRicerca.isEmpty()) {
                                                        JOptionPane.showMessageDialog(null, "Inserisci il nome della lista!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                        return;
                                                    }
                                                    if (categoria.isEmpty()) {
                                                        JOptionPane.showMessageDialog(null, "Inserisci la categoria dell'articolo!", "Errore", JOptionPane.ERROR_MESSAGE);
                                                        return;
                                                    }

                                                    try {
                                                        ListaSpesa listaSpesaRicerca = ricercaLista(nomeListaRicerca);
                                                        if (listaSpesaRicerca == null) {
                                                            JOptionPane.showMessageDialog(null, "La lista della spesa " + nomeListaRicerca + " non esiste.", "Errore", JOptionPane.ERROR_MESSAGE);
                                                            return;
                                                        }

                                                        ArrayList<Articolo> articoliTrovati = new ArrayList<>();
                                                        for (Articolo articolo : listaSpesaRicerca.getArticoli()) {
                                                            if (articolo.getCategoria().equalsIgnoreCase(categoria)) {
                                                                articoliTrovati.add(articolo);
                                                            }
                                                        }

                                                        if (articoliTrovati.isEmpty()) {
                                                            JOptionPane.showMessageDialog(null, "Nessun articolo trovato nella categoria: " + categoria, "Risultato della ricerca", JOptionPane.INFORMATION_MESSAGE);
                                                        } else {
                                                            textAreaCategoria.setText("");
                                                            for (Articolo articolo : articoliTrovati) {
                                                                textAreaCategoria.append("Articolo: " + articolo.getNome() + ", Quantità: " + articolo.getQuantita() + ", Prezzo: " + articolo.getCosto() + ", Categoria: " + articolo.getCategoria() + "\n");
                                                            }
                                                        }
                                                    } catch (GestoreException ex) {
                                                        JOptionPane.showMessageDialog(null, "Errore durante la ricerca: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                            });


                                            JButton btnMenu9 = new JButton("Torna al Menu");
                                            btnMenu9.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    menuPanel.removeAll();
                                                    menuPanel.add(labeltitolo);
                                                    menuPanel.add(listaMenu1);
                                                    listaMenu1.setVisible(true);
                                                    menuPanel.revalidate();
                                                    menuPanel.repaint();
                                                }
                                            });

                                            menuPanel.add(Box.createVerticalGlue());
                                            menuPanel.add(btnMenu9);
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
                                menuPanel.removeAll();
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint(); 
                            }
                        });

                        menuPanel.add(options6Panel);
                        menuPanel.add(btnMenu6);
                        break;
                    /**
                     * Ricerca di un articolo dati nome della lista e un prefisso del nome dell'articolo
                     */
                    case "7. Ricerca rapida articolo":
                        listaMenu1.setVisible(false);
                        labeltitolo.setVisible(false);
                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                        
                        JPanel title7Panel = new JPanel();
                        title7Panel.setLayout(new BoxLayout(title7Panel, BoxLayout.X_AXIS));
                        JLabel title7Label = new JLabel("RICERCA RAPIDA ARTICOLO:");
                        title7Panel.add(Box.createHorizontalGlue()); 
                        title7Panel.add(title7Label);
                        title7Panel.add(Box.createHorizontalGlue()); 
                        menuPanel.add(title7Panel, "Title");
                        title7Panel.setVisible(true);

                        
                        JPanel options7Panel = new JPanel(new FlowLayout());

                        
                        options7Panel.add(new JLabel("Nome lista da cercare:"));
                        JTextField textFieldNomeLista7 = new JTextField(20);
                        options7Panel.add(textFieldNomeLista7);
                        options7Panel.add(new JLabel("Prefisso articolo:"));
                        JTextField textFieldPrefisso = new JTextField(20);
                        options7Panel.add(textFieldPrefisso);
                        JButton btnCercaLista = new JButton("Cerca");
                        options7Panel.add(btnCercaLista);
                        menuPanel.add(options7Panel, "Options");

                        JTextArea textAreaLista = new JTextArea(10, 30);
                        textAreaLista.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textAreaLista);

                        btnCercaLista.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                String nomeListaRicerca = textFieldNomeLista7.getText();
                                String prefisso = textFieldPrefisso.getText();
                                if (nomeListaRicerca != null && !nomeListaRicerca.isEmpty()) {
                                    try {
                                        ListaSpesa listaSpesaRicerca = ricercaLista(nomeListaRicerca);
                                        if (listaSpesaRicerca == null) {
                                            JOptionPane.showMessageDialog(null, "La lista della spesa " + nomeListaRicerca + " non esiste.", "Errore", JOptionPane.ERROR_MESSAGE);
                                            return;
                                        }
                                        if (prefisso.isEmpty()) {
                                            JOptionPane.showMessageDialog(null, "Il prefisso non può rimanere vuoto! ", "Errore", JOptionPane.ERROR_MESSAGE);
                                            return;
                                        }
                                        if (prefisso != null && !prefisso.isEmpty()) {
                                            ArrayList<Articolo> articoliTrovati = cercaArticoliPerPrefisso(listaSpesaRicerca, prefisso);
                                            if (articoliTrovati.isEmpty()) {
                                                JOptionPane.showMessageDialog(null, "Nessun articolo trovato con il prefisso: " + prefisso, "Risultato della ricerca", JOptionPane.INFORMATION_MESSAGE);
                                            } else {
                                                textAreaLista.setText("");
                                                for (Articolo articolo : articoliTrovati) {
                                                    textAreaLista.append("Articolo: " + articolo.getNome() + ", Quantità: " + articolo.getQuantita() + ", Prezzo: " + articolo.getCosto() + ", Categoria: " + articolo.getCategoria() + "\n");
                                                }
                                            }
                                        }
                                    } catch (GestoreException ex) {
                                        JOptionPane.showMessageDialog(null, "Errore durante la ricerca: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                textFieldPrefisso.setText("");
                                textFieldNomeLista7.setText("");
                            }
                        });

                        JButton btnMenu7 = new JButton("Torna al Menu");
                        btnMenu7.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuPanel.removeAll();
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint(); 
                            }
                        });

                        options7Panel.add(btnMenu7);
                        menuPanel.add(scrollPane);
                        menuPanel.add(options7Panel);
                        menuPanel.add(btnMenu7);
                        break;

                    /**
                     * Visualizzazione lista della spesa con calcolo del totale
                     */
                    case "8. Visualizzazione lista della spesa":
                        listaMenu1.setVisible(false);
                        labeltitolo.setVisible(false);
                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

                        JPanel title8Panel = new JPanel();
                        title8Panel.setLayout(new BoxLayout(title8Panel, BoxLayout.X_AXIS));
                        JLabel title8Label = new JLabel("VISUALIZZAZIONE LISTA DELLA SPESA:");
                        title8Panel.add(Box.createHorizontalGlue()); 
                        title8Panel.add(title8Label);
                        title8Panel.add(Box.createHorizontalGlue()); 
                        menuPanel.add(title8Panel);
                        
                        JPanel input8Panel = new JPanel(new FlowLayout());
                        input8Panel.add(new JLabel("Nome lista:"));
                        JTextField textFieldNomeLista8 = new JTextField(20);
                        input8Panel.add(textFieldNomeLista8);
                        JButton btnVisualizzaLista = new JButton("Visualizza Lista");
                        input8Panel.add(btnVisualizzaLista);
                        menuPanel.add(input8Panel);
                        
                        JTextArea textAreaVisualizzazione = new JTextArea(15, 30);
                        textAreaVisualizzazione.setEditable(false);
                        JScrollPane scrollPane1 = new JScrollPane(textAreaVisualizzazione);
                        menuPanel.add(scrollPane1);
                        
                        JButton btnMenu8 = new JButton("Torna al Menu");
                        btnMenu8.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuPanel.removeAll();
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint(); 
                            }
                        });
                        menuPanel.add(btnMenu8);

                        btnVisualizzaLista.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nomeLista8 = textFieldNomeLista8.getText().trim();
                                if (nomeLista8.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Inserisci un nome per la lista!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                try {
                                    ListaSpesa listaSpesa = ricercaLista(nomeLista8);
                                    if (listaSpesa.getArticoli().isEmpty()) {
                                        textAreaVisualizzazione.setText("La lista della spesa è vuota");
                                    } else {
                                        StringBuilder visualizzazione = new StringBuilder("Articoli nella lista della spesa:\n");
                                        for (Articolo articolo : listaSpesa.getArticoli()) {
                                            visualizzazione.append("Nome: ").append(articolo.getNome())
                                                    .append(", Quantità: ").append(articolo.getQuantita())
                                                    .append(", Prezzo: ").append(articolo.getCosto())
                                                    .append(", Categoria: ").append(articolo.getCategoria())
                                                    .append("\n");
                                        }
                                        double totale = listaSpesa.costoTotale();
                                        visualizzazione.append("\nTotale della spesa: ").append(String.format("%.2f", totale));
                                        textAreaVisualizzazione.setText(visualizzazione.toString());
                                        updateTextArea(visualizzazione.toString());
                                    }
                                } catch (GestoreException ex) {
                                    JOptionPane.showMessageDialog(null, "Errore durante la ricerca della lista: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        });
                        break;

                    /**
                     * Aggiunta di una categoria dato il suo nome
                     */
                    case "9. Aggiunta categoria":
                        listaMenu1.setVisible(false);
                        labeltitolo.setVisible(false);
                        String nuovaCategoria = JOptionPane.showInputDialog("Inserisci il nome della nuova categoria:");
                        if (nuovaCategoria != null && !nuovaCategoria.isEmpty()) {
                            GestoreListe.aggiungiCategoria(nuovaCategoria);
                            JOptionPane.showMessageDialog(null, "Categoria aggiunta: " + nuovaCategoria, "Aggiunta Categoria", JOptionPane.INFORMATION_MESSAGE);
                            updateTextArea("Categoria aggiunta: " + nuovaCategoria);
                        }
                        JButton btnMenu9 = new JButton("Torna al Menu");
                        btnMenu9.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuPanel.removeAll();
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint(); 
                            }
                        });
                        menuPanel.add(btnMenu9);
                        break;

                    /**
                     * Cancellazione di una categoria dato il suo nome
                     */
                    case "10. Cancellazione categoria":
                        listaMenu1.setVisible(false);
                        labeltitolo.setVisible(false);
                        String categoriaDaCancellare = JOptionPane.showInputDialog("Inserisci il nome della categoria da cancellare:");
                        if (categoriaDaCancellare != null && !categoriaDaCancellare.isEmpty()) {
                            try {
                                GestoreListe.cancellaCategoria(categoriaDaCancellare);
                            } catch (GestoreException ex) {
                                throw new RuntimeException(ex);
                            }
                            JOptionPane.showMessageDialog(null, "Categoria rimossa: " + categoriaDaCancellare, "Rimozione Categoria", JOptionPane.INFORMATION_MESSAGE);
                            updateTextArea("Categoria rimossa: " + categoriaDaCancellare);
                        }

                        JButton btnMenu10 = new JButton("Torna al Menu");
                        btnMenu10.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                
                                menuPanel.removeAll();
                                
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint(); 
                            }
                        });
                        menuPanel.add(btnMenu10);
                        break;

                    /**
                     * Modifica di una categoria dato il suo nome
                     */
                    case "11. Modifica categoria":
                        listaMenu1.setVisible(false);
                        labeltitolo.setVisible(false);
                        String categoriaVecchia = JOptionPane.showInputDialog("Inserisci il nome della categoria da modificare:");
                        String categoriaNuova = JOptionPane.showInputDialog("Inserisci il nuovo nome della categoria:");
                        if (categoriaVecchia != null && !categoriaVecchia.isEmpty() && categoriaNuova != null && !categoriaNuova.isEmpty()) {
                            try {
                                GestoreListe.modificaCategoria(categoriaVecchia, categoriaNuova);
                            } catch (ListaException ex) {
                                throw new RuntimeException(ex);
                            }
                            JOptionPane.showMessageDialog(null, "Categoria modificata da " + categoriaVecchia + " a " + categoriaNuova, "Modifica Categoria", JOptionPane.INFORMATION_MESSAGE);
                            updateTextArea("Categoria modificata da " + categoriaVecchia + " a " + categoriaNuova);
                        }


                        JButton btnMenu11 = new JButton("Torna al Menu");
                        btnMenu11.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                
                                menuPanel.removeAll();
                                
                                menuPanel.add(labeltitolo);
                                menuPanel.add(listaMenu1);
                                listaMenu1.setVisible(true);
                                menuPanel.revalidate(); 
                                menuPanel.repaint(); 
                            }
                        });
                        menuPanel.add(btnMenu11);
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