package map;

import javax.swing.JPanel;

import data.District;

public class Grid extends JPanel{
	
	int Taille = 5;
	District[][] grid = new District[Taille][Taille];
	
	public Grid(){
		for(int lig=0;lig<Taille;lig++){
    		for(int col=0;col<Taille;col++){
    			grid[lig][col] = null;
    		}
    	}
	}
	
	public void updateGrid(int x,int y,District dist){
		
		//conversion pixel vers case du tab
		
		
	}

}
