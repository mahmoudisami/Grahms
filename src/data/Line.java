package data;

public class Line {


	private Station firstStation;
	private Station secondStation;
	private int visitedDistrict;
	
	
	
	public void setFirstDistrict(District dist) {
		firstStation = dist.getStation();
	}
	
	public void setSecondDistrict(District dist) {
		secondStation = dist.getStation();
	}
	
}
