package moteur;

public class District {

	private Station station;
	private int gain;
	private int cost;

	public District(int gain,int cost) {
		this.gain = gain;
		this.cost = cost;
		station = null;
	}
}
