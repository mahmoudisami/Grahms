package moteur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import data.*;
import ihm.*;

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
	public GameProgress(Clock clock, Money money, Grid grid,JPanel infoVillePanel) {
		this.clock = clock;
		this.money = money;
		this.grid = grid;
		this.infoVillePanel = infoVillePanel;
		map = grid.getMapTab();
		allLines = grid.getAllLines();
		nbrLine = map.length;
		nbrRow = map[0].length;
	}
	
	public void launchGameProgress () {
		if(clock.getDayPos()==7 && clock.getHour().equals("23")) { //Chaque dimanche a 23h
			historicCalculator();
		}
		if(clock.getHour().equals("01")) {
			initDistrict();
		}
		if(clock.getHour().equals("07") && clock.getDayPos() != 7 && clock.getDayPos() != 6) {
			goWork();
		}
		if(clock.getHour().equals("08") && clock.getDayPos() != 7 && clock.getDayPos() != 6) {
			arriveWork(0);
		}
		if(clock.getHour().equals("09") && clock.getDayPos() != 7 && clock.getDayPos() != 6) {
			arriveWork(1);
		}
		if(clock.getHour().equals("10") && clock.getDayPos() != 7 && clock.getDayPos() != 6) {
			arriveWork(2);
		}
		if(clock.getHour().equals("16") && clock.getDayPos() != 7 && clock.getDayPos() != 6) {
			fin.cumulMoney(map,commercialWorkerByDistrict, serviceWorkerByDistrict);
			goHome();
		}
		if(clock.getDayPos()==7 && clock.getHour().equals("01")) { //Call the function every Monday at 1am
			happinessCalculator();
		}
		if(clock.getDayPos() == 6 && clock.getHour().equals("10")) {
			servicesNeeds();
		}
		 calHappinessTotal();
		
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
						if(!isDoneComm && visitedDistrict.getDistrict().isCommercial()) { // Quartier commercial ou ils vont travailler
							cWorkingDistrict = visitedDistrict.getDistrict();				
							cWorkingDistrict.setPeople(0);
							isDoneComm = true;
						}
						if(!isDoneServ && visitedDistrict.getDistrict().isService()) { // Quartier commercial ou ils vont travailler
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
		if(canWorkComm(dist)) {
			for(index = 0; index < 3; index ++) { //Numero du train, 1 par heure
				if(commercial >= 500) { // 500 étant la limite par train
					commercial -= 500;
					commercialWorker.put(index, 500);
				}else if(commercial > 0) {
					commercialWorker.put(index, commercial);
					commercial = 0;
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
			for(index = 0; index < 3; index ++) {
				if(service >= 500) {
					service -= 500;
					serviceWorker.put(index, 500);
				}else if(service > 0) {
					serviceWorker.put(index, service);
					service = 0;
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
		commercialWorkerByDistrict.put(dist, commercialWorker); // On lie aux quartiers les déplacements
		serviceWorkerByDistrict.put(dist, serviceWorker); 
		remain = commercial + service; // habitants restants
		if(remain > 0) {
			/*
			 * Gerer la satisfaction par rapport au nombre restant d'habitants
			 */
		}
	}
	
	public void servicesNeeds() {
		int i,j;
		int popRemain;
		District dist;
		for(i=0; i<nbrLine;i++) {
			for(j=0; j<nbrRow;j++) {
				dist = map[i][j];
				if(dist != null && dist.isResidential()) {
					popRemain = dist.getActualPeople();
					if(canWorkServ(dist)) {
						ArrayList<AccessibleDistrict> aDist = dist.getAccessibleDistrict();
						for(int index = 0; index<aDist.size(); index ++ ) {
							if(aDist.get(index).getDistrict().isService()) {
								if(popRemain > 500) {
									popRemain -= 500;
									//Augmenter la satisfaction de 1
									map[i][j].setSatisfaction(1);
									System.out.println("Services Need : Satisfaction level on map["+i+"]["+j+"] has increased by 1");
								}
								else if(popRemain > 0) {
									popRemain = 0;
									//Augmenter la satisfaction de 5
									map[i][j].setSatisfaction(5);
									System.out.println("Services Need : Satisfaction level on map["+i+"]["+j+"] has increased by 5");
								}
							}
						}
						if(popRemain != 0) {
							//R�duire la satisfaction de 2
							map[i][j].setSatisfaction(-2);
							System.out.println("Services Need : Satisfaction level on map["+i+"]["+j+"] has decreased by 2");
						}
					}
					else {
						//R�duire satisfaction de 5
						map[i][j].setSatisfaction(-5);
						System.out.println("Services Need : Satisfaction level on map["+i+"]["+j+"] has decreased by 5");
					}
				}
			}
		}
	}
	
	public void calHappinessTotal() {
		int somme = 0;
		int counter = 0;
		int happinessTotal = 0;
		
		if(gotDistrict()) {
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
			}else {
				happinessTotal = 0;
			}
			
			JProgressBar bar_SatisfactionCity = new JProgressBar();
			bar_SatisfactionCity.setBounds(112, 104, 138, 20);
			bar_SatisfactionCity.setValue(happinessTotal);
			bar_SatisfactionCity.setStringPainted(true);
			infoVillePanel.add(bar_SatisfactionCity);
			//happTotal.setHappinessTotal(happinessTotal);
			//System.out.println(happinessTotal);
					
		}
		
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
	public boolean canWorkComm(District dist){
		ArrayList<AccessibleDistrict> aDist = dist.getAccessibleDistrict();
		for(int index = 0; index<aDist.size(); index ++ ) {
			if(aDist.get(index).getDistrict().isCommercial()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canWorkServ(District dist){
		ArrayList<AccessibleDistrict> aDist = dist.getAccessibleDistrict();
		for(int index = 0; index<aDist.size(); index ++ ) {
			if(aDist.get(index).getDistrict().isService()) {
				return true;
			}
		}
		return false;
	}
	
	public void arriveWork(int trainNumber) {
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
	
	public void historicCalculator() {
		tmpMoney = fin.districtCalculatorCost(map);
		money.withDraw(tmpMoney);
		historicText += "------------ "+ clock.getDay() +" "+ clock.getMonthName() +"------------\n";
		historicText += "-" + tmpMoney + " Entretien des quartiers\n";
		
		tmpMoney = fin.calculatorGainCS();
		money.addMoney(tmpMoney);
		historicText += "+" + tmpMoney + " Gain des quartiers commerciaux et services \n";
		
		tmpMoney = fin.calculatorGainResidential(map);
		money.addMoney(tmpMoney);
		historicText += "+" + tmpMoney + " Gain des impots r�sidentiels \n";
		
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
	
	public void neighbourCalculator(int a, int b) {
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
				System.out.println("Satisfaction level on map["+i+"]["+j+"] has decreased by 10");
				//grid.repaint();
			}
			
			if(gotHisOwnStation == true && map[i][j].getUpdateStatusGotStation() == 0) {
				map[i][j].setSatisfaction(10);
				map[i][j].setUpdateStatusGotStationTrue();
				System.out.println("Satisfaction level on map["+i+"]["+j+"] has increased by 10");
				//grid.repaint();
			}
			
			if (calNbStation > 0 && calNbStation <= 9 && map[i][j].getUpdateStatusGotStationNearby() == 0) {
				map[i][j].setSatisfaction(5);
				map[i][j].setUpdateStatusGotStationNearbyTrue();
				System.out.println("Satisfaction level on map["+i+"]["+j+"] has increased by 5");
				//grid.repaint();
			}
			
			if(calNbStation == 0 && map[i][j].getUpdateStatusNoStationNearby() == 0) {
				map[i][j].setSatisfaction(5);
				map[i][j].setUpdateStatusNoStationNearbyTrue();
				System.out.println("Satisfaction level on map["+i+"]["+j+"] has decreased by 5");
			}
			
		}	
	}
	
	
	public String getHistoricText() {
		return historicText;
	}
	
	public void initDistrict() {
		District dist;
		for(int x = 0; x<nbrLine; x++) {
			for(int y = 0; y<nbrRow; y++) {
				dist = map[x][y];
				if(dist != null) {
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

