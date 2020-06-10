package application;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Zone {

	private LinkedHashMap<Integer,Float> listeAnomalies;
	
	public Zone() {
		listeAnomalies=new LinkedHashMap<Integer, Float>();
	}
	public LinkedHashMap<Integer,Float> getListeAnomalies() {
		return listeAnomalies;
	}
	
	/**
	* Fonction qui ajoute une paire cl� valeur dans la Map ListeAnomalie dont la cl� est l'ann�e et la valeur est l'anomalie
	* 
	* @param  int  L'ann�e que l'on rentre dans la table.
	* @param Float    anomalie que l'on rentre dans la table.
	*/
	public void ajouterAnomalie(int annee, Float anom) {
		listeAnomalies.put(annee, anom);
	}
	
}
