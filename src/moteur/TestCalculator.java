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
		
		/*grid[1][1] = new Residential();
		grid[1][2] = new Commercial();
		grid[1][3] = new Services();
		
		Line line1 = new Line(grid[1][1],grid[1][2],1,true, null);
		Line line2 = new Line(grid[1][1],grid[1][3],3,true, null);
		
		distLink.linkDistrict(line1);
		distLink.linkDistrict(line2);
		*/
		grid[0][0] = new Services();
		grid[1][0] = new Residential();
		grid[1][1] = new District(1);
		grid[2][2] = new Commercial();
		grid[3][3] = new Residential();
		grid[4][4] = new Commercial();
		
		Line line1 = new Line(grid[0][0],grid[1][0],1,true, null);
		Line line2 = new Line(grid[0][0],grid[2][2],3,true, null);
		//Line line3 = new Line(grid[1][1],grid[2][2],2,true, null);
		//Line line4 = new Line(grid[2][2],grid[3][3],10,true, null);
		//Line line5 = new Line(grid[4][4],grid[3][3],1,true, null);
		
		distLink.linkDistrict(line1);
		distLink.linkDistrict(line2);
		//distLink.linkDistrict(line3);
		//distLink.linkDistrict(line4);
		//distLink.linkDistrict(line5);
		
		District dist = grid[1][0];
		for(int i = 0; i<dist.getAccessibleDistrict().size();i++) {
			System.out.println(dist.getAccessibleDistrict().get(i).getDistrict().toString() + "  " + dist.getAccessibleDistrict().get(i).getDistance());	
		}
		
	}

}
