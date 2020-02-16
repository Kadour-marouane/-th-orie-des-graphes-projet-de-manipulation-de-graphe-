package L3NEW_TG_B1_projet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;


/**
 * Cette classe permet de gerer les differents graphes créés et de leur appliquer differentes méthodes.
 * @author thsou
 *
 */
public class L3NEW_TG_B1_TraitementGraphes {
	
	private Hashtable<Integer, L3NEW_TG_B1_Graphe> listegraphes;
	
	
	/**
	 * Constructeur qui initialise la liste de graphes.
	 */
	public L3NEW_TG_B1_TraitementGraphes() {
		listegraphes = new Hashtable<Integer, L3NEW_TG_B1_Graphe>();
	}
	
	/**
	 * Permet d'obtenir la liste des graphes
	 * @return Retourne la liste des graphes
	 */
	public Hashtable<Integer, L3NEW_TG_B1_Graphe> getListeGraphes(){
		return listegraphes;
	}
	
	/**
	 * Permet d'obtenir le nombre de lignes d'un fichier
	 * @param file Path du fichier
	 * @return Retourne le nombre de lignes du fichier
	 * @throws IOException
	 */
	public static int nbLignes(String file) throws IOException {
		
		int nblignes = 0;
		
		BufferedReader fichier = new BufferedReader(new FileReader(file));
		
		while(fichier.readLine() != null) { //Pour chaque ligne, incrémente nblignes.
			nblignes++;
		}
		return nblignes;
		
	}
	
	/**
	 * Permet d'extraire les données d'un fichier texte et de les enrigister dans une instance de la classe Graphe
	 * Puis de stocker ce graphe dans la liste de graphe.
	 * @param file Fichier contenant les information du graphe possédant le format adéquat (voir annexe).
	 * @throws IOException
	 */
	public void extraitGraphe(String file, int nb) throws IOException {
			
		System.out.println("Lecture du graphe sur fichier");
		BufferedReader fichier = new BufferedReader(new FileReader(file));
		
		String line;
		String[] elements;
		L3NEW_TG_B1_Graphe newgraphe = new L3NEW_TG_B1_Graphe(); //Construction d'un nouveau graphe
		
		line = fichier.readLine();
		newgraphe.setNbSommets(Integer.parseInt(line)); //Récupère le nombre de sommets du graphe
		System.out.println(Integer.parseInt(line) + " sommets");
		
		line = fichier.readLine();
		newgraphe.setNbArcs(Integer.parseInt(line)); //Récupère le nombre d'arcs du graphe
		System.out.println(Integer.parseInt(line) + " arcs");
		
		newgraphe.initAdjacence(newgraphe.getNbSommets());
		newgraphe.initMatricePoids(newgraphe.getNbSommets());
		
		
		for(int i = 2; i<nbLignes(file); i++) {
		
			line = fichier.readLine();
			
			elements = line.split(" ");
			
			int ligne = Integer.parseInt(elements[0]);
			int colonne = Integer.parseInt(elements[1]);
			int valeur = Integer.parseInt(elements[2]);
			System.out.println(ligne + " -> " + colonne + " = " + valeur);
			
			newgraphe.setAdjacence(ligne, colonne, true);
			newgraphe.setMatricePoids(ligne, colonne, valeur);
		
		}
		fichier.close();
		System.out.println();
		
		listegraphes.put(nb,newgraphe);
		
	}
	
	/**
	 * Permet d'obtenir le nombre de prédécesseurs dans une liste d'entiers pour chaque Sommet
	 * @param graphe Graphe utilisé
	 * @return Retourne une liste d'entiers correspondant au nombre de prédécesseurs par sommet
	 */
	//Légende :
	//0: Pas de successeur
	//-1: A supprimer
	//-2: Supprimé
	public int[] listeDegres(L3NEW_TG_B1_Graphe graphe) {
		
		int[] listedegres = new int[graphe.getNbSommets()];
		
		for(int colonne=0; colonne<graphe.getNbSommets();colonne++) { //Pour chaque colonne (sommet), compte le nombre de prédécesseurs
			for(int ligne=0; ligne<graphe.getNbSommets();ligne++) {
				//System.out.println("colonne : " + colonne + " ,ligne : " + ligne + " ,bool : " + graphe.getAdjacence()[colonne][ligne]);
				
				if(graphe.getAdjacence()[ligne][colonne] == true) {
				
					listedegres[colonne] += 1;
				}
			}
		}
		return listedegres;
	}
	
	/**
	 * Renvoie la liste du nombre de prédécesseur par sommet.
	 * Si l'entier vaut -1 c'est que le sommet correspondant n'avait pas de prédécesseur et a été supprimé
	 * @param listep Liste des nombres de predecesseurs par sommet à modifier
	 * @return Retourne une liste d'entiers correspondant au nombre de prédécesseurs par sommet en indiquant quel sommet a été "supprimé"
	 */
	public int[] changementpreced(int[] listep){
		
		for (int i=0;i < listep.length ;i++) {
			if (listep[i]==0) { //Si un sommet n'a pas de predecesseur, il passe à l'état "supprimé" (listep[i] = -1)
				listep[i]=-1;
			}
		}
		return listep;
	}	
	
	/**
	 * Mets à jour la matrice d'adjacence en supprimant les liens vers les successeurs des sommets "supprimés"
	 * @param listeadj Matrice d'adjacence à modifier
	 * @param listep Liste des nombres de prédecesseurs par sommet contenant des -1 pour les sommets à supprimer
	 * @return Retourne une liste de booleens à deux dimmension avec les arcs restant après "suppression" des sommets
	 */
	public boolean [][] Madjacechg(boolean[][] listeadj ,int[] listep) {
		
		boolean[][] newlisteadj = new boolean[listep.length][listep.length];
		
		for (int i=0; i<listep.length;i++) {
			if (listep[i]== -1) { //Pour chaque sommet à supprimer..
				
				for (int j=0;j<listep.length;j++) { //..Parcours toutes les colonnes..
					if (listeadj[i][j]==true) {
						newlisteadj[i][j]=false; //..pour supprimer les arcs qui mènent à ses successeurs
					}				
				}
			}
			else
				newlisteadj[i] = listeadj[i];
		}
		return newlisteadj; //Nouvelle liste d'adjacence, les sommets "supprimés" n'ont plus de liens avec les autres sommets
	}
	
	/**
	 * Mets à jour le nombre de prédécesseurs par sommets en prenant en compte les sommets supprimés (Les place à -2)
	 * @param listeadj Liste d'adjacence à utiliser pour recompter le nombre de predecesseurs par sommet
	 * @param listep Liste du nombre de prédecesseurs par sommet dont on se sert pour créer la nouvelle
	 * @return Retourne une liste d'entiers avec le nouveau nombre de prédécesseurs par sommet.
	 */
	public int[] listeDegreschg(boolean[][] listeadj, int[] listep) {
		
		int[] listedegreschg = new int[listep.length];// Nouvelle liste du nombre de prédecesseurs par sommet
		
		for(int i = 0; i<listep.length; i++) { //Cette boucle donne la valeur -2 au nombre de predecesseurs d'un sommet ayant été supprimé à l'opération précédente
			if (listep[i] <= -1)
				listedegreschg[i] = -2;
		}
		
		for(int colonne=0; colonne<listep.length;colonne++) { //Pour chaque colonne (sommet), compte le nombre de prédécesseurs
			for(int ligne=0; ligne<listep.length;ligne++) {
				//System.out.println("colonne : " + colonne + " ,ligne : " + ligne + " ,bool : " + graphe.getAdjacence()[colonne][ligne]);
				
				if(listeadj[ligne][colonne] == true) {
				
					listedegreschg[colonne] += 1;
				}
			}
		}
		return listedegreschg;
	}
	
	/**
	 * Indique si le graphe possède des sommets sans predecesseurs ou non
	 * @param graphe Graphe utilisé
	 * @param listep Liste du nombre de predecesseurs de chaque sommet
	 * @return Retourne True si tous les sommets n'ont pas de prédecesseurs, False sinon
	 */
	public boolean hasSommetsSansPred(int[] listep) {
		
		boolean sommetsanspred = true; //indique s'il y'a des sommets sans predecesseurs
		
		for(int i=0; i<listep.length;i++) {
			if(listep[i] == 0) {
				sommetsanspred = false;
			}
		}
		return sommetsanspred;
	}
	
	/**
	 * S'il reste un sommet ayant au moins 1 predecesseur, renvoie true
	 * @param listep Liste du nombre de predecesseurs par sommet
	 * @return 
	 */
	public boolean resteDesSommets(int[] listep) {
		
		for(int i = 0; i< listep.length; i++) {
			if(listep[i] > 0) { //S'il reste un sommet ayant au moins 1 predecesseur, renvoie true
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Detecte la présence d'un circuit dans un graphe et affiche les étapes
	 * @param graphe Graphe utilisé
	 * @return Retourne true si le graphe est composé d'un circuit, false sinon
	 */
	public boolean AfficheHasCircuit(L3NEW_TG_B1_Graphe graphe) {
		
		System.out.println("Detection de circuit");
		System.out.println("Methode d'élimination des points d'entrées");
		
		int[] listep = listeDegres(graphe); //Liste des nombres de prédecesseurs par sommet
		
		boolean[][] listeadj = graphe.getAdjacence(); //Matrice d'adjacence initiale
		
		while (hasSommetsSansPred(listep) == false) { //S'arretera lorsqu'il n'y aura plus de sommet sans predecesseurs.
			
			
			
			listep = changementpreced(listep); //Met à -1 les sommets à supprimer
			afficherPointEntree(listep);
			
			//System.out.println(Arrays.toString(listep));
			listeadj = Madjacechg( listeadj ,listep); //Change la matrice d'adjacence
			listep = listeDegreschg( listeadj, listep); // Recréé la liste du nombre de prédécesseurs par sommet en mettant les sommets supprimés à -2
			afficherSommetsRestants(listep);
			//System.out.println(Arrays.toString(listep));
		}
		
		if(resteDesSommets(listep) == true)
			System.out.println("Le graphe contient au moins un circuit" + '\n');
			
		else
			System.out.println("Le graphe ne contient pas de circuit" + '\n');
		
		
		if(resteDesSommets(listep) == true)
			
			return true;
		else
			return false;
		
	}
	
	/**
	 * Detecte la présence d'un circuit dans un graphe et n'affiche pas
	 * @param graphe Graphe utilisé
	 * @return Retourne true si le graphe est composé d'un circuit, false sinon
	 */
	public boolean hasCircuit(L3NEW_TG_B1_Graphe graphe) {
		
		int[] listep = listeDegres(graphe); //Liste des nombres de prédecesseurs par sommet
		
		boolean[][] listeadj = graphe.getAdjacence(); //Matrice d'adjacence initiale
		
		while (hasSommetsSansPred(listep) == false) { //S'arretera lorsqu'il n'y aura plus de sommet sans predecesseurs.
			
			
			
			listep = changementpreced(listep); //Met à -1 les sommets à supprimer
			
			
			//System.out.println(Arrays.toString(listep));
			listeadj = Madjacechg( listeadj ,listep); //Change la matrice d'adjacence
			listep = listeDegreschg( listeadj, listep); // Recréé la liste du nombre de prédécesseurs par sommet en mettant les sommets supprimés à -2
			
			//System.out.println(Arrays.toString(listep));
		}
		
		if(resteDesSommets(listep) == true)
			
			return true;
		else
			return false;
		
	}
	
	/**
	 * Met à jour la liste des rangs
	 * @param listerang Liste des rangs à mettre à jour
	 * @param listep2 Liste du nombre de prédécesseurs par sommets
	 * @return Renvoie la mise à jour des rang des sommets du graphe.
	 */
	public int[] listerangchg(int[] listerang, int[] listep2) {
		
		for(int i = 0; i<listep2.length; i++) {
			if(listep2[i] != -2) {
				listerang[i] += 1;
			}
		}
		return listerang;
	}
	
	
	/**
	 * Affiche le rang de chaque sommet
	 * @param graphe Graphe utilisé
	 */
	public void afficheRang(L3NEW_TG_B1_Graphe graphe) {
		
		
		if(AfficheHasCircuit(graphe) == true) { //Verifie s'il n'y a pas de circuits
			System.out.println("Le calcul de rangs n'est pas possible" + '\n');
			return;
		}
		
		System.out.println("Calcul des rangs des sommets :");
		
		int[] listerang = new int[graphe.getNbSommets()]; //Liste des rangs
		 	
		int[] listep2 = listeDegres(graphe); //Liste des nombres de prédecesseurs par sommet
		//System.out.println(Arrays.toString(listep2));
		
		boolean[][] listeadj = graphe.getAdjacence(); //Matrice d'adjacence initiale
			
		while (hasSommetsSansPred(listep2) == false) { //S'arretera lorsqu'il n'y aura plus de sommet sans predecesseurs.
				
			listep2 = changementpreced(listep2); //Met à -1 les sommets à supprimer
			
			listeadj = Madjacechg( listeadj ,listep2); //Change la matrice d'adjacence
			listep2 = listeDegreschg( listeadj, listep2); // Recréé la liste du nombre de prédécesseurs par sommet en mettant les sommets supprimés à -2
			
			listerang = listerangchg(listerang, listep2); // ajoute 1 au rang de tous les sommets qui n'ont pas encore été supprimés à chaque suppression.
			//System.out.println(Arrays.toString(listerang));
		}
		
		for(int i=0; i<listep2.length; i++) { //affichage du rang de chaque sommet
			System.out.println("Le rang du sommet " + i + " est égal à " + listerang[i]);
		}
	}
	
	/**
	 * Renvoie la liste des rangs par sommet
	 * @param graphe Graphe utilisé
	 * @return Renvoie la liste des rangs par sommet
	 */
	public int[] rang(L3NEW_TG_B1_Graphe graphe) {
		
		
		if(hasCircuit(graphe) == true) { //Verifie s'il n'y a pas de circuits
			return null;
		}
		
		int[] listerang = new int[graphe.getNbSommets()]; //Liste des rangs
		 	
		int[] listep2 = listeDegres(graphe); //Liste des nombres de prédecesseurs par sommet
		//System.out.println(Arrays.toString(listep2));
		
		boolean[][] listeadj = graphe.getAdjacence(); //Matrice d'adjacence initiale
			
		while (hasSommetsSansPred(listep2) == false) { //S'arretera lorsqu'il n'y aura plus de sommet sans predecesseurs.
				
			listep2 = changementpreced(listep2); //Met à -1 les sommets à supprimer
			
			listeadj = Madjacechg( listeadj ,listep2); //Change la matrice d'adjacence
			listep2 = listeDegreschg( listeadj, listep2); // Recréé la liste du nombre de prédécesseurs par sommet en mettant les sommets supprimés à -2
			
			listerang = listerangchg(listerang, listep2); // ajoute 1 au rang de tous les sommets qui n'ont pas encore été supprimés à chaque suppression.
			//System.out.println(Arrays.toString(listerang));
		}
		
		return listerang;
	}
	
	/**
	 * Renvoie le rang du grap
	 * @param graphe
	 * @return Renvoie le rang du graphe, -1 si le graphe n'a pas de rang
	 */
	public int valRang(L3NEW_TG_B1_Graphe graphe) {
		
		
		int rang = 0;
		int[] listerg = rang(graphe);
		
		if(listerg == null) {
			return -1;
		}
		
		for(int i =0; i<graphe.getNbSommets(); i++) {
			if(listerg[i] > rang) {
				rang = listerg[i];
			}
		}
		return rang;
	}
	
	
	/**
	 * Affiche les differents points d'entrée s'il y'en a
	 * @param liste
	 */
	public void afficherPointEntree(int[] liste) {
		System.out.println("Points d'entrée :");
		
		for(int i=0; i<liste.length;i++) {
			if(liste[i] == -1) {
				System.out.print(i + " ");
			}
		}
		System.out.println();
	}
	
	/**
	 * Affiche les differents sommets restants
	 * @param liste
	 */
	public void afficherSommetsRestants(int[] liste) {
		
		System.out.println("Sommets restants :");

		for(int i=0; i<liste.length;i++) {
			
			if(liste[i] != -2) {
				System.out.print(i + " ");
					
			}
		}
		System.out.println();
	}
	
	//Partie 2
	
	
	/**
	 * Verifie si le graphe ne possède qu'un seul point d'entrée
	 * @param graphe Graphe utilisé
	 * @return Renvoie true si le graphe ne possède qu'un seul point d'entree, false sinon
	 */
	public boolean hasUnPointEntree(L3NEW_TG_B1_Graphe graphe) {
		int nbentrees = 0;
		boolean predecesseurs;
		
		for(int colonne = 0; colonne<graphe.getNbSommets(); colonne++) { //Parcours les sommets
			predecesseurs = false;
			
			for(int ligne = 0; ligne<graphe.getNbSommets(); ligne++) { //Regarde si le sommet possède au moins un successeur
				if(graphe.getAdjacence()[ligne][colonne] == true) {
					predecesseurs = true;
				}
			}
			if(predecesseurs == false) { //Compte le nombre d'entrée
				nbentrees += 1; 
			}
		}
		
		if(nbentrees == 1) //Vérifie s'il n'y en a qu'une
			return true;
		
		else 
			return false;
		
	}
	
	/**
	 * Verifie si le graphe ne possède qu'un seul point de sortie
	 * @param graphe Graphe utilisé
	 * @return Renvoie true si le graphe ne possède qu'un seul point de sortie, false sinon
	 */
	public boolean hasUnPointSortie(L3NEW_TG_B1_Graphe graphe) {
		int nbsorties = 0;
		boolean successeurs;
		
		for(int ligne = 0; ligne<graphe.getNbSommets(); ligne++) { //Parcours les sommets
			successeurs = false;
			
			for(int colonne = 0; colonne<graphe.getNbSommets(); colonne++) { //Regarde si le sommet possède au moins un predecesseur
				if(graphe.getAdjacence()[ligne][colonne] == true) {
					successeurs = true;
				}
			}
			if(successeurs == false) { //Compte le nombre de sorties
				nbsorties += 1;
			}
		}
		if(nbsorties == 1) { //Vérifie s'il n'y en a qu'une
			return true;
		}
		else
			return false;
	}
	
	
	/**
	 * Verifie si tous les arcs incidents vers l'exterieur à un sommet on une valeur identique
	 * @param graphe Graphe utilisé
	 * @return Renvoie true si tous les arcs incidents vers l'exterieur à un sommet on une valeur identique, false sinon
	 */
	public boolean valeursidentique(L3NEW_TG_B1_Graphe graphe) {
		
		for(int ligne=0;ligne<graphe.getNbSommets();ligne++) { //Pour chaque sommet
			for (int colonne = 0;colonne<graphe.getNbSommets();colonne++) { 
				
				if (graphe.getAdjacence()[ligne][colonne] == true) { //Pour chaque successeur
					int i=graphe.getMatricePoids()[ligne][colonne];
					
						for (int c=colonne;c<graphe.getNbSommets();c++) { //Compare la valeur avec tous les autres successeurs
							
							if(graphe.getAdjacence()[ligne][c]==true) {
								if (graphe.getMatricePoids()[ligne][c] != i) //Si une valeur est différente, retourne false
									return false;
							}
							
						}
				}
			}
		}
		return true;
	}
	
	/**
	 * Verifie si le graphe possede un arc à valeur negative
	 * @param graphe Graphe utilisé
	 * @return true si le graphe possede un arc à valeur negative, false sinon
	 */
	public boolean hasNoValNeg(L3NEW_TG_B1_Graphe graphe) {
		
		boolean valneg = true;
		
		for(int colonne = 0; colonne<graphe.getNbSommets(); colonne++) {
			for(int ligne = 0; ligne<graphe.getNbSommets(); ligne++) {
				if(graphe.getMatricePoids()[ligne][colonne] < 0) {
					valneg = false;
				}
			}
		}
		return valneg;
	}
	
	/**
	 * Calcul la longueur de chaque tache
	 * @param graphe Graphe utilisé
	 * @return La liste des longueur des taches
	 */
	public int[] longueurParTache(L3NEW_TG_B1_Graphe graphe) {
		
		int[] liste = new int[graphe.getNbSommets()];
		
		for(int ligne = 0; ligne<graphe.getNbSommets(); ligne++) {
			for(int colonne = 0; colonne<graphe.getNbSommets(); colonne++) {
				if(graphe.getAdjacence()[ligne][colonne] == true) {
					liste[ligne] = graphe.getMatricePoids()[ligne][colonne];
				}
			}
		}
		return liste;
	}
	
	/**
	 * Trouve le point d'entree du graphe
	 * @param graphe Graphe utilisé
	 * @return Renvoie l'entree du graphe s'il n'en possède qu'une
	 */
	public int PointEntree(L3NEW_TG_B1_Graphe graphe) {
		
		int entree = -1;
		boolean predecesseurs = false;
		
		if(hasUnPointEntree(graphe)==true) {
			for(int colonne = 0; colonne<graphe.getNbSommets(); colonne++) {
				predecesseurs = false;
				for(int ligne = 0; ligne<graphe.getNbSommets(); ligne++) {
					if(graphe.getAdjacence()[ligne][colonne] == true) {
						predecesseurs = true;
					}
				}	
				if(predecesseurs == false) { //retient le seul sommet qui n'a pas de predecesseurs
					entree = colonne;
				}
			}
		}
		return entree;
	}
	
	/**
	 * Trouve le point de sortie du graphe
	 * @param graphe Graphe utilisé
	 * @return Renvoie la sortie du graphe s'il n'en possède qu'une, -1 sinon
	 */
	public int PointSortie(L3NEW_TG_B1_Graphe graphe) {
		
		int sortie = -1;
		boolean predecesseurs = false;
		
		if(hasUnPointSortie(graphe)==true) {
			for(int ligne = 0; ligne<graphe.getNbSommets(); ligne++) {
				predecesseurs = false;
				for(int colonne = 0; colonne<graphe.getNbSommets(); colonne++) {
					if(graphe.getAdjacence()[ligne][colonne] == true) {
						predecesseurs = true;
					}
				}	
				if(predecesseurs == false) { //Retient le seul sommet qui n'a pas de successeurs
					sortie = ligne;
				}
			}
		}
		return sortie;
	}
	
	/**
	 * Vérifie si les arcs incidents au point d'entrée sont nuls
	 * @param graphe Graphe utilisé
	 * @return Renvoie true si les valeurs sont nulles, false sinon
	 */
	public boolean verifPointEntree(L3NEW_TG_B1_Graphe graphe) {
		int entree = PointEntree(graphe);
		int calcul = 0;
		
		if(hasUnPointEntree(graphe) == true) {
			for(int colonne = 0; colonne<graphe.getNbSommets(); colonne++) {
				if(graphe.getAdjacence()[entree][colonne] == true) 
					calcul += graphe.getMatricePoids()[entree][colonne]; //Fait la somme de tous les arcs sortants
			}
			if (calcul == 0)
				return true;
		}
			
			 
			return false;
		
	}
	
	/**
	 * Calcul les dates au plus tot pour chaque sommet du graphe
	 * @param graphe Graphe utilisé
	 * @return Renvoie la liste des dates au plus tot pour chaque sommet du graphe
	 */
	public int[] calendrierAuPlusTot(L3NEW_TG_B1_Graphe graphe) {
		System.out.println("!!!!!!");
		int[] listerg = rang(graphe); //Liste des rangs par sommet
		//System.out.println("listerg " + Arrays.toString(listerg));
		int [] listelongueur = longueurParTache(graphe); //Longueur des taches
		//System.out.println("listelongueur " + Arrays.toString(listelongueur));
		int[] valauplustot = new int[graphe.getNbSommets()]; //Liste des valeurs au plus tot
		
		
		for(int rg = 1; rg<=valRang(graphe); rg++) { //Parcours les rangs de 1 à n
			
			for(int sommet=0; sommet<graphe.getNbSommets(); sommet++) { //Pour chaque de sommet de rang rg
				if(listerg[sommet] == rg) {
					
					//System.out.println("sommet " + sommet);
					int cheminlepluslong =0;
					
					for(int ligne =0; ligne<graphe.getNbSommets(); ligne++) { //Trouve le plus long chemin parmis tous les predecesseurs
							
						if(graphe.getAdjacence()[ligne][sommet] == true) {
							/*
							System.out.println("predecesseur " + ligne);
							System.out.println("valauplustot " + valauplustot[ligne]);
							System.out.println("graphe.getMatricePoids()[ligne][colonne] " + graphe.getMatricePoids()[ligne][sommet]);
							*/
							if(valauplustot[ligne] + graphe.getMatricePoids()[ligne][sommet] >= cheminlepluslong) {
								cheminlepluslong = valauplustot[ligne] + graphe.getMatricePoids()[ligne][sommet] ;
								}
							}
						}
						
					valauplustot[sommet] = cheminlepluslong;
				}
			}
		}
		return valauplustot;
	}
	
	/**
	 * Renvoie le maximum d'ube liste d'entiers
	 * @param liste
	 * @return
	 */
	public int maxListe(int[] liste) {
		int max = 0;
		for(int i =0; i<liste.length; i++) {
			if(liste[i]>max) {
				max = liste[i];
			}
		}
		return max;
	}
	
	/**
	 * Calcul des dates au plus tard pour chaque sommet
	 * @param graphe Graphe utilisé
	 * @return Renvoie la liste d'entiers des dates au plus tard de chaque sommet
	 */
	public int[] calendrierAuPlusTard(L3NEW_TG_B1_Graphe graphe) {
		int[] listerg = rang(graphe); //Liste des rangs par sommet
		int[] listelongueur = longueurParTache(graphe); //Longueur des taches
		int[] valauplustard = calendrierAuPlusTot(graphe); //Liste des valeurs au plus tard
		
		for(int rg = valRang(graphe)-1; rg>0; rg--) { //Parcours les rangs de 0 à n
			for(int sommet=0; sommet<graphe.getNbSommets(); sommet++) { //Pour chaque de sommet de rang rg
				if(listerg[sommet] == rg) {
					
					int cheminlepluscourt = maxListe(valauplustard);
					
					for(int colonne =0; colonne<graphe.getNbSommets(); colonne++) { //Trouve le plus court chemin parmis les successeurs
						if(graphe.getAdjacence()[sommet][colonne] == true) {
							/*
							System.out.println("successeur " + colonne);
							System.out.println("valauplustard " + valauplustard[colonne]);
							System.out.println("listelongueur[sommet] " + listelongueur[sommet]);
							*/
							if((valauplustard[colonne] - listelongueur[sommet]) <= cheminlepluscourt) {
								cheminlepluscourt = valauplustard[colonne] - listelongueur[sommet];
							}
						}
					}
					valauplustard[sommet] = cheminlepluscourt;
				}
			}
		}
		return valauplustard;
	}
	
	/**
	 * Calcule la marge totale de chaque sommet
	 * @param listetot calendrier au plus tot
	 * @param listetard calendrier au plus tard
	 * @return Renvoie une liste des marges totales par sommet
	 */
	public int[] margeTotale(int[] listetot, int[] listetard) {
		
		int[] margetotale = new int[listetot.length];
		
		for(int i =0; i<listetot.length; i++) {
			margetotale[i] = listetard[i] - listetot[i];
		}
		return margetotale;
	}
	
	/**
	 * Calcule la marge libre de chaque sommet
	 * @param graphe Graphe utilisé
	 * @return Renvoie une liste des marges libre par sommet
	 */
	public int[] margeLibre(L3NEW_TG_B1_Graphe graphe) {
		
		int[] margelibre = new int[graphe.getNbSommets()];
		
		for(int sommet = 0; sommet<graphe.getNbSommets(); sommet++) { // Pour chaque sommet..
			int min = maxListe(calendrierAuPlusTot(graphe));
			for(int colonne = 0; colonne < graphe.getNbSommets(); colonne++) { //Parcours les successeurs
				if(graphe.getAdjacence()[sommet][colonne] == true) {
					if(calendrierAuPlusTot(graphe)[colonne] <= min ) {
						min = calendrierAuPlusTot(graphe)[colonne];
					}
				}
					
			}
			margelibre[sommet] = min - (calendrierAuPlusTot(graphe)[sommet] + longueurParTache(graphe)[sommet]);
		}
		return margelibre;
	}
	
	@Override
	public String toString() {
		return "TraitementGraphes [listegraphes=" + listegraphes + "]";
	}	
	
	
	
}
