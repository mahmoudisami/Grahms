package data;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		District dist1 = new Residential();
		District dist2 = new Commercial(); 
		District dist3 = new Services(); 
		
		System.out.println("Gain resid: "+dist1.getCost()+"\nCout resid: "+dist1.getGain()+"\n"+"Cout maintenance: "+dist1.getMaintenanceCost());
		System.out.println("Chemin img resid: "+dist1.getImg()+"\n");
		System.out.println("Gain comm: "+dist2.getCost()+"\nCout comm: "+dist2.getGain()+"\n"+"Cout maintenance: "+dist2.getMaintenanceCost());
		System.out.println("Chemin img resid: "+dist2.getImg()+"\n");
		System.out.println("Gain serv: "+dist3.getCost()+"\nCout serv: "+dist3.getGain()+"\n"+"Cout maintenance: "+dist3.getMaintenanceCost());
		System.out.println("Chemin img serv: "+dist3.getImg()+"\n");

		}

}
