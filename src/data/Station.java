package data;

public class Station {
	
	private int constructionCost;
	private int maintenanceCost;
	
	public Station() {
		constructionCost = Const.CONSTRUCTION_COST_STATION;
		maintenanceCost = Const.MAINTENANCE_COST_STATION;
	}
	
	public int getMaintenanceCost() {
		return maintenanceCost;
	}
	
	public int getConstructionCost() {
		return constructionCost;
	}
}
