package ihm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import data.District;

public class Grid extends JPanel{
	
	public JPanel myGridScreen;
	int width = 18;
	int height = 12;
	int sizeScreenX = 900;
	int sizeScreenY = 600;
	
	District[][] grid = new District[width][height];
	public Image img;
	int caseX, caseY, caseWidth;
	private JPanel districtPanel;
	private JPanel subwayPanel;
	private JPanel infoDistrictPanel;
	
	
	public Grid(JPanel districtPanel, JPanel subwayPanel, JPanel infoDistrictPanel){
		this.districtPanel = districtPanel;
		this.subwayPanel = subwayPanel;
		this.infoDistrictPanel = infoDistrictPanel;
		
		try {
			img = ImageIO.read(new File("src/image/land.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int lig=0;lig<width-10;lig++){
			for(int col=0;col<height-10;col++){
				grid[lig][col] = null;
			}
		}
		setBounds(10, 10, 900, 600);
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
                int y=e.getY();
                // Verify that the coordinates clicked are on map
                if(x < sizeScreenX && y < sizeScreenY ) {
                	
	                caseX = getCaseX(x, width); // Return the X of clicked case
	                caseY = getCaseY(y, height); // Return the Y of clicked case
	                System.out.println(caseX+","+caseY); // Affichage
	                neighbourCalculator(caseX,caseY);
	                if(grid[caseX][caseY]== null){
	                	subwayPanel.setVisible(false);
	                	infoDistrictPanel.setVisible(false);
	                	districtPanel.setVisible(true);
	                }else{
	                	districtPanel.setVisible(false);
	                	subwayPanel.setVisible(true);
	                	infoDistrictPanel.setVisible(true);
	                }
	                
                }
			}
		});
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		caseWidth = getHeight()/height;
	
		//Dessin des lignes de la grille
		for(int i=0; i<width+1; i++){
			g.drawLine(caseWidth*i, 0, caseWidth*i, sizeScreenY);
			g.drawLine(0, caseWidth*i, sizeScreenX, caseWidth*i);
		}
	
		//Affichage des images de terrain nu sur les cases vides
		int sizeX = sizeScreenX/width;
		int sizeY = sizeScreenY/height;
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				if (grid[x][y] == null) {
					g.drawImage(img, 1+(caseWidth*x), 1+(caseWidth*y), sizeX-1, sizeY-1, this);
				}else {
					g.drawImage(grid[x][y].getImg(), 1+(caseWidth*x), 1+(caseWidth*y), sizeX-1, sizeY-1, this);
				}
			}
		}
	}
	
	public int getCaseX(int coordinateX, int numberOfSquare) {
        int divider = sizeScreenX / numberOfSquare;
        int position = (int) Math.floor(coordinateX / divider);
        return position;
    }
	public int getCaseY(int coordinateY, int numberOfSquare) {
        int divider = sizeScreenY / numberOfSquare;
        int position = (int) Math.floor(coordinateY / divider);
        return position;
    }
	public void neighbourCalculator(int a, int b) {
		int calNbRes = 0;
		int calNbServ = 0;
		int calNbShop = 0;
		int calNbStation = 0;
		int i = a;
		int j = b;
			//for(int i=0; i<nbrLine; i++) { 
				//for(int j=0; j<nbrRow; j++) {
					if(grid[i][j] != null) {
						for(int k= i-1;k<=i+1;k++) {
							if(k>=0 && k<=width && j-1>=0 && j-1<height ) {
								if((grid[k][j-1] != null && grid[k][j-1].isResidential())) {
									calNbRes++;
								}
								if((grid[k][j-1] != null && grid[k][j-1].isStation() && grid[k][j-1].isResidential())) {
									calNbStation++;
								}
							}
						}
						for(int l= i-1;l<=i+1;l++) {
							if(l>=0 && l<=width && j>=0 && j<height ) {
								if(grid[l][j] != null && grid[l][j] != grid[a][b] && grid[l][j].isResidential()) {
									calNbRes++;
								}
								if(grid[l][j] != null && grid[l][j] != grid[a][b] && grid[l][j].isStation() && grid[l][j].isResidential()) {
									calNbStation++;
								}
							}
						}
						for(int m= i-1;m<= i+1;m++) {
							if(m>=0 && m<=width && j+1>=0 && j+1<height ) {
								if(grid[m][j+1] != null && grid[m][j+1].isResidential()) {
									calNbRes++;
								}
								if(grid[m][j+1] != null && grid[m][j+1].isStation() && grid[m][j+1].isResidential()) {
									calNbStation++;
								}
							}
						}
						System.out.println("grid["+i+"]["+j+"] got "+calNbRes+" residential neighborhood(s) nearby");
						System.out.println("grid["+i+"]["+j+"] got "+calNbStation+" station(s) nearby");
						
						if(grid[i][j].isStation()) {
							System.out.println("grid["+i+"]["+j+"] got his own station ");
						}
					}
					
				}
			//}
		//}
	public void setGridscreen(JPanel contentPane){
		myGridScreen = contentPane;
	}

	public int getCoordsX() {
		return caseX;
	}
	
	public int getCoordsY() {
		return caseY;
	}
	
	public District[][] getMapTab() {
		return grid;
	}
	public District[][] setMapTab(int lig, int col, District newDistrict){
		grid[lig][col] = newDistrict;
		return grid;
	}
}
