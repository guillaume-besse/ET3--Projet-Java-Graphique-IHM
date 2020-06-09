package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import data.CSVReader;

public class Globe {

	//private HashMap<Coordonnees, Zone> listeZone;
	private LinkedHashMap<Coordonnees, Zone> listeZone;
	
	
	public Globe() {
		listeZone=new LinkedHashMap<Coordonnees,Zone>();
	}
	public HashMap<Coordonnees, Zone> getListeZone(){
		return listeZone;
	}
	
	
	public void chargerCSV(String path) throws Exception {
		CSVReader.parse(path, this);
	}
	
	
	public void ajouterZone(Coordonnees coord,Zone zone) {
		listeZone.put(coord, zone);
	}
	
	public LinkedHashMap<Integer,Float> getValueZone(Coordonnees cor){

		for( Map.Entry entry : listeZone.entrySet() ) {
			if(cor.equalsCoor((Coordonnees)entry.getKey())) {
				return listeZone.get(entry.getKey()).getListeAnomalies();
			}
		}
		return null;
		
	}
	public ArrayList<Float> getMaxMin(){
		Float max =0.0f;
		Float min =0.0f;
		ArrayList<Float> maxMin=new ArrayList<Float>();
		for( Map.Entry entryGlobe : listeZone.entrySet() ) {
			for(Map.Entry entryZone : listeZone.get(entryGlobe.getKey()).getListeAnomalies().entrySet()) {
				if(listeZone.get(entryGlobe.getKey()).getListeAnomalies().get(entryZone.getKey())>max ) {
					max=listeZone.get(entryGlobe.getKey()).getListeAnomalies().get(entryZone.getKey());
				}
				if(listeZone.get(entryGlobe.getKey()).getListeAnomalies().get(entryZone.getKey())<min ) {
					min=listeZone.get(entryGlobe.getKey()).getListeAnomalies().get(entryZone.getKey());
				}
			}
		}
		maxMin.add(max);
		maxMin.add(min);
		return maxMin;
	}
	

	public LinkedHashMap<Coordonnees,Float> getValue(int annee){
		LinkedHashMap<Coordonnees, Float> hash=new LinkedHashMap <Coordonnees,Float>();
		for( Map.Entry entryGlobe : listeZone.entrySet() ) {
			for(Map.Entry entryZone : listeZone.get(entryGlobe.getKey()).getListeAnomalies().entrySet()) {
				
				if(annee==(int)entryZone.getKey()){
					Coordonnees cor=new Coordonnees(((Coordonnees)entryGlobe.getKey()).getLat(),((Coordonnees)entryGlobe.getKey()).getLong());
					Float anomalie=(Float)entryZone.getValue();
					hash.put(cor, anomalie);
				}
			}	
		}
		
		
		return hash;
	}
	
	public Float getValue(Coordonnees cor, int annee) {
		for( Map.Entry entryGlobe : listeZone.entrySet() ) {
			if(cor.equalsCoor((Coordonnees)entryGlobe.getKey())) {
				for(Map.Entry entryZone : listeZone.get(entryGlobe.getKey()).getListeAnomalies().entrySet()) {
					if(annee==(int)entryZone.getKey()){
						return (Float)entryZone.getValue();
					}
				}
			}
		}
		return null;
	}
}
