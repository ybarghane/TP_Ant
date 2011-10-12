/*
 * Copyright (c) 2009 - Ahcene Bounceur
 */
package org.ubo.elementz.model;

//--------------------------------------------------------------------------
// Projet : ElementZ Lite
// Version Beta
//--------------------------------------------------------------------------
// Cours : IHM
// 2009
//--------------------------------------------------------------------------
// On appellera dans ce qui suit famille à n boules un groupe de n boules
// voisines ayant la même couleur
// Famille en ligne : famille qui se trouve sur la même ligne
// Famille en colonne : famille qui se trouve sur la même colonne
//--------------------------------------------------------------------------
// But du jeu :
// Il faut permuter deux boules voisines de telle sorte à former
// des familles en lignes et/ou en colonne à 3, 4 ou 5 boules.
//--------------------------------------------------------------------------

import java.util.Random;

import org.bushe.swing.event.EventBus;
import org.ubo.elementz.model.events.ScoreChangedEvt;

/**
 * Classe principale du modèle pour ElementZ.
 * 
 * @author Ahcene Bounceur
 */
public class ElementZ_Model {
	// --------------------------------------------------------------------------
	// Les variables
	// --------------------------------------------------------------------------
	/** Taille de la grille. */
	private int size = 8;
	/** Matrice du jeu. */
	private int[][] matrix = new int[size][size];
	/** Matrice des familles. */
	private boolean[][] matrixb = new boolean[size][size];
	/** Le score courant. */
	private int score;

	// --------------------------------------------------------------------------
	// Les constructeurs
	// --------------------------------------------------------------------------
	public ElementZ_Model() {
		merge(); // Mélanger les boules
		prepar(); // Préparer le plateau sans qu'il y ait de familles
					// en ligne ou en colonne
		setScore(0); // Initialiser le score
	}

	// --------------------------------------------------------------------------
	// Les accesseurs
	// --------------------------------------------------------------------------
	/** Le score courant. */
	public int getScore() {
		return score;
	}

	/** Défini le nouveau score. */
	private void setScore(final int newScore) {
		this.score = newScore;
		EventBus.publish(new ScoreChangedEvt(this.score));
	}

	/** La taille de la grille. */
	public int getSize(){
		return size;
	}
	
	/**
	 * Affecter une couleur à la boule de la ligne x et de la colonne y.
	 * 
	 * @param x
	 *            ligne de la valeur à modifier
	 * @param y
	 *            colonne de la valeur à modifier
	 * @param value
	 *            valeur à affecter. Valeurs possibles : de 1 à 6
	 */
	public void setXY(int x, int y, int value) {
		matrix[x][y] = value;
	}

	/**
	 * Récuperer la couleur de la boule de la ligne x et de la colonne y
	 * 
	 * @param x
	 *            ligne de la valeur à récupérer
	 * @param y
	 *            colonne de la valeur à récupérer
	 * @return la valeur comprise en 1 et 6
	 */
	public int getXY(int x, int y) {
		return matrix[x][y];
	}

	// --------------------------------------------------------------------------
	// Les méthodes
	// --------------------------------------------------------------------------
	/**
	 * Initialiser la matrice des familles. Au debut aucune famille n'est
	 * formée.
	 */
	protected void initMatrixb() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrixb[i][j] = false;
			}
		}
	}

	/**
	 * Trouver toutes les familles en lignes à n boules.
	 * <p>
	 * Ceci met a jour la matrice des familles matrixb de la maniere suivante :<br/>
	 * On met la valeur <code>true</code> dans la matrice {@link #matrixb} dans
	 * les cases correspondant aux boules faisant partie d'une famille à n
	 * boules.
	 * </p>
	 */
	public void detectLine(int n) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < (size - n + 1); j++) {
				boolean lineOk = true;
				int first = matrix[i][j];
				int k = j;
				while (k < j + n && lineOk) {
					if (matrix[i][k] != first && matrix[i][k] != 0)
						lineOk = false;
					k++;
				}
				if (lineOk) {
					for (k = j; k < (j + n); k++)
						matrixb[i][k] = true;
					j += n + 1;
				}
			}
		}
	}

	/**
	 * Même chose que la méthode detectLine() : mais pour les colonnes.
	 */
	public void detectCol(int n) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < (size - n + 1); j++) {
				boolean lineOk = true;
				int first = matrix[j][i];
				int k = j;
				while (k < j + n && lineOk) {
					if (matrix[k][i] != first && matrix[k][i] != 0)
						lineOk = false;
					k++;
				}
				if (lineOk) {
					for (k = j; k < (j + n); k++)
						matrixb[k][i] = true;
					j += n + 1;
				}
			}
		}
	}

	/**
	 * Détecte toutes les familles en ligne à 3, 4 et 5 boules.
	 */
	public void detectLines() {
		for (int i = 5; i > 2; i--) {
			detectLine(i);
		}
	}

	/**
	 * Détecte toutes les familles en colonne à 3, 4 et 5 boules.
	 */
	public void detectCols() {
		for (int i = 5; i > 2; i--) {
			detectCol(i);
		}
	}

	/**
	 * Détecte toutes les familles en ligne et en colonne à 3, 4 et 5 boules
	 */
	public void detectLinesAndCols() {
		for (int i = 5; i > 2; i--) {
			detectLine(i);
			detectCol(i);
		}
	}

	/**
	 * Supprime toutes les familles à 3, 4 ou 5 boules. Mettre 0 dans les cases
	 * correspondantes de la matrice du jeu matrix
	 */
	public void deleteTheSame() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (matrixb[i][j])
					matrix[i][j] = 0;
			}
		}
		initMatrixb();
	}

	/**
	 * Occuper les vides apres avoir supprimer les boules.
	 * <p>
	 * Les boules d'en haut descendent en bas pour occuper les vides. Tous les
	 * vides se trouveront en haut. C'est à dire, tous les zéros de la matrice
	 * du jeu matrix se trouveront dans la partie supérieure de cette matrice.
	 * </p>
	 */
	public void gravity() {
		for (int j = 0; j < size; j++) {
			int k = 7;
			for (int i = 7; i >= 0; i--) {
				if (matrix[i][j] != 0) {
					matrix[k][j] = matrix[i][j];
					k--;
				}
			}
			for (int i = k; i >= 0; i--) {
				matrix[i][j] = 0;
			}
		}
	}

	/**
	 * Remplacer les vides par de nouvelles boules.
	 * <p>
	 * C'est a dire, remplacer les zéros de la matrice matrix par des boules
	 * (numeros de 1 à 6).
	 * </p>
	 */
	public void fillGaps() {
		Random x = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (matrix[i][j] == 0)
					matrix[i][j] = x.nextInt(6) + 1;
			}
		}
	}

	/**
	 * Apres avoir remplacer les vides, vérifier s'il y a des familles à 3, 4 ou
	 * 5 boules.
	 * <p>
	 * S'il y a des familles qui se créent, il faut les supprimer à nouveau
	 * avant de laisse la main au joueur.<br/>
	 * <u>Remarque : </u>les familles créées automatiquement seront
	 * contabilisées dans le score.
	 * </p>
	 */
	public boolean notReadyToPlay() {
		detectLinesAndCols();
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (matrixb[i][j])
					return true;
		return false;
	}

	/**
	 * Après une permutation du joueur :
	 * <ol>
	 * <li>on vérifie toutes les familles créées</li>
	 * <li>si au moins une famille est créée (notReadyToPlay = VRAI)</li>
	 * <li>on met à jour le score par rapport aux familles créées</li>
	 * <li>supprimer toutes ses familles</li>
	 * <li>faire descendre les boules pour occuper les vides</li>
	 * <li>remplacer les vides par de nouvelles boules</li>
	 * <li>revenir au point 2</li>
	 * <li>sinon on redonne la main au joueur pour faire une autre permutation</li>
	 * </ol>
	 */
	public void prepar() {
		while (notReadyToPlay()) {
			setScore(score + somme());
			deleteTheSame();
			gravity();
			fillGaps();
		}
	}

	/**
	 * Permuter deux boules voisine (verticalement ou horizontalement).
	 */
	public void permut(int i, int j, int ip, int jp) {
		if (((i == ip) && (j == (jp + 1))) || ((i == ip) && (j == (jp - 1)))
				|| ((j == jp) && (i == (ip + 1)))
				|| ((j == jp) && (i == (ip - 1)))) {
			int s = matrix[i][j];
			matrix[i][j] = matrix[ip][jp];
			matrix[ip][jp] = s;
		}
	}

	/**
	 * Jouer : après une permutation, on vérifie si une famille est créée ou
	 * non.
	 * <p>
	 * Si oui, on la supprime du jeu, on remplace le vide par de nouvelles
	 * boules et voir si à nouveau d'autres familles sont créées sinon on laisse
	 * la main au joueur.<br/>
	 * Si la permutation n'engendre pas de famille, on ne fait rien.
	 * </p>
	 */
	public void play(int x, int y, int xp, int yp) {
		permut(x, y, xp, yp);
		detectLinesAndCols();
		if (matrixb[x][y] || matrixb[xp][yp])
			prepar();
		else
			permut(x, y, xp, yp);
	}

	/**
	 * Créer une matrice de jeu (matrix) avec des boules aléatoires.
	 */
	public void merge() {
		Random x = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] = x.nextInt(6) + 1;
			}
		}
	}

	/**
	 * Le score est égale au nombre de boules créées par le nombre de familles.
	 */
	public int somme() {
		int s = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				s += (matrixb[i][j] ? 1 : 0);
			}
		}
		return s;
	}

	/**
	 * Voir l'état des matrices (de jeu et de famille).
	 */
	public String toString() {
		String s = "";
		String s1;
		String s2;
		for (int i = 0; i < size; i++) {
			s1 = "";
			s2 = "";
			for (int j = 0; j < size; j++) {
				s1 += matrix[i][j] + " ";
				s2 += (matrixb[i][j] ? 1 : 0) + " ";
			}
			s += s1 + "            " + s2 + '\n';
		}
		return s;
	}
}
