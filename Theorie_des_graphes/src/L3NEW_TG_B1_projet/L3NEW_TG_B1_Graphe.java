package L3NEW_TG_B1_projet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Cette classe permet de créer des objet de type graphe contenant ses differentes caractéristiques
 * @author thsou
 *
 */
public class L3NEW_TG_B1_Graphe {
	private int nbsommets;
	private int nbarcs;
	private boolean[][] adjacence;
	private int[][] matricepoids;
	
	/**
	 * Constructeur par défaut de Graphe
	 */
	public L3NEW_TG_B1_Graphe() {}
	
	public L3NEW_TG_B1_Graphe(int s, int a, boolean[][] adj, int[][] mpoids) {
		nbsommets = s;
		nbarcs = a;
		adjacence = adj;
		matricepoids = mpoids;
	}
	
	public L3NEW_TG_B1_Graphe(L3NEW_TG_B1_Graphe graphe) {
		this(graphe.getNbSommets(), graphe.getNbArcs(), graphe.getAdjacence(), graphe.getMatricePoids());
	}
	
	
	//Getters
	/**
	 * Permet d'obtenir le nombre de sommets du graphe
	 * @return Le nombre de sommets du graphe
	 */
	public int getNbSommets() {
		return nbsommets;
	}
	
	public int getNbArcs() {
		return nbarcs;
	}

	public boolean[][] getAdjacence() {
		return adjacence;
	}
	
	public int[][] getMatricePoids() {
		return matricepoids;
	}
	
	//setters
	/**
	 * Permet de changer le nombre de sommets du graphe
	 * @param S Nouvelle valeur du nombre de sommets
	 */
	public void setNbSommets(int S) {
		nbsommets = S;
	}
	
	public void setNbArcs(int A) {
		nbarcs = A;
	}
	
	/**
	 * Fixe la valeur à true ou false si le chemin entre 2 sommets existe ou non.
	 * 
	 * @param ligne
	 * @param colonne
	 * @param valeur
	 */
	public void setAdjacence(int ligne, int colonne, boolean bool) {
		
		adjacence[ligne][colonne] = bool;
	}
	
	public void setMatricePoids(int ligne, int colonne, int valeur){
		matricepoids[ligne][colonne] = valeur;
	}
	
	/**
	 * Initialise la matrice d'adjacence avec les bonnes dimensions
	 * @param n : Dimensions de la liste
	 */
	public void initAdjacence(int n) {
		adjacence = new boolean[n][n];
		
	}
	
	/**
	 * Initialise la matrice des valeurs avec les bonnes dimensions
	 * @param n : Dimensions de la liste
	 */
	public void initMatricePoids(int n) {
		matricepoids = new int[n][n];
	}

	/**
	 * Affiche le nombre de sommets du graphe.
	 */
	public void afficheNbSommets() {
		System.out.println("Nombre de sommets: " + nbsommets);
	}
	
	/**
	 * Affiche le nombre de sommets du graphe.
	 */
	public void afficheNbArcs() {
		System.out.println("Nombre d'Arcs: " + nbarcs);
	}
	
	/**
	 * Renvoie le nombre de chiffres qui composent le nombre
	 * @param a 
	 * @return
	 */
	public int nbChiffres(int a) {
		int nb = 1;
		while(a/10 != 0) {
			nb+=1;
			a /= 10;
		}
		return nb;
	}
	
	/**
	 * Affiche la matrice d'ajacence du graphe.
	 */
	public void afficheAdjacence() {
		
		int nbespaces = nbChiffres(nbsommets);
		
		System.out.print("Matrice d'adjacence :" + '\n');
		for(int i =0; i<=nbespaces; i++) {
			System.out.print(" ");
		}
		
		for(int i =0; i<nbsommets;i++) {
			System.out.print(i);
			for(int j =0; j<=(nbespaces - nbChiffres(i)); j++){
				System.out.print(" ");
			}
			
		}
		System.out.println();
		
		for (int i=0; i<nbsommets; i++) {
			System.out.print(i);
			for(int k =0; k<=(nbespaces - nbChiffres(i)); k++){
				System.out.print(" ");
			}
			
			for (int j=0; j<nbsommets; j++) {
				if(adjacence[i][j] == true) {
					System.out.print("V");
					for(int k =0; k<=(nbespaces - 1); k++){
						System.out.print(" ");
					}
				}
				if(adjacence[i][j] == false) {
					System.out.print("F");
					for(int k =0; k<=(nbespaces - 1); k++){
						System.out.print(" ");
					}
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Affiche la matrice des valeurs du graphe.
	 */
	public void afficheMatricePoids() {
		
		int nbespaces = nbChiffres(nbsommets);
		
		System.out.print("Matrice de poids :" + '\n');
		for(int i =0; i<=nbespaces; i++) {
			System.out.print(" ");
		}
		for(int i =0; i<nbsommets;i++) {
			System.out.print(i);
			for(int j =0; j<=(nbespaces - nbChiffres(i)); j++){
				System.out.print(" ");
			}
		}
		System.out.println();
		
		
		for (int i=0; i<nbsommets; i++) {
			System.out.print(i);
			for(int k =0; k<=(nbespaces - nbChiffres(i)); k++){
				System.out.print(" ");
			}
			
			for (int j=0; j<nbsommets; j++) {
				if(adjacence[i][j] == true) {
					System.out.print(matricepoids[i][j]);
					for(int l =0; l<=(nbespaces - nbChiffres(matricepoids[i][j])); l++){
						System.out.print(" ");
					}
				}
					
				if(adjacence[i][j] == false) {
					System.out.print("*");
					for(int k =0; k<=(nbespaces - 1); k++){
						System.out.print(" ");
					}
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Affiche la matrice d'une liste de booleen à double dimension
	 * @param tab
	 */
	public void afficheMatriceDoubleDimBool(boolean[][] tab) {
		System.out.print("[");
		for (int i=0; i<tab.length-1; i++) {
			System.out.println(Arrays.toString(tab[i]));
		}
		System.out.println(Arrays.toString(tab[tab.length-1]) + "]");
	}

	/**
	 * Affiche la matrice d'une liste d'Int à double dimension
	 * @param tab
	 */
	public void afficheMatriceDoubleDimInt(int[][] tab) {
		System.out.print("[");
		for (int i=0; i<tab.length-1; i++) {
			System.out.println(Arrays.toString(tab[i]));
		}
		System.out.println(Arrays.toString(tab[tab.length-1]) + "]");
	}
	
	@Override
	public String toString() {
		return "Graphe [nbsommets=" + nbsommets + ", nbarcs=" + nbarcs + ", adjacence=" + Arrays.toString(adjacence)
				+ ", matricepoids=" + Arrays.toString(matricepoids) + "]";
	}

	

	
}
