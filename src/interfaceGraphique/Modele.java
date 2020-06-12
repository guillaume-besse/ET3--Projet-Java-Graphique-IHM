package interfaceGraphique;

import application.Globe;

public class Modele {

	private Globe globe;
	private Integer annee;
	
	
	
	public Modele(String path) throws Exception {
		
		globe=new Globe();
		globe.chargerCSV(path);
		annee=1880;
	}
	
	public Globe getGlobe() {
		return globe;
	}
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee=annee;
	}
	
	
	
}
