package demineur.ihm;

import demineur.metier.EtatCase;

import javax.swing.*;
import java.awt.*;

public class CasesIHM extends JButton {

    public final ImageIcon flag = new ImageIcon(getClass().getResource("/DemineurImages/images/flag.png"));
    public final ImageIcon minered = new ImageIcon(getClass().getResource("/DemineurImages/images/minered.png"));
    public final ImageIcon mine = new ImageIcon(getClass().getResource("/DemineurImages/images/mine.png"));
    public final ImageIcon maybe = new ImageIcon(getClass().getResource("/DemineurImages/images/maybe.png"));
    private final int numeroDeLaCase;
    private final Color LOOSE = new Color(255, 0, 0);
    private final Color BG = new Color(219, 219, 219);
    private final Color zero = new Color(179, 177, 177, 255);
    private final Color un = new Color(0, 0, 255);
    private final Color deux = new Color(0, 255, 0);
    private final Color trois = new Color(255, 0, 0);
    private final Color quatre = new Color(0, 0, 132);
    private final Color cinq = new Color(100, 76, 59);

    /**
     * Crée un bouton de 40x40 vide et stocke le numéro
     */
    public CasesIHM(int numero) {
        super("");
        this.numeroDeLaCase = numero;
        this.setPreferredSize(new Dimension(50, 50));
        this.setMargin(new Insets(1, 1, 1, 1));
        this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
    }

    public int getNumeroDeLaCase() {
        return this.numeroDeLaCase;
    }

    /**
     * Permet en fonction de l'etat de la case, la valeur de la case et de si la partie est gagné de changer le texte du bouton
     *
     * @param EtatCase       L'etat de la case OUVERT, MINE et DOUTE, FERME
     * @param PFvaleur       -1, 0, 1, etc
     * @param PFpartieGagnee true or false
     */
    public void RefreshLaCase(EtatCase EtatCase, int PFvaleur, boolean PFpartieGagnee) {
        switch (EtatCase) {
            case OUVERT -> {
                this.setBackground(BG);
                this.setText("" + PFvaleur);
                this.setIcon(null);
                switch (PFvaleur) {
                    case -1 -> {
                        if (!PFpartieGagnee) {
                            this.setText("");
                            this.setBackground(LOOSE);
                            this.setIcon(minered);
                        }
                        this.setText("");
                        this.setIcon(mine);
                    }
                    case 0 -> this.setForeground(zero);
                    case 1 -> this.setForeground(un);
                    case 2 -> this.setForeground(deux);
                    case 3 -> this.setForeground(trois);
                    case 4 -> this.setForeground(quatre);
                    case 5 -> this.setForeground(cinq);
                }
            }
            case MINE -> {
                this.setText("");
                this.setIcon(flag);
            }
            case DOUTE -> {
                this.setText("");
                this.setIcon(maybe);
            }
            case FERME -> {
                this.setIcon(null);
                this.setText("");
            }
        }
    }
}
