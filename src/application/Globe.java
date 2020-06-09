package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import data.CSVReader;

public class Globe {

	
	private LinkedHashMap<Coordonnees, Zone> listeZone;
	
	
	public Globe() {
		listeZone=new LinkedHashMap<Coordonnees,Zone>();
	}
	public HashMap<Coordonnees, Zone> getListeZone(){
		return listeZone;
	}
	
	/**
	* Charge les donnée du fichier CSV dans l'instance de Globe courante.
	* 
	* @param  String  Chemin du fichier CSV que l'on veut charger.
	* @throws Exception
	*/
	public void chargerCSV(String path) throws Exception {
		CSVReader.parse(path, this);
	}
	
	/**
	* Fonction qui ajoute une paire clé valeur de Coordonnées associés à une Zone.
	* 
	* @param  Coordonnees  Coordonnées de la Zone que l'on va ajouter.
	* @param  Zone  zone que l'on va ajouter dans le globe.
	*/
	
	
	public void ajouterZone(Coordonnees coord,Zone zone) {
		listeZone.put(coord, zone);
	}
	
	/**
	* Fonction qui retourne la liste des anomalies par année d'une zone.
	* 
	* @param  Coordonnees  Coordonnées de la Zone dont on souhaite avoir la liste d'anomalie.
	* @return LinkedHashMap<Integer,Float>    Map qui contient l'année associée à une anomalie de température
	*/
	
	public LinkedHashMap<Integer,Float> getValueZone(Coordonnees cor){

		for( Map.Entry entry : listeZone.entrySet() ) {
			if(cor.equalsCoor((Coordonnees)entry.getKey())) {
				return listeZone.get(entry.getKey()).getListeAnomalies();
			}
		}
		return null;
		
	}
	
	/**
	* Fonction qui recherche parmi l'ensemble des données les anomalies maximale et minimale.
	* 
	* @return ArrayList<Float>    Liste qui contient l'anomalie maximale et l'anomalie minimale de tout le fichier CSV
	*/
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
	
	/**
	* Fonction qui retourne la liste des anomalies pour chacune des zones du globe à une année donnée.
	* 
	* @param  int  entier qui représente l'année recherchée
	* @return LinkedHashMap<Coordonnees,Float>    Map qui contient les anomalies recherchée associée à leurs zones respectives
	*/
	

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
	
	/**
	* Fonction qui retourne l'anomalie pour une zone donnée à une année donnée
	* 
	* @param  Coordonnees  Coordonnées de la Zone que l'on recherche
	* @param  int  l'année pour laquelle on effectue la recherche
	* @return Float    Anomalie recherchée
	*/
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
