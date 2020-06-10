package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import application.Coordonnees;
import application.Globe;
import application.Zone;

public class CSVReader {
	
	
	/**
	* Fonction qui lit le CSV et charge les données dans le globe passé en paramètre.
	* 
	* @param  String  Chemin du fichier CSV que l'on va lire.
	* @param  Globe   Globe dans lequel on va charger les données.
	*/
	public static void parse (String path, Globe globe) throws Exception{ 
		
		ArrayList<Integer> listeAnnee=new ArrayList<Integer>();
		int i=0;
		Float anomalie=0.0f;
		FileReader file = new FileReader(path);
		BufferedReader bufRead = new BufferedReader(file);
		

		String line = bufRead.readLine();
		while ( line != null) {
		   	
		   	String[] array = line.split(",");
		   	if (i==0) {
		   		for(int j=2;j<array.length;j++) {
		   			listeAnnee.add(Integer.parseInt(array[j].substring(1,5)));				   			
		   		}
		   		i=1;
		   	}else {
		   		Coordonnees coord =new Coordonnees(Integer.parseInt(array[0]),Integer.parseInt(array[1]));
		   		Zone zone=new Zone();
		   		for(int j=2;j<array.length;j++) {
		   			if(array[j].equals("NA")) {
		   				anomalie=Float.NaN;
		   			}
		   			else {
		   				anomalie=Float.parseFloat(array[j]);
		   			}
		   			zone.ajouterAnomalie(listeAnnee.get(j-2),anomalie);
		   			
		   			
		   		}
		   		globe.ajouterZone(coord, zone);
		   	}   		
		    line = bufRead.readLine();
		}

		bufRead.close();
		file.close();
		
		
	}
}
