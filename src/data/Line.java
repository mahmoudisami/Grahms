package data;

public class Line {

	private Station firstStation;
	private Station secondStation;
	private int distance;
	
	public Line(Station s1, Station s2, int distance) {
		firstStation = s1;
		secondStation = s2;
		this.distance = distance;
	}
	
	public int getDistance() {
		return distance;
	}

	public Station getFirstStation() {
		return firstStation;
	}

	public Station getSecondStation() {
		return secondStation;
	}
	
}
