package moteur;

import data.*;

public class TestCalculator {

	
	public static void main(String[] args) {
		int size = 5;
		District[][] grid = new District[size][size];
		//FinancialCalculator calc = new FinancialCalculator();
		DistrictLinker distLink = new DistrictLinker();
		
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				grid[i][j] = null;
			}
		}
		
		grid[0][0] = new Services();
		grid[1][0] = new Residential();
		grid[1][1] = new District(1,1);
		grid[2][2] = new Commercial();
		
		Line line1 = new Line(grid[0][0],grid[1][0],1);
		Line line2 = new Line(grid[0][0],grid[2][2],3);
		Line line3 = new Line(grid[1][1],grid[2][2],2);
		
		distLink.linkDistrict(line1);
		distLink.linkDistrict(line2);
		distLink.linkDistrict(line3);
		
		System.out.println("------------------------------------------");
		District dist = grid[0][0];
		for(int i = 0; i<dist.getAccessibleDistrict().size();i++) {
			System.out.println(dist.getAccessibleDistrict().get(i).getDistrict().toString() + "  " + dist.getAccessibleDistrict().get(i).getDistance());	
		}
		
	}

}
