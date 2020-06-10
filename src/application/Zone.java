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
	* Fonction qui ajoute une paire clé valeur dans la Map ListeAnomalie dont la clé est l'année et la valeur est l'anomalie
	* 
	* @param  int  L'année que l'on rentre dans la table.
	* @param Float    anomalie que l'on rentre dans la table.
	*/
	public void ajouterAnomalie(int annee, Float anom) {
		listeAnomalies.put(annee, anom);
	}
	
}
