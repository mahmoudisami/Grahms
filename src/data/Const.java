package data; 

public class Const {	
	
	/***********************      MONEY        ********************************/
	public final static int START_MONEY = 1000; // Argent de départ
	
	/***********************      STATIONS        *****************************/
	public final static int MAINTENANCE_COST_STATION = 100; // Cout chaque semaine/mois à définir 
	public final static int CONSTRUCTION_COST_STATION = 200; // Cout de construction à définir
	
	/***********************      LINE        *********************************/
	public final static int MAINTENANCE_COST_LINE = 100; // Cout chaque semaine/mois à définir PAR quartier traversé
	public final static int CONSTRUCTION_COST_LINE = 200; // Cout de construction à définir PAR quartier traversé

	/***********************      COMMERCIAL        ***************************/
	public final static int GAIN_COM = 300; // Gain par semaine ou par mois à définir
	
	/***********************      RESIDENTIEL        **************************/
	public final static int GAIN_RES = 100; // Gain par semaine ou par mois à définir
	
	/***********************      SERVICES        *****************************/	
	public final static int MAINTENANCE_COST_SERV = 100; // Cout chaque semaine/mois à définir
	public final static int GAIN_SERV = 400; // Gain par semaine ou par mois à définir
	public final static int COST_SERV = 300; // Cout de construction
}
