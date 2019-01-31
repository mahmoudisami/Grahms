package moteur;

public class People {
	
	private int satisfaction;
	private int nbr;
	private District workPlace;
	private District home;
	private District relaxationPlace;
	
	public People() {
		satisfaction = 50;  // 1 = Mauvais, 100 = Bien
		nbr = 10; // Nombre d'habitants de départ a définir
	}
}
