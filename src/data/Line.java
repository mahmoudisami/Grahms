package data;

public class Line {

	private District firstDistrict;
	private District secondDistrict;
	private int distance;
	
	public Line(District d1, District d2, int distance) {
		firstDistrict = d1;
		secondDistrict = d2;
		this.distance = distance;
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
}
