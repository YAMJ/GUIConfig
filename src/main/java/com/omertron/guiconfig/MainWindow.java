package com.omertron.guiconfig;

import com.omertron.guiconfig.model.LibraryInfo;
import com.omertron.guiconfig.tools.FileFilterExtension;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author stuart.boston
 */
public class MainWindow extends javax.swing.JFrame {

    private static final Logger LOGGER = Logger.getLogger(GuiConfig.class);
    private static LibraryInfo guiLibrary = new LibraryInfo();  // Used to temorarily store the library details
    private static DefaultTableModel modelViewLibraries = new DefaultTableModel();

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();

        // Add the column names to the table
        modelViewLibraries.setColumnIdentifiers(new String[]{"Path", "Player Path", "Exclude", "Description", "Prebuf", "Scrape Library"});

//        textPath.getDocument().addDocumentListener(new DocumentListener() {
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                LOGGER.info("TextPath: Changed");
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                LOGGER.info("TextPath: Remove");
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                LOGGER.info("TextPath: Insert: " + textPath.getText());
//            }
//        });
    }

    /**
     * Quit the program
     */
    private void quitProgram() {
        //TODO: Check to see if the library has been saved and warn if not
        System.exit(0);
    }

    /**
     * Add an entry to the library list
     */
    private void addLibrary() {
        // Add the various parts to the guiLibrary
        guiLibrary.setPath(textPath.getText());
        guiLibrary.setPlayerPath(textPlayerPath.getText());
        guiLibrary.setDescription(null);
        guiLibrary.setScrapeLibrary(Boolean.TRUE);
        
        // Check that the library is valid before we add it.
        if (guiLibrary.isValid()) {
            // Add the library to the array
            LOGGER.info("Adding library to array");
            GuiConfig.libraries.addLibrary(guiLibrary);
            modelViewLibraries.addRow(new String[]{guiLibrary.getPath(), guiLibrary.getPlayerPath(), guiLibrary.getExclude(), guiLibrary.getDescription(), guiLibrary.getPrebuf(), String.valueOf(guiLibrary.isScrapeLibrary())});
            clearEntry();
            updateLibraryCounter();
        } else {
            // Library is not valid
            LOGGER.info("Library entry is not valid");

            StringBuilder sb = new StringBuilder("The library etry is not valid\nPlease enter a ");
            if (StringUtils.isBlank(guiLibrary.getPath())) {
                sb.append("'path'");
                if (StringUtils.isBlank(guiLibrary.getPlayerPath())) {
                    sb.append(" and 'player path'");
                }
            } else if (StringUtils.isBlank(guiLibrary.getPlayerPath())) {
                sb.append("'player path'");
            }

            JOptionPane.showMessageDialog(rootPane, sb.toString(), "Invalid Library", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Save the library entries
     */
    private void saveLibrary() {
        // Save all the library entries
        LOGGER.info("Saving library");
        buttonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/omertron/guiconfig/images/add.png"))); // NOI18N
    }

    /**
     * Check for an update
     */
    private void checkForUpdate() {
        openLink(GuiConfig.convertStringToUri());
    }

    /**
     * Open the about screen
     */
    private void aboutProgram() {
        About.setVisible(true);
    }

    /**
     * Clear all the on-screen entries
     */
    private void clearEntry() {
        guiLibrary = new LibraryInfo();
        guiLibrary.setDescription("");
        guiLibrary.setPath("C:\\");
        guiLibrary.setPlayerPath("Please select a location");

        textPath.setText(guiLibrary.getPath());
        textPlayerPath.setText(guiLibrary.getPlayerPath());
    }

    /**
     * Clear all the generated entries and reset
     */
    private void clearAllEntries() {
        clearEntry();
        GuiConfig.libraries.clear();
        updateLibraryCounter();

        LOGGER.info("Clearing " + modelViewLibraries.getRowCount() + " library entries");
        modelViewLibraries.setRowCount(0);
    }

    private void openLink(String stringUrl) {
        try {
            openLink(new URI(stringUrl));
        } catch (URISyntaxException ex) {
            LOGGER.warn("Invalid URL: " + stringUrl);
        }
    }

    private void openLink(URI newUri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(newUri);
            } catch (IOException ex) {
                LOGGER.warn("Unable to open web browser!");
            }
        }
    }

    /**
     * Update the library label to increase or decrease the value
     */
    private void updateLibraryCounter() {

        int libraryCount = GuiConfig.libraries.count();

        if (libraryCount < 0) {
            libraryCount = 0;
        } else if (libraryCount > 9) {
            libraryCount = 9;
        }

        StringBuilder numberFilename = new StringBuilder("/images/number-");
        numberFilename.append(libraryCount).append(".png");

        labelLibraryCounter.setIcon(new javax.swing.ImageIcon(getClass().getResource(numberFilename.toString()))); // NOI18N
        if (libraryCount == 1) {
            labelLibraryCounter.setText("Library");
        } else {
            labelLibraryCounter.setText("Libraries");
        }

//        LOGGER.info("Updating library counter to '" + String.valueOf(libraryCount) + "': " + numberFilename.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        groupLocation = new javax.swing.ButtonGroup();
        About = new javax.swing.JDialog();
        labelYamjLogo = new javax.swing.JLabel();
        buttonOk = new javax.swing.JButton();
        labelProgramName = new javax.swing.JLabel();
        labelProgramUrl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        buttonDonate = new javax.swing.JButton();
        buttonVisitMPS = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();
        logoYAMJ = new javax.swing.JLabel();
        labelLibraryCounter = new javax.swing.JLabel();
        comboMediaPlayerModel = new javax.swing.JComboBox();
        labelMediaPlayerModel = new javax.swing.JLabel();
        tabPanelLibraries = new javax.swing.JTabbedPane();
        tabAddLibrary = new javax.swing.JPanel();
        labelPath = new javax.swing.JLabel();
        textPath = new javax.swing.JTextField();
        textPathCalc = new javax.swing.JTextField();
        buttonBrowsePath = new javax.swing.JButton();
        buttonAdd = new javax.swing.JButton();
        buttonClear = new javax.swing.JButton();
        panelFileLocation = new javax.swing.JPanel();
        radioUsb = new javax.swing.JRadioButton();
        radioNetwork = new javax.swing.JRadioButton();
        radioInternal = new javax.swing.JRadioButton();
        radioPC = new javax.swing.JRadioButton();
        comboUsb = new javax.swing.JComboBox();
        comboNetwork = new javax.swing.JComboBox();
        comboInternal = new javax.swing.JComboBox();
        labelPlayerPath = new javax.swing.JLabel();
        textPlayerPath = new javax.swing.JTextField();
        tabViewLibraries = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableViewLibraries = new javax.swing.JTable();
        buttonClearAll = new javax.swing.JButton();
        tabLibraryConfig = new javax.swing.JPanel();
        labelJukebox = new javax.swing.JLabel();
        textJukebox = new javax.swing.JTextField();
        buttonBrowseJukebox = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuLoadLibraryFile = new javax.swing.JMenuItem();
        menuFileAdd = new javax.swing.JMenuItem();
        menuFileSave = new javax.swing.JMenuItem();
        menuFileClear = new javax.swing.JMenuItem();
        menuFileClearAll = new javax.swing.JMenuItem();
        menuFileExit = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuHelpDonate = new javax.swing.JMenuItem();
        menuHelpUpdate = new javax.swing.JMenuItem();
        menuHelpAbout = new javax.swing.JMenuItem();

        fileChooser.setDialogTitle("This is my open dialog");
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        About.setTitle("About");
        About.setAlwaysOnTop(true);
        About.setIconImage(null);
        About.setMinimumSize(new java.awt.Dimension(370, 500));
        About.setModal(true);
        About.setResizable(false);

        labelYamjLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/YAMJ.png"))); // NOI18N

        buttonOk.setText("OK");
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });

        labelProgramName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProgramName.setText(GuiConfig.getTitle() + " " + GuiConfig.getVersion());

        labelProgramUrl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProgramUrl.setText(GuiConfig.getUrlMediaPlayerSite());
        labelProgramUrl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelProgramUrlMouseClicked(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(jTextArea1.getFont());
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("This is the about text. We should talk about what YAMJ should do and can do");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(null);
        jTextArea1.setFocusable(false);
        jScrollPane1.setViewportView(jTextArea1);

        buttonDonate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/paypal.png"))); // NOI18N
        buttonDonate.setText("<HTML>Donate to support YAMJ</HTML>");
        buttonDonate.setDefaultCapable(false);
        buttonDonate.setMaximumSize(new java.awt.Dimension(225, 75));
        buttonDonate.setMinimumSize(new java.awt.Dimension(225, 75));
        buttonDonate.setPreferredSize(new java.awt.Dimension(225, 75));
        buttonDonate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDonateActionPerformed(evt);
            }
        });

        buttonVisitMPS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/omertron.png"))); // NOI18N
        buttonVisitMPS.setText("<HTML>Visit Media Player Site</HTML>");
        buttonVisitMPS.setDefaultCapable(false);
        buttonVisitMPS.setMaximumSize(new java.awt.Dimension(225, 75));
        buttonVisitMPS.setMinimumSize(new java.awt.Dimension(225, 75));
        buttonVisitMPS.setPreferredSize(new java.awt.Dimension(225, 75));
        buttonVisitMPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVisitMPSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AboutLayout = new javax.swing.GroupLayout(About.getContentPane());
        About.getContentPane().setLayout(AboutLayout);
        AboutLayout.setHorizontalGroup(
            AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AboutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(buttonOk, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(labelYamjLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(AboutLayout.createSequentialGroup()
                        .addComponent(buttonDonate, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonVisitMPS, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelProgramUrl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(labelProgramName, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                .addContainerGap())
        );
        AboutLayout.setVerticalGroup(
            AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AboutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelYamjLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelProgramName)
                .addGap(5, 5, 5)
                .addComponent(labelProgramUrl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonDonate, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonVisitMPS, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonOk)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GUI Config v1.0");
        setName("windowMain"); // NOI18N
        setResizable(false);

        buttonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        buttonSave.setText("Save");

        buttonExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        buttonExit.setText("Exit");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });

        logoYAMJ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoYAMJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/YAMJ.png"))); // NOI18N

        labelLibraryCounter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/number-0.png"))); // NOI18N
        labelLibraryCounter.setText("Libraries");

        comboMediaPlayerModel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A1x0", "A2x0", "A300", "C200", "C300", "Other" }));

        labelMediaPlayerModel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMediaPlayerModel.setText("Media Player Model");

        labelPath.setLabelFor(textPath);
        labelPath.setText("Location of your video library directory to scan:");

        textPath.setText("C:\\");
            textPath.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    textPathActionPerformed(evt);
                }
            });
            textPath.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    textPathFocusLost(evt);
                }
            });
            textPath.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    textPathPropertyChange(evt);
                }
            });

            textPathCalc.setText("C:\\");
                textPathCalc.setEnabled(false);

                buttonBrowsePath.setText("Browse");
                buttonBrowsePath.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        buttonBrowsePathActionPerformed(evt);
                    }
                });

                buttonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
                buttonAdd.setText("Add");
                buttonAdd.setToolTipText("Add to library");
                buttonAdd.setMaximumSize(new java.awt.Dimension(100, 100));
                buttonAdd.setMinimumSize(new java.awt.Dimension(100, 100));
                buttonAdd.setOpaque(false);
                buttonAdd.setPreferredSize(new java.awt.Dimension(100, 100));
                buttonAdd.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        buttonAddActionPerformed(evt);
                    }
                });

                buttonClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear.png"))); // NOI18N
                buttonClear.setText("Clear");
                buttonClear.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        buttonClearActionPerformed(evt);
                    }
                });

                panelFileLocation.setBorder(javax.swing.BorderFactory.createTitledBorder("Video files are on:"));

                groupLocation.add(radioUsb);
                radioUsb.setText("a USB drive attached to the NMT");
                radioUsb.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        radioUsbActionPerformed(evt);
                    }
                });

                groupLocation.add(radioNetwork);
                radioNetwork.setText("a Network Path on the NMT");
                radioNetwork.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        radioNetworkActionPerformed(evt);
                    }
                });

                groupLocation.add(radioInternal);
                radioInternal.setText("on the NMT's internal hard disk");
                radioInternal.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        radioInternalActionPerformed(evt);
                    }
                });

                groupLocation.add(radioPC);
                radioPC.setText("Jukebox only for my PC");
                radioPC.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        radioPCActionPerformed(evt);
                    }
                });

                comboUsb.setEditable(true);
                comboUsb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "USB_DRIVE_A-1", "USB_DRIVE_B-1", "USB_DRIVE_C-1", "USB_DRIVE_D-1" }));
                comboUsb.setEnabled(false);

                comboNetwork.setEditable(true);
                comboNetwork.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Network Path" }));
                comboNetwork.setEnabled(false);

                comboInternal.setEditable(true);
                comboInternal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SATA_DISK", "HARD_DISK" }));
                comboInternal.setEnabled(false);

                javax.swing.GroupLayout panelFileLocationLayout = new javax.swing.GroupLayout(panelFileLocation);
                panelFileLocation.setLayout(panelFileLocationLayout);
                panelFileLocationLayout.setHorizontalGroup(
                    panelFileLocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFileLocationLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelFileLocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFileLocationLayout.createSequentialGroup()
                                .addGroup(panelFileLocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(radioUsb)
                                    .addComponent(radioNetwork)
                                    .addComponent(radioInternal))
                                .addGap(18, 18, 18)
                                .addGroup(panelFileLocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboNetwork, 0, 1, Short.MAX_VALUE)
                                    .addComponent(comboUsb, 0, 1, Short.MAX_VALUE)
                                    .addComponent(comboInternal, 0, 1, Short.MAX_VALUE)))
                            .addComponent(radioPC))
                        .addContainerGap())
                );
                panelFileLocationLayout.setVerticalGroup(
                    panelFileLocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFileLocationLayout.createSequentialGroup()
                        .addGroup(panelFileLocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioUsb)
                            .addComponent(comboUsb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFileLocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioNetwork)
                            .addComponent(comboNetwork, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFileLocationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboInternal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radioInternal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioPC))
                );

                labelPlayerPath.setLabelFor(textPlayerPath);
                labelPlayerPath.setText("Player Path: (WARNING: Edit this only if you know what you are doing)");

                textPlayerPath.setText("Player Path");

                javax.swing.GroupLayout tabAddLibraryLayout = new javax.swing.GroupLayout(tabAddLibrary);
                tabAddLibrary.setLayout(tabAddLibraryLayout);
                tabAddLibraryLayout.setHorizontalGroup(
                    tabAddLibraryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabAddLibraryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tabAddLibraryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelPath, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                            .addGroup(tabAddLibraryLayout.createSequentialGroup()
                                .addComponent(textPath, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonBrowsePath))
                            .addComponent(textPathCalc, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                            .addGroup(tabAddLibraryLayout.createSequentialGroup()
                                .addComponent(buttonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panelFileLocation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelPlayerPath, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                            .addComponent(textPlayerPath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE))
                        .addContainerGap())
                );
                tabAddLibraryLayout.setVerticalGroup(
                    tabAddLibraryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabAddLibraryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelPath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabAddLibraryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonBrowsePath))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPathCalc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelFileLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPlayerPath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPlayerPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabAddLibraryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                );

                tabPanelLibraries.addTab("Add Library", tabAddLibrary);

                tableViewLibraries.setModel(modelViewLibraries);
                tableViewLibraries.setColumnSelectionAllowed(true);
                tableViewLibraries.getTableHeader().setReorderingAllowed(false);
                jScrollPane2.setViewportView(tableViewLibraries);
                tableViewLibraries.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

                buttonClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear.png"))); // NOI18N
                buttonClearAll.setText("Clear All");
                buttonClearAll.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        buttonClearAllActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout tabViewLibrariesLayout = new javax.swing.GroupLayout(tabViewLibraries);
                tabViewLibraries.setLayout(tabViewLibrariesLayout);
                tabViewLibrariesLayout.setHorizontalGroup(
                    tabViewLibrariesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabViewLibrariesLayout.createSequentialGroup()
                        .addGroup(tabViewLibrariesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabViewLibrariesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE))
                            .addGroup(tabViewLibrariesLayout.createSequentialGroup()
                                .addGap(143, 143, 143)
                                .addComponent(buttonClearAll, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                );
                tabViewLibrariesLayout.setVerticalGroup(
                    tabViewLibrariesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabViewLibrariesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonClearAll, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                );

                tabPanelLibraries.addTab("View Libraries", tabViewLibraries);

                labelJukebox.setLabelFor(textJukebox);
                labelJukebox.setText("Location of where you want the jukebox to be stored:");

                textJukebox.setText("Please select a location");

                buttonBrowseJukebox.setText("Browse");
                buttonBrowseJukebox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        buttonBrowseJukeboxActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout tabLibraryConfigLayout = new javax.swing.GroupLayout(tabLibraryConfig);
                tabLibraryConfig.setLayout(tabLibraryConfigLayout);
                tabLibraryConfigLayout.setHorizontalGroup(
                    tabLibraryConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabLibraryConfigLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tabLibraryConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelJukebox)
                            .addGroup(tabLibraryConfigLayout.createSequentialGroup()
                                .addComponent(textJukebox, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonBrowseJukebox)))
                        .addContainerGap())
                );
                tabLibraryConfigLayout.setVerticalGroup(
                    tabLibraryConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabLibraryConfigLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelJukebox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabLibraryConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textJukebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonBrowseJukebox))
                        .addContainerGap(277, Short.MAX_VALUE))
                );

                tabPanelLibraries.addTab("Library Config", tabLibraryConfig);

                menuFile.setText("File");
                menuFile.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        menuFileActionPerformed(evt);
                    }
                });

                menuLoadLibraryFile.setText("Load Library File");
                menuLoadLibraryFile.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        menuLoadLibraryFileActionPerformed(evt);
                    }
                });
                menuFile.add(menuLoadLibraryFile);

                menuFileAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
                menuFileAdd.setText("Add");
                menuFileAdd.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        menuFileAddActionPerformed(evt);
                    }
                });
                menuFile.add(menuFileAdd);

                menuFileSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
                menuFileSave.setText("Save");
                menuFileSave.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        menuFileSaveActionPerformed(evt);
                    }
                });
                menuFile.add(menuFileSave);

                menuFileClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear.png"))); // NOI18N
                menuFileClear.setText("Clear");
                menuFileClear.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        menuFileClearActionPerformed(evt);
                    }
                });
                menuFile.add(menuFileClear);

                menuFileClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear.png"))); // NOI18N
                menuFileClearAll.setText("Clear All");
                menuFileClearAll.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        menuFileClearAllActionPerformed(evt);
                    }
                });
                menuFile.add(menuFileClearAll);

                menuFileExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
                menuFileExit.setText("Exit");
                menuFileExit.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        menuFileExitActionPerformed(evt);
                    }
                });
                menuFile.add(menuFileExit);

                menuBar.add(menuFile);

                menuHelp.setText("Help");

                menuHelpDonate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/paypal_sm.png"))); // NOI18N
                menuHelpDonate.setText("Donate");
                menuHelpDonate.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        menuHelpDonateActionPerformed(evt);
                    }
                });
                menuHelp.add(menuHelpDonate);

                menuHelpUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
                menuHelpUpdate.setText("Check for updates");
                menuHelpUpdate.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        menuHelpUpdateActionPerformed(evt);
                    }
                });
                menuHelp.add(menuHelpUpdate);

                menuHelpAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/about.png"))); // NOI18N
                menuHelpAbout.setText("About");
                menuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        menuHelpAboutActionPerformed(evt);
                    }
                });
                menuHelp.add(menuHelpAbout);

                menuBar.add(menuHelp);

                setJMenuBar(menuBar);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(logoYAMJ)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelMediaPlayerModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelLibraryCounter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboMediaPlayerModel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addComponent(tabPanelLibraries)
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(labelMediaPlayerModel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboMediaPlayerModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelLibraryCounter))
                            .addComponent(logoYAMJ, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabPanelLibraries, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        quitProgram();
    }//GEN-LAST:event_buttonExitActionPerformed

    private void buttonBrowsePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBrowsePathActionPerformed
        fileChooser.setDialogTitle(labelPath.getText());
        fileChooser.setCurrentDirectory(new File(textPath.getText()));
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            textPath.setText(file.getAbsolutePath());
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_buttonBrowsePathActionPerformed

    private void buttonBrowseJukeboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBrowseJukeboxActionPerformed
        fileChooser.setDialogTitle(labelJukebox.getText());
        fileChooser.setCurrentDirectory(new File(textJukebox.getText()));
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            textJukebox.setText(file.getAbsolutePath());
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_buttonBrowseJukeboxActionPerformed

    private void radioUsbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioUsbActionPerformed
        comboUsb.setEnabled(true);
        comboNetwork.setEnabled(false);
        comboInternal.setEnabled(false);
    }//GEN-LAST:event_radioUsbActionPerformed

    private void radioNetworkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNetworkActionPerformed
        comboUsb.setEnabled(false);
        comboNetwork.setEnabled(true);
        comboInternal.setEnabled(false);
    }//GEN-LAST:event_radioNetworkActionPerformed

    private void radioInternalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioInternalActionPerformed
        comboUsb.setEnabled(false);
        comboNetwork.setEnabled(false);
        comboInternal.setEnabled(true);
    }//GEN-LAST:event_radioInternalActionPerformed

    private void radioPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPCActionPerformed
        comboUsb.setEnabled(false);
        comboNetwork.setEnabled(false);
        comboInternal.setEnabled(false);
    }//GEN-LAST:event_radioPCActionPerformed

    private void menuFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileActionPerformed
    }//GEN-LAST:event_menuFileActionPerformed

    private void menuFileAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileAddActionPerformed
        addLibrary();
    }//GEN-LAST:event_menuFileAddActionPerformed

    private void menuFileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileSaveActionPerformed
        saveLibrary();
    }//GEN-LAST:event_menuFileSaveActionPerformed

    private void menuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileExitActionPerformed
        quitProgram();
    }//GEN-LAST:event_menuFileExitActionPerformed

    private void menuHelpUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpUpdateActionPerformed
        checkForUpdate();
    }//GEN-LAST:event_menuHelpUpdateActionPerformed

    private void menuHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpAboutActionPerformed
        aboutProgram();
    }//GEN-LAST:event_menuHelpAboutActionPerformed

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
        clearEntry();
    }//GEN-LAST:event_buttonClearActionPerformed

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        addLibrary();
    }//GEN-LAST:event_buttonAddActionPerformed

    private void menuFileClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileClearActionPerformed
        clearEntry();
    }//GEN-LAST:event_menuFileClearActionPerformed

    private void menuFileClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileClearAllActionPerformed
        clearAllEntries();
    }//GEN-LAST:event_menuFileClearAllActionPerformed

    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
        About.setVisible(false);
    }//GEN-LAST:event_buttonOkActionPerformed

    private void labelProgramUrlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelProgramUrlMouseClicked
        openLink(labelProgramUrl.getText());
    }//GEN-LAST:event_labelProgramUrlMouseClicked

    private void buttonDonateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDonateActionPerformed
        openLink(GuiConfig.getUrlPaypalDonate());
    }//GEN-LAST:event_buttonDonateActionPerformed

    private void buttonVisitMPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVisitMPSActionPerformed
        openLink(GuiConfig.getUrlMediaPlayerSite());
    }//GEN-LAST:event_buttonVisitMPSActionPerformed

    private void menuHelpDonateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpDonateActionPerformed
        openLink(GuiConfig.getUrlPaypalDonate());
    }//GEN-LAST:event_menuHelpDonateActionPerformed

    private void menuLoadLibraryFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLoadLibraryFileActionPerformed
        fileChooser.setDialogTitle("Select the library to load");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileFilterExtension(".xml", "Library Files"));
        fileChooser.setCurrentDirectory(new File("D:\\YAMJ\\PC"));
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("Attepmting to load " + file.getAbsolutePath());
            GuiConfig.getLibraries().readLibraryFile(file.getAbsolutePath());
            updateLibraryCounter();
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_menuLoadLibraryFileActionPerformed

    private void buttonClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearAllActionPerformed
        clearAllEntries();
    }//GEN-LAST:event_buttonClearAllActionPerformed

    private void textPathPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_textPathPropertyChange
        LOGGER.info("1Path changed to '" + textPath.getText() + "'");
    }//GEN-LAST:event_textPathPropertyChange

    private void textPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPathActionPerformed
    }//GEN-LAST:event_textPathActionPerformed

    private void textPathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textPathFocusLost
    }//GEN-LAST:event_textPathFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog About;
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonBrowseJukebox;
    private javax.swing.JButton buttonBrowsePath;
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton buttonClearAll;
    private javax.swing.JButton buttonDonate;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonOk;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonVisitMPS;
    private javax.swing.JComboBox comboInternal;
    private javax.swing.JComboBox comboMediaPlayerModel;
    private javax.swing.JComboBox comboNetwork;
    private javax.swing.JComboBox comboUsb;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.ButtonGroup groupLocation;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelJukebox;
    private javax.swing.JLabel labelLibraryCounter;
    private javax.swing.JLabel labelMediaPlayerModel;
    private javax.swing.JLabel labelPath;
    private javax.swing.JLabel labelPlayerPath;
    private javax.swing.JLabel labelProgramName;
    private javax.swing.JLabel labelProgramUrl;
    private javax.swing.JLabel labelYamjLogo;
    private javax.swing.JLabel logoYAMJ;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFileAdd;
    private javax.swing.JMenuItem menuFileClear;
    private javax.swing.JMenuItem menuFileClearAll;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JMenuItem menuFileSave;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuHelpAbout;
    private javax.swing.JMenuItem menuHelpDonate;
    private javax.swing.JMenuItem menuHelpUpdate;
    private javax.swing.JMenuItem menuLoadLibraryFile;
    private javax.swing.JPanel panelFileLocation;
    private javax.swing.JRadioButton radioInternal;
    private javax.swing.JRadioButton radioNetwork;
    private javax.swing.JRadioButton radioPC;
    private javax.swing.JRadioButton radioUsb;
    private javax.swing.JPanel tabAddLibrary;
    private javax.swing.JPanel tabLibraryConfig;
    private javax.swing.JTabbedPane tabPanelLibraries;
    private javax.swing.JPanel tabViewLibraries;
    private javax.swing.JTable tableViewLibraries;
    private javax.swing.JTextField textJukebox;
    private javax.swing.JTextField textPath;
    private javax.swing.JTextField textPathCalc;
    private javax.swing.JTextField textPlayerPath;
    // End of variables declaration//GEN-END:variables
}
