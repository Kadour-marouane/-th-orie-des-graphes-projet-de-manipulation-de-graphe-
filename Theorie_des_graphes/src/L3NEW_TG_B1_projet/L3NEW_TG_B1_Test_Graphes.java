package L3NEW_TG_B1_projet;

import java.io.IOException;
import java.util.Arrays;

/**
 * Cette classe est une classe de tests
 * @author thsou
 *
 */
public class L3NEW_TG_B1_Test_Graphes {

	public static void main(String[] args) throws IOException {
		
		L3NEW_TG_B1_TraitementGraphes tg = new L3NEW_TG_B1_TraitementGraphes();
		
		//Question 1: extrait les infos d'un fichier et le stock en mémoire
		String path = System.getProperty("user.dir");

		System.out.println(path);

		tg.extraitGraphe(path +"/L3NEW-TG-B1-g10.txt", 10);
	
		
		
		//Question 2: Affichage du graphe sous forme matricielle (matrice d'adjacence + matrice des valeurs)
		tg.getListeGraphes().get(0).afficheAdjacence();
		
		tg.getListeGraphes().get(0).afficheMatricePoids();
		
		//Question 4:
		/*
		System.out.println(Arrays.toString(tg.listeDegres(tg.getListeGraphes().get(0))));
		System.out.println(Arrays.toString(tg.changementpreced(tg.getListeGraphes().get(0))));
		tg.getListeGraphes().get(0).afficheMatriceDoubleDimBool(tg.Madjacechg(tg.getListeGraphes().get(0)));
		System.out.println(Arrays.toString(tg.listeDegreschg(tg.getListeGraphes().get(0))));
		System.out.println(tg.hasCircuit(tg.getListeGraphes().get(0)));
		*/
		
		/*
		//Test detection de circuit
		int[] listep = tg.listeDegres(tg.getListeGraphes().get(0));//Init degrés
		boolean[][] listeadj = tg.getListeGraphes().get(0).getAdjacence();
		System.out.println(Arrays.toString(listep)); 
		
		
		listep = tg.changementpreced(listep);//Met à -1 les sommets à supprimer
		System.out.println(Arrays.toString(listep));
		
		listeadj = tg.Madjacechg( listeadj ,listep);
		listep = tg.listeDegreschg( listeadj, listep);
		System.out.println(Arrays.toString(listep));
		
		
		
		listep = tg.changementpreced(listep);//Met à -1 les sommets à supprimer
		System.out.println(Arrays.toString(listep));
		
		listeadj = tg.Madjacechg( listeadj ,listep);
		listep = tg.listeDegreschg( listeadj, listep);
		System.out.println(Arrays.toString(listep));
		*/
		
		
		//L3NEW_TG_B1_Graphe graphe = new Graphe(tg.getListeGraphes().get(0));
		//System.out.println(tg.hasCircuit(L3NEW_TG_B1_Graphe));
		//tg.rang(L3NEW_TG_B1_Graphe);
		
		
		//PARTIE II
		
		//Verifie s'il y a bien un seul point d'entrée
		//System.out.println(tg.hasUnPointEntree(tg.getListeGraphes().get(0)));
		
		//Verifie s'il y a bien un seul point de sortie
		//System.out.println(tg.hasUnPointSortie(tg.getListeGraphes().get(0)));
		
		//Verifie s'il n'y a pas de valeur negative
		//System.out.println(tg.hasNoValNeg(tg.getListeGraphes().get(0)));
		
		//Liste des valeurs des arcs
		//System.out.println(Arrays.toString(tg.longueurParTache(tg.getListeGraphes().get(0))));
		
		//Entrée du graphe
		//System.out.println(tg.PointEntree(tg.getListeGraphes().get(0)));
		
		//Sortie du graphe
		//System.out.println(tg.PointSortie(tg.getListeGraphes().get(0)));
		
	//	tg.afficheRang(tg.getListeGraphes().get(0));
		/*
		System.out.println(Arrays.toString(tg.rang(tg.getListeGraphes().get(0))));
		System.out.println(tg.valRang(tg.getListeGraphes().get(0)));
		
		System.out.println("cal au plus tot" + Arrays.toString(tg.calendrierAuPlusTot(tg.getListeGraphes().get(0))));
		System.out.println("cal au plus tard" + Arrays.toString(tg.calendrierAuPlusTard(tg.getListeGraphes().get(0))));
		System.out.println("marges totales" + Arrays.toString(tg.margeTotale(tg.calendrierAuPlusTot(tg.getListeGraphes().get(0)),tg.calendrierAuPlusTard(tg.getListeGraphes().get(0)))));
		System.out.println("marge libre" + Arrays.toString(tg.margeLibre(tg.getListeGraphes().get(0))));
		*/
	}
}
