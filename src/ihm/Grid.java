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
	int gridSize = 5;
	District[][] grid = new District[gridSize][gridSize];
	public Image img;
	int caseX, caseY, caseWidth;
	private JPanel districtPanel;
	private JPanel subwayPanel;
	
	
	public Grid(JPanel districtPanel, JPanel subwayPanel){
		this.districtPanel = districtPanel;
		this.subwayPanel = subwayPanel;
		
		try {
			img = ImageIO.read(new File("src/image/land.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int lig=0;lig<gridSize;lig++){
			for(int col=0;col<gridSize;col++){
				grid[lig][col] = null;
			}
		}
		setBounds(10, 10, 610, 610);
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
                int y=e.getY();
                caseX = getCase(x, 5); // Retourne la case cliquee en X
                caseY = getCase(y, 5); // Retourne la case cliquee en Y
                System.out.println(caseX+","+caseY); // Affichage
                if(grid[caseX][caseY]== null){
                	districtPanel.setVisible(true);
                }else{
                	districtPanel.setVisible(false);
                	subwayPanel.setVisible(true);
                }
			}
		});
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		caseWidth = getWidth()/gridSize;
		
		//Dessin des lignes de la grille
		for(int i=0; i<6; i++){
			g.drawLine(caseWidth*i, 0, caseWidth*i, getHeight());
			g.drawLine(0, caseWidth*i, getWidth(), caseWidth*i);
		}
		
		//Affichage des images de terrain nu sur les cases vides
		for(int x=0; x<gridSize; x++){
			for(int y=0; y<gridSize; y++){
				if (grid[x][y] == null) {
					g.drawImage(img, 1+(caseWidth*x), 1+(caseWidth*y), 120, 120, this);
				} else {
					g.drawImage(grid[x][y].getImg(), 1+(caseWidth*x), 1+(caseWidth*y), 120, 120, this);
				}
			}
		}
	}
	
	public int getCase(int coordinate, int numberOfSquare) {
        int divider = 610 / numberOfSquare;
        int position = (int) Math.floor(coordinate / divider);
        return position;
    }
	
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
}
