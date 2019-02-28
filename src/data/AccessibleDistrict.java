package data;

public class AccessibleDistrict {

	private District district;
	private int distance;
	
	public AccessibleDistrict(District district, int distance){
		this.distance = distance;
		this.district = district;
	}

	public District getDistrict() {
		return district;
	}

	public int getDistance() {
		return distance;
	}
}
