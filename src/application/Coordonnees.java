package application;

public class Coordonnees {

	private Integer lattitude;
	private Integer longitude;
	
	public Coordonnees(int lat,int longi) {
		lattitude=lat;
		longitude=longi;
	}
	public int getLat() {
		return lattitude;
	}
	public int getLong() {
		return longitude;
	}
	
	public boolean equalsCoor(Coordonnees cor) {
		
		if(this.lattitude.equals(cor.getLat()) && this.longitude.equals(cor.getLong())) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		String res=new String(lattitude+", "+longitude);
		return res;
	}
}
