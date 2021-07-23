package demineur.metier;
/**
 * Modélisation d'une grille de démineur.
 * 
 * La grille est de taille largeur x hauteur et contient nbMines mines
 * Dans la grille, -1 représente une mine et sinon, la valeur correspond au nombre de mines sur les cases voisines
 * 
 * 
 * NB: cette classe n'est pas PUBLIQUE et ne doit pas être utilisée en dehors du package demineur.metier
 * 
 * @author Fabrice PELLEAU
 *
 */
class Grille {
	private int     largeur;
	private int     hauteur;
	private int		nbMines;
	private int[][] grille;

	public Grille(int largeur, int hauteur, int level) {
		if (largeur<3) {largeur = 3; }
		if (hauteur<3) {hauteur = 3; }
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.grille = new int[hauteur][largeur];
		// calcul du nombre de bombes en fonction du level
		if (level <1) {
			nbMines = 1;
		} else if (level>=5) {
			// 25% de mines (level max)
			this.nbMines = (int)Math.floor((largeur*hauteur)*0.25);
		} else {
			// 1=5%, 2=10%, 3=15%, 4=20%
			this.nbMines = (int)Math.floor( 0.05*level*(largeur*hauteur) );
		}
		if (nbMines<1) {
			nbMines = 1;
		}
		this.initialiser();
	}
	
	public void initialiser() {
		for (int y=0; y<this.hauteur; y++) {
			for (int x=0; x<this.largeur; x++) {
				this.grille[y][x] = 0;
			}
		}
		for (int m=0; m<this.nbMines; m++) {
			boolean tryAgain = true;
			do {
				int px = (int)(Math.random()*this.largeur);
				int py = (int)(Math.random()*this.hauteur);
				if (this.grille[py][px] != -1) {
					this.grille[py][px] = -1;
					this.actualiserComptageMine(px,py);
					tryAgain = false;
				}
			} while (tryAgain);
		}
	}
	public int get( int x, int y ) {
		if (x>=0 && y>=0 && x<this.largeur && y<this.hauteur) {
			return this.grille[y][x];
		} else {
			return -1; // cas hors borne ==> mine fictive ;-)
		}
	}
	public int get( int position ) {
		if (position>=0 && position<this.largeur*this.hauteur) {
			int x = position%this.largeur;
			int y = position/this.largeur;
			return this.grille[y][x];
		} else {
			return -1; // cas hors borne ==> mine fictive ;-)
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
	private void actualiserComptageMine(int positionX, int positionY) {
		// on ajoute 1 au compteur des 8 cases voisines (si elles existent et si elles ne contiennent pas une mine)
		if (positionX>0) {
			if (positionY>0) {
				if (this.grille[positionY-1][positionX-1]!=-1) { this.grille[positionY-1][positionX-1] ++ ; }
			}
			if (this.grille[positionY][positionX-1]!=-1)       { this.grille[positionY][positionX-1] ++ ;   }
			if (positionY<this.hauteur-1) {
				if (this.grille[positionY+1][positionX-1]!=-1) { this.grille[positionY+1][positionX-1] ++ ; }
			}
		}
		if (positionY>0) {
			if (this.grille[positionY-1][positionX]!=-1)       { this.grille[positionY-1][positionX] ++ ; }
		}
		if (positionY<this.hauteur-1) {
			if (this.grille[positionY+1][positionX]!=-1)       { this.grille[positionY+1][positionX] ++ ; }
		}
		if (positionX<this.largeur-1) {
			if (positionY>0) {
				if (this.grille[positionY-1][positionX+1]!=-1) { this.grille[positionY-1][positionX+1] ++ ; }
			}
			if (this.grille[positionY][positionX+1]!=-1)       { this.grille[positionY][positionX+1] ++ ;   }
			if (positionY<this.hauteur-1) {
				if (this.grille[positionY+1][positionX+1]!=-1) { this.grille[positionY+1][positionX+1] ++ ; }
			}
		}
	}
	/**
	 * transformer la grille en chaine de caractère (pour debuggage)
	 */
	public String toString() {
		String result = "";
		for (int y=0; y<this.hauteur; y++) {
			for (int x=0; x<this.largeur; x++) {
				if (this.grille[y][x]==-1) {
					result += " X";
				} else {
					result += " "+this.grille[y][x];
				}
			}
			result += "\n";
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println("=== test unitaire de la classe ''grille'' ===");
		System.out.println("----------- ( 5 x 5  lv 1 ) -----------");
		System.out.println( new Grille(5,5,1) );
		System.out.println("----------- ( 6 x 6  lv 4 ) -----------");
		System.out.println( new Grille(6,6,4) );
		System.out.println("----------- ( 10 x 5  lv 5 ) -----------");
		System.out.println( new Grille(10,5,4) );
	}
}
