package L3NEW_TG_B1_projet;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class L3NEW_TG_B1_Main {
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Souhaitez vous tester un graphe ? ");
		Scanner scan = new Scanner(System.in);
		String reponse = scan.nextLine();
		
		if(!reponse.equals("oui")) {
			System.out.println("Fin du programme.");
			return;
		}
		
		L3NEW_TG_B1_TraitementGraphes tg = new L3NEW_TG_B1_TraitementGraphes(); 
		
		
		while(reponse.equals("oui")) { 
			
			System.out.println("Choisissez un graphe à traiter :");
			
			scan = new Scanner(System.in);
			int numg = scan.nextInt();
			
			if(!tg.getListeGraphes().containsKey(numg)) { //Si le graphe n'est pas déja en mémoire, l'extraire du fichier
				String path = System.getProperty("user.dir");
				tg.extraitGraphe(path + "/L3NEW-TG-B1-g" + numg + ".txt", numg );
			}
			
			
			System.out.println("Matrices du graphe :");
			tg.getListeGraphes().get(numg).afficheAdjacence();
			tg.getListeGraphes().get(numg).afficheMatricePoids();
			
			//tg.hasCircuit(tg.getListeGraphes().get(numg));
			
			tg.afficheRang(tg.getListeGraphes().get(numg));
			
			
			if(tg.hasUnPointEntree(tg.getListeGraphes().get(numg)) //Verifie les conditions d'ordonnancement
					&& tg.hasUnPointSortie(tg.getListeGraphes().get(numg)) 
					&& !tg.hasCircuit(tg.getListeGraphes().get(numg)) 
					&& tg.valeursidentique(tg.getListeGraphes().get(numg))
					&& tg.verifPointEntree(tg.getListeGraphes().get(numg)) 
					&& tg.hasNoValNeg(tg.getListeGraphes().get(numg))) { 
				
				System.out.println('\n' + "Ce graphe est un graphe d'ordonnancement" + '\n');
				
				System.out.println("Calendrier au plus tôt : " + Arrays.toString(tg.calendrierAuPlusTot(tg.getListeGraphes().get(numg))));
				System.out.println("Calendrier au plus tard : " + Arrays.toString(tg.calendrierAuPlusTard(tg.getListeGraphes().get(numg))));
				System.out.println("Marges totales : " + Arrays.toString(tg.margeTotale(tg.calendrierAuPlusTot(tg.getListeGraphes().get(numg)), tg.calendrierAuPlusTard(tg.getListeGraphes().get(numg)))));
				System.out.println("Marges libres : " + Arrays.toString(tg.margeLibre(tg.getListeGraphes().get(numg))));
				
			}
				
			if(!(tg.hasUnPointEntree(tg.getListeGraphes().get(numg)) 
					&& tg.hasUnPointSortie(tg.getListeGraphes().get(numg)) 
					&& !tg.hasCircuit(tg.getListeGraphes().get(numg))
					&& tg.valeursidentique(tg.getListeGraphes().get(numg))
					&& tg.verifPointEntree(tg.getListeGraphes().get(numg)) 
					&& tg.hasNoValNeg(tg.getListeGraphes().get(numg)))){
				
				System.out.println('\n' + "Ce graphe n'est pas un graphe d'ordonnancement car :");
			
				if(!tg.hasUnPointEntree(tg.getListeGraphes().get(numg)))
					System.out.println("   L'entrée n'est pas unique");
				
				if(!tg.hasUnPointSortie(tg.getListeGraphes().get(numg)))
					System.out.println("   La sortie n'est pas unique");
				
				if(tg.hasCircuit(tg.getListeGraphes().get(numg)))
					System.out.println("   Le graphe contient au moins un circuit");
				
				if(!tg.valeursidentique(tg.getListeGraphes().get(numg)))
					System.out.println("   Certains arcs incidents vers l'exterieur à un sommet ont  des valeurs differentes");
				
				if(!tg.verifPointEntree(tg.getListeGraphes().get(numg)))
					System.out.println("   Les arcs incidents vers l'exterieur au point d'entrée ne sont pas tous nuls");
				
				if(!tg.hasNoValNeg(tg.getListeGraphes().get(numg)))
					System.out.println("   Le graphe contient des arcs à valeur negative");
			}
			
			
				
			System.out.println('\n' + "Souhaitez vous tester un nouveau graphe ? ");
			scan = new Scanner(System.in);
			reponse = scan.nextLine();
				
		}
		System.out.println("Fin du programme.");
		return;
	}

}
