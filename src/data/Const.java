package data; 

public class Const {	
	
	/***********************      MONEY        ********************************/
	public final static int START_MONEY = 5000; // Argent de depart
	
	/***********************      STATIONS        *****************************/
	public final static int MAINTENANCE_COST_STATION = 100; // Cout chaque semaine/mois a definir 
	public final static int CONSTRUCTION_COST_STATION = 200; // Cout de construction a definir
	
	/***********************      LINE        *********************************/ 
	public final static int MAINTENANCE_COST_LINE = 20; // Cout chaque semaine/mois a definir PAR quartier traverse
	public final static int CONSTRUCTION_COST_LINE = 10; // Cout de construction a definir PAR quartier traverse

	/***********************      COMMERCIAL & SERVICES        ****************/
	public final static double GAIN_CS = 0.8; // Gain par semaine ou par mois a definir
	
	/***********************      RESIDENTIEL        **************************/
	public final static double GAIN_RES = 0.2; // Gain par semaine ou par mois a definir
	public final static int MEDIUM_SIZE_RES = 300; // Nbr d'habitants pour changer la taille en médium
	public final static int HIGH_SIZE_RES = 600; // Nbr d'habitants pour changer la taille en high
	public final static int SATISFACTION_THRESHOLD = 90; //Seuil de satisfaction pour évolution
	public final static int UPGRADE_PRICE = 5000; //Seuil de satisfaction pour évolution
	public final static int CAPACITY = 200;
	
	/***********************      SERVICES        *****************************/	
	public final static int MAINTENANCE_COST_SERV = 300; // Cout chaque semaine/mois a definir
	public final static int COST_SERV = 300; // Cout de construction
}
