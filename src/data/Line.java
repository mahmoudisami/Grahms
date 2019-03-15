package data;

import java.util.ArrayList;

import moteur.DistrictLinker;

public class Line {

	private District firstDistrict;
	private District secondDistrict;
	private int distance;
	private boolean isVisible;
	private ArrayList<Coordinates> coo;
	
	public Line(District d1, District d2, int distance, boolean isVisible, ArrayList<Coordinates> coo) {
		firstDistrict = d1;
		secondDistrict = d2;
		this.distance = distance;
		this.isVisible = isVisible;
		this.coo = coo;
		d1.addLine(this);
		d2.addLine(this);
	}
	
	public int getDistance() {
		return distance;
	}

	public District getFirstDistrict() {
		return firstDistrict;
	}

	public District getSecondDistrict() {
		return secondDistrict;
	}
	
	public Station getFirstStation() {
		return firstDistrict.getStation();
	}
	
	public Station getSecondStation() {
		return secondDistrict.getStation();
	}
	
	public boolean isVisible() {
		return isVisible;
	}
}
