package demineur.metier;

/**
 * Énumération des états possibles d'une case de Démineur.
 * 
 * Chaque case du masque peut avoir 1 des 4 états :
 *   FERME  : case Fermée : non marquée et non découverte (aucune info particulière et on ne voit pas ce qui est dessous)
 *   MINE   : case protégée elle a un marqueur de mine et ne peut pas être retournée
 *   DOUTE  : case protégée elle a un marque de "doute" et ne peut pas être retournée
 *   OUVERT : case Ouverte : ayant déjà été retournée 
 * 
 * @author Fabrice PELLEAU
 *
 */
public enum EtatCase {
	MINE,
	DOUTE,
	OUVERT,
	FERME
}
