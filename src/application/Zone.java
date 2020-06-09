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
	public void ajouterAnomalie(int annee, Float anom) {
		listeAnomalies.put(annee, anom);
	}
	
}
