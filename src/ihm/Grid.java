package ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import data.Const;
import data.Coordinates;
import data.District;
import data.Line;
import data.Money;
import moteur.DistrictLinker;

public class Grid extends JPanel{
	
	public JPanel myGridScreen;
	public static int width = 18;
	public static int height = 12;
	public int sizeScreenX = 900;
	public int sizeScreenY = 600;
	
	public DistrictLinker districtLinker = new DistrictLinker();
	boolean addLineBool = false;
	boolean addLineBoolChangedToTrue = false;	//TRUE si on viens de passer AddLineBool de FALSE à TRUE, sinon FALSE
	ArrayList<Coordinates> lineCoo;
	ArrayList<Line> allLines = new ArrayList<Line>();
	private Money money;
	
	public static District[][] grid = new District[width][height];
	public Image img;
	int caseX, caseY, caseWidth;
	int previousCaseX, previousCaseY;
	private JPanel districtPanel;
	private JPanel subwayPanel;		//Panel for district without station
	private JPanel subwayPanel2;	//Panel for district with station
	private JPanel infoDistrictPanel;
	
	
	public Grid(JPanel districtPanel, JPanel subwayPanel, JPanel subwayPanel2, JPanel infoDistrictPanel,Money money){
		this.districtPanel = districtPanel;
		this.subwayPanel = subwayPanel;
		this.subwayPanel2 = subwayPanel2;
		this.infoDistrictPanel = infoDistrictPanel;
		this.money = money;
		
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
		JProgressBar bar_SatisfactionDistrict = new JProgressBar();
		bar_SatisfactionDistrict.setBounds(113, 83, 138, 20);
		
		JLabel lblValDistrictPop = new JLabel();
		lblValDistrictPop.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblValDistrictPop.setBounds(113, 38, 83, 20);
		
		addMouseListener(new MouseAdapter(){	
			public void mouseClicked(MouseEvent e) {
				boolean crossBool = false;		//False par defaut, True si on click sur une case deja ajoutee dans l'arrayList
				//MouseEvent Branche classique.
				int happinessLevel = 0;
				int popNumber;
				String popNumberLb;
				
        		
				if ( getAddLineBool() == false){
					System.out.println("Branche classique");
					int x=e.getX();
	                int y=e.getY();
	                // Verify that the coordinates clicked are on map
	                if(x < sizeScreenX && y < sizeScreenY ) {
	                	previousCaseX = caseX;
	                	previousCaseY = caseY;
		                caseX = getCaseX(x, width); // Return the X of clicked case
		                caseY = getCaseY(y, height); // Return the Y of clicked case
		                System.out.println("Previous: "+previousCaseX+","+previousCaseY);
		                System.out.println("Current: "+caseX+","+caseY); // Affichage
		                drawCaseSelected(caseX,caseX);
		                neighbourCalculator(caseX,caseY);
		                if(grid[caseX][caseY]== null){
		                	subwayPanel.setVisible(false);
		                	subwayPanel2.setVisible(false);
		                	infoDistrictPanel.setVisible(false);
		                	districtPanel.setVisible(true);
		                }else{
		                	if(grid[caseX][caseY].isStation()){
		                		districtPanel.setVisible(false);
			                	subwayPanel2.setVisible(true);
			                	subwayPanel.setVisible(false);
		                	}else{
		                		districtPanel.setVisible(false);
			                	subwayPanel.setVisible(true);
			                	subwayPanel2.setVisible(false);
		                	}
		                	
		                	if(grid[caseX][caseY].isResidential()) { //If residential = show panel for district
		                		popNumber = grid[caseX][caseY].getActualPeople();
		                		popNumberLb = Integer.toString(popNumber);
		                		lblValDistrictPop.setText(popNumberLb);
		                		infoDistrictPanel.add(lblValDistrictPop);
		                		
			            		happinessLevel = grid[caseX][caseY].getSatisfaction();
			            		bar_SatisfactionDistrict.setValue(happinessLevel);
			            		bar_SatisfactionDistrict.setStringPainted(true);
			            		infoDistrictPanel.add(bar_SatisfactionDistrict);
			            		infoDistrictPanel.setVisible(true);
		                	}else {
		                		
		                		infoDistrictPanel.setVisible(false);
		                	}
		                	
		                }
	                }
	            // MouseEvent Branche pour la création de ligne.
	            }else{
	            	if(addLineBoolChangedToTrue == true){
	            		lineCoo = new ArrayList<Coordinates>();
	            		Coordinates coo = new Coordinates(caseX, caseY);
	            		lineCoo.add(coo);
	            		setAddLineBoolChangedToTrue(false);
	            	}
	            	System.out.println("Branche addLine");
					int x=e.getX();
					int y=e.getY();
					if(x < sizeScreenX && y < sizeScreenY ) {
						previousCaseX = caseX;
	                	previousCaseY = caseY;
		                caseX = getCaseX(x, width); // Return the X of clicked case
		                caseY = getCaseY(y, height); // Return the Y of clicked case
		                System.out.println("Previous: "+previousCaseX+","+previousCaseY);
		                System.out.println("Current: "+caseX+","+caseY); // Affichage
		                Coordinates coo = new Coordinates(caseX, caseY);
		                if(caseX != previousCaseX || caseY != previousCaseY){
		                	for(int i=0;i<lineCoo.size();i++){
		                		if(lineCoo.get(i).getX() == coo.getX() && lineCoo.get(i).getY() == coo.getY()){
		                			System.out.println("Cette case a deja ete selectionnee, creation de ligne annulee");
		                			crossBool = true;
		                			setAddLineBool(false);
		                			if(grid[caseX][caseY]== null){
		    		                	subwayPanel.setVisible(false);
		    		                	subwayPanel2.setVisible(false);
		    		                	infoDistrictPanel.setVisible(false);
		    		                	districtPanel.setVisible(true);
		    		                }else{
		    		                	if(grid[caseX][caseY].isStation()){
		    		                		districtPanel.setVisible(false);
		    			                	subwayPanel2.setVisible(true);
		    			                	subwayPanel.setVisible(false);
		    		                	}else{
		    		                		districtPanel.setVisible(false);
		    			                	subwayPanel.setVisible(true);
		    			                	subwayPanel2.setVisible(false);
		    		                	}
		    		                }
		                		}
		                	}
		                	if(crossBool == false){
		                		if(((lineCoo.get(lineCoo.size()-1).getX() == caseX-1 || lineCoo.get(lineCoo.size()-1).getX() == caseX+1) && lineCoo.get(lineCoo.size()-1).getY() == caseY) || ((lineCoo.get(lineCoo.size()-1).getY() == caseY-1 || lineCoo.get(lineCoo.size()-1).getY() == caseY+1) && lineCoo.get(lineCoo.size()-1).getX() == caseX)){
		                			lineCoo.add(coo);
		                			for(int i=0;i<lineCoo.size();i++){
		    		                	System.out.println("ArrayList N°"+i+" : "+lineCoo.get(i).getX()+" et "+lineCoo.get(i).getY());
		    		                }
		                			if(grid[caseX][caseY] != null && grid[caseX][caseY].isStation() == true && (caseX != previousCaseX || caseY != previousCaseY)){
		                				setAddLineBool(false);
		                				System.out.println("Ligne completee avec succes");
		                				money.withDraw((lineCoo.size() -1 )*Const.MAINTENANCE_COST_LINE);
		                				Line lineCompleted = new Line(grid[lineCoo.get(0).getX()][lineCoo.get(0).getY()], grid[lineCoo.get(lineCoo.size()-1).getX()][lineCoo.get(lineCoo.size()-1).getY()], lineCoo.size()-1, true, lineCoo);
		                				districtLinker.linkDistrict(lineCompleted);
		                				allLines.add(lineCompleted);
		                				repaint();
		                			}
		                		}else{
		                			System.out.println("Cette case n'est pas adjacente a la precedente, creation de ligne annulee");
		                			setAddLineBool(false);
		                			if(grid[caseX][caseY]== null){
		    		                	subwayPanel.setVisible(false);
		    		                	subwayPanel2.setVisible(false);
		    		                	infoDistrictPanel.setVisible(false);
		    		                	districtPanel.setVisible(true);
		    		                }else{
		    		                	if(grid[caseX][caseY].isStation()){
		    		                		districtPanel.setVisible(false);
		    			                	subwayPanel2.setVisible(true);
		    			                	subwayPanel.setVisible(false);
		    		                	}else{
		    		                		districtPanel.setVisible(false);
		    			                	subwayPanel.setVisible(true);
		    			                	subwayPanel2.setVisible(false);
		    		                	}
		    		                }
		                		}
		                		
		                	}
		                }
		                
					}
	            }
			}
		});
	}
	public void drawCaseSelected(int caseX, int caseY) {
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
		drawSubwayLine(g, caseWidth);
	}
	
	public void drawSubwayLine(Graphics g, int caseWidth){
		int xPixel, yPixel, xNextPixel, yNextPixel;
		ArrayList<Coordinates> lineCoords;
		Color color;
		for(int i=0; i<allLines.size();i++) {
			lineCoords = allLines.get(i).getVisitedCoordonates();
			color = allLines.get(i).getColor();
			for(int j = 0; j < lineCoords.size()-1; j++) {
				xPixel = (lineCoords.get(j).getX()+1 )*(caseWidth) - (caseWidth/2);
				yPixel = (lineCoords.get(j).getY()+1 )*(caseWidth) - (caseWidth/2);
				xNextPixel = (lineCoords.get(j+1).getX()+1 )*(caseWidth) - (caseWidth/2);
				yNextPixel = (lineCoords.get(j+1).getY()+1 )*(caseWidth) - (caseWidth/2);
					g.setColor(color);
					g.drawLine(xPixel,yPixel, xNextPixel, yNextPixel);
					g.drawLine(xPixel+1,yPixel, xNextPixel+1, yNextPixel);
                    g.drawLine(xPixel-1,yPixel, xNextPixel-1, yNextPixel);
                    g.drawLine(xPixel,yPixel+1, xNextPixel, yNextPixel+1);
                    g.drawLine(xPixel,yPixel-1, xNextPixel, yNextPixel-1);
                    g.drawLine(xPixel+2,yPixel, xNextPixel+2, yNextPixel);
                    g.drawLine(xPixel-2,yPixel, xNextPixel-2, yNextPixel);
                    g.drawLine(xPixel,yPixel+2, xNextPixel, yNextPixel+2);
                    g.drawLine(xPixel,yPixel-2, xNextPixel, yNextPixel-2);
			}
			
		}
		g.setColor(Color.black);
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
		
		if((grid[i][j] != null && grid[i][j].isResidential())) {
			
			if(i-1 > 0 && j-1 > 0) {
				if((grid[i-1][j-1] != null && grid[i-1][j-1].isResidential())) {
					calNbRes++;
				}
				if((grid[i-1][j-1] != null && grid[i-1][j-1].isStation() && grid[i-1][j-1].isResidential())) {
					calNbStation++;
				}
			}
			if ( i+1 < width && j-1 > 0) {
				if((grid[i+1][j-1] != null && grid[i+1][j-1].isResidential())) {
					calNbRes++;
				}
				if((grid[i+1][j-1] != null && grid[i+1][j-1].isStation() && grid[i+1][j-1].isResidential())) {
					calNbStation++;
				}
			}
			if(i-1 > 0 && j+1 < height) {
				if((grid[i-1][j+1] != null && grid[i-1][j+1].isResidential())) {
					calNbRes++;
				}
				if((grid[i-1][j+1] != null && grid[i-1][j+1].isStation() && grid[i-1][j+1].isResidential())) {
					calNbStation++;
				}
			}
			if( i-1 > 0) {
				if((grid[i-1][j] != null && grid[i-1][j].isResidential())) {
					calNbRes++;
				}
				if((grid[i-1][j] != null && grid[i-1][j].isStation() && grid[i-1][j].isResidential())) {
					calNbStation++;
				}	
			}
			if(i+1 < width) {
				if((grid[i+1][j] != null && grid[i+1][j].isResidential())) {
					calNbRes++;
				}
				if((grid[i+1][j] != null && grid[i+1][j].isStation() && grid[i+1][j].isResidential())) {
					calNbStation++;
				}
			}
			if( j-1 > 0) {
				if((grid[i][j-1] != null && grid[i][j-1].isResidential())) {
					calNbRes++;
				}
				if((grid[i][j-1] != null && grid[i][j-1].isStation() && grid[i][j-1].isResidential())) {
					calNbStation++;
				}	
			}
			if(j+1 < height) {
				if((grid[i][j+1] != null && grid[i][j+1].isResidential())) {
					calNbRes++;
				}
				if((grid[i][j+1] != null && grid[i][j+1].isStation() && grid[i][j+1].isResidential())) {
					calNbStation++;
				}
			
			
			}
			if(i+1 < width && j+1 < height) {
				if((grid[i+1][j+1] != null && grid[i+1][j+1].isResidential())) {
					calNbRes++;
				}
				if((grid[i+1][j+1] != null && grid[i+1][j+1].isStation() && grid[i+1][j+1].isResidential())) {
					calNbStation++;
				}
			}
			System.out.println("\n|///////////////////////////////////////////////////////////////////////|");
			System.out.println("|\tgrid["+i+"]["+j+"] got "+calNbRes+" residential neighborhood(s) nearby\t\t|");
			System.out.println("|\t\tgrid["+i+"]["+j+"] got "+calNbStation+" station(s) nearby\t\t\t|");
			System.out.println("|\t\tgrid["+i+"]["+j+"] got "+grid[i][j].getSatisfaction()+" satisfaction\t\t\t\t|");
			
			if(grid[a][b] != null && grid[a][b].isStation()) {
				System.out.println("|\t\tgrid["+i+"]["+j+"] got his own station\t\t\t\t|");
			}
			System.out.println("|///////////////////////////////////////////////////////////////////////|");
		}
				//-----------------------------------------------------------------//
					
	}
			//}
		//}
	public void destroyLines(District clickedDistrict,Grid myGrid){
		int i = 0;
		while(i < myGrid.getAllLines().size()) {
			if (clickedDistrict == myGrid.getAllLines().get(i).getFirstDistrict()){
				districtLinker.removedLine(myGrid.getAllLines().get(i));
				myGrid.getAllLines().remove(i);
			}
			else if (clickedDistrict == myGrid.getAllLines().get(i).getSecondDistrict()){
				districtLinker.removedLine(myGrid.getAllLines().get(i));
				myGrid.getAllLines().remove(i);
			} else {
				i++;
			}
		}
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
	
	public int getPreviousCoordsX() {
		return previousCaseX;
	}
	
	public int getPreviousCoordsY() {
		return previousCaseY;
	}
	
	public boolean getAddLineBool(){
		return addLineBool;
	}
	
	public ArrayList<Line> getAllLines(){
		return allLines;
	}
	
	public void setAddLineBool(boolean newBool){
		addLineBool = newBool;
	}
	
	public boolean getAddLineBoolChangedToTrue(){
		return addLineBoolChangedToTrue;
	}
	
	public void setAddLineBoolChangedToTrue(boolean newBool){
		addLineBoolChangedToTrue = newBool;
	}
	
	public static District[][] getMapTab() {
		return grid;
	}
	public District[][] setMapTab(int lig, int col, District newDistrict){
		grid[lig][col] = newDistrict;
		return grid;
	}
	
	public static int getWidthMap() {
		return width;
	}
	public static int getHeightMap() {
		return height;
	}
}
