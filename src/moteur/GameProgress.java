package moteur;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import data.*;
import ihm.*;
/*
 * 
 * Main class that allows the running of the game
 * 
 */
public class GameProgress {
	
	private Clock clock;
	private Money money;
	private HappinessTotal happTotal;
	private FinancialCalculator fin = new FinancialCalculator();
	private int tmpMoney;
	private String historicText ="";
	private Grid grid;
	private District[][] map;
	private ArrayList<Line> allLines;
	private DistrictLinker dlink = new DistrictLinker();
	private int nbrLine, nbrRow;
	private HashMap<Integer, Integer> commercialWorker = new HashMap<>();
	private HashMap<Integer, Integer> serviceWorker = new HashMap<>();
	private HashMap<District, HashMap<Integer, Integer>> commercialWorkerByDistrict = new HashMap<>();
	private HashMap<District, HashMap<Integer, Integer>> serviceWorkerByDistrict = new HashMap<>();
	int width = 18;
	int height = 12;
	private int tmpHapp;
	private JPanel infoVillePanel;
	private JProgressBar bar_SatisfactionCity = new JProgressBar();
	private JLabel lblCityPop = new JLabel();
	private int actualDistanceComm;
	private int actualDistanceServ;
	private PopulationTotal popTotal = new PopulationTotal();
	
	public GameProgress(Clock clock, Money money, Grid grid,JPanel infoVillePanel) {
		this.clock = clock;
		this.money = money;
		this.grid = grid;
		this.infoVillePanel = infoVillePanel;
		map = grid.getMapTab();
		allLines = grid.getAllLines();
		nbrLine = map.length;
		nbrRow = map[0].length;
		
		bar_SatisfactionCity.setBounds(112, 104, 138, 20);
	}
	
	public void launchGameProgress () {  // Performs daily actions
		if(clock.getDayPos()==7 && clock.getHour().equals("23")) { 
			historicCalculator();
		}
		if(clock.getHour().equals("01")) {
			initDistrict();
		}
		if(clock.getDayPos() != 7 && clock.getDayPos() != 6) {
			if(clock.getHour().equals("07")) {
				goWork();
			}
			if(clock.getHour().equals("08") ) {
				arriveWork(0);
			}
			if(clock.getHour().equals("09") ) {
				arriveWork(1);
			}
			if(clock.getHour().equals("10") ) {
				arriveWork(2);
			}
			if(clock.getHour().equals("16") ) {
				fin.cumulMoney(map,commercialWorkerByDistrict, serviceWorkerByDistrict);
				goHome();
			}
		}
		if(clock.getDayPos()==7 && clock.getHour().equals("01")) { //Call the function every Monday at 1am
			happinessCalculator();
		}
		if(clock.getDayPos() == 6 && clock.getHour().equals("10")) {
			servicesNeeds();
			popEvolutionCalculator();
		}
		 calHappinessTotal();
		 calPopTotal();
		 grid.repaint();
		
	}
	
	public void goWork() {
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j] != null && map[i][j].isResidential()) {
					sendToWork(map[i][j]);
				}
			}
		}
	}
	
	public void goHome() {
		boolean isDoneComm = false;
		boolean isDoneServ = false;
		District cWorkingDistrict, sWorkingDistrict;
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j] != null && map[i][j].isResidential()) {
					ArrayList<AccessibleDistrict> aDistrict = map[i][j].getAccessibleDistrict();
					AccessibleDistrict visitedDistrict;
					for(int index = 0; index < aDistrict.size(); index ++) {
						visitedDistrict = aDistrict.get(index); 
						if(!isDoneComm && visitedDistrict.getDistrict().isCommercial()) {
							cWorkingDistrict = visitedDistrict.getDistrict();				
							cWorkingDistrict.setPeople(0);
							isDoneComm = true;
						}
						if(!isDoneServ && visitedDistrict.getDistrict().isService()) {
							sWorkingDistrict = visitedDistrict.getDistrict();				
							sWorkingDistrict.setPeople(0);
							isDoneServ = true;
						}
					}
				}
			}
		}
	}
	
	public void sendToWork(District dist) {
		int commercial = (int) (dist.getActualPeople()*0.66);
		int service = dist.getActualPeople()-commercial;
		int remain;
		int index;
		int trainCapacity = Const.CAPACITY+ 150*dist.getSize();
		Collections.sort(dist.getAccessibleDistrict());
		if(canWorkComm(dist)) {
			if(actualDistanceComm == 2) {
				dist.setSatisfaction(-1);
			}
			else if(actualDistanceComm == 3) {
				dist.setSatisfaction(-2);
			}
			else if(actualDistanceComm >= 4){
				dist.setSatisfaction(-3);
			}
			for(index = 0; index < 3; index ++) { //Train number, one per hour
				if(commercial >= trainCapacity) { 
					commercial -= trainCapacity;
					commercialWorker.put(index, trainCapacity);
					dist.setSatisfaction(1);
				}else if(commercial > 0) {
					commercialWorker.put(index, commercial);
					commercial = 0;
					dist.setSatisfaction(2);
				}
				else {
					commercialWorker.put(index, 0); 
				}
			}
		}
		else {
			for(index = 0; index < 3; index ++) {
				commercialWorker.put(index, 0);
			}
		}
		if(canWorkServ(dist)) {
			if(actualDistanceServ == 2) {
				dist.setSatisfaction(-1);
			}
			else if(actualDistanceServ == 3) {
				dist.setSatisfaction(-2);
			}
			else if(actualDistanceServ >= 4){
				dist.setSatisfaction(-3);
			}
			for(index = 0; index < 3; index ++) {
				if(service >= trainCapacity) {
					service -= trainCapacity;
					serviceWorker.put(index, trainCapacity);
					dist.setSatisfaction(1);
				}else if(service > 0) {
					serviceWorker.put(index, service);
					service = 0;
					dist.setSatisfaction(2);
				}
				else {
					serviceWorker.put(index, 0); 
				}
			}
		}
		else {
			for(index = 0; index < 3; index ++) {
				serviceWorker.put(index, 0);
			}
		}
		commercialWorkerByDistrict.put(dist, commercialWorker); // linked districts and deplacements
		serviceWorkerByDistrict.put(dist, serviceWorker); 
		remain = commercial + service; // habitants restants
		if(remain > 0) {
			int lessSatisfaction = 1+(remain/10);
			dist.setSatisfaction(-lessSatisfaction);
		}
	}
	
	public void servicesNeeds() { // the inhabitants go to the service districts for their needs
		int i,j;
		int popRemain;
		District dist;
		for(i=0; i<nbrLine;i++) {
			for(j=0; j<nbrRow;j++) {
				dist = map[i][j];
				if(dist != null && dist.isResidential()) {
					int trainCapacity = 100 + Const.CAPACITY*dist.getSize();
					popRemain = dist.getActualPeople();
					if(canWorkServ(dist)) {
						if(actualDistanceServ == 2) {
							dist.setSatisfaction(-1);
						}
						else if(actualDistanceServ == 3) {
							dist.setSatisfaction(-2);
						}
						else if(actualDistanceServ >= 4){
							dist.setSatisfaction(-3);
						}
						ArrayList<AccessibleDistrict> aDist = dist.getAccessibleDistrict();
						for(int index = 0; index<aDist.size(); index ++ ) {
							if(aDist.get(index).getDistrict().isService()) {
								if(popRemain > trainCapacity) {
									popRemain -= trainCapacity;
									map[i][j].setSatisfaction(1);
									System.out.println("Services Need : Satisfaction level on map["+i+"]["+j+"] has increased by 1");
								}
								else if(popRemain > 0) {
									popRemain = 0;
									map[i][j].setSatisfaction(2);
									System.out.println("Services Need : Satisfaction level on map["+i+"]["+j+"] has increased by 3");
								}
							}
						}
						if(popRemain != 0) {
							map[i][j].setSatisfaction(-2);
							System.out.println("Services Need : Satisfaction level on map["+i+"]["+j+"] has decreased by 2");
						}
					}
					else {
						map[i][j].setSatisfaction(-5);
						System.out.println("Services Need : Satisfaction level on map["+i+"]["+j+"] has decreased by 5");
					}
				}
			}
		}
	}
	public void calPopTotal() {
		int total = 0;
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
					if(map[i][j] != null && map[i][j].isResidential()) {
						total = map[i][j].getActualPeople() + total;
						
					}
			}
		}
		popTotal.setPopulation(total);
		//lblCityPop = new JLabel(""+popTotal.getPopulationTotal());
		//Font fontInfo = new Font("Yu Gothic UI Semibold", Font.PLAIN, 15);
		//lblCityPop.setFont(fontInfo);
		lblCityPop.setText(""+popTotal.getPopulationTotal());
		lblCityPop.setBounds(150, 30, 100, 20);
		infoVillePanel.add(lblCityPop);
		grid.repaint();
		
	}
	public void calHappinessTotal() {
		int somme = 0;
		int counter = 0;
		int happinessTotal = 0;
		
		//if(gotDistrict()) {
			for(int i=0; i<nbrLine; i++) {
				for(int j=0; j<nbrRow; j++) {
						if(map[i][j] != null && map[i][j].isResidential()) {
						counter++;
						somme = map[i][j].getSatisfaction() + somme;
					}
					
				}
			}
			if(counter != 0) {
				happinessTotal = somme/counter;
				HappinessTotal.setHappinessTotal(happinessTotal);	
			}else {
				happinessTotal = 0;
			}
			
			
			bar_SatisfactionCity.setValue(happinessTotal);
			bar_SatisfactionCity.setStringPainted(true);
			infoVillePanel.add(bar_SatisfactionCity);
			//happTotal.setHappinessTotal(happinessTotal);
			//System.out.println(happinessTotal);
					
		//}
			grid.repaint();
		
	}

	public boolean gotDistrict() {
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
					if(map[i][j] != null && map[i][j].isResidential()) {
						return true;
					}
			}
		}
		return false;
	}
	
	public boolean canWorkComm(District dist){ //Is a Commercial District accessible?
		ArrayList<AccessibleDistrict> aDist = dist.getAccessibleDistrict();
		for(int index = 0; index<aDist.size(); index ++ ) {
			if(aDist.get(index).getDistrict().isCommercial()) {
				actualDistanceComm = aDist.get(index).getDistance();
				return true;
			}
		}
		return false;
	}
	
	public boolean canWorkServ(District dist){ //Is a Services District accessible?
		ArrayList<AccessibleDistrict> aDist = dist.getAccessibleDistrict();
		for(int index = 0; index<aDist.size(); index ++ ) {
			if(aDist.get(index).getDistrict().isService()) {
				actualDistanceServ = aDist.get(index).getDistance();
				return true;
			}
		}
		return false;
	}
	
	public void arriveWork(int trainNumber) { // Time to work ! Arrive with the train
		boolean isDoneComm = false;
		boolean isDoneServ = false;
		District cWorkingDistrict;
		District sWorkingDistrict;
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j] != null && map[i][j].isResidential()) {
					ArrayList<AccessibleDistrict> aDistrict = map[i][j].getAccessibleDistrict();
					AccessibleDistrict visitedDistrict;
					for(int index = 0; index < aDistrict.size(); index ++) {
						visitedDistrict = aDistrict.get(index); 
						if(!isDoneComm && visitedDistrict.getDistrict().isCommercial()) { // Quartier commercial ou ils vont travailler
							cWorkingDistrict = visitedDistrict.getDistrict();				
							cWorkingDistrict.addPeople(commercialWorkerByDistrict.get(map[i][j]).get(trainNumber));
							isDoneComm = true;
						}
						if(!isDoneServ && visitedDistrict.getDistrict().isService()) { // Quartier commercial ou ils vont travailler
							sWorkingDistrict = visitedDistrict.getDistrict();				
							sWorkingDistrict.addPeople(serviceWorkerByDistrict.get(map[i][j]).get(trainNumber));
							isDoneServ = true;
						}
					}
				}
			}
		}
	}
	
	public void historicCalculator() { // write the history and manage money
		tmpMoney = fin.districtCalculatorCost(map);
		money.withDraw(tmpMoney);
		historicText += "------------ "+ clock.getDay() +" "+ clock.getMonthName() +"------------\n";
		historicText += "-" + tmpMoney + " Entretien des quartiers\n";
		
		tmpMoney = fin.calculatorGainCS();
		money.addMoney(tmpMoney);
		historicText += "+" + tmpMoney + " Gain des quartiers commerciaux et services \n";
		
		tmpMoney = fin.calculatorGainResidential(map);
		money.addMoney(tmpMoney);
		historicText += "+" + tmpMoney + " Gain des impots résidentiels \n";
		
		tmpMoney = fin.stationCalculatorCost(map);
		money.withDraw(tmpMoney);
		historicText += "-" + tmpMoney + " Entretien des stations \n";
		
		tmpMoney = fin.lineCalculatorCost(allLines);
		money.withDraw(tmpMoney);
		historicText += "-" + tmpMoney + " Entretien des lignes \n";
	}
	
	public void happinessCalculator() {
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j] != null && map[i][j].isResidential()) {
					neighbourCalculator(i,j);
				}
			}
		}
	}
	
	public void neighbourCalculator(int a, int b) { // Is there a neighbour with a station to walk?
		int calNbRes = 0;
		int calNbServ = 0;
		int calNbShop = 0;
		int calNbStation = 0;
		boolean gotHisOwnStation = false;
		int i = a;
		int j = b;
		
		if((map[i][j] != null && map[i][j].isResidential())) {
			
			if(i-1 > 0 && j-1 > 0) {
				if((map[i-1][j-1] != null && map[i-1][j-1].isResidential())) {
					calNbRes++;
				}
				if((map[i-1][j-1] != null && map[i-1][j-1].isStation() && map[i-1][j-1].isResidential())) {
					calNbStation++;
				}
			}
			if ( i+1 < width && j-1 > 0) {
				if((map[i+1][j-1] != null && map[i+1][j-1].isResidential())) {
					calNbRes++;
				}
				if((map[i+1][j-1] != null && map[i+1][j-1].isStation() && map[i+1][j-1].isResidential())) {
					calNbStation++;
				}
			}
			if(i-1 > 0 && j+1 < height) {
				if((map[i-1][j+1] != null && map[i-1][j+1].isResidential())) {
					calNbRes++;
				}
				if((map[i-1][j+1] != null && map[i-1][j+1].isStation() && map[i-1][j+1].isResidential())) {
					calNbStation++;
				}
			}
			if( i-1 > 0) {
				if((map[i-1][j] != null && map[i-1][j].isResidential())) {
					calNbRes++;
				}
				if((map[i-1][j] != null && map[i-1][j].isStation() && map[i-1][j].isResidential())) {
					calNbStation++;
				}	
			}
			if(i+1 < width) {
				if((map[i+1][j] != null && map[i+1][j].isResidential())) {
					calNbRes++;
				}
				if((map[i+1][j] != null && map[i+1][j].isStation() && map[i+1][j].isResidential())) {
					calNbStation++;
				}
			}
			if( j-1 > 0) {
				if((map[i][j-1] != null && map[i][j-1].isResidential())) {
					calNbRes++;
				}
				if((map[i][j-1] != null && map[i][j-1].isStation() && map[i][j-1].isResidential())) {
					calNbStation++;
				}	
			}
			if(j+1 < height) {
				if((map[i][j+1] != null && map[i][j+1].isResidential())) {
					calNbRes++;
				}
				if((map[i][j+1] != null && map[i][j+1].isStation() && map[i][j+1].isResidential())) {
					calNbStation++;
				}
			
			
			}
			if(i+1 < width && j+1 < height) {
				if((map[i+1][j+1] != null && map[i+1][j+1].isResidential())) {
					calNbRes++;
				}
				if((map[i+1][j+1] != null && map[i+1][j+1].isStation() && map[i+1][j+1].isResidential())) {
					calNbStation++;
				}
			}
				
			if(map[i][j] != null && map[i][j].isStation()) {
				gotHisOwnStation = true;
			}
			
			if (gotHisOwnStation == false && calNbStation == 0 && map[i][j].getUpdateStatusNoStation() == 0) {
				map[i][j].setSatisfaction(-10); //if the district dont have his own station and no station nearby, the satisfaction will decrease by 10
				map[i][j].setUpdateStatusNoStationTrue();
				//map[i][j].setUpdateStatusGotStationFalse();
				System.out.println("Satisfaction level on map["+i+"]["+j+"] has decreased by 10");
				//grid.repaint();
			}
			
			if(gotHisOwnStation == true && map[i][j].getUpdateStatusGotStation() == 0) {
				map[i][j].setSatisfaction(10);
				map[i][j].setUpdateStatusGotStationTrue();
				//map[i][j].setUpdateStatusNoStationFalse(); //set var to 0
				System.out.println("Satisfaction level on map["+i+"]["+j+"] has increased by 10");
				//grid.repaint();
			}
			
			if (calNbStation > 0 && calNbStation <= 9 && map[i][j].getUpdateStatusGotStationNearby() == 0) {
				map[i][j].setSatisfaction(5);
				map[i][j].setUpdateStatusGotStationNearbyTrue();
				//map[i][j].setUpdateStatusNoStationNearbyFalse();
				System.out.println("Satisfaction level on map["+i+"]["+j+"] has increased by 5");
				//grid.repaint();
			}
			
			if(calNbStation == 0 && map[i][j].getUpdateStatusNoStationNearby() == 0) {
				map[i][j].setSatisfaction(5);
				map[i][j].setUpdateStatusNoStationNearbyTrue();
				//map[i][j].setUpdateStatusGotStationNearbyFalse();
				System.out.println("Satisfaction level on map["+i+"]["+j+"] has decreased by 5");
			}
			
		}	
	}
	
	public void popEvolutionCalculator() { //  Increase or Decrease the population
		int tmp = 0;
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j] != null && map[i][j].isResidential()) {
					if(map[i][j].getSatisfaction() < 15 ) {
						tmp = -(10*map[i][j].getActualPeople())/100; //Decrease 10%
						System.out.println("PopEvol : map["+i+"]["+j+"] has decreased 10%");
						
					}
					if(map[i][j].getSatisfaction() >= 15 && map[i][j].getSatisfaction() < 25 ) {
						tmp = -(7*map[i][j].getActualPeople())/100; //Decrease 15%
						System.out.println("PopEvol : map["+i+"]["+j+"] has decreased 7%");
						
					}
					if(map[i][j].getSatisfaction() >= 25 && map[i][j].getSatisfaction() < 50 ) {
						tmp = -(5*map[i][j].getActualPeople())/100; //Decrease 10%
						System.out.println("PopEvol : map["+i+"]["+j+"] has decreased 5%");
						
					}
					if(map[i][j].getSatisfaction() >= 50 && map[i][j].getSatisfaction() < 70 ) {
						tmp = (5*map[i][j].getActualPeople())/100; //Increase 10%
						System.out.println("PopEvol : map["+i+"]["+j+"] has increased 5%");
						
					}
					if(map[i][j].getSatisfaction() >= 75 && map[i][j].getSatisfaction() < 95 ) {
						tmp = (7*map[i][j].getActualPeople())/100; // Increase 15%
						System.out.println("PopEvol : map["+i+"]["+j+"] has increased 7%");
					}
					if(map[i][j].getSatisfaction() >= 95) {
						tmp = 10*map[i][j].getActualPeople()/100; //Increase 20%
						System.out.println("PopEvol : map["+i+"]["+j+"] has increased 10%");
					}
			
					
					map[i][j].addPeople(tmp);
					grid.repaint();
				}
			}
		}
	}
	
	public String getHistoricText() {
		return historicText;
	}
	
	public void initDistrict() { // Add neighbour district to accessible districts
		District dist;
		for(int x = 0; x<nbrLine; x++) {
			for(int y = 0; y<nbrRow; y++) {
				dist = map[x][y];
				if(dist != null && !map[x][y].isStation()) {
					if(x!=0 && map[x-1][y] != null) {
						if(map[x-1][y].isStation()) {
							dist.addAccessibleDistrict(map[x-1][y], 1,false);
							dlink.refreshAD(map[x-1][y], dist, 1,false);
						}
					}
					if(y!=0  && map[x][y-1] != null) {
						if(map[x][y-1].isStation()) {
							dist.addAccessibleDistrict(map[x][y-1], 1,false);
							dlink.refreshAD(map[x][y-1],dist,  1,false);
						}
					}
					if(x != 0 && y != 0  && map[x-1][y-1] != null) {
						if(map[x-1][y-1].isStation()) {
							dist.addAccessibleDistrict(map[x-1][y-1], 1,false);
							dlink.refreshAD(map[x-1][y-1],dist,  1,false);
						}
					}
					if(x != nbrLine-1  && map[x+1][y] != null) {
						if(map[x+1][y].isStation()) {
							dist.addAccessibleDistrict(map[x+1][y], 1,false);
							dlink.refreshAD(map[x+1][y],dist,  1,false);
						}
					}
					if(y != nbrRow-1  && map[x][y+1] != null) {
						if(map[x][y+1].isStation()) {
							dist.addAccessibleDistrict(map[x][y+1], 1,false);
							dlink.refreshAD(map[x][y+1], dist, 1,false);
						}
					}
					if(x != nbrLine-1 && y != nbrRow-1  && map[x+1][y+1] != null) {
						if(map[x+1][y+1].isStation()) {
							dist.addAccessibleDistrict(map[x+1][y+1], 1,false);
							dlink.refreshAD( map[x+1][y+1],dist,  1,false);
						}
					}
					if(x != 0 && y != nbrRow-1  && map[x-1][y+1] != null) {
						if(map[x-1][y+1].isStation()) {
							dist.addAccessibleDistrict(map[x-1][y+1], 1,false);
							dlink.refreshAD(map[x-1][y+1],dist,  1,false);
						}
					}
					if(x != nbrLine-1 && y != 0  && map[x+1][y-1] != null) {
						if(map[x+1][y-1].isStation()) {
							dist.addAccessibleDistrict(map[x+1][y-1], 1,false);
							dlink.refreshAD(map[x+1][y-1],dist,  1,false);
						}
					}
				}
			}
		}
	}
}

