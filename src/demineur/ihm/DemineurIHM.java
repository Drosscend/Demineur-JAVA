package demineur.ihm;

import demineur.metier.Demineur;
import demineur.metier.EtatCase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class DemineurIHM extends JFrame implements ActionListener {

    private static final String title = "Démineur - V1.0";

    private static final String nomMenuPartie = "Partie";
    private static final String nomSousMenuNouvellePartie = "Nouvelle partie";
    private static final String nomSousMenuExit = "Quitter";
    private static final String nomMenuGrille = "Grille";
    private static final String grille_10x5 = "Grille 10 x 5";
    private static final String grille_10x10 = "Grille 10 x 10";
    private static final String grille_15x15 = "Grille 15 x 15";
    private static final String grille_20x15 = "Grille 20 x 15";
    private static final String nomMenuLevel = "Niveau";
    private static final String level_1 = "Niveau 1";
    private static final String level_2 = "Niveau 2";
    private static final String level_3 = "Niveau 3";
    private static final String level_4 = "Niveau 4";
    private static final String level_5 = "Niveau 5";
    private static final String nomMenuAide = "?";
    private static final String nomSousMenuCredit = "Crédit";
    private static final String nomSousMenuAide = "Aide";
    private static final String nomSousMenuTricher = "Tricher";
    private static JLabel nBBombe = new JLabel("");
    private static JLabel nBBombeDouteuse = new JLabel("");
    private static JLabel nBBonbeProposés = new JLabel("");
    private static JLabel infoChrono = new JLabel("");
    private static JLabel points = new JLabel("");
    private final JPanel contentPane = new JPanel();
    private final JPanel menuPanel = new JPanel();
    private final EmptyBorder emptyBorder5 = new EmptyBorder(5, 5, 5, 5);
    private final EmptyBorder emptyBorder10 = new EmptyBorder(10, 10, 10, 10);
    private JPanel gamePanel = new JPanel();
    private int largeur = 10;
    private int hauteur = 5;
    private int level = 2;
    private boolean enCours;
    private Demineur demineur = new Demineur(largeur, hauteur, level);
    private CasesIHM[] tabBoutons;
    // Partie crhonomètre
    private long debut; // en ms
    private long duree;
    private Timer timer = null;
    private int nombreDeCase;
    private long pts = 0;

    public DemineurIHM() {
        //Création de la fenêtre
        super(title);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Création du menu
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menuPartie = new JMenu(nomMenuPartie);
        menuBar.add(menuPartie);
        JMenuItem boutonNouvellePartie = new JMenuItem(nomSousMenuNouvellePartie);
        menuPartie.add(boutonNouvellePartie);
        menuPartie.addSeparator();
        JMenuItem boutonExit = new JMenuItem(nomSousMenuExit);
        menuPartie.add(boutonExit);

        JMenu menuLevel = new JMenu(nomMenuLevel);
        menuBar.add(menuLevel);
        JMenuItem buttonLevel_1 = new JMenuItem(level_1);
        menuLevel.add(buttonLevel_1);
        JMenuItem buttonLevel_2 = new JMenuItem(level_2);
        menuLevel.add(buttonLevel_2);
        JMenuItem buttonLevel_3 = new JMenuItem(level_3);
        menuLevel.add(buttonLevel_3);
        JMenuItem buttonLevel_4 = new JMenuItem(level_4);
        menuLevel.add(buttonLevel_4);
        JMenuItem buttonLevel_5 = new JMenuItem(level_5);
        menuLevel.add(buttonLevel_5);

        JMenu menuGrille = new JMenu(nomMenuGrille);
        menuBar.add(menuGrille);
        JMenuItem boutonNouvelleGrille10x5 = new JMenuItem(grille_10x5);
        menuGrille.add(boutonNouvelleGrille10x5);
        JMenuItem boutonNouvelleGrille10x10 = new JMenuItem(grille_10x10);
        menuGrille.add(boutonNouvelleGrille10x10);
        JMenuItem boutonNouvelleGrille15x15 = new JMenuItem(grille_15x15);
        menuGrille.add(boutonNouvelleGrille15x15);
        JMenuItem boutonNouvelleGrille20x15 = new JMenuItem(grille_20x15);
        menuGrille.add(boutonNouvelleGrille20x15);

        JMenu menuAide = new JMenu(nomMenuAide);
        menuBar.add(menuAide);
        JMenuItem boutonCredit = new JMenuItem(nomSousMenuCredit);
        menuAide.add(boutonCredit);
        JMenuItem boutonAide = new JMenuItem(nomSousMenuAide);
        menuAide.add(boutonAide);
        JMenuItem boutonTricher = new JMenuItem(nomSousMenuTricher);
        menuAide.add(boutonTricher);

        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(emptyBorder10);
        this.setContentPane(contentPane);

        contentPane.add(menuPanel, BorderLayout.EAST);

        menuPanel.setLayout(new FlowLayout());
        menuPanel.setBorder(emptyBorder5);
        menuPanel.setPreferredSize(new Dimension(200, 200));
        JButton bAide = new JButton(nomSousMenuAide);
        JButton bNouvellePartie = new JButton(nomSousMenuNouvellePartie);
        JButton bTricher = new JButton(nomSousMenuTricher);

        bAide.setPreferredSize(bNouvellePartie.getPreferredSize());

        menuPanel.add(bAide);
        menuPanel.add(bNouvellePartie);
        menuPanel.add(nBBombe);
        menuPanel.add(nBBonbeProposés);
        menuPanel.add(nBBombeDouteuse);
        menuPanel.add(infoChrono);
        menuPanel.add(points);

        // Afectation des actions
        // Actions pour la partie
        boutonNouvellePartie.setActionCommand(nomSousMenuNouvellePartie);
        boutonNouvellePartie.addActionListener(this);

        boutonExit.setActionCommand(nomSousMenuExit);
        boutonExit.addActionListener(this);

        // Actions pour les informations
        boutonCredit.setActionCommand(nomSousMenuCredit);
        boutonCredit.addActionListener(this);
        boutonAide.setActionCommand(nomSousMenuAide);
        boutonAide.addActionListener(this);
        boutonTricher.setActionCommand(nomSousMenuTricher);
        boutonTricher.addActionListener(this);

        // Actions pour les grilles
        boutonNouvelleGrille10x10.setActionCommand(grille_10x10);
        boutonNouvelleGrille10x10.addActionListener(this);
        boutonNouvelleGrille15x15.setActionCommand(grille_15x15);
        boutonNouvelleGrille15x15.addActionListener(this);
        boutonNouvelleGrille20x15.setActionCommand(grille_20x15);
        boutonNouvelleGrille20x15.addActionListener(this);
        boutonNouvelleGrille10x5.setActionCommand(grille_10x5);
        boutonNouvelleGrille10x5.addActionListener(this);

        // Actions pour les niveaux
        buttonLevel_1.setActionCommand(level_1);
        buttonLevel_1.addActionListener(this);
        buttonLevel_2.setActionCommand(level_2);
        buttonLevel_2.addActionListener(this);
        buttonLevel_3.setActionCommand(level_3);
        buttonLevel_3.addActionListener(this);
        buttonLevel_4.setActionCommand(level_4);
        buttonLevel_4.addActionListener(this);
        buttonLevel_5.setActionCommand(level_5);
        buttonLevel_5.addActionListener(this);

        // Actions pour le panel
        bAide.setActionCommand(nomSousMenuAide);
        bAide.addActionListener(this);
        bNouvellePartie.setActionCommand(nomSousMenuNouvellePartie);
        bNouvellePartie.addActionListener(this);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                quitter();
            }
        });

        this.pack();
        this.setResizable(false);

        init(this.largeur, this.hauteur, this.level);
    }


    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case nomSousMenuExit -> quitter();
            case nomSousMenuCredit -> credits();
            case nomSousMenuAide -> aide();
            case nomSousMenuTricher -> tricher();
            case nomSousMenuNouvellePartie -> newGame(this.largeur, this.hauteur, this.level);
            case grille_10x5 -> {
                newGame(10, 5, this.level);
                this.pts += 5;
            }
            case grille_10x10 -> {
                newGame(10, 10, this.level);
                this.pts += 10;
            }
            case grille_15x15 -> {
                newGame(15, 15, this.level);
                this.pts += 15;
            }
            case grille_20x15 -> {
                newGame(20, 15, this.level);
                this.pts += 20;
            }
            case level_1 -> newGame(this.largeur, this.hauteur, 1);
            case level_2 -> newGame(this.largeur, this.hauteur, 2);
            case level_3 -> newGame(this.largeur, this.hauteur, 3);
            case level_4 -> newGame(this.largeur, this.hauteur, 4);
            case level_5 -> newGame(this.largeur, this.hauteur, 5);
            default -> System.out.println("Action inconnue ???");
        }
    }

    /**
     * Permet de lancer une nouvelle partie
     */
    public void newGame(int Largeur, int Hauteur, int Level) {
        if (!enCours) {
            init(Largeur, Hauteur, Level);
        } else {
            int reponse = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment relancer une partie ?", "Quitter", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (reponse == JOptionPane.OK_OPTION) {
                init(Largeur, Hauteur, Level);
            }
        }
    }

    /**
     * Fonction affichant les valeurs pour tricher
     */
    public void tricher() {
        for (int i = 0; i < nombreDeCase; i++) {
            if (this.demineur.getValeur(i) == -1) {
                this.tabBoutons[i].setBackground(Color.RED);
            }
            this.tabBoutons[i].setText("" + this.demineur.getValeur(i));
        }
    }

    /**
     * Permet de demander si l'on veut vraiment quitter le jeu
     */
    public void quitter() {
        int reponse = JOptionPane.showConfirmDialog(this, "Voulez-vous quitter ?", "Quitter", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (reponse == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Permet d'afficher les crédits
     */
    public void credits() {
        JOptionPane.showMessageDialog(this, "<html><h1>Kévin Véronési</h1></br></br>" + title + "</html>", "Crédit", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Permet d'afficher les aides
     */
    public void aide() {
        JOptionPane.showMessageDialog(this, "<html>" +
                "<h2>Voici le but du Jeu :</h2>" +
                "<p>Vous devez retourner toutes les cases qui ne sont pas des mines</p>" +
                "<h5>Comment jouer ?</h5>" +
                "<ul>" +
                "<li>clic gauche pour retourner une case</li>" +
                "<li>clic droit pour marquer une case</li>" +
                "<li>clic droit une seconde fois pour marquer une comme doute</li>" +
                "<li>clic droit une troisième fois pour supprimer une marque</li>" +
                "</ul>" +
                "<p>Vous pouvez changer votre niveau de dificulté et la grille dans le menu en haut" +
                "</html>", "Aide", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Fonction permettant l'initialisation de la grille dans son ensemble
     *
     * @param x     largeur de la grille
     * @param y     hauteur de la grille
     * @param level niveau de la grille
     */
    public void init(int x, int y, int level) {
        enCours = true;
        nBBombe.setText("Mines dans la partie : " + this.demineur.getNbMinesATrouver());

        this.debut = System.currentTimeMillis();

        this.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualiseDuree();
            }
        });
        this.timer.start();

        // On stocke les valeurs pour si on change le level / la grille que le level / la grille reste le même
        if (x != largeur || y != hauteur || level != this.level) {
            this.largeur = x;
            this.hauteur = y;
            this.level = level;
            this.demineur = new Demineur(x, y, level);
        } else {
            this.demineur.initialiser();
        }


        // Re faire la grille
        contentPane.remove(gamePanel);
        nombreDeCase = this.largeur * this.hauteur;
        this.gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(this.hauteur, this.largeur));

        if (this.largeur == this.hauteur) {
            gamePanel.setPreferredSize(new Dimension(this.largeur * 43, this.hauteur * 43));
        }

        // Créations de la grille

        this.tabBoutons = new CasesIHM[nombreDeCase];
        for (int i = 0; i < nombreDeCase; i++) {
            this.tabBoutons[i] = new CasesIHM(i);
            this.tabBoutons[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    changerEtat(e);
                }
            });
            gamePanel.add(tabBoutons[i]);
        }


        //contentPane.removeAll();
        //contentPane.add(Panel, BorderLayout.EAST);
        contentPane.add(gamePanel, BorderLayout.WEST);

        this.pack();
        this.setVisible(true);
    }

    /**
     * Fonction pour rafraichir les boutons
     */
    public void refreshButtons() {
        if (enCours) {
            for (int i = 0; i < this.tabBoutons.length; i++) {
                EtatCase Etat = this.demineur.getEtat(i);
                int Valeur = this.demineur.getValeur(i);
                boolean estPerdue = !this.demineur.isPartiePerdue();
                tabBoutons[i].RefreshLaCase(Etat, Valeur, estPerdue);

            }
            if (this.demineur.isPartieTerminee()) {
                enCours = false;
                if (!this.demineur.isPartiePerdue()) {
                    JOptionPane.showMessageDialog(this, "Félicitation vous avez gagné la partie et vous avez fait un score de  " + calculpts() + " points.", "Félicitation", JOptionPane.INFORMATION_MESSAGE);
                    calculpts();
                } else {
                    JOptionPane.showMessageDialog(this, "Dommage vous avez perdu cette partie la prochaine sera meilleure", "Dommage", JOptionPane.INFORMATION_MESSAGE);
                    calculpts();
                }
            }
            nBBombeDouteuse.setText("Mines Douteuses : " + this.demineur.getNbCasesDouteuses());
            nBBonbeProposés.setText("Mines proposés : " + this.demineur.getNbMinesProposees());
            nBBombe.setText("Mines restantes : " + this.demineur.getNbMinesATrouver());
        }
    }

    /**
     * Fonction pour calculer les points de la partie
     *
     * @return les points de la partie.
     */
    private long calculpts() {
        switch (this.level) {
            case 1 -> pts += 10;
            case 2 -> pts += 20;
            case 3 -> pts += 30;
            case 4 -> pts += 40;
            case 5 -> pts += 50;
        }
        pts -= this.duree / 5;
        if (!this.demineur.isPartiePerdue()) {
            this.points.setText("Votre score : " + pts + " pts");
        } else {
            pts = 0;
            this.points.setText("Votre score : " + pts + " pts");
        }
        return pts;
    }

    /**
     * Fonction permettant le changemement de l'état lors du clique sur un bouton
     * et actualise avec la fonction refreshButtons
     *
     * @param e
     */
    private void changerEtat(MouseEvent e) {
        if (e.getSource() instanceof CasesIHM) {
            CasesIHM BoutonUtilisé = (CasesIHM) e.getSource();
            switch (e.getButton()) {
                case MouseEvent.BUTTON1 -> this.demineur.retourner(BoutonUtilisé.getNumeroDeLaCase());
                case MouseEvent.BUTTON3 -> this.demineur.marquer(BoutonUtilisé.getNumeroDeLaCase());
            }
            this.refreshButtons();
        }
    }

    /**
     * Fonction permettant de actualiser la durée du timer
     */
    public void actualiseDuree() {
        if (enCours) {
            long fin = System.currentTimeMillis();
            double Duree = (fin - debut) / 1000;
            this.duree = (long) Duree;
            this.infoChrono.setText("Temps : " + Duree + "s");
        }
    }

}
