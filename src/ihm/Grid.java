package ihm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import data.District;

public class Grid extends JPanel{
	
	public JPanel myGridScreen;
	int Taille = 5;
	District[][] grid = new District[Taille][Taille];
	public Image img;
	private JPanel districtPanel;
	
	
	public Grid(JPanel districtPanel){
		
		this.districtPanel = districtPanel;
		
		try {
			img = ImageIO.read(new File("src/image/land.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int lig=0;lig<Taille;lig++){
			for(int col=0;col<Taille;col++){
				grid[lig][col] = null;
			}
		}
		setBounds(10, 10, 610, 610);
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
                int y=e.getY();
                //System.out.println(x+","+y); // Coordonnees cliquees
                int caseX = getCase(x, 5); // Retourne la case cliquee en X
                int caseY = getCase(y, 5); // Retourne la case cliquee en Y
                System.out.println(caseX+","+caseY); // Affichage
                districtPanel.setVisible(true);
			}
			
		});
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		int uniteX = getWidth()/5;
		int uniteY = getHeight()/5;
		//Dessin de la grille.
		for(int i=0; i<6; i++){
			g.drawLine(uniteX*i, 0, uniteX*i, getHeight());
			g.drawLine(0, uniteY*i, getWidth(), uniteY*i);
		}
		//Affichage des images de terrain nu sur les cases vides.
		for(int x=0; x<5; x++){
			for(int y=0; y<5; y++){
				g.drawImage(img, 1+(122*x), 1+(122*y), 120, 120, this);
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
	
}
