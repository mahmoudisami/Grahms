package moteur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import data.*;
import ihm.*;

public class GameProgress {
	
	private Clock clock;
	private Money money;
	private FinancialCalculator fin = new FinancialCalculator();
	private int tmpMoney;
	private String historicText ="";
	private Grid grid;
	private District[][] map;
	private int nbrLine, nbrRow;
	private HashMap<Integer, Integer> commercialWorker = new HashMap<>();
	private HashMap<Integer, Integer> serviceWorker = new HashMap<>();
	private HashMap<District, HashMap<Integer, Integer>> commercialWorkerByDistrict = new HashMap<>();
	private HashMap<District, HashMap<Integer, Integer>> serviceWorkerByDistrict = new HashMap<>();
	int width = 18;
	int height = 12;
	
	public GameProgress(Clock clock, Money money, Grid grid) {
		this.clock = clock;
		this.money = money;
		this.grid = grid;
		map = grid.getMapTab();
		nbrLine = map.length;
		nbrRow = map[0].length;
	}
	
	public void launchGameProgress () {
		if(clock.getDayPos()==7 && clock.getHour().equals("23")) { //Chaque dimanche a 23h
			historicCalculator();
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
			goHome();
		}
		if(clock.getDayPos()==1 && clock.getHour().equals("1")) { //Call the function every Monday at 1am
			happinessCalculator();
		}
	}
	
	public void goWork() {
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j] != null && map[i][j].isResidential()) {
					System.out.println("Send to work");
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
						//System.out.println(visitedDistrict.getDistrict());
						if(!isDoneComm && visitedDistrict.getDistrict().isCommercial()) { // Quartier commercial ou ils vont travailler
							cWorkingDistrict = visitedDistrict.getDistrict();				
							System.out.println(cWorkingDistrict.getActualPeople() +"Bien parti du commercial");
							cWorkingDistrict.setPeople(0);
							isDoneComm = true;
						}
						if(!isDoneServ && visitedDistrict.getDistrict().isService()) { // Quartier commercial ou ils vont travailler
							sWorkingDistrict = visitedDistrict.getDistrict();				
							sWorkingDistrict.setPeople(0);
							isDoneServ = true;
							System.out.println("Bien parti du service");
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
					System.out.println("500 Parti du commercial");
					commercialWorker.put(index, 500);
				}else if(commercial > 0) {
					commercialWorker.put(index, commercial);
					System.out.println(commercial + " Parti au commercial");
					commercial = 0;
				}
				else {
					commercialWorker.put(index, 0); 
				}
			}
		}
		if(canWorkServ(dist)) {
			for(index = 0; index < 3; index ++) {
				if(service >= 500) {
					service -= 500;
					serviceWorker.put(index, 500);
				}else if(service > 0) {
					serviceWorker.put(index, service);
					System.out.println(service + " Parti au Service");
					service = 0;
				}
				else {
					serviceWorker.put(index, 0); 
				}
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
	
	public boolean canWorkComm(District dist){
		ArrayList<AccessibleDistrict> aDist = dist.getAccessibleDistrict();
		for(int index = 0; index<aDist.size(); index ++ ) {
			if(aDist.get(index).getDistrict().isCommercial()) {
				return true;
			}
		}
		System.out.println("Les habitants ne peuvent pas travailler dans un commerce");
		return false;
	}
	
	public boolean canWorkServ(District dist){
		ArrayList<AccessibleDistrict> aDist = dist.getAccessibleDistrict();
		for(int index = 0; index<aDist.size(); index ++ ) {
			if(aDist.get(index).getDistrict().isService()) {
				return true;
			}
		}
		System.out.println("Les habitants ne peuvent pas travailler dans un service");
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
					System.out.println(map[i][j]);
					for(int index = 0; index < aDistrict.size(); index ++) {
						visitedDistrict = aDistrict.get(index); 
						//System.out.println(visitedDistrict.getDistrict());
						if(!isDoneComm && visitedDistrict.getDistrict().isCommercial()) { // Quartier commercial ou ils vont travailler
							cWorkingDistrict = visitedDistrict.getDistrict();				
							cWorkingDistrict.addPeople(commercialWorkerByDistrict.get(map[i][j]).get(trainNumber));
							isDoneComm = true;
							System.out.println("Bien arrivé au comm ");
						}
						if(!isDoneServ && visitedDistrict.getDistrict().isService()) { // Quartier commercial ou ils vont travailler
							sWorkingDistrict = visitedDistrict.getDistrict();				
							sWorkingDistrict.addPeople(serviceWorkerByDistrict.get(map[i][j]).get(trainNumber));
							isDoneServ = true;
							System.out.println("Bien arrivé au serv");
						}
					}
				}
			}
		}
	}
	
	public void historicCalculator() {
		tmpMoney = fin.districtCalculatorCost(grid.getMapTab());
		money.withDraw(tmpMoney);
		historicText += "------------ "+ clock.getDay() +" "+ clock.getMonthName() +"------------\n";
		historicText += "-" + tmpMoney + " Entretien des quartiers\n";
		tmpMoney = fin.districtCalculatorGain(grid.getMapTab());
		money.addMoney(tmpMoney);
		historicText += "+" + tmpMoney + " Gain des quartiers \n";
		tmpMoney = fin.stationCalculatorCost(grid.getMapTab());
		money.withDraw(tmpMoney);
		historicText += "-" + tmpMoney + " Entretien des stations \n";
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
			
			if (gotHisOwnStation == false && calNbStation == 0) {
				map[i][j].setSatisfaction(-10); //if the district dont have his own station and no statio nearby, the satisfaction will decrease by 10
			}
			
			if(gotHisOwnStation == true) {
				map[i][j].setSatisfaction(10);
			}
			
			if (gotHisOwnStation == false && calNbStation > 0 && calNbStation <= 4) {
				map[i][j].setSatisfaction(5);
			}
			
			if (gotHisOwnStation == false && calNbStation >= 5 && calNbStation <= 9) {
				map[i][j].setSatisfaction(5);
			}
		}	
	}
	
	
	public String getHistoricText() {
		return historicText;
	}
}

