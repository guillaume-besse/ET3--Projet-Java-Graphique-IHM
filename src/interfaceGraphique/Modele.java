package interfaceGraphique;

import application.Globe;

public class Modele {

	private Globe globe;
	private Integer annee;
	private boolean animation;
	private boolean rectangle;
	private Integer vitesse;
	
	
	
	public Modele(String path) throws Exception {
		animation=false;
		globe=new Globe();
		globe.chargerCSV(path);
		annee=1880;
		rectangle=true;
		vitesse=1;
	}
	
	public Globe getGlobe() {
		return globe;
	}
	public Integer getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee=annee;
	}
	public boolean getAnimation() {
		return animation;
	}
	public void setAnimation(boolean anim) {
		this.animation=anim;
	}
	public boolean getRectangle() {
		return rectangle;
	}
	public void setRectangle(boolean rect) {
		rectangle=rect;
	}
	public void augmenteVitesse() {
		vitesse=vitesse*2;
	}
	public void diminueVitesse() {
		if(vitesse>1) {
			vitesse=vitesse/2;
		}
	}
	public int getVitesse() {
		return vitesse;
	}
	
	
	
}
