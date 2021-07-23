package demineur.metier;
/**
 * Modélisation d'un masque de grille de démineur.
 * 
 * Le masque est une grille de taille largeur x hauteur
 * Chaque case du masque peut avoir un des 4 états de EtatCase :
 *   FERME  : case Fermée : non marquée et non découverte (aucune info particulière et on ne voit pas ce qui est dessous)
 *   MINE   : case protégée elle a un marqueur de mine et ne peut pas être retournée
 *   DOUTE  : case protégée elle a un marque de "doute" et ne peut pas être retournée
 *   OUVERT : case Ouverte : ayant déjà été retournée 
 * 
 * Quand on "marque" une case avec la méthode masquer(x,y) :
 *   elle passe à MINE  si elle était FERME
 *   elle passe à DOUTE si elle était MINE
 *   elle passe à FERME si elle était DOUTE
 *   rien ne se passe si elle était déjà retournée (OUVERT)
 * Quand on "retourne" une case  avec la méthode retourner(x,y)
 *   elle passe à OUVERT si elle était à FERME
 *   rien ne se passe si elle était à MINE, DOUTE ou OUVERT
 * 
 * 
 * NB: cette classe n'est pas PUBLIQUE et ne doit pas être utilisée en dehors du package demineur.metier
 * 
 * 
 * @author Fabrice PELLEAU
 *
 */
class Masque {
	private int          largeur;
	private int          hauteur;
	private EtatCase[][] masque;
	
	private int nbMines;
	private int nbDoutes;
	
	public Masque(int largeur, int hauteur) {
		if (largeur<3) {largeur = 3; }
		if (hauteur<3) {hauteur = 3; }
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.masque = new EtatCase[hauteur][largeur];
		this.initialiser();
	}
	
	public void initialiser() {
		this.nbMines  = 0;
		this.nbDoutes = 0;
		for (int y=0; y<this.hauteur; y++) {
			for (int x=0; x<this.largeur; x++) {
				this.masque[y][x] = EtatCase.FERME;
			}
		}
	}
	public void decouvrir() {
		for (int y=0; y<this.hauteur; y++) {
			for (int x=0; x<this.largeur; x++) {
				this.masque[y][x] = EtatCase.OUVERT;
			}
		}
	}
	public EtatCase get( int x, int y ) {
		if (x>=0 && y>=0 && x<this.largeur && y<this.hauteur) {
			return this.masque[y][x];
		} else {
			return EtatCase.MINE; // cas hors borne ==> mine fictive ;-)
		}
	}
	public EtatCase get( int position ) {
		if (position>=0 && position<this.largeur*this.hauteur) {
			int x = position%this.largeur;
			int y = position/this.largeur;
			return this.masque[y][x];
		} else {
			return EtatCase.MINE; // cas hors borne ==> mine fictive ;-)
		}
	}
	public int getLargeur() {
		return this.largeur;
	}
	public int getHauteur() {
		return this.hauteur;
	}
	public int getNbMines() {
		return this.nbMines;
	}
	public int getNbDoutes() {
		return this.nbDoutes;
	}
	/**
	 * Marquer une case :
	 *   elle passe à MINE si elle était FERME
	 *   elle passe à '?' si elle était MINE
	 *   elle passe à FERME si elle était DOUTE
	 *   rien ne se passe si elle était déjà retournée (OUVERT)
	 * 
	 * @param x positionX
	 * @param y positionY
	 * @return l'état de la case (OUVERT,FERME,MINE ou DOUTE)
	 */
	public EtatCase marquer(int x, int y) {
		if (x>=0 && y>=0 && x<this.largeur && y<this.hauteur) {
			switch (this.masque[y][x]) {
			case FERME:
				this.nbMines++;
				this.masque[y][x] = EtatCase.MINE;
				break;
			case MINE:
				this.nbMines--;
				this.nbDoutes++;
				this.masque[y][x] = EtatCase.DOUTE;
				break;
			case DOUTE:
				this.nbDoutes--;
				this.masque[y][x] = EtatCase.FERME;
				break;
			default:
				// pas de changement
				break;
			}
			return this.masque[y][x];
		} else {
			return EtatCase.MINE; // cas hors borne ==> mine fictive ;-)
		}
	}
		
	/**
	 * Retourner une case :
	 *   elle passe à OUVERT si elle était à FERME
	 *   rien ne se passe si elle était à MINE, DOUTE ou OUVERT
	 * 
	 * @param x positionX
	 * @param y positionY
	 * @return l'état de la case (OUVERT,FERME,MINE ou DOUTE)
	 */
	public EtatCase retourner(int x, int y) {
		if (x>=0 && y>=0 && x<this.largeur && y<this.hauteur) {
			if (this.masque[y][x] == EtatCase.FERME ) {
				this.masque[y][x] = EtatCase.OUVERT;
			}
			return this.masque[y][x];
		} else {
			return EtatCase.MINE; // cas hors borne ==> mine fictive ;-)
		}
	}
		

	/**
	 * Transformer le masque en chaîne de caractère (pour debuggage)
	 */
	public String toString() {
		String result = "";
		for (int y=0; y<this.hauteur; y++) {
			for (int x=0; x<this.largeur; x++) {
				result += " "+this.masque[y][x];
			}
			result += "\n";
		}
		return result;
	}
	
}
