package demineur.metier;

/**
 * Modélisation d'une partie de démineur.
 * 
 * <br/>
 * On peut choisir la largeur, la hauteur de la grille de jeu (à la création)
 * On peut redémarrer une partie à tout moment (terminée ou non)
 * On ne peut pas changer les dimensions ou le niveau de difficulté sur un objet Demineur déjà créé (il faut en créer un autre si nécessaire)
 * 
 * <br/>
 * Un jeu de démineur utilise une grille et un masque de même taille.<br/>
 * le masque est l'état de la partie en cours et des coups joués et lui permet de connaître l'état d'une case : elle est du type EtatCase (OUVERT, FERME, MINE ou DOUTE)<br/>
 * la grille est l'état de la zone de jeu (position des mines, nombre de mines adjacentes, ...) et lui permet de connaître la valeur d'une case : c'est un nombre indiquant le nombre de mines autour de la case (-1 si la case est une mine)
 *
 * <br/>
 * Pour marquer ou retourner une case, le jeu propose (au choix) <br/>
 *  - soit de donner la position avec 2 coordonnées (x et y)
 *  - soit de donner la position avec 1 seule valeur (comme si toutes les cases étaient dans un grand tableau à une dimension avec toutes les lignes à la suite)
 *  
 * @author Fabrice PELLEAU
 *
 */
public class Demineur {

	private Grille grille;
	private Masque masque;
	private boolean partiePerdue;
	private boolean partieTerminee;

	/**
	 * Création d'une partie de Démineur.
	 * 
	 * Le niveau de difficulté fixe le pourcentage de mines :  1=5%, 2=10%, 3=15%, 4=20%, 5 ou plus = 25%
	 * 
	 * @param largeur de la grille
	 * @param hauteur de la grille
	 * @param level niveau de difficulté (de 1 à 5)
	 */
	public Demineur(int largeur, int hauteur, int level) {
		this.grille = new Grille(largeur, hauteur, level);
		this.masque = new Masque(largeur, hauteur);
		this.partiePerdue   = false;
		this.partieTerminee = false;
	}
	/**
	 * Redémarrer une partie (sans changer les paramètres).
	 */
	public void initialiser() {
		this.grille.initialiser();
		this.masque.initialiser();
		this.partiePerdue   = false;
		this.partieTerminee = false;
	}
	/**
	 * Largeur de la grille de jeu.
	 * 
	 * @return Nombre de cases en largeur
	 */
	public int getLargeur() {
		return this.grille.getLargeur();
	}
	/**
	 * Hauteur de la grille de jeu.
	 * 
	 * @return Nombre de cases en hauteur
	 */
	public int getHauteur() {
		return this.grille.getHauteur();
	}
	/**
	 * Nombre de mines à trouver sur la grille.
	 * 
	 * @return le nombre de mines sur la grille
	 */
	public int getNbMinesATrouver() {
		return this.grille.getNbMines();
	}
	/**
	 * Nombre de mines proposées (marquées) sur la grille.
	 * 
	 * @return le nombre de cases marquées comme mines
	 */
	public int getNbMinesProposees() {
		return this.masque.getNbMines();
	}
	/**
	 * Nombre de cases douteuses (marquées en DOUTE dans le masque)
	 * 
	 * @return le nombre de cases marquées comme douteuses
	 */
	public int getNbCasesDouteuses() {
		return this.masque.getNbDoutes();
	}
	
	
	/**
	 * Savoir si une partie est perdue ou non.
	 * 
	 * @return true si la partie est perdue
	 */
	public boolean isPartiePerdue() {
		return this.partiePerdue;
	}
	/**
	 * Savoir si une partie est terminée ou non.
	 * 
	 * @see  demineur.metier.Demineur#isPartiePerdue() pour savoir si c'est une victoire ou non
	 * 
	 * @return true si la partie est terminée
	 */
	public boolean isPartieTerminee() {
		return this.partieTerminee;
	}
	/**
	 * Marquer une case.
	 * 
	 * Positionner un DOUTE ou une MINE ou annuler DOUTE/MINE sur une case
     * opération sans effet si la case est déjà OUVERTE
	 * 
	 * @param x position en largeur
	 * @param y position en hauteur
	 * @return l'état du masque de la case marquée (OUVERT, FERME, MINE ou DOUTE)
	 */
	public EtatCase marquer(int x, int y) {
		return this.masque.marquer(x, y);
	}
	/**
	 * Marquer une case en fonction de sa position [0..largeur*hauteur[
	 * @see demineur.metier.Demineur#marquer(int, int)
	 */
	public EtatCase marquer(int position) {
		int x = position%this.getLargeur();
		int y = position/this.getLargeur();
		return this.marquer(x,y);
	}

	/**
	 * Retourner une case du jeu.
	 * 
	 * La case est FERMEE et passe à OUVERTE
     * opération sans effet si la case est MINE ou DOUTE
	 * 
	 * <br/>
	 * Après chaque case retournée, il faut actualiser la visualisation de la grille et tester si la partie est terminée.
	 * Cette méthode retourne 'true' tant qu'on a pas découvert une mine.
	 * 
	 * @param x position en largeur
	 * @param y position en hauteur
	 * @return false si on vient de retourner une mine (partie perdue)
	 */
	public boolean retourner(int x, int y) {
		if ( masque.retourner(x,y)==EtatCase.OUVERT ) {
			if ( grille.get(x, y)==0 ) {
				this.propagerCaseVide(x, y,true);
			}
			if ( grille.get(x, y)==-1 ) {
				this.partiePerdue = true;
				this.partieTerminee = true;
				this.masque.decouvrir();
				return false; // faux car c'est une mine
			} else {
				checkFinPartie();
				return true; // vrai car ce n'est pas une mine
			}
		}
		return true;
	}
	/**
	 * Retourner une case en fonction de sa position [0..largeur*hauteur[
	 * @see demineur.metier.Demineur#retourner(int, int)
	 */
	public boolean retourner( int position ) {
		int x = position%this.getLargeur();
		int y = position/this.getLargeur();
		return this.retourner(x,y);
	}

	/**
	 * Connaître l'état d'une case (valeur du masque)
	 * Si une case est en état OUVERT, on peut connaître le contenu de la case avec la méthode getValeur(x,y);
	 * @param x position en largeur
	 * @param y position en hauteur
	 * @return l'état du masque de la case marquée (OUVERT, FERME, MINE ou DOUTE)
	 */
	public EtatCase getEtat(int x, int y) {
		return this.masque.get(x, y);
	}
	public EtatCase getEtat(int position) {
		return this.masque.get(position);
	}
	/**
	 * Connaître la valeur d'une case (valeur de la grille).
	 * NB: a priori on ne demande la valeur des cases que si elle on un état OUVERT)
	 *     mais la méthode retourne la valeur indépendamment du masque pour permettre la création d'un cheat-mode (par exemple)
	 * @param x position en largeur
	 * @param y position en hauteur
	 * @return la valeur de la case (nombre de mines adjacentes ou -1 si c'est une mine)
	 */
	public int getValeur(int x, int y) {
		return this.grille.get(x, y);
	}
	public int getValeur(int position) {
		return this.grille.get(position);
	}
	
	/**
	 * Teste si la partie est terminée
	 * @return true si partie terminée... false sinon
	 */
	private boolean checkFinPartie() {
		if (!this.partieTerminee) {
			boolean resteCaseATrouver = false;
			for (int y=0; y<this.getHauteur(); y++) {
				for (int x=0; x<this.getLargeur(); x++) {
					if (this.masque.get(x, y)==EtatCase.FERME && this.grille.get(x, y)!=-1) {
						// une case non retournée n'est pas une mine => il reste des choses à trouver
						resteCaseATrouver = true;
						x=this.getLargeur(); y=this.getHauteur(); // fin des recherches
					}
				}
			}
			if (!resteCaseATrouver) {
				this.partieTerminee = true;
				this.masque.decouvrir();
			}
		}
		return this.partieTerminee;
	}
	/**
	 * Méthode recursive de propagation des cases vides.
	 * <br/>
	 * Si une case est vide on la retourne et on teste de la même manière les 8 cases adjacentes.
	 * @param x position en largeur
	 * @param y position en hauteur
	 */
	private void propagerCaseVide(int x, int y, boolean isFirst) {
		if (x>=0 && y>=0 && x<this.getLargeur() && y<this.getHauteur()) {
			if ( isFirst || ( this.masque.get(x,y)==EtatCase.FERME && this.grille.get(x,y)!=-1 ) ) {
				this.masque.retourner(x, y);
				if (this.grille.get(x,y)==0) {
					propagerCaseVide(x  , y+1, false);
					propagerCaseVide(x+1, y+1, false);
					propagerCaseVide(x+1, y  , false);
					propagerCaseVide(x+1, y-1, false);
					propagerCaseVide(x  , y-1, false);
					propagerCaseVide(x-1, y-1, false);
					propagerCaseVide(x-1, y  , false);
					propagerCaseVide(x-1, y+1, false);
				}
			}
		}
	}
	
}
