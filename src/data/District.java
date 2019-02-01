package data;

public class District {

	private Station station;
	private int gain;
	private int cost;
	private int people;
	private int satisfaction;
	

	public District(int gain,int cost) {
		this.gain = gain;
		this.cost = cost;
		station = null; 
	}
}
