package data;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		District dist1 = new Residential();
		District dist2 = new Commercial(); 
		District dist3 = new Services(); 
		
		System.out.println("Gain residentiel: "+dist1.getCost()+"\nCout construction residentiel: "+dist1.getGain()+"\n"+"Cout maintenance residentiel: "+dist1.getMaintenanceCost());
		System.out.println("Chemin img residentiel: "+dist1.getImg()+"\n");
		System.out.println("Gain commercial: "+dist2.getCost()+"\nCout construction commercial: "+dist2.getGain()+"\n"+"Cout maintenance commercial: "+dist2.getMaintenanceCost());
		System.out.println("Chemin img commercial: "+dist2.getImg()+"\n");
		System.out.println("Gain services: "+dist3.getCost()+"\nCout construction services: "+dist3.getGain()+"\n"+"Cout services: "+dist3.getMaintenanceCost());
		System.out.println("Chemin img services: "+dist3.getImg()+"\n");
		

	}

}
